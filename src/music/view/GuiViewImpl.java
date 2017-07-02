package music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;


import javax.swing.JScrollPane;

import music.model.ModelViewer;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewImpl extends javax.swing.JFrame implements GuiView {
  private final ConcreteGuiViewPanel displayPanel;
  private final JScrollPane scroller;
  private final ModelViewer model;
  private final int w;
  private final int h;

  /**
   * Constructs a new {@Code GuiViewImpl} view frame object.
   */
  public GuiViewImpl(ModelViewer model) {
    this.model = model;
    displayPanel = new ConcreteGuiViewPanel(model);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.w = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            model.getNumRows() * 20 + 58);
    this.h = (int) Math.min(Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
            model.getPiece().size() * 20 + 30);
    scroller = new JScrollPane(displayPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroller.getHorizontalScrollBar().setUnitIncrement(45);
    scroller.getVerticalScrollBar().setUnitIncrement(20);
    setSizes();
    getContentPane().add(scroller);
  }

  /**
   * Sets all sizes to the preferred dimensions based on the model and window.
   */
  private void setSizes() {
    setPreferredSize(new Dimension(w, h));
    displayPanel.setPreferredSize(new Dimension(model.getNumRows() * 20 + 58,
            model.getPiece().size() * 20 + 30));
    scroller.setPreferredSize(new Dimension(w, h));
    scroller.setSize(new Dimension(w, h));
    pack();
  }

  @Override
  public void changeView(int x, int y) {
    scroller.getHorizontalScrollBar().setValue(x);
    scroller.getVerticalScrollBar().setValue(y);
  }

  @Override
  public void addListeners(KeyListener keys, MouseListener mouse) {
    addKeyListener(keys);
    displayPanel.addMouseListener(mouse);
  }

  @Override
  public void refresh() {
    repaint();
    setSizes();
  }

  /**
   * instantiates the tick of the displayPanel and moves the display accordingly.
   *
   * @param tick the long that the displayPanel's tick will be set to
   */
  void instantiate(long tick) {
    displayPanel.setTick(tick);
    if (tick % Toolkit.getDefaultToolkit().getScreenSize().getWidth() == 0) {
      changeView(Math.toIntExact(tick), 0);
    }
    repaint();
  }

  @Override
  public void initialize() {
    if (model == null) {
      throw new IllegalStateException("Null Model");
    }
    this.setVisible(true);
  }

}