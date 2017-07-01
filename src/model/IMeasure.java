package cs3500.music.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by michaelfleischmann on 12/5/16.
 */
public interface IMeasure {


  /**
   * Returns the number of Beats in a measure.
   *
   * @return the number of Beats in a measure
   */
  int numBeats();

  /**
   * Returns the note of the measure.
   *
   * @return the note of the measure
   */
  INote getNote();

  /**
   * Returns the beats of the measure.
   *
   * @return the beats in the measure
   */
  List<IBeat> getBeats();

  /**
   * Changes the instrument of a specific beat.
   *
   * @param beatnumber the number of the beat to be changed
   * @param instrument the number that the beat instrument will be changed to
   */
  void changeBeatInstrument(int beatnumber, int instrument);

  /**
   * Finds the starting time of a beat number (from 1 - number of beats) in a measure.
   *
   * @param beatnumber the (counting from 1 - number of beats) beat number
   * @return the starting time of the beat number
   * @throws IllegalArgumentException if the beat number is higher that numBeats or lower than 1
   */
  int findBeat(int beatnumber);

  /**
   * /**
   * Finds the last sustain of the beat specified.
   *
   * @param beatnumber the (counting from 1 - number of beats) beat number
   * @return the ending time of the beat number
   */
  int getBeatEnd(int beatnumber);

  /**
   * Creates a hashmap connecting all the beats of the measure with their beatnumber.
   *
   * @return the hashmap
   */
  HashMap<Integer, Integer> hashBeat();

  /**
   * Returns true if the space in the measure (time - time + duration)
   * does not contain another beat.
   *
   * @param duration the duration of the questioned space
   * @param time     the starting time of the beat
   * @return true or false depending on whether the beat is available
   * @throws IllegalArgumentException if the time is less than 0
   */
  boolean unavailable(int duration, int time);


}
