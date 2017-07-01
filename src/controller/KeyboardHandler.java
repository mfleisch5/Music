package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by michaelfleischmann on 11/12/16.
 */
public class KeyboardHandler implements KeyListener {
  Controller controller;

  /**
   * Creates a keyboard handler that works with a specific controller.
   *
   * @param controller the controller that they keyboard handler will work with.
   */
  KeyboardHandler(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //our controller does nothing when this happens so it can remain empty
  }

  @Override
  public void keyPressed(KeyEvent e) {
    controller.putKeys();
    try {
      controller.getKeyPressedMap().get(e.getExtendedKeyCode()).run();
    } catch (NullPointerException n) {
      //if it is null it means the user pressed a key that doesn’t do anything,
      // so this shouldn’t do anything
    } catch (IllegalArgumentException l) {
      System.out.print(l.getMessage() + "\n");
    }
    controller.readjustView();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //our controller does nothing when this happens so it can remain empty
  }

}
