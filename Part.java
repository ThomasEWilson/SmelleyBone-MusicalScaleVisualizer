import java.util.LinkedList;
import java.util.Iterator;

public class Part implements Iterable<Measure> {
   private final char SECTION_SEP = '*'; // character separating measure section from octave section from chord section
   private final char MEASURE_SEP = '/'; // character separating measures from each other
   private final char PI_SEP = ':';      // character separating player from instrument

   // assigned at construction
   public String code, codeMeasures, codeOctaves, codeChords, type, instrument, player;
   public int num;
   
   // assigned in buildMeasures()
   public LinkedList<Measure> measures = new LinkedList<>();
   
   // assigned upon buildStats() being called from parent's buildStats()  
   public StatMap stats = new StatMap();

   // CONSTRUCTOR
   public Part(int numIn, String codeIn) {
      num = numIn;
      code = codeIn;
      if (code.charAt(0) == '[') { 
         int division = code.indexOf(PI_SEP);
         player = code.substring(1, division);
         int division2 = code.indexOf(']');
         instrument = code.substring(division + 1, division2);
         code = code.substring(division2 + 1);
         type = "Solo";
      }
      else {
         type = "Melody";
      }
      
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
         division = tempCode.indexOf(MEASURE_SEP);
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
      int a = code.indexOf(SECTION_SEP);
      String code2 = code.substring(a + 1);
      int b = code2.indexOf(SECTION_SEP);
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