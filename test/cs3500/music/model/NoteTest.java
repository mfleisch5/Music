package cs3500.music.model;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michaelfleischmann on 10/15/16.
 */
public class NoteTest {
  Note note1 = new Note(Pitch.As, 10);
  Note note2 = new Note(Pitch.B, 10);
  Note note3 = new Note(Pitch.Gs, 9);
  Note note4 = new Note(Pitch.F, 6);
  Note note5 = new Note(Pitch.F, 6);

  @Test
  public void testToString() {
    assertEquals(" A#10", note1.toString());
    assertEquals("  G#9", note3.toString());
    assertEquals("  B10", note2.toString());
    assertEquals("   F6", note4.toString());
  }

  @Test
  public void testToInt() {
    assertEquals(130, note1.toInt());
    assertEquals(131, note2.toInt());
    assertEquals(116, note3.toInt());
    assertEquals(77, note4.toInt());
  }

  @Test
  public void testIntToNote() {
    assertEquals(note1, Note.intToNote(130));
    assertEquals(note2, Note.intToNote(131));
    assertEquals(note3, Note.intToNote(116));
    assertEquals(note4, Note.intToNote(77));
  }

  @Test
  public void testEquals() {
    assertEquals(note4, note5);
    assertNotEquals(note1, note2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadNote1() {
    Note badnote = new Note(null, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadNote2() {
    Note badnote = new Note(Pitch.A, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadNote3() {
    Note badnote = new Note(Pitch.A, 11);
  }

}
