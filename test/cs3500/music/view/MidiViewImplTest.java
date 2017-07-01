package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorCreator;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;

/**
 * Tests the MidiViewImpl class.
 */
public class MidiViewImplTest {
  private CompositionBuilder<IMusicEditorModel> builder = new MusicEditorCreator.Builder();
  IMusicEditorModel model = builder.build();
  Note note = new Note(Pitch.A, 9);
  List<Integer> listOfData = new ArrayList();
  List<Integer> wantedData = new ArrayList<>();

  /**
   * Tests the playNote() method in MidiViewImpl.
   */
  /*@Test
  public void playNoteTest() {
    builder.setTempo(50);
    model.addNote(note, 1, 1);
    model.addNote(Note.intToNote(17), 4, 2, 5, 6);
    for (IMeasure measure : model.getPiece()) {
      for (int i = 1; i <= measure.numBeats(); i++) {
        listOfData.add(measure.getBeats().get(measure.findBeat(i)).getInstrument());
        listOfData.add(measure.getNote().toInt());
        listOfData.add(measure.getBeats().get(measure.findBeat(i)).getVolume());
        listOfData.add(1000000 + (measure.findBeat(i) * builder.getTempo()));
        listOfData.add(1000000 + (measure.getBeatEnd(i) * builder.getTempo()));
      }
    }
    Integer[] wanted = new Integer[]{5, 17, 6, 1000100, 1000250, 0, 117, 0, 1000050, 1000050};
    wantedData.addAll(Arrays.asList(wanted));

    assertEquals(wantedData, listOfData);
  }

  @Test
  public void runMaryLambMock() {
    FileReader reader = null;
    try {
      reader = new FileReader("mary-little-lamb.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    MusicReader.parseFile(reader, builder);
    MidiViewImpl midi = new MidiViewImpl(builder);
    MockReceiver receiver = new MockReceiver();
    /*midi.setReceiver(receiver);
    try {
      midi.playNote();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    String theString = "";
    for (MidiMessage message : receiver.getMessages()) {
      theString += receiver.printMessage((ShortMessage) message) + "\n";
    }
    assertEquals("144 0 52 72\n" +
            "128 0 52 72\n" +
            "144 0 55 70\n" +
            "128 0 55 70\n" +
            "144 0 55 79\n" +
            "128 0 55 79\n" +
            "144 0 55 77\n" +
            "128 0 55 77\n" +
            "144 0 55 79\n" +
            "128 0 55 79\n" +
            "144 0 55 78\n" +
            "128 0 55 78\n" +
            "144 0 55 79\n" +
            "128 0 55 79\n" +
            "144 0 55 78\n" +
            "128 0 55 78\n" +
            "144 0 60 71\n" +
            "128 0 60 71\n" +
            "144 0 60 71\n" +
            "128 0 60 71\n" +
            "144 0 60 73\n" +
            "128 0 60 73\n" +
            "144 0 62 72\n" +
            "128 0 62 72\n" +
            "144 0 62 79\n" +
            "128 0 62 79\n" +
            "144 0 62 75\n" +
            "128 0 62 75\n" +
            "144 0 62 77\n" +
            "128 0 62 77\n" +
            "144 0 62 75\n" +
            "128 0 62 75\n" +
            "144 0 62 69\n" +
            "128 0 62 69\n" +
            "144 0 62 80\n" +
            "128 0 62 80\n" +
            "144 0 62 75\n" +
            "128 0 62 75\n" +
            "144 0 62 74\n" +
            "128 0 62 74\n" +
            "144 0 62 70\n" +
            "128 0 62 70\n" +
            "144 0 64 72\n" +
            "128 0 64 72\n" +
            "144 0 64 85\n" +
            "128 0 64 85\n" +
            "144 0 64 78\n" +
            "128 0 64 78\n" +
            "144 0 64 74\n" +
            "128 0 64 74\n" +
            "144 0 64 82\n" +
            "128 0 64 82\n" +
            "144 0 64 73\n" +
            "128 0 64 73\n" +
            "144 0 64 84\n" +
            "128 0 64 84\n" +
            "144 0 64 76\n" +
            "128 0 64 76\n" +
            "144 0 64 74\n" +
            "128 0 64 74\n" +
            "144 0 64 77\n" +
            "128 0 64 77\n" +
            "144 0 64 81\n" +
            "128 0 64 81\n" +
            "144 0 67 84\n" +
            "128 0 67 84\n" +
            "144 0 67 75\n" +
            "128 0 67 75\n", theString);
    try {
      assertEquals(receiver, midi.getSequencer().getReceiver());
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }*/

}
