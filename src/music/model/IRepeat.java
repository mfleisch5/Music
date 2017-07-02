package music.model;

/**
 * Created by michaelfleischmann on 12/12/16.
 */
public interface IRepeat {

  /**
   * Returns the boolean value of the repeat, indicating whether or not it is a start repeat.
   *
   * @return the boolean value of the repeat
   */
  RepeatType getType();

  /**
   * Returns the location of the repeat in the piece.
   *
   * @return the location
   */
  int getLocation();

  /**
   * Creates a new IRepeat based on the start and location values.
   *
   * @param type the type of IRepeat being returned
   * @param location the location of the repeat
   */
  IRepeat addRepeat(RepeatType type, int location);
}
