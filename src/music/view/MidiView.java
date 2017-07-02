package music.view;

import music.model.RepeatType;

/**
 * Created by michaelfleischmann on 12/6/16.
 */
public interface MidiView extends PlayableView {
  /**
   * Sets the tempo to the specified tempo.
   *
   * @param tempo the tempo it shall be set to
   */
  void setTempo(int tempo);

  /**
   * Depending on if the view is already paused, this pauses or resumes the view.
   */
  void pause();

  /**
   * Shows a new repeat in the view.
   *
   * @param type     the type of repeat marker
   * @param location the location of the repeat
   */
  void addRepeats(RepeatType type, int location);

  /**
   * Adds or subtracts a new time from the current time of the song.
   *
   * @param time the time that will be added or subtracted
   */
  void changeTime(int time);
}
