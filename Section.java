import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class Section  implements Iterable<Measure> {
   private final char MEAS_SEP = '*';
   private StatMap stats;
   private String code, codeMeasures, codeOctaves, codeChords;
   private LinkedList<Measure> measures;
   
   private double[] totals = new double[13];
   private int[] Uintervals = new int[26];
   private int[] Dintervals = new int[26];

   // CONSTRUCTOR
   public Section(String codeIn) {
      code = codeIn;
      measures = new LinkedList<Measure>();
      
      splitSection();
      buildMeasures();
      assignOctaves();
      assignChords();
   }

   // ITERATOR OVERRIDE
   @Override
   public Iterator<Measure> iterator() {
      return measures.iterator();
   }

   /////// METHODS ///////
   
   public void buildMeasures() {
      String tempCode = codeMeasures;
      int division = 0;
   
      while (!tempCode.isEmpty()) {
         division = tempCode.indexOf('/');
         measures.add(new Measure(tempCode.substring(0, division + 1)));
         tempCode = tempCode.substring(division + 1);
      }
   }
   
   public void assignOctaves() {
      int allNotes = 0;
      int o = 0;
      Note prevNote = null;
      int octave = 0;
      int count = 0;
      int interval;
      for (Measure measure : measures) {
         for (Note note : measure) {
            if (note.getPitch() == -1) {
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
            note.setOctave(octave);
            if (prevNote != null) {
               interval = Music.getInterval(prevNote, note);
               note.setInterval(interval);
               if (interval > 0) {
                  Uintervals[interval]++;
               }
               else if (interval < 0) {
                  Dintervals[interval*-1]++;
               }
               else {
                  Uintervals[0]++;
                  Dintervals[0]++;
               }
            }
            count--;
            prevNote = note;
         }
      }
   }

   public void assignChords() {
   
   }

   public void splitSection() {
      int a = code.indexOf(MEAS_SEP);
      String code2 = code.substring(a + 1);
      int b = code2.indexOf(MEAS_SEP);
      codeMeasures = code.substring(0, a);
      //System.out.println("Measures: " + codeMeasures);
      codeOctaves = code2.substring(0, b);
      //System.out.println("Octaves: " + codeOctaves);
      codeChords = code2.substring(b + 1);
      //System.out.println("Chords: " + codeChords);
   }

   public void buildStats() {
      stats = new StatMap();
      
      for (Measure measure : measures) {
         measure.buildStats();
         stats.add(measure.getStats());
      }
      
      Map<String, Map<String, Integer>> totals = stats.get("totals");
      Map<String, Map<String, Integer>> percents = stats.get("percents");
      
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
   
   public LinkedList<Measure> getMeasures() {
      return measures;
   }
   
   public StatMap getStats() {
      return stats;
   }
   
   public String getCode() {
      return code;
   }
   
   public String getCodeMeasures() {
      return codeMeasures;
   }
   
   public String getCodeOctaves() {
      return codeOctaves;
   }
   
   public String getCodeChords() {
      return codeChords;
   }
}