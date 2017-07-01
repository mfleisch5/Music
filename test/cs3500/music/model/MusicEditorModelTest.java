package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by michaelfleischmann on 10/17/16.
 */

public class MusicEditorModelTest {
  Note noteC3 = new Note(Pitch.C, 3);
  Note noteB2 = new Note(Pitch.B, 2);
  Note noteGs2 = new Note(Pitch.Gs, 2);
  Note noteF2 = new Note(Pitch.F, 2);
  IMusicEditorModel model = new MusicEditorModel();
  IMusicEditorModel model2 = new MusicEditorModel();

  @Test
  public void testAddNote() {
    model.addNote(noteB2, 4, 3);
    assertEquals("   B2\n" +
            "0     \n" +
            "1     \n" +
            "2     \n" +
            "3  X  \n" +
            "4  |  \n" +
            "5  |  \n" +
            "6  |  ", model.getMusicState());
    model.addNote(noteC3, 2, 2);
    assertEquals("   B2   C3\n" +
            "0          \n" +
            "1          \n" +
            "2       X  \n" +
            "3  X    |  \n" +
            "4  |       \n" +
            "5  |       \n" +
            "6  |       ", model.getMusicState());
    model.addNote(noteGs2, 7, 10);
    model.addNote(noteGs2, 3, 13);
    assertEquals("   G#2   A2  A#2   B2   C3\n" +
            " 0                         \n" +
            " 1                         \n" +
            " 2                      X  \n" +
            " 3                 X    |  \n" +
            " 4                 |       \n" +
            " 5                 |       \n" +
            " 6                 |       \n" +
            " 7                         \n" +
            " 8                         \n" +
            " 9                         \n" +
            "10  X                      \n" +
            "11  |                      \n" +
            "12  |                      \n" +
            "13  X                      \n" +
            "14  |                      \n" +
            "15  |                      \n" +
            "16  |                      ", model.getMusicState());
    model.addNote(noteB2, 3, 20);
    model.addNote(noteF2, 4, 20);
    assertEquals("    F2  F#2   G2  G#2   A2  A#2   B2   C3\n" +
            " 0                                        \n" +
            " 1                                        \n" +
            " 2                                     X  \n" +
            " 3                                X    |  \n" +
            " 4                                |       \n" +
            " 5                                |       \n" +
            " 6                                |       \n" +
            " 7                                        \n" +
            " 8                                        \n" +
            " 9                                        \n" +
            "10                 X                      \n" +
            "11                 |                      \n" +
            "12                 |                      \n" +
            "13                 X                      \n" +
            "14                 |                      \n" +
            "15                 |                      \n" +
            "16                 |                      \n" +
            "17                                        \n" +
            "18                                        \n" +
            "19                                        \n" +
            "20  X                             X       \n" +
            "21  |                             |       \n" +
            "22  |                             |       \n" +
            "23  |                                     ", model.getMusicState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBad1() {
    model.addNote(noteB2, 1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBad2() {
    model.addNote(noteB2, -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddBad3() {
    model.addNote(null, 1, -2);
  }

  @Test
  public void testEdit() {
    model.addNote(noteF2, 6, 12);
    assertSame(17, model.getPiece().get(0).getBeatEnd(1));
    model.edit(noteF2, 1, -3);
    assertSame(14, model.getPiece().get(0).getBeatEnd(1));
    model.edit(noteF2, 1, 17);
    assertSame(31, model.getPiece().get(0).getBeatEnd(1));
    model.addNote(noteB2, 5, 20);
    model.edit(noteF2, 1, -10);
    assertSame(21, model.getPiece().get(0).getBeatEnd(1));
    assertEquals(model.getPiece().get(3).getBeats().size(),
            model.getPiece().get(0).getBeats().size());
    assertEquals("    F2  F#2   G2  G#2   A2  A#2   B2\n" +
            " 0                                   \n" +
            " 1                                   \n" +
            " 2                                   \n" +
            " 3                                   \n" +
            " 4                                   \n" +
            " 5                                   \n" +
            " 6                                   \n" +
            " 7                                   \n" +
            " 8                                   \n" +
            " 9                                   \n" +
            "10                                   \n" +
            "11                                   \n" +
            "12  X                                \n" +
            "13  |                                \n" +
            "14  |                                \n" +
            "15  |                                \n" +
            "16  |                                \n" +
            "17  |                                \n" +
            "18  |                                \n" +
            "19  |                                \n" +
            "20  |                             X  \n" +
            "21  |                             |  \n" +
            "22                                |  \n" +
            "23                                |  \n" +
            "24                                |  ", model.getMusicState());
  }

  /*@Test(expected = IllegalArgumentException.class)
  public void testEditBad1() {
    model.addNote(noteF2, 6, 12);
    model.addNote(noteF2, 2, 20);
    model.edit(noteF2, 1, 4);
  }*/

  @Test(expected = IllegalArgumentException.class)
  public void testEditBad2() {
    model.addNote(noteF2, 6, 12);
    model.edit(noteF2, 1, -7);
  }

  @Test
  public void testRemove() {
    model.addNote(noteB2, 3, 20);
    model.addNote(noteF2, 4, 20);
    model.addNote(noteGs2, 7, 10);
    model.addNote(noteGs2, 3, 13);
    model.addNote(noteC3, 2, 2);
    assertEquals(2, model.getPiece().get(3).numBeats());
    model.remove(noteGs2, 1);
    assertEquals(1, model.getPiece().get(3).numBeats());
    model.remove(noteGs2, 1);
    assertEquals(0, model.getPiece().get(3).numBeats());
    assertEquals(noteF2, model.getPiece().get(0).getNote());
    model.remove(noteF2, 1);
    assertNotEquals(noteF2, model.getPiece().get(0).getNote());
  }

  /*@Test
  public void testMove() {
    model.addNote(noteF2, 4, 20);
    assertEquals(20, model.getPiece().get(0).findBeat(1));
    model.move(noteF2, 1, Direction.Up, 20);
    assertEquals(40, model.getPiece().get(0).findBeat(1));
    model.move(noteF2, 1, Direction.Down, 30);
    assertEquals(10, model.getPiece().get(0).findBeat(1));
    model.move(noteF2, 1, Direction.Right, 6);
    assertEquals(noteB2, model.getPiece().get(0).getNote());
    model.move(noteB2, 1, Direction.Left, 6);
    assertEquals(noteF2, model.getPiece().get(0).getNote());
  }*/

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBad1() {
    model.addNote(noteF2, 4, 20);
    model.move(noteF2, 1, null, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBad2() {
    model.addNote(noteF2, 4, 20);
    model.move(noteF2, 1, Direction.Up, -4);
  }

  /*@Test(expected = IllegalArgumentException.class)
  public void testMoveBad3() {
    model.addNote(noteF2, 4, 20);
    model.addNote(noteF2, 1, 25);
    model.move(noteF2, 2, Direction.Down, 4);
  }*/

  @Test
  public void testCombine() {
    model.addNote(noteB2, 4, 3);
    model.addNote(noteC3, 2, 2);
    model.addNote(noteGs2, 7, 10);
    model.addNote(noteGs2, 3, 2);
    model.addNote(noteB2, 3, 20);
    model.addNote(noteF2, 4, 20);
    model.addNote(noteF2, 8, 9);
    assertEquals(model, MusicEditorCreator.combine(model, model2, 's'));
    model2.addNote(noteB2, 4, 3);
    model2.addNote(noteC3, 2, 2);
    model2.addNote(noteGs2, 7, 10);
    model2.addNote(noteGs2, 3, 2);
    model2.addNote(noteB2, 3, 20);
    model2.addNote(noteF2, 4, 20);
    model2.addNote(noteF2, 8, 9);
    assertEquals(model.getMusicState(),
            MusicEditorCreator.combine(model, model2, 's').getMusicState());
    assertEquals("    F2  F#2   G2  G#2   A2  A#2   B2   C3\n" +
                    " 0                                        \n" +
                    " 1                                        \n" +
                    " 2                 X                   X  \n" +
                    " 3                 |              X    |  \n" +
                    " 4                 |              |       \n" +
                    " 5                                |       \n" +
                    " 6                                |       \n" +
                    " 7                                        \n" +
                    " 8                                        \n" +
                    " 9  X                                     \n" +
                    "10  |              X                      \n" +
                    "11  |              |                      \n" +
                    "12  |              |                      \n" +
                    "13  |              |                      \n" +
                    "14  |              |                      \n" +
                    "15  |              |                      \n" +
                    "16  |              |                      \n" +
                    "17                                        \n" +
                    "18                                        \n" +
                    "19                                        \n" +
                    "20  X                             X       \n" +
                    "21  |                             |       \n" +
                    "22  |                             |       \n" +
                    "23  |                                     \n" +
                    "24                                        \n" +
                    "25                                        \n" +
                    "26                 X                   X  \n" +
                    "27                 |              X       \n" +
                    "28                                |       \n" +
                    "29                                |       \n" +
                    "30                                        \n" +
                    "31                                        \n" +
                    "32                                        \n" +
                    "33  X                                     \n" +
                    "34  |              X                      \n" +
                    "35  |              |                      \n" +
                    "36  |              |                      \n" +
                    "37  |              |                      \n" +
                    "38  |              |                      \n" +
                    "39  |              |                      \n" +
                    "40                                        \n" +
                    "41                                        \n" +
                    "42                                        \n" +
                    "43                                        \n" +
                    "44  X                             X       \n" +
                    "45  |                             |       \n" +
                    "46  |                                     ",
            MusicEditorCreator.combine(model, model2, 'c').getMusicState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineBad() {
    model.addNote(noteB2, 2, 2);
    model2.addNote(noteB2, 2, 2);
    MusicEditorCreator.combine(model, model2, 'x');

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineBad2() {
    MusicEditorCreator.combine(model, null, 'x');

  }



}
