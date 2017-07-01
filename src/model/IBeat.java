package cs3500.music.model;

/**
 * Created by michaelfleischmann on 12/12/16.
 */
public interface IBeat {

  /**
   * Sets the instrument of the Beat to the specified instrument.
   */
  void setInstrument(int instrument);

  /**
   * Returns the Beat type.
   *
   * @return the beat type
   */
  BeatType getType();

  /**
   * Returns the instrument number.
   *
   * @return the instrument number
   */
  int getInstrument();

  /**
   * Returns the volume.
   *
   * @return the volume
   */
  int getVolume();
}
