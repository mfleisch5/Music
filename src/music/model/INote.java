package music.model;

/**
 * Created by michaelfleischmann on 12/12/16.
 */
public interface INote extends Comparable<INote> {

  /**
   * Returns the integer counterpart of the note.
   *
   * @return the integer associated with the note
   */
  int toInt();

  /**
   * Returns a new INote derived from the specific integer.
   *
   * @param i the integer to convert to an INote
   * @return a new INote converted from the integer
   */
  INote addNote(int i);
}
