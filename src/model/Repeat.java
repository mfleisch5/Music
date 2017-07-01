package cs3500.music.model;

import java.util.Objects;

/**
 * Created by michaelfleischmann on 12/12/16.
 */
class Repeat implements IRepeat {
  final RepeatType type;
  final int location;

  /**
   * A Repeat is a marker that either marks the start of a part of the model to be repeated, or a
   * time in which the repeat should begin. It can also mark an end cap of the model.
   *
   * @param type    the type of repeat
   * @param location the location, or time, in the model that the repeat should be placed at
   */
  private Repeat(RepeatType type, int location) {
    this.type = type;
    this.location = location;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Repeat)) {
      return false;
    } else {
      Repeat that = (Repeat) object;
      return this.type == that.type && this.location == that.location;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, location);
  }


  @Override
  public RepeatType getType() {
    return type;
  }

  @Override
  public int getLocation() {
    return location;
  }

  /**
   * Creates a new repeat with a specified location and type.
   *
   * @param type    the type of repeat marker
   * @param location the location in the piece of the repeat
   * @return the new repeat
   */
  public static IRepeat newRepeat(RepeatType type, int location) {
    return new Repeat(type, location);
  }

  @Override
  public IRepeat addRepeat(RepeatType type, int location) {
    return Repeat.newRepeat(type, location);
  }

}