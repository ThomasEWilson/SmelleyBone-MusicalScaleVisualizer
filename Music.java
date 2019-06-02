import java.util.LinkedList;

public class Music {
   static String[] FLATS = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
   static String[] SHARPS = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
   static int[] PITCHES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
   static String[] INTERVALS = {"P1/d2", "m2/A1", "M2/d3", "m3/A2", "M3/d4", "P4/A3", "D5/A4/TT", "P5/d6", "m6/A5", "M6/d7", "m7/A6", "M7/d8", "P8/A7/d9", "m9/A8", "M9/d10", "m10/A9", "M10/d11", "P11/A10", "d12/A11", "P12/d13", "m13/A12", "M13/d14", "m14/A13", "M14/d15", "P15/A14", "A15"};
   static String[] MODES = {"Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian"};

   /////// METHODS ///////

   public static int charToPitch(char pitchIn) {
      return stringToPitch(Character.toString(pitchIn));
   }

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

   public static char[] getKeyPitches(String key) {
      char[] keyPitches = {'0','0','0','0','0','0','0','0','0','0','0','0'};
      int root;
      switch (key) {
         case "C": root = 0;
            break;
         case "Db":
         case "C#": root = 1;
            break;
         case "D": root = 2;
            break;
         case "Eb": root = 3;
            break;
         case "E":
         case "Fb": root = 4;
            break;
         case "F": root = 5;
            break;
         case "Gb":
         case "F#": root = 6;
            break;
         case "G": root = 7;
            break;
         case "Ab":
         case "G#": root = 8;
            break;
         case "A": root = 9;
            break;
         case "Bb": root = 10;
            break;
         case "B":
         case "Cb": root = 11;
            break;
         default: root = 0;
            break;
      }
      
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
  
   public static String[] getAccidentalsOf(String key) {
      if (key.contains("b") || key.equals("F") || key.equals("C")) {
         return FLATS;
      }
      else {
         return SHARPS;
      }
   }
   
   public static int getInterval(Note a, Note b) {
      int pA = a.getPitch();
      int pB = b.getPitch();
      int oA = a.getOctave();
      int oB = b.getOctave();
      int interval = (oB + 1)*12 + pB - ((oA + 1)*12 + pA);
      
      //System.out.println("Pitch A: " + pA + ", Pitch B: " + pB + ", Octave A: " + oA + ", Octave B: " + oB + " ... Interval: " + interval);
      
      return interval;
   }
}