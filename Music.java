import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class Music {
   public static String[] PITCHES = {"(B#) C ", "(C#) Db", "D ", "(D#) Eb", "(Fb) E ", "F ", "(F#) Gb", "G ", "(G#) Ab", "A ", "(A#) Bb", "(Cb) B "};
   public static String[] FLATS = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
   public static String[] SHARPS = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
   public static String[] INTERVALS = {"P1", "m2/A1", "M2/d3", "m3/A2", "M3/d4", "P4/A3", "D5/A4", "P5/d6", "m6/A5", "M6/d7", "m7/A6", "M7/d8", "P8", "m9/A8", "M9/d10", "m10/A9", "M10/d11", "P11/A10", "d12/A11", "P12/d13", "m13/A12", "M13/d14", "m14/A13", "M14/d15", "P15/A14", "A15"};
   public static String[] MODES = {"Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian"};
   
   // imported from database
   public static LinkedList<Scale> SCALES = new LinkedList<Scale>();
   public static LinkedList<Chord> CHORDS = new LinkedList<Chord>();
   public static LinkedList<Progression> PROGRESSIONS = new LinkedList<Progression>(); // not yet

   /////// METHODS ///////

   // see 'stringToPitch', this is just the character parameter version
   public static int charToPitch(char pitchIn) {
      return stringToPitch(Character.toString(pitchIn));
   }

   // converts string note name to pitch integer (lowercase refers to flat)
   public static int stringToPitch(String pitchIn) {
      switch (pitchIn) {
         case "Cb":
         case "c": 
         case "B": 
            return 11;
         case "b":
         case "Bb":
         case "A#": 
            return 10;
         case "A": 
            return 9;
         case "Ab":
         case "a":
         case "G#": 
            return 8;
         case "G": 
            return 7;
         case "Gb":
         case "g":
         case "F#": 
            return 6;
         case "F":
         case "E#": 
            return 5;
         case "Fb":
         case "E":
         case "f": 
            return 4;
         case "Eb":
         case "D#":
         case "e": 
            return 3;
         case "D": 
            return 2;
         case "d":
         case "Db":
         case "C#": 
            return 1;
         case "C":
         case "B#": 
            return 0;
         default: 
            return -1;
      }
   }

   // converts 'key' string to 12-character array of 1 & 0 to represent where pitches reside (major scale only at the moment)
   // Ex: C would return [1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1], whereas Db or C# would be shifted to the right one
   // After importing scales (such as the major scale), this method is probably redundant
   public static char[] getKeyPitches(String key) {
      char[] keyPitches = {'0','0','0','0','0','0','0','0','0','0','0','0'};
      int root = stringToPitch(key);
      
      keyPitches[root++] = 1;
      keyPitches[(++root > 11) ? root++ - 12 : root++] = 1; // whole
      keyPitches[(++root > 11) ? root++ - 12 : root++] = 1; // whole
      keyPitches[(root > 11) ? root++ - 12 : root++] = 1;   // half
      keyPitches[(++root > 11) ? root++ - 12 : root++] = 1; // whole
      keyPitches[(++root > 11) ? root++ - 12 : root++] = 1; // whole
      keyPitches[(++root > 11) ? root++ - 12 : root++] = 1; // whole
      keyPitches[(root > 11) ? root - 12 : root] = 1;       // half
      
      return keyPitches;
   }
  
   // returns note names corresponding to the key (flats vs sharps)
   public static String[] getAccidentalsOf(String key) {
      if (key.contains("b") || key.equals("F") || key.equals("C")) {
         return FLATS;
      }
      else {
         return SHARPS;
      }
   }
   
   // returns the distance from note a to note b (half step = 1)
   public static int getInterval(Note a, Note b) {
      int pA = a.pitch;
      int pB = b.pitch;
      int oA = a.octave;
      int oB = b.octave;
      int interval = (oB + 1)*12 + pB - ((oA + 1)*12 + pA);
      
      //System.out.println("Pitch A: " + pA + ", Pitch B: " + pB + ", Octave A: " + oA + ", Octave B: " + oB + " ... Interval: " + interval);
      
      return interval;
   }
   
   // adds scale with given info to scale list if it doesn't already exist
   public static void addScale(String nameIn, String degreesIn, String formulaIn, String infoIn) {
      boolean found = false;
      if (SCALES != null) {
         for (Scale scale : SCALES) {
            if (scale.name.equals(nameIn)) {
               System.out.println("Existing " + nameIn + " Scale found");
               found = true;
            }
         }
      }
      if (!found) {
         Scale scale = new Scale(nameIn, degreesIn, formulaIn, infoIn);
         SCALES.add(scale);
      }
   }
   
   // adds chord with given info to chord list if it doesn't already exist
   public static void addChord(String nameIn, String symbolIn, String qualityIn, String formulaIn, String notationIn, String infoIn) {
      boolean found = false;
      if (CHORDS != null) {
         for (Chord chord : CHORDS) {
            if (chord.name.equals(nameIn)) {
               System.out.println("Existing " + nameIn + " Chord found");
               found = true;
            }
         }
      }
      if (!found) {
         Chord chord = new Chord(nameIn, symbolIn, qualityIn, formulaIn, notationIn, infoIn);
         CHORDS.add(chord);
      }
   }
}