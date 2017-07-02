package music.model;

import java.util.Objects;

/**
 * Created by michaelfleischmann on 10/15/16.
 */
public class Note implements INote {
  private final Pitch pitch;
  private final int octave;

  /**
   * Constructs a {@Code Note} object.
   *
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   * @throws IllegalArgumentException if the pitch is null or the octave is not between 1 and 10
   */
  public Note(Pitch pitch, int octave) {
    if (pitch == null || octave < 0 || octave > 10) {
      throw new IllegalArgumentException("Invalid Note");
    }
    this.pitch = pitch;
    this.octave = octave;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Note)) {
      return false;
    } else {
      Note that = (Note) object;
      return this.pitch.equals(that.pitch) &&
              this.octave == that.octave;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(pitch, octave);
  }


  @Override
  public String toString() {
    return " " + String.format("%1$4s", pitch.toString() + octave);
  }

  @Override
  public int toInt() {
    return pitch.toInt() + (octave * 12);
  }

  /**
   * Static creator of INotes that converts the int into a Note.
   *
   * @param i the int to be converted
   * @return a new INote converted from the int
   */
  public static INote intToNote(int i) {
    return new Note(Pitch.intToPitch(i % 12), (i / 12));
  }

  @Override
  public INote addNote(int i) {
    return Note.intToNote(i);
  }

}