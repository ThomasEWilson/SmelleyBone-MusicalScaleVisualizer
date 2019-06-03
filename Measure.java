import java.util.Map;
import java.util.LinkedList;
import java.util.Iterator;

public class Measure implements Iterable<Note> {

   // assigned upon construction
   public String code; // from database
   public LinkedList<Note> notes = new LinkedList<>();
   
   // assigned upon buildStats() being called from parent's buildStats()   
   public StatMap stats = new StatMap();
   
   // CONSTRUCTOR
   Measure(String codeIn) {
      code = codeIn;
      
      assignNotes();
   }
   
   /////// METHODS ///////
   
   // build note sequence based on imported database code
   public void assignNotes() {
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
   
   // builds initial stats to be the base stat map upon which all others create their own
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
         if (note.pitch != -1) {
            stats.noteCount++;  
         }
         
         // get relevant note values
         String pitch = Integer.toString(note.pitch);
         String octave = Integer.toString(note.octave);
         String interval = Integer.toString(note.interval);
         String length = Double.toString(note.length);
         
         // add values to map
         pitches.put(pitch, (pitches.containsKey(pitch)) ? pitches.get(pitch) + 1 : 1);
         octaves.put(octave, (octaves.containsKey(octave)) ? octaves.get(octave) + 1 : 1);
         intervals.put(interval, (intervals.containsKey(interval)) ? intervals.get(interval) + 1 : 1); 
         if (note.interval > 0) { pos++; } 
         if (note.interval < 0) { neg++; }
         lengths.put(length, (lengths.containsKey(length)) ? lengths.get(length) + 1 : 1);
      }
      intervals.put("+", pos); intervals.put("-", neg);
      //pitches.put("inKey", inKey);
      
      stats.calculatePercents();
   } 
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Note> iterator() {
      return notes.iterator();
   }
}