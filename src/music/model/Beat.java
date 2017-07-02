package music.model;

import java.util.Objects;

/**
 * Created by michaelfleischmann on 10/15/16.
 */
public class Beat implements IBeat {
  private final BeatType type;
  private int instrument;
  private int volume;

  /**
   * Constructs a {@Code Beat} object that sets both instrument and volume value to 0.
   *
   * @param type the type of Beat {Head, Sustain, or Rest}
   */
  public Beat(BeatType type) {
    this.type = type;
    this.instrument = 0;
    this.volume = 0;
  }

  /**
   * Constructs a {@Code Beat} object.
   *
   * @param type       the type of Beat {Head, Sustain, or Rest}
   * @param instrument the instrument that will be played when the beat is on
   * @param volume     the volume of the beat
   */
  public Beat(BeatType type, int instrument, int volume) {
    if (instrument > 15 || instrument < 0 || volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid Beat");
    }
    this.type = type;
    this.instrument = instrument;
    this.volume = volume;

  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Beat)) {
      return false;
    } else {
      Beat that = (Beat) object;
      return that.type == this.type;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }

  @Override
  public String toString() {
    return this.type.toString();
  }

  @Override
  public void setInstrument(int instrument) {
    if (instrument > 15 || instrument < 0) {
      throw new IllegalArgumentException("Bad Instrument");
    }
    this.instrument = instrument;
  }

  @Override
  public BeatType getType() {
    return type;
  }

  @Override
  public int getInstrument() {
    return instrument;
  }

  @Override
  public int getVolume() {
    return volume;
  }

}
