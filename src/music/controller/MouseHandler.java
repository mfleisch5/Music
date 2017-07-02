package music.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Created by michaelfleischmann on 11/12/16.
 */
class MouseHandler implements MouseListener {
  private Point pos = new Point(0, 2);
  private int widthbuffer;
  private int heightbuffer;
  private int xlength;
  private int ylength;


  /**
   * Constructs a {@Code MouseHandler} object. This will handle the editor's mouse clicks.
   */
  MouseHandler() {
    widthbuffer = 0;
    heightbuffer = 0;
    xlength = 1;
    ylength = 1;

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    pos = e.getPoint();
  }

  @Override
  public void mousePressed(MouseEvent e) {
  //our view does not need to do anything for this method
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  //our view does not need to do anything for this method
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  //our view does not need to do anything for this method
  }

  @Override
  public void mouseExited(MouseEvent e) {
  //our view does not need to do anything for this method
  }

  public Point getPos() {
    return new Point((pos.x - widthbuffer) / xlength, (pos.y - heightbuffer) / ylength);
  }

  /**
   * Sets the values of the gui based on which gui it is being implemented by.
   *
   * @param widthbuffer  the lefthand width buffer of the gui
   * @param heightbuffer the top buffer of the gui
   * @param xlength      the note-width in the gui
   * @param ylength      the note-height in the gui
   */
  public void setValues(int widthbuffer, int heightbuffer, int xlength, int ylength) {
    this.widthbuffer = widthbuffer;
    this.heightbuffer = heightbuffer;
    this.xlength = xlength;
    this.ylength = ylength;
  }
}
