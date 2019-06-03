public class Note {
 
   // assigned at construction
   public double length, modifier;
   public String lengthName;
   public char pitchName;
   public int pitch;
   
   // assigned in Section.assignOctaves() & assignChords()
   public int octave, interval;
   public String chord;

   // CONSTRUCTOR
   Note(char name, int lengthIn, double modifierIn) {
      pitchName = name;
      modifier = modifierIn;
      pitch = Music.charToPitch(name);
      assignLength(lengthIn);
   }
   
   /////// METHODS ///////
   
   // assign mathematical length value (0 to 1) based on imported code length integer
   private void assignLength(int lengthIn) {
      switch (lengthIn) {
         case 1: length = 1; // whole
            lengthName = "whole";
            break;
         case 2: length = 0.5; // half
            lengthName = "half";
            break;
         case 4: length = 0.25; // quarter
            lengthName = "quarter";
            break;
         case 6: length = 0.0625; // sixteenth
            lengthName = "sixteenth";
            break;
         case 7: length = 0.03125; // thirty-second
            lengthName = "thirty-second";
            break;
         case 8: length = 0.125; // eighth
            lengthName = "eighth";
            break;
         default: length = 0; // error?
            lengthName = "error";
            break;
      }
      
      if (modifier > 1) {
         lengthName += " (.)";
      }
      else if (modifier < 1) {
         lengthName += " (T)";
      }
      
      length *= modifier;
   }
}