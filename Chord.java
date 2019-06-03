import java.util.LinkedList;

public class Chord {
   // Chords exists as info-holders while in the list of all chords (within Music.java)
   // They have notes assigned (withRoot(String note)) once they are needed to be children of parts of songs that have been imported
   
   // assigned at construction
   public String name, symbol, quality, formula, notation, info;
   
   // not assigned unless withRoot(String note) is called 
   public LinkedList<Note> notes;

   // CONSTRUCTOR
   public Chord(String nameIn, String symbolIn, String qualityIn, String formulaIn, String notationIn, String infoIn) {
      name = nameIn;
      symbol = symbolIn;
      quality = qualityIn;
      formula = formulaIn;
      notation = notationIn;
      info = infoIn;
   }
   
   /////// METHODS ///////
   
   // returns pitches of chord based on given root note (probably use notation for this)
   public Chord withRoot(String note) {
      Chord chord = new Chord(name, symbol, quality, formula, notation, info);
   
   // add to chord's notes here based on notation
   
      return chord;
   }
}