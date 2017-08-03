package music.controller;

import music.model.*;
import music.util.CompositionBuilder;
import music.view.*;

import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Created by michaelfleischmann on 11/14/16.
 */
public class Controller {
  private KeyListener keys;
  private MouseHandler mouse;
  private IMusicEditorModel model;
  private CompositionBuilder<IMusicEditorModel> builder;
  private View view;
  private HashMap<Integer, Runnable> keyPressedMap;

  /**
   * Creates a {@Code Controller} object to dictate between the view and the model.
   *
   * @param builder the builder in which the model and tempo information comes from
   */
  public Controller(CompositionBuilder<IMusicEditorModel> builder) {
    this.builder = builder;
    this.keys = new KeyboardHandler(this);
    this.mouse = new MouseHandler();
    this.model = builder.build();
    this.keyPressedMap = new HashMap<>();
  }

  /**
   * Returns the keyPressedMap.
   *
   * @return the keyPressedMap
   */
  HashMap<Integer, Runnable> getKeyPressedMap() {
    return keyPressedMap;
  }

  /**
   * Creates the controls that change parts of the model and then get reflected in the view.
   */
  private void modelKeys() {
    IMeasure measure = model.getPiece().get(model.getPiece().size() - (mouse.getPos().y + 1));
    keyPressedMap.put(82, () -> {
      model.remove(measure.getNote(), measure.hashBeat().get(mouse.getPos().x));
    });
    keyPressedMap.put(83, () -> {
      model.edit(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), -1);
    });
    keyPressedMap.put(76, () -> {
      model.edit(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), 1);
    });
    keyPressedMap.put(65, () -> {
      model.addNote(measure.getNote(), 1, mouse.getPos().x, 0, 64);
    });
    keyPressedMap.put(73, () -> {
      measure.changeBeatInstrument(measure.hashBeat().get(mouse.getPos().x), 1);
    });
    keyPressedMap.put(75, () -> {
      measure.changeBeatInstrument(measure.hashBeat().get(mouse.getPos().x), -1);
    });
    keyPressedMap.put(37, () -> {
      model.move(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), Direction.Left, 1);
    });
    keyPressedMap.put(38, () -> {
      model.move(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), Direction.Up, 1);
    });
    keyPressedMap.put(39, () -> {
      model.move(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), Direction.Right, 1);
    });
    keyPressedMap.put(40, () -> {
      model.move(measure.getNote(), measure.hashBeat().get(mouse.getPos().x), Direction.Down, 1);
    });
    keyPressedMap.put(84, () -> {
      INote newNote = Note.intToNote(model.getPiece().lastKey().toInt() + 1);
      model.getPiece().put(newNote, Measure.newOffMeasure(newNote, model.getNumRows()));
    });
    keyPressedMap.put(71, () -> {
      INote newNote = Note.intToNote(model.getPiece().firstKey().toInt() - 1);
      model.getPiece().put(newNote, Measure.newOffMeasure(newNote, model.getNumRows()));
    });
    keyPressedMap.put(81, () -> {
      model.addRepeat(RepeatType.Start, mouse.getPos().x);
      if (view instanceof MidiView) {
        ((MidiView) view).addRepeats(RepeatType.Start, mouse.getPos().x * builder.getTempo());
      }
    });
    keyPressedMap.put(90, () -> {
      model.addRepeat(RepeatType.Play, mouse.getPos().x);
      if (view instanceof MidiView) {
        ((MidiView) view).addRepeats(RepeatType.Play, mouse.getPos().x * builder.getTempo());
      }
    });
  }

  /**
   * Controls that manipulate a midiview in some way.
   */
  private void changeMidiKeys() {
    keyPressedMap.put(49, () -> {
      ((MidiView) view).changeTime(-200000);
    });
    keyPressedMap.put(51, () -> {
      ((MidiView) view).changeTime(200000);
    });
    keyPressedMap.put(80, () -> {
      ((MidiView) view).pause();
    });
  }

  /**
   * Controls that manipulate the guiview in some way.
   */
  private void changeGuiViewKeys() {
    keyPressedMap.put(17, () -> {
      ((GuiView) view).changeView(0, 0);
    });
    keyPressedMap.put(27, () -> {
      ((GuiView) view).changeView(model.getNumRows() * 20 + 58, 0);
    });
  }

  /**
   * Creates the controls of the editor by putting specific lambda methods associated with each
   * key code.
   */
  void putKeys() {
    try {
      modelKeys();
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Click outside model bounds");
    }
    if (view instanceof MidiView) {
      changeMidiKeys();
    }
    if (view instanceof GuiView) {
      changeGuiViewKeys();
    }
  }

  /**
   * readjusts the view after a change has been made to the model.
   */
  void readjustView() {
    if (view instanceof PlayableView) {
      ((PlayableView) view).refresh();
    }
  }

  /**
   * Updates the view to include listeners and a tempo.
   */
  private void updateView() {
    mouse.setValues(50, 20, 20, 20);
    if (this.view instanceof PlayableView) {
      ((PlayableView) this.view).addListeners(keys, mouse);
    }
    if (this.view instanceof MidiView) {
      ((MidiView) this.view).setTempo(builder.getTempo());
    }
  }

  /**
   * Creates a new view based on a string given to it and adds the specific listeners and
   * information to it.
   *
   * @param view the view that the main will run with
   * @return a new integrated view that is ready to be initialized
   */
  public View createWorld(String view) {
    switch (view) {
      case "console":
        this.view = new ConsoleViewImpl(model);
        break;
      case "midi":
        this.view = new MidiViewImpl(model);
        break;
      case "visual":
        this.view = new GuiViewImpl(model);
        break;
      case "music":
        this.view = new MusicViewImpl(model);
        break;
      default:
        throw new IllegalArgumentException("Not a valid view type");
    }
    updateView();
    return this.view;
  }
}
