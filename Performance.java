import java.util.LinkedList;
import java.util.Iterator;

public class Performance implements Iterable<Part> {
   private final char SECTION_SEP = '-'; // character separating solo or melody codes from each other
   
   // assigned at construction
   public int tempo;
   public String soloCode, melodyCode, venue;
   
   // assigned with buildSections()
   public LinkedList<Part> parts = new LinkedList<>();
   public int soloCount = 1, melodyCount = 1;
   
   // assigned upon buildStats() being called from parent's buildStats()  
   public StatMap stats = new StatMap();

   // CONSTRUCTOR
   public Performance(String venueIn, int tempoIn, String soloIn, String melodyIn) {
      venue = venueIn;
      tempo = tempoIn;
      soloCode = soloIn;
      melodyCode = melodyIn;
      
      buildSections(melodyCode);
      buildSections(soloCode);
   }
   
   /////// METHODS ///////
   
   // Populates a list of sections according to given code (either solo or melody)
   public void buildSections(String code) {
      if (!code.isEmpty()) {
         int count = (code.charAt(0) == '[') ? soloCount : melodyCount;
         int division = 0;
         while (division != -1) {
            division = code.indexOf(SECTION_SEP);
            if (division == -1) {
               parts.add(new Part(count, code));
            }
            else {
               parts.add(new Part(count, code.substring(0, division)));
               code = code.substring(division + 1);
            }
            count++;
         }
      }
   }
   
   // Build children's stats & build own stats based on those
   public void buildStats() {
      for (Part part : parts) {
         part.buildStats();
         stats.add(part.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Part> iterator() {
      return parts.iterator();
   }
}