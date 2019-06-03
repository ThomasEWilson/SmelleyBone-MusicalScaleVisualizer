import java.util.LinkedList;

public class Scale {
  // assigned at construction
   public String name, formula, info;
   public int[] degrees;

   // CONSTRUCTOR
   public Scale(String nameIn, String degreesIn, String formulaIn, String infoIn) {
      name = nameIn;
      formula = formulaIn;
      info = (infoIn == null) ? " " : infoIn;
      degrees = new int[12];
      for (int i = 0; i < 12; i++) {
         degrees[i] = Character.getNumericValue(degreesIn.charAt(i));
      }
   }
   
   /////// METHODS ///////
   
   // returns scale pitches rooted at 'key' (not necessarily in that key) - fix later
   public String[] getInKey(String key) {
      String[] notes = new String[12];
      String[] accidentals = Music.getAccidentalsOf(key);
      int position = Music.stringToPitch(key);
      int count = 0;
      while (count < 12) {
         if (degrees[count] == 1) {
            notes[count] = accidentals[position];
         }
         else {
            notes[count] = "-";
         }
         count++;
         position++;
         if (position == 12) {
            position = 0;
         }
      }
      return notes;
   }
}