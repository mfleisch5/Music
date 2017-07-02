package music;

import music.model.IMusicEditorModel;
import music.util.CompositionBuilder;

import javax.sound.midi.InvalidMidiDataException;
import java.io.FileReader;
import java.io.IOException;

public class MusicEditor {

  /**
   * Runs the Music Editor.
   *
   * @param args two arguments: the file to be played, and the type of view
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    CompositionBuilder<IMusicEditorModel> builder = new music.model.MusicEditorCreator.Builder();
    FileReader reader = new FileReader(args[0]);
    music.util.MusicReader.parseFile(reader, builder);
    music.controller.Controller controller = new music.controller.Controller(builder);
    controller.createWorld(args[1]).initialize();
  }
}
