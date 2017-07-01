package cs3500.music.view;

import cs3500.music.model.ModelViewer;

/**
 * Created by michaelfleischmann on 11/6/16.
 */
public class ConsoleViewImpl implements View {
  private ModelViewer model;

  /**
   * Constructs a {@Code ConsoleViewImpl} object, which prints the music of the model of the
   * builder.
   */
  public ConsoleViewImpl(ModelViewer model) {
    if (model == null) {
      throw new IllegalStateException("Null Model");
    }
    this.model = model;
  }

  @Override
  public void initialize() {
    System.out.print(model.getMusicState());
  }

}
