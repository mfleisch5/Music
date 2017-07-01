package cs3500.music.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by michaelfleischmann on 10/15/16.
 */
public class Measure implements IMeasure {
  private final INote note;
  private List<IBeat> beats;

  /**
   * Constructs a {@code Measure} object. It is essentially the list of beats for a note.
   *
   * @param note the note of the measure
   */
  public Measure(INote note) {
    this.note = note;
    this.beats = new ArrayList<>();
  }

  /**
   * Constructs a {@Code measure} object, which is just a note and its list of beats.
   *
   * @param note  the note that defines the measure
   * @param beats the different beats and their beattypes that are apart of the measure
   */
  public Measure(INote note, ArrayList<IBeat> beats) {
    this.note = note;
    this.beats = beats;
  }

  @Override
  public INote getNote() {
    return note;
  }

  @Override
  public List<IBeat> getBeats() {
    return beats;
  }

  @Override
  public int numBeats() {
    int result = 0;
    for (IBeat b : beats) {
      if (b.getType().equals(BeatType.Head)) {
        result++;
      }
    }
    return result;
  }

  @Override
  public int findBeat(int beatnumber) {
    ArrayList<Integer> beatlist = new ArrayList<Integer>();
    for (int i = 0; i < beats.size(); i++) {
      if (beats.get(i).getType().equals(BeatType.Head)) {
        beatlist.add(i);
      }
    }
    if (beatnumber > beatlist.size() || beatnumber < 1) {
      throw new IllegalArgumentException("Invalid Beat Number");
    }
    return beatlist.get(beatnumber - 1);
  }

  @Override
  public int getBeatEnd(int beat) {
    int actualbeat = findBeat(beat) + 1;
    while (actualbeat != beats.size() && beats.get(actualbeat).getType().equals(BeatType.Sustain)) {
      actualbeat = actualbeat + 1;
    }
    return actualbeat - 1;
  }

  @Override
  public HashMap<Integer, Integer> hashBeat() {
    HashMap<Integer, Integer> result = new HashMap<>();
    int value = 0;
    for (int i = 0; i < beats.size(); i++) {
      if (beats.get(i).getType().equals(BeatType.Head)) {
        value += 1;
        result.put(i, value);
      }
      if (beats.get(i).getType().equals(BeatType.Sustain)) {
        result.put(i, value);
      }
    }
    return result;
  }

  @Override
  public void changeBeatInstrument(int beatnumber, int instrument) {
    for (int i = findBeat(beatnumber); i <= getBeatEnd(beatnumber); i++) {
      beats.get(i).setInstrument(beats.get(i).getInstrument() + instrument);
    }
  }

  @Override
  public boolean unavailable(int duration, int time) {
    int end;
    boolean availability = false;
    if (duration + time >= beats.size()) {
      end = beats.size();
    } else {
      end = duration + time;
      for (int i = time; i < end; i++) {
        availability = availability || hashBeat().containsKey(i);
      }
    }
    return availability;
  }

  /**
   * Static method that creates a new measure with as many BeatType.Rest as there are number of
   * rows.
   *
   * @param note    the note of the measure
   * @param numrows the number of rows to be added
   * @return a new Measure with the note and specified number of rows
   */
  public static Measure newOffMeasure(INote note, int numrows) {
    ArrayList<IBeat> result = new ArrayList<>();
    for (int i = 0; i < numrows; i++) {
      result.add(new Beat(BeatType.Rest));
    }
    return new Measure(note, result);
  }
}