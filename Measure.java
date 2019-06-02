import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class Measure  implements Iterable<Note> {
   private String code; // from database
   private String[] chords;
   private LinkedList<Note> notes;
   private StatMap stats;
   
   // CONSTRUCTOR
   Measure(String codeIn) {
      code = codeIn;
      
      assignNotes();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Note> iterator() {
      return notes.iterator();
   }
   
   /////// METHODS ///////
   
   public void assignNotes() {
      notes = new LinkedList<Note>();
      double modifier;
      for (int i = 0; i < code.length(); i++) {
         modifier = 1;
         if (Character.isDigit(code.charAt(i))) { // Number (Rest)
            notes.add(new Note('R', Integer.parseInt(code.substring(i,i+1)), modifier));
         }
         else if (Character.isLetter(code.charAt(i))) { // Letter (Note)
            if (code.charAt(i) == 'x') { // Repeat Last
               notes.add(notes.getLast());
            }
            else {
               String chain = code.substring(i,i+1);
               int length;
               // Collect pitch group
               while (Character.isLetter(code.charAt(++i))) {
                  chain += code.substring(i,i+1);
               }
               if (code.charAt(i) == '3') { // Triplet
                  i++;
                  length = Integer.parseInt(code.substring(i,i+1));
                  modifier = 2.0/3;
                  //System.out.println("Modifier = " + modifier);
               }
               else if (code.charAt(i) == '.') { // Dotted
                  i++;
                  length = Integer.parseInt(code.substring(i,i+1));
                  modifier = 1.5;
               }
               else {
                  length = Integer.parseInt(code.substring(i,i+1)); // Normal notated length
               }
               for (int n = 0; n < chain.length(); n++) {
                  notes.add(new Note(chain.charAt(n), length, modifier));
               }
            }
         }
      }
   }
   
   public void buildStats() {
      stats = new StatMap();
      Map<String, Map<String, Integer>> percents = stats.get("percents");
      Map<String, Map<String, Integer>> totals = stats.get("totals");
      Map<String, Integer> pitches = totals.get("pitches");
      Map<String, Integer> octaves = totals.get("octaves");
      Map<String, Integer> intervals = totals.get("intervals");
      Map<String, Integer> lengths = totals.get("lengths");
      int pos = 0, neg = 0;
      
      // totals
      for (Note note : notes) {
         if (note.getPitch() != -1) {
            stats.incrementNoteCount();  
         }
         
         // get relevant note values
         String pitch = Integer.toString(note.getPitch());
         String octave = Integer.toString(note.getOctave());
         String interval = Integer.toString(note.getInterval());
         String length = Double.toString(note.getLength());
         
         // add values to map
         pitches.put(pitch, (pitches.containsKey(pitch)) ? pitches.get(pitch) + 1 : 1);
         octaves.put(octave, (octaves.containsKey(octave)) ? octaves.get(octave) + 1 : 1);
         intervals.put(interval, (intervals.containsKey(interval)) ? intervals.get(interval) + 1 : 1); 
         if (note.getInterval() > 0) { pos++; } 
         if (note.getInterval() < 0) { neg++; }
         lengths.put(length, (lengths.containsKey(length)) ? lengths.get(length) + 1 : 1);
      }
      intervals.put("+", pos); intervals.put("-", neg);
      //pitches.put("inKey", inKey);
   
      totals.forEach( 
         (category, nestMap) -> {
            nestMap.forEach(
               (key, value) -> {
                  double val = value; // cast to double
                  percents.get(category).put(key, (int)java.lang.Math.round(value/stats.getNoteCount()*100));
               });
         });
   } 
   
   public void statSummary() {
      stats.summary();
   }
   
   /////// GETTERS ///////
   
   public LinkedList<Note> getNotes() {
      return notes;
   }
   
   public String[] getChords() {
      return chords;
   }
   
   public String getCode() {
      return code;
   }
   
   public StatMap getStats() {
      return stats;
   }
}