package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorCreator;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;


public class MusicEditor {

  /**
   * Runs the Music Editor.
   *
   * @param args two arguments: the file to be played, and the type of view
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    CompositionBuilder<IMusicEditorModel> builder = new MusicEditorCreator.Builder();
    FileReader reader = new FileReader(args[0]);
    MusicReader.parseFile(reader, builder);
    Controller controller = new Controller(builder);
    controller.createWorld(args[1]).initialize();
  }
}
