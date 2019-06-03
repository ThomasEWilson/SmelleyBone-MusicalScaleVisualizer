import java.util.LinkedList;

public class Progression {
   private LinkedList<Chord> chords;
   private String name;

   // CONSTRUCTOR
   public Progression() {
      chords = new LinkedList<Chord>();
   }
   
   /////// METHODS ///////
   
   
   
   /////// GETTERS ///////
   
   public String getName() {
      return name;
   }
   
   public LinkedList<Chord> getChords() {
      return chords;
   }
}