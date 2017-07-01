package cs3500.music.view;


import java.awt.*;

import javax.swing.*;

import cs3500.music.model.Beat;
import cs3500.music.model.BeatType;
import cs3500.music.model.IBeat;
import cs3500.music.model.IRepeat;
import cs3500.music.model.ModelViewer;


public class ConcreteGuiViewPanel extends JPanel {
  private ModelViewer model;
  private int tick;

  /**
   * Constructs the panel inside of the GUI view frame.
   *
   * @param model the model that will be viewed
   */
  public ConcreteGuiViewPanel(ModelViewer model) {
    this.model = model;
    this.tick = 0;

  }

  /**
   * Sets the tick of the gui to a specific time.
   *
   * @param t the tick
   */
  public void setTick(long t) {
    this.tick = Math.toIntExact(t);
  }

  /**
   * Draws the notes and times of the beats.'
   *
   * @param g the drawing tool
   */
  private void drawSideWords(Graphics g) {
    for (int i = 0; i < model.getPiece().size(); i++) {
      g.drawString(model.getPiece().get(i).getNote().toString(), 5, 33 +
              (model.getPiece().size() - 1) * 20 - i * 20);
    }
    for (int i = 0; i < model.getNumRows(); i++) {
      if (i % 8 == 0) {
        g.drawString(String.valueOf(i), 50 + i * 20, 15);
      }
    }
  }

  /**
   * Draws the boxes and the actual notes that will be manipulated.
   *
   * @param g the drawing tool
   */
  private void drawBoxes(Graphics g) {
    for (int i = 0; i < model.getPiece().size(); i++) {
      for (int k = 0; k < model.getNumRows(); k++) {
        if (k % 4 == 0) {
          g.setColor(Color.BLACK);
          g.drawRect(50 + k * 20, 18 + i * 20, 80, 20);
          g.setColor(new Color(153, 153, 153));
          g.fillRect(51 + k * 20, 19 + i * 20, 79, 19);
        }
      }
    }
    for (int i = 0; i < model.getPiece().size(); i++) {
      for (int k = 0; k < model.getNumRows(); k++) {
        IBeat beat = model.getPiece().get(i).getBeats().get(k);
        if (beat.equals(new Beat(BeatType.Head))) {
          g.setColor(new Color(0, beat.getInstrument() * 15, 153));
          g.fillRect(50 + k * 20, 19 + (model.getPiece().size() - 1) * 20 - i * 20, 20, 19);
        }
        if (beat.equals(new Beat(BeatType.Sustain))) {
          g.setColor(new Color(178, (152 - beat.getInstrument() * 10), 255));
          g.fillRect(50 + k * 20, 19 + (model.getPiece().size() - 1) * 20 - i * 20, 20, 19);
        }
      }
    }
  }

  /**
   * Draws all the repeats of the model.
   *
   * @param g the drawing tool
   */
  private void drawRepeats(Graphics g) {
    for (IRepeat r : model.getRepeats()) {
      switch (r.getType()) {
        case Start:
          g.setColor(Color.yellow);
          g.fillOval((r.getLocation() * 20 + 45), 15, 15, 15);
          break;
        case Play:
          g.setColor(Color.magenta);
          g.fillRect((r.getLocation() * 20 + 45), 15, 15, 15);
          break;
        default:
          throw new IllegalArgumentException("Invalid Repeat");
      }
    }
  }

  /**
   * Draws the line based on where the tick is at.
   *
   * @param g    the drawing tool
   * @param tick the parameter that keeps changing the view
   */
  private void drawLine(Graphics g, int tick) {
    g.setColor(Color.RED);
    g.drawLine(50 + tick, 0, 50 + tick, this.getHeight());
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    drawSideWords(g);
    drawBoxes(g);
    drawRepeats(g);
    drawLine(g, this.tick);
  }

}
