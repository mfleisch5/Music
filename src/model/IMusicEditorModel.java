package cs3500.music.model;

/**
 * Created by michaelfleischmann on 10/27/16.
 */
public interface IMusicEditorModel extends ModelViewer {

  /**
   * Adds a beat with an instrument to the model. If the note is higher or lower than all the notes
   * already present, it creates new measures that have the same numrows as the present measures.
   * it then adds a beat to the specific measure that contains the note. Then it readjusts.
   *
   * @param note       the note that will be added
   * @param duration   the duration of the note
   * @param time       the timing of the note inside the piece
   * @param instrument the instrument that will play with the beat
   * @throws IllegalArgumentException if given invalid input
   */
  void addNote(INote note, int duration, int time, int instrument, int volume)
          throws IllegalArgumentException;

  /**
   * Adds a beat without an instrument to the model. If the note is higher or lower than all the
   * notes already present, it creates new measures that have the same numrows as the present
   * measures. It then adds a beat to the specific measure that contains the note.
   * Then it readjusts.
   *
   * @param note     the note that will be added
   * @param duration the duration of the note
   * @param time     the timing of the note inside the piece
   */
  void addNote(INote note, int duration, int time) throws IllegalArgumentException;


  /**
   * Adds either a repeat start symbol, end symbol,
   * or a repeat play symbol at a specific location in the piece.
   *
   * @param type     the type of marker to be added
   * @param location the location of the repeat
   */
  void addRepeat(RepeatType type, int location);

  /**
   * Edits a given note. If the editlength is positive, it adds sustains to the note. If it is
   * negative, it removes a given amount of sustains. It replaces these lost sustains with
   * Rest.
   *
   * @param note       the note to be edited
   * @param beat       the specific beat that will be edited (numbered from 1 - the number of
   *                   beats)
   * @param editlength the length of the edit, or rather, how many sustains to be added or removed
   * @throws IllegalArgumentException if adding to the beat takes away from another beat or if the
   *                                  user is trying to remove more sustains than there are in the
   *                                  beat.
   */
  void edit(INote note, int beat, int editlength) throws IllegalArgumentException;

  /**
   * Removes a beat completely from the piece.
   *
   * @param note the note to be removed
   * @param beat the exact beat to be removed (from 1 - number of beats in measure)
   */
  void remove(INote note, int beat);

  /**
   * Moves a beat either Upwards(later start time), Downwards(earlier start time), to the Left
   * (Lower octave or pitch), or to the Right(Higher octave or pitch).
   *
   * @param note       The note of the measure
   * @param beat       The specific beat to be moved
   * @param direction  Up, Down, Left, or Right
   * @param editlength the length that the beat will be moved
   * @throws IllegalArgumentException if adding to the beat takes away from another beat or if the
   *                                  editlength is negative, or if the direction is null.
   */
  void move(INote note, int beat, Direction direction, int editlength)
          throws IllegalArgumentException;


}
