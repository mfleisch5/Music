package music.model;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by michaelfleischmann on 10/15/16.
 */
public final class MusicEditorModel implements IMusicEditorModel {
  private final ConcurrentSkipListMap<INote, IMeasure> piece;
  private int numrows;
  private final List<IRepeat> repeats;

  /**
   * Constructs a {@code MusicEditorModel} object, the main model of the editor.
   */
  public MusicEditorModel() {
    this.repeats = new ArrayList<>();
    this.piece = new ConcurrentSkipListMap<>();
    this.numrows = 0;
  }

  /**
   * Part of the musicstate, gets the top portion of the music state (just the notes).
   *
   * @return the notestate
   */
  private String getNoteState() {
    String result;
    if (numrows < 10) {
      result = "";
    } else {
      result = String.format("%1$" + (String.valueOf(numrows).length() - 1) + "s", " ");
    }
    for (IMeasure measure : piece.values()) {
      result += measure.getNote().toString();

    }
    return result;
  }

  /**
   * Part of the musicstate, gets the bottom portion of the music state (just the measure beats).
   *
   * @return the beatstate
   */
  private String getBeatState(int r) {
    String result = String.format("%1$" + String.valueOf(numrows).length() + "s", r);
    for (IMeasure measure : piece.values()) {
      result += measure.getBeats().get(r).toString();
    }
    return result;
  }

  @Override
  public String getMusicState() {
    if (piece.size() == 0) {
      return "";
    }
    String result = getNoteState();
    for (int i = 0; i < numrows; i++) {
      result += "\n" +
              getBeatState(i);
    }
    return result;
  }

  @Override
  public ConcurrentSkipListMap<INote, IMeasure> getPiece() {
    return piece;
  }

  @Override
  public int getNumRows() {
    return numrows;
  }

  @Override
  public List<IRepeat> getRepeats() {
    Collections.sort(repeats, (IRepeat o1, IRepeat o2) -> {
      return o1.getLocation() - o2.getLocation();
    });
    return repeats;
  }

  /**
   * Keeps re-establishing the number of rows that the model has based on the last sustain
   * in the model.
   */
  private void findNumRows() {
    int rows = 0;
    if (piece.size() == 0) {
      numrows = 0;
      return;
    }
    for (IMeasure measure : piece.values()) {
      int rowMax = Math.max(measure.getBeats().lastIndexOf(new Beat(BeatType.Sustain)),
              measure.getBeats().lastIndexOf(new Beat(BeatType.Head)));
      rows = Math.max(rowMax, rows);
    }
    numrows = rows + 1;
  }

  /**
   * Personally, my favorite mutation. It readjusts the model after each method by making sure
   * The measures are equally sized, that the numrows is the number of the last sustain,
   * and that there are no empty measures at the lowest or highest note.
   */
  private void readjust() {
    findNumRows();
    for (IMeasure measure : piece.values()) {
      while (measure.getBeats().size() < numrows) {
        measure.getBeats().add(new Beat(BeatType.Rest));
      }
      while (measure.getBeats().size() > numrows) {
        measure.getBeats().remove(measure.getBeats().size() - 1);
      }
    }
    if (piece.size() == 0) {
      return;
    }
    IMeasure last = piece.lastEntry().getValue();
    IMeasure first = piece.firstEntry().getValue();
    while (!last.getBeats().contains(new Beat(BeatType.Head))) {
      piece.pollLastEntry();
    }
    while (!first.getBeats().contains(new Beat(BeatType.Head))) {
      piece.pollFirstEntry();
    }
  }

  /**
   * Finds whether adding the repeat will allow for a valid list of repeats.
   *
   * @param repeat the repeat that will be added to the list
   * @return the truth on whether adding the repeat is valid
   */
  private boolean validRepeats(IRepeat repeat) {
    boolean result = true;
    List<IRepeat> repeats = new ArrayList<>();
    repeats.addAll(this.repeats);
    repeats.add(repeat);
    Collections.sort(repeats, (Comparator.comparing(IRepeat::getLocation)));
    for (int i = 0; i < (repeats.size() - 1); i++) {
      if (repeats.get(i).getType() == RepeatType.Start) {
        result = result && ((repeats.get(i + 1).getType() == RepeatType.Play)
                && repeats.get(i).getLocation() <= numrows && repeats.get(i).getLocation() >= 0);
      }

    }
    return result;
  }

  @Override
  public void addNote(INote note, int duration, int time, int instrument, int volume) {
    if (duration < 0 || time < 0 || note == null) {
      throw new IllegalArgumentException("Invalid Note Addition");
    }
    int sustain = time + duration;
    if (piece.size() == 0) {
      piece.put(note, (new Measure(note)));
    }
    if (note.toInt() < piece.firstKey().toInt()) {
      int firstKey = piece.firstKey().toInt();
      for (int i = note.toInt(); i < firstKey; i++) {
        piece.put(Note.intToNote(i), Measure.newOffMeasure(Note.intToNote(i), numrows));
      }
    }
    if (note.toInt() > piece.lastKey().toInt()) {
      int lastKey = piece.lastKey().toInt();
      for (int i = lastKey + 1; i <= note.toInt(); i++) {
        piece.put(Note.intToNote(i), (Measure.newOffMeasure(Note.intToNote(i), numrows)));
      }
    }
    //System.out.println(getMusicState());
    IMeasure measure = piece.get(note);
    if (numrows < time) {
      for (int i = numrows; i < time; i++) {
        measure.getBeats().add(new Beat(BeatType.Rest));
      }
    } else {
      for (int i = time; i < sustain; i++) {
        try {
          measure.getBeats().remove(time);
        } catch (IndexOutOfBoundsException e) {
          break;
        }
      }
    }
    if (duration > 0) {
      measure.getBeats().add(time, new Beat(BeatType.Head, instrument, volume));
    }
    for (int i = time + 1; i < sustain; i++) {
      measure.getBeats().add(i, new Beat(BeatType.Sustain, instrument, volume));
    }
    readjust();
  }

  @Override
  public void addNote(INote note, int duration, int time) {
    addNote(note, duration, time, 0, 0);
  }

  @Override
  public void addRepeat(RepeatType type, int location) {
    IRepeat r = Repeat.newRepeat(type, location);
    if (!validRepeats(r)) {
      throw new IllegalArgumentException("Invalid Repeats");
    }
    repeats.add(r);
  }

  @Override
  public void edit(INote note, int beat, int editlength) {
    IMeasure measure = piece.get(note);
    if (editlength > 0) {
      if (measure.unavailable(editlength, measure.getBeatEnd(beat) + 1)) {
        throw new IllegalArgumentException("Unavailable Space");
      }
      while (editlength > 0) {
        measure.getBeats().add(measure.findBeat(beat) + 1, new Beat(BeatType.Sustain,
                measure.getBeats().get(measure.findBeat(beat)).getInstrument(),
                measure.getBeats().get(measure.findBeat(beat)).getVolume()));
        try {
          measure.getBeats().remove(measure.getBeatEnd(beat) + 1);
        } catch (IndexOutOfBoundsException e) {
          //nothing here, because the point of this catch is to stop trying to
          //remove the Volumes, since there are none left to remove. After it is caught
          //the method moves on.
        }
        editlength--;
        readjust();
      }
    } else if (editlength < 0) {
      if (Math.abs(editlength) > measure.getBeatEnd(beat) - measure.findBeat(beat)) {
        throw new IllegalArgumentException("Beat already removed");
      }
      while (editlength < 0) {
        measure.getBeats().remove(measure.getBeatEnd(beat));
        measure.getBeats().add(measure.getBeatEnd(beat) + 1, new Beat(BeatType.Rest));
        editlength++;
        readjust();
      }
    } else {
      return;
    }
  }

  @Override
  public void remove(INote note, int beat) {
    IMeasure measure = piece.get(note);
    int actualbeat = measure.findBeat(beat);
    try {
      edit(note, beat, actualbeat - measure.getBeatEnd(beat));
    } catch (IllegalArgumentException e) {

    }
    measure.getBeats().remove(actualbeat);
    measure.getBeats().add(actualbeat, new Beat(BeatType.Rest));
    readjust();
  }

  @Override
  public void move(INote note, int beat, Direction direction, int editlength) {
    if (editlength < 0 || direction == null) {
      throw new IllegalArgumentException("Invalid Move Parameters");
    }
    IMeasure measure = piece.get(note);
    switch (direction) {
      case Right:
        if (measure.unavailable(editlength, measure.getBeatEnd(beat) + 1)) {
          throw new IllegalArgumentException("Inavailable Beat");
        }
        for (int i = 0; i < editlength; i++) {
          measure.getBeats().add(measure.findBeat(beat), new Beat(BeatType.Rest));
          try {
            measure.getBeats().remove(measure.getBeatEnd(beat) + 1);
          } catch (IndexOutOfBoundsException e) {
            //nothing here, because the point of this catch is to stop trying to
            //remove the Volumes, since there are none left to remove. After it is caught
            //the method moves on.
          }

        }
        readjust();
        break;
      case Left:
        if (measure.findBeat(beat) - editlength < 0 ||
                measure.unavailable(editlength, measure.findBeat(beat) - editlength)) {
          throw new IllegalArgumentException("Inavailable Beat");
        }
        for (int i = 0; i < editlength; i++) {
          measure.getBeats().remove(measure.findBeat(beat) - 1);
          measure.getBeats().add(measure.getBeatEnd(beat) + 1, new Beat(BeatType.Rest));
        }
        readjust();
        break;
      case Down:
        INote leftnote = Note.intToNote(note.toInt() - editlength);
        addNote(leftnote, measure.getBeatEnd(beat) - measure.findBeat(beat) + 1,
                measure.findBeat(beat), measure.getBeats().get(measure.findBeat(beat)).
                        getInstrument(),
                measure.getBeats().get(measure.findBeat(beat)).getVolume());
        remove(note, beat);
        break;
      case Up:
        INote rightnote = Note.intToNote(note.toInt() + editlength);
        addNote(rightnote, measure.getBeatEnd(beat) - measure.findBeat(beat) + 1,
                measure.findBeat(beat), measure.getBeats().get(measure.findBeat(beat)).
                        getInstrument(),
                measure.getBeats().get(measure.findBeat(beat)).getVolume());
        remove(note, beat);
        break;
      default:
        throw new IllegalArgumentException("Shouldn't get here");

    }
  }
}



