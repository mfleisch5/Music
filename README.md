# Music Editor.java


Welcome to Music Editor! Here are a few answers to your questions.

Notes must have a pitch (int) and an octave (enum). Pitches have corresponding numbered notes from 0 to 119.

Next we have measures. Pieces of music are just certain notes and their list of beats. Notes, at any given time, are either a Head, A Sustain,
or they are Rest (an enum called BeatType), their volume, their instrument, and the time in which they start and end.

## Model
I start with four fields: The piece, which is just a list of measures, the number of rows in the piece, and the highest and lowest note. So, in order to manipulate
pieces, we have readjust, addNote, edit, remove, and move.

I wanted to make adding notes as easy as possible, so users can input a specific note, the time
they want the note to start, and its duration. (Notes can write over other notes)

Next we have edit, remove, and move. These take in an actual
beat number (number of beats in the measure are counted) to make things more easy for the user.

Next we have our static methods, and a few others which are in the Javadoc comments.

## Controller
1. Clicking a note and pressing R removes the note.
2. Clicking a note and pressing S makes a note shorter.
3. Clicking a note and pressing L makes a note longer.
4. Clicking a note and pressing any of the arrows moves the note.
5. Clicking anywhere on the piece and pressing A creates a new notehead.
5. Pressing "esc" moves the view to the end.
6. Pressing "control" moves the view to the beginning.
7. Pressing T creates a new row of higher pitches
8. Pressing G creates a new row of lower pitches
9. Pressing P pauses/plays the music.
10. Pressing 1 moves the current time back a note.
11. Pressing 3 moves the current time forward a note.
12. Pressing Q creates a new start repeat.
13. Pressing Z creates a new play repeat.
15. Clicking on a note and pressing I increases the instrument number.
16. Clicking on a note and pressing K decreases the instrument number.


### Note: UPDATE


We decided to change a Beat from an enum to a Class with a BeatType enum, a Volume, and
an Instrument in order to give each beat a specific instrument and volume. We then overloaded the
addNote method in order to put in the Volume and Instrument parameters, which set the Volume and
Instrument fields to whatever the addNote method says it should be. We also changed the Note.toInt
and vice versa method in order to account for the 0 octave.

We created a View interface that takes only the method initialize() which initializes each view.
For Midi, it creates a new empty frame and plays the note on the GUI. For the Console, it just
prints the getMusicState (it's only method), and for the Frame, it sets it to visible.

The Frame contents are put into a scrollable panel which has a preferred size set to the size of the
model. The Frame's size is the size of the model, and the model is set by the builder.build()
method. The CompositionBuilder got a tempo getter added to it in order to use that for MIDI and
construct the right time for notes. We also set the notes to start a second past the start time,
in order to let it load.

The main method then takes in two arguments and determines which view to portray. And you can see
that for yourself!

- Michael and Sarah

### Note: UPDATE 2

We now have a controller class which relates the keyboardhandler and mousehandler to the different
views. It has a method called putKeys() which creates the runnables and assigns them to different
keys. We also have a method called readjustView() which readjusts the view according to the new
model.

We also updated the ConcreteGuiViewPanel to include a red line that moves as the song is playing.
