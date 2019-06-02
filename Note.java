public class Note {
 
   // assigned at construction
   private double length, modifier;
   private String lengthName;
   private char pitchName;
   private int pitch;
   
   // assigned independently
   private int octave, interval;
   private String chord;

   // CONSTRUCTOR
   Note(char name, int lengthIn, double modifierIn) {
      pitchName = name;
      modifier = modifierIn;
      assignPitch(name);
      assignLength(lengthIn);
   }
   
   // METHODS
   private void assignPitch(char pitchIn) {
      switch (pitchIn) {
         case 'c': 
         case 'B': pitch = 11;
            break;
         case 'b': pitch = 10;
            break;
         case 'A': pitch = 9;
            break;
         case 'a': pitch = 8;
            break;
         case 'G': pitch = 7;
            break;
         case 'g': pitch = 6;
            break;
         case 'F': pitch = 5;
            break;
         case 'f':
         case 'E': pitch = 4;
            break;
         case 'e': pitch = 3;
            break;
         case 'D': pitch = 2;
            break;
         case 'd': pitch = 1;
            break;
         case 'C': pitch = 0;
            break;
         default: pitch = -1;
            break;
      }
   }
   
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
   
   // GETTERS
   public int getPitch() {
      return pitch;
   }
   
   public char getPitchName() {
      return pitchName;
   }
   
   public double getLength() {
      return length;
   }
   
   public String getLengthName() {
      return lengthName;
   }
   
   public int getOctave() {
      return octave;
   }
   
   public int getInterval() {
      return interval;
   }
   
   // SETTERS
   public void setOctave(int octave) {
      this.octave = octave;
   }
   
   public void setInterval(int interval) {
      this.interval = interval;
   }
}