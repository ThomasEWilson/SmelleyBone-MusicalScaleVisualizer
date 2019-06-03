import java.util.LinkedList;
import java.util.Iterator;

public class Section implements Iterable<Measure> {
   private final char SECT_SEP = '*'; // character separating measure section from octave section from chord section
   private final char MEAS_SEP = '/'; // character separating measures from each other

   // assigned at construction
   public String code, codeMeasures, codeOctaves, codeChords;
   
   // assigned in buildMeasures()
   public LinkedList<Measure> measures = new LinkedList<>();
   
   // assigned upon buildStats() being called from parent's buildStats()  
   public StatMap stats = new StatMap();

   // CONSTRUCTOR
   public Section(String codeIn) {
      code = codeIn;
      
      splitSection();
      buildMeasures();
      assignOctaves();
      assignChords();
   }

   /////// METHODS ///////
   
   // builds measures separated by MEAS_SEP
   public void buildMeasures() {
      String tempCode = codeMeasures;
      int division = 0;
   
      while (!tempCode.isEmpty()) {
         division = tempCode.indexOf(MEAS_SEP);
         measures.add(new Measure(tempCode.substring(0, division + 1)));
         tempCode = tempCode.substring(division + 1);
      }
   }
   
   // assigns octaves to every note of every measure for this section based on the imported octave code
   public void assignOctaves() {
      int allNotes = 0;
      int o = 0;
      Note prevNote = null;
      int octave = 0;
      int count = 0;
      int interval;
      for (Measure measure : measures) {
         for (Note note : measure) {
            if (note.pitch == -1) {
               continue;
            }
         // get next octave group
            if (count == 0) {
               octave = Character.getNumericValue(codeOctaves.charAt(o));
               // Multiple instance of octave
               if ((o + 1) != codeOctaves.length() && codeOctaves.charAt(o+1) == '(') {
                  // One digit amount
                  if (codeOctaves.charAt(o+3) == ')') {
                     count = Integer.parseInt(codeOctaves.substring(o+2, o+3));
                     o += 4;
                  }
                  // Two digit amount
                  else {
                     count = Integer.parseInt(codeOctaves.substring(o+2, o+4));
                     o += 5;
                  }
               }
               // Remaining notes
               else if ((o + 1) == codeOctaves.length()) {
                  //System.out.println(octave + " will carry to end");
                  count = 1000;
               }
               // Single instance of octave
               else {
                  count = 1;
                  o++;
               }
            }
            note.octave = octave;
            if (prevNote != null) {
               interval = Music.getInterval(prevNote, note);
               note.interval = interval;
            }
            count--;
            prevNote = note;
         }
      }
   }

   // assigns chords to every measure for this section based on the imported chord code
   public void assignChords() {
   // find chord name(s) associated with measure
   // get that chord in this song's key
   }

   // splits the entire solo or melody into measures, octaves, and chords
   public void splitSection() {
      int a = code.indexOf(SECT_SEP);
      String code2 = code.substring(a + 1);
      int b = code2.indexOf(SECT_SEP);
      codeMeasures = code.substring(0, a);
      //System.out.println("Measures: " + codeMeasures);
      codeOctaves = code2.substring(0, b);
      //System.out.println("Octaves: " + codeOctaves);
      codeChords = code2.substring(b + 1);
      //System.out.println("Chords: " + codeChords);
   }
   
   // Build children's stats & build own stats based on those
   public void buildStats() {
      for (Measure measure : measures) {
         measure.buildStats();
         stats.add(measure.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Measure> iterator() {
      return measures.iterator();
   }
}