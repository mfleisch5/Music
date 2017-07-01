package cs3500.music.util;

import org.junit.Test;

import java.io.StringReader;

import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.MusicEditorCreator;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MusicReader class.
 */
public class MusicReaderTest {
  private MusicReader reader = new MusicReader();
  private Readable readable0 = new StringReader("This string is bad!");
  private Readable readable1 = new StringReader("10 This string is only half bad.");
  private Readable readable2 = new StringReader("tempo 200000\n" +
          "note 0 2 1 64 72\n" +
          "note 0 7 1 55 70\n" +
          "note 2 4 1 62 72\n" +
          "note 4 6 1 60 71\n" +
          "note 6 8 1 62 79\n" +
          "note 8 15 1 55 79\n" +
          "note 8 10 1 64 85\n" +
          "note 10 12 1 64 78\n" +
          "note 12 15 1 64 74\n" +
          "note 16 24 1 55 77\n" +
          "note 16 18 1 62 75\n" +
          "note 18 20 1 62 77\n" +
          "note 20 24 1 62 75\n" +
          "note 24 26 1 55 79\n" +
          "note 24 26 1 64 82\n" +
          "note 26 28 1 67 84\n" +
          "note 28 32 1 67 75\n" +
          "note 32 40 1 55 78\n" +
          "note 32 34 1 64 73\n" +
          "note 34 36 1 62 69\n" +
          "note 36 38 1 60 71\n" +
          "note 38 40 1 62 80\n" +
          "note 40 48 1 55 79\n" +
          "note 40 42 1 64 84\n" +
          "note 42 44 1 64 76\n" +
          "note 44 46 1 64 74\n" +
          "note 46 48 1 64 77\n" +
          "note 48 56 1 55 78\n" +
          "note 48 50 1 62 75\n" +
          "note 50 52 1 62 74\n" +
          "note 52 54 1 64 81\n" +
          "note 54 56 1 62 70\n" +
          "note 56 64 1 52 72\n" +
          "note 56 64 1 60 73\n");
  private CompositionBuilder<IMusicEditorModel> builder = new MusicEditorCreator.Builder();

  /**
   * Tests the parseFile method by passing in bad input to see if the input it is rejected as it
   * should be.
   */
  @Test(expected = IllegalArgumentException.class)
  public void parseBadFileTest() {
    reader.parseFile(readable0, builder);
    reader.parseFile(readable1, builder);
  }

  /**
   * Tests the parseFile method.
   */
  @Test
  public void parseFileTest() {
    IMusicEditorModel parsedModel = reader.parseFile(readable2, builder);
    assertEquals(parsedModel.getMusicState(),
            "    E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5   G5\n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X                           \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X                                     \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                                  X                           \n" +
            " 7                                                    |                           \n" +
            " 8                 X                                            X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                                                                                \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                            X                 \n" +
            "25                 |                                            |                 \n" +
            "26                                                                             X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X                 \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X                           \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X                                     \n" +
            "37                 |                        |                                     \n" +
            "38                 |                                  X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                            X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X                           \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                            X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X                           \n" +
            "55                 |                                  |                           \n" +
            "56  X                                       X                                     \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     ");
  }
}
