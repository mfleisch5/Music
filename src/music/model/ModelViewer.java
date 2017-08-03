package music.model;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by michaelfleischmann on 12/5/16.
 */

/**
 * The limited edition of the model that only the view can see.
 */
public interface ModelViewer {

  /**
   * returns the piece, which includes all the information of the model.
   *
   * @return the piece
   */
  ConcurrentSkipListMap<INote, IMeasure> getPiece();

  /**
   * returns the number of rows in the piece.
   *
   * @return the number of rows in the piece
   */
  int getNumRows();

  /**
   * Returns the piece in String form.
   *
   * @return the music state of the editor
   */
  String getMusicState();

  /**
   * Returns the list of IRepeats that the model contains.
   *
   * @return the list of IRepeats
   */
  List<IRepeat> getRepeats();


}
