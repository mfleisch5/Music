package music.model;

/**
 * Created by michaelfleischmann on 10/27/16.
 */

import music.util.CompositionBuilder;

/**
 * A class built up of static methods that create IMusicEditorModels.
 */
public class MusicEditorCreator {
  /**
   * A new static final class which creates a tempo and new IMusicEditorModel and adds notes to it.
   */
  public static final class Builder implements CompositionBuilder<IMusicEditorModel> {
    private int tempo;
    private IMusicEditorModel model = new MusicEditorModel();

    @Override
    public int getTempo() {
      return tempo;
    }

    @Override
    public IMusicEditorModel build() {
      return model;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument,
                                                         int pitch, int volume) {
      model.addNote(Note.intToNote(pitch), end - start, start, instrument, volume);
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }
  }

  /**
   * A static method that combines two pieces so they play either simultaneously or consecutively.
   *
   * @param model1  The first model to be combined
   * @param model2  The second model to be combined
   * @param command Must be either 'c' for consecutive or 's' for simultaneous
   * @return A new model that combines both models.
   * @throws IllegalArgumentException if the command is neither 'c' nor 's'
   */
  public static IMusicEditorModel combine(IMusicEditorModel model1,
                                          IMusicEditorModel model2, char command) {
    if (model1 == null || model2 == null) {
      throw new IllegalArgumentException("No Null Models!");
    }
    int model1rows;
    if (model1.getPiece().size() == 0) {
      model1rows = 0;
    } else {
      model1rows = model1.getPiece().firstEntry().getValue().getBeats().size();
    }
    switch (command) {
      case 'c':
        for (IMeasure measure : model2.getPiece().values()) {
          for (int i = 1; i <= measure.numBeats(); i++) {
            model1.addNote(measure.getNote(),
                    measure.getBeatEnd(i) - measure.findBeat(i),
                    measure.findBeat(i) + model1rows);
          }
        }
        return model1;
      case 's':
        for (IMeasure measure : model2.getPiece().values()) {
          for (int i = 1; i <= measure.numBeats(); i++) {
            model1.addNote(measure.getNote(),
                    measure.getBeatEnd(i) - measure.findBeat(i),
                    measure.findBeat(i));
          }
        }
        return model1;
      default:
        throw new IllegalArgumentException("Invalid Combination");
    }
  }
}
