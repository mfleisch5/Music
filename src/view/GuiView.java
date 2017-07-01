package cs3500.music.view;

/**
 * Created by michaelfleischmann on 12/13/16.
 */
public interface GuiView extends PlayableView {

  /**
   * Changes the viewport to a specific position.
   * @param x the x position of the viewport
   * @param y the y position of the viewport
   */
  void changeView(int x, int y);
}
