package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.swing.*;

import cs3500.music.model.IMeasure;
import cs3500.music.model.ModelViewer;
import cs3500.music.model.RepeatType;


public class MidiViewImpl implements MidiView {
  private ModelViewer model;
  private JFrame frame;
  private int tempo;
  private Synthesizer synth;
  private Receiver receiver;
  private long currentTime;
  private boolean paused;
  private List<Integer> repeatWhen;
  private List<Integer> repeatThese;
  private int currentRepeat;


  /**
   * Constructs a {@code MidiViewImpl} standalone midi object.
   *
   * @param model the model viewer that midi takes information from
   */
  public MidiViewImpl(ModelViewer model) {
    this.tempo = -1;
    this.model = model;
    this.frame = new JFrame();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    paused = false;
    repeatWhen = new ArrayList<>();
    repeatThese = new ArrayList<>();
    this.currentRepeat = 0;
  }

  /**
   * Constructs a {@code MidiViewImpl} midi object with a specified view.
   *
   * @param model the model viewer that midi takes information from
   * @param view  the view to be constructed alongside midi
   */
  public MidiViewImpl(ModelViewer model, JFrame view) {
    this.model = model;
    this.tempo = -1;
    this.frame = view;
    paused = false;
    repeatThese = new ArrayList<>();
    repeatWhen = new ArrayList<>();
    this.currentRepeat = 0;
  }

  @Override
  public void setTempo(int t) {
    this.tempo = t;
  }

  /**
   * Gets the tempo of the view.
   *
   * @return the tempo
   */
  public int getTempo() {
    return tempo;
  }

  /**
   * Gets the frame of the view.
   *
   * @return the frame
   */
  JFrame getFrame() {
    return frame;
  }

  /**
   * Gets the current time of the view.
   *
   * @return the current time
   */
  long getCurrentTime() {
    return currentTime;
  }

  @Override
  public void addListeners(KeyListener keys, MouseListener mouse) {
    frame.addKeyListener(keys);
  }

  @Override
  public void addRepeats(RepeatType type, int location) {
    switch (type) {
      case Start:
        repeatThese.add(location);
        Collections.sort(repeatThese);
        break;
      case Play:
        repeatWhen.add(location);
        Collections.sort(repeatWhen);
        break;
      default:
        return;
    }
  }

  /**
   * Initializes midi and reinitializes that so that the synth is open.
   */
  private void startMidi() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = this.synth.getReceiver();
      synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reinitializes the currentTime after every tick as long as midi is playing.
   */
  private void runTime() {
    if (!paused && currentTime < model.getNumRows() * tempo) {
      currentTime = currentTime + 1000;
    }
  }

  /**
   * If the current time is a start repeat, the current repeater changes to the current time. Then,
   * the current time gets removed from the list of starters. Once a play repeat is hit, the time
   * switches back to the start repeat and deletes that play from the list of players.
   */
  private void runRepeats() {
    if (repeatThese.contains(Math.toIntExact(currentTime))) {
      currentRepeat = Math.toIntExact(currentTime);
      repeatThese.remove(0);
    }
    if (repeatWhen.contains(Math.toIntExact(currentTime))) {
      daCapo(currentRepeat);
      repeatWhen.remove(0);
      currentRepeat = 0;
    }
  }

  /**
   * Plays alternate endings for the piece.
   */

  /**
   * It's go time! Creates a new timer and runs the repeats and the time instantiator once every
   * millisecond.
   */
  private void goTime() {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        runTime();
        runRepeats();
      }
    }, 0, 1);
  }

  @Override
  public void refresh() {
    //midi does not need to refresh after every view change, it only does this when paused.
  }

  /**
   * Reinitializes midi and replays the song from the new current time.
   */
  private void replay() {
    synth.close();
    startMidi();
    try {
      playSong(currentTime);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void pause() {
    if (!paused) {
      synth.close();
    } else {
      replay();
    }
    paused = !paused;
  }

  /**
   * Reinitializes the current time and then replays the song.
   *
   * @param time the new current time
   */
  private void daCapo(int time) {
    currentTime = time;
    replay();
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  public void playSong(long start) throws InvalidMidiDataException {
    for (IMeasure measure : model.getPiece()) {
      for (int i = 1; i <= measure.numBeats(); i++) {
        int beat = measure.findBeat(i);
        if ((beat * tempo) < start) {
          continue;
        }
        receiver.send(new ShortMessage(ShortMessage.NOTE_ON,
                        measure.getBeats().get(beat).getInstrument(),
                        measure.getNote().toInt(),
                        measure.getBeats().get(beat).getVolume()),
                (beat * tempo) - start);
        receiver.send(new ShortMessage(ShortMessage.NOTE_OFF,
                        measure.getBeats().get(beat).getInstrument(),
                        measure.getNote().toInt(),
                        measure.getBeats().get(beat).getVolume()),
                ((measure.getBeatEnd(i) + 1) * tempo) - start);
      }
    }
  }


  @Override
  public void changeTime(int time) {
    if (paused && currentTime + time >= 0 && currentTime + time <=
            (model.getNumRows() + 1) * tempo) {
      currentTime = currentTime + time;
    }
  }

  @Override
  public void initialize() {
    if (model == null || tempo == -1) {
      throw new IllegalStateException("Null Model or Tempo");
    }
    startMidi();
    daCapo(0);
    goTime();
  }
}