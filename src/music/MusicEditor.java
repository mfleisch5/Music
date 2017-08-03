package music;

import music.controller.Controller;
import music.model.IMusicEditorModel;
import music.model.MusicEditorCreator;
import music.util.CompositionBuilder;
import music.util.MusicReader;

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
    CompositionBuilder<IMusicEditorModel> builder = new MusicEditorCreator.Builder();
    FileReader reader = new FileReader("txts/" + args[0]);
    MusicReader.parseFile(reader, builder);
    Controller controller = new Controller(builder);
    controller.createWorld(args[1]).initialize();
  }
}
