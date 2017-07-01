package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by michaelfleischmann on 11/12/16.
 */

public interface PlayableView extends View {

  /**
   * Adds the necessary controller listeners to the view.
   *
   * @param keys  adds a keylistener to the view.
   * @param mouse adds a mouselistener to the view.
   */
  void addListeners(KeyListener keys, MouseListener mouse);

  /**
   * refreshes the view and updates it after new information has been added.
   */
  void refresh();

}
