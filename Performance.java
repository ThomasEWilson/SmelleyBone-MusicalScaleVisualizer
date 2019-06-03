import java.util.LinkedList;
import java.util.Iterator;

public class Performance implements Iterable<SectionList> {
   private final char SECT_SEP = '-'; // character separating solo or melody codes from each other
   
   // assigned at construction
   public int tempo;
   public String soloCode, melodyCode, venue;
   
   // assigned with buildSections()
   public LinkedList<SectionList> lists = new LinkedList<>();
   
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
         SectionList list = new SectionList(code.charAt(0));
         String tempCode = code.substring(1);
         int division = 0;
      
         while (division != -1) {
            division = tempCode.indexOf(SECT_SEP);
            if (division == -1) {
               list.sections.add(new Section(tempCode.substring(0)));
            }
            else {
               list.sections.add(new Section(tempCode.substring(0, division)));
               tempCode = tempCode.substring(division + 1);
            }
         }
         lists.add(list);
      }
   }
   
   // Build children's stats & build own stats based on those
   public void buildStats() {
      for (SectionList list : lists) {
         list.buildStats();
         stats.add(list.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<SectionList> iterator() {
      return lists.iterator();
   }
}