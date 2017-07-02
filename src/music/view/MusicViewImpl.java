package music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import music.model.ModelViewer;
import music.model.RepeatType;

/**
 * Created by michaelfleischmann on 11/15/16.
 */
public class MusicViewImpl implements MidiView, GuiView {
  GuiViewImpl gui;
  MidiViewImpl midi;
  ModelViewer model;

  /**
   * Creates a new {@Code MusicViewImpl} Object that contains both a gui and midi view.
   *
   * @param model the model that this object will obtain information from
   */
  public MusicViewImpl(ModelViewer model) {
    this.model = model;
    this.midi = new MidiViewImpl(model, new GuiViewImpl(model));
    this.gui = (GuiViewImpl) midi.getFrame();
  }

  @Override
  public void setTempo(int tempo) {
    midi.setTempo(tempo);
  }

  @Override
  public void addListeners(KeyListener keys, MouseListener mouse) {
    gui.addListeners(keys, mouse);
  }

  @Override
  public void addRepeats(RepeatType type, int location) {
    midi.addRepeats(type, location);
  }

  @Override
  public void refresh() {
    gui.refresh();
    midi.refresh();
  }

  @Override
  public void changeView(int x, int y) {
    gui.changeView(x, y);
  }

  @Override
  public void pause() {
    midi.pause();
  }

  @Override
  public void changeTime(int time) {
    midi.changeTime(time);
  }

  @Override
  public void initialize() {
    midi.initialize();
    gui.initialize();
    while (true) {
      gui.instantiate(((midi.getCurrentTime()) / midi.getTempo()) * 20);
    }
  }

}
