package cs3500.music.model;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by michaelfleischmann on 10/21/16.
 */
public class MeasureTest {
  MusicEditorModel model = new MusicEditorModel();
  Note noteC3 = new Note(Pitch.C, 3);
  IMeasure measureC3 = new Measure(noteC3);

  @Test
  public void testNumBeats() {
    assertEquals(0, measureC3.numBeats());
    model.addNote(noteC3, 4, 0);
    measureC3 = model.getPiece().get(0);
    assertEquals(1, measureC3.numBeats());
    model.addNote(noteC3, 7, 6);
    assertEquals(2, measureC3.numBeats());
    model.addNote(noteC3, 2, 15);
    assertEquals(3, measureC3.numBeats());
  }

  @Test
  public void testFindBeat() {
    model.addNote(noteC3, 4, 0);
    model.addNote(noteC3, 7, 6);
    model.addNote(noteC3, 2, 15);
    measureC3 = model.getPiece().get(0);
    assertSame(0, measureC3.findBeat(1));
    assertSame(6, measureC3.findBeat(2));
    assertSame(15, measureC3.findBeat(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFindBeatBad() {
    measureC3.findBeat(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFindBeatBad2() {
    measureC3.findBeat(-1);
  }

  @Test
  public void testBeatEnd() {
    model.addNote(noteC3, 4, 0, 7, 9);
    model.addNote(noteC3, 7, 6, 3, 6);
    model.addNote(noteC3, 2, 15, 9, 3);
    measureC3 = model.getPiece().get(0);
    assertSame(3, measureC3.getBeatEnd(1));
    assertSame(12, measureC3.getBeatEnd(2));
    assertSame(16, measureC3.getBeatEnd(3));
    assertEquals(7, measureC3.getBeats().get(3).getInstrument());
  }

  @Test
  public void testAvailable() {
    model.addNote(noteC3, 4, 0);
    model.addNote(noteC3, 7, 6);
    model.addNote(noteC3, 2, 15);
    measureC3 = model.getPiece().get(0);
    assertTrue(measureC3.unavailable(5, 2));
    assertFalse(measureC3.unavailable(5, 20));
  }

  @Test
  public void testNewOffMeasure() {
    measureC3 = Measure.newOffMeasure(noteC3, 3);
    assertEquals(3, measureC3.getBeats().size());
    assertEquals(new Beat(BeatType.Rest), measureC3.getBeats().get(0));
    assertEquals(new Beat(BeatType.Rest), measureC3.getBeats().get(1));
    assertEquals(new Beat(BeatType.Rest), measureC3.getBeats().get(2));

  }

  @Test
  public void testHashBeats() {
    model.addNote(noteC3, 4, 2);
    model.addNote(noteC3, 4, 4);
    model.addNote(noteC3, 6, 17);
    measureC3 = model.getPiece().get(0);
    assertSame(2, measureC3.hashBeat().get(4));
  }
}
