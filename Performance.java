import java.util.LinkedList;
import java.util.Iterator;

public class Performance implements Iterable<SectionList> {
   private final char SECT_SEP = '-';
   private LinkedList<SectionList> lists;
   private StatMap stats;
   private String venue;
   private int tempo;
   private String soloCode, melodyCode;

   // CONSTRUCTOR
   public Performance(String venueIn, int tempoIn, String soloIn, String melodyIn) {
      venue = venueIn;
      tempo = tempoIn;
      soloCode = soloIn;
      melodyCode = melodyIn;
      
      lists = new LinkedList<SectionList>();
      
      buildSections(melodyCode);
      buildSections(soloCode);
      //sumNotes();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<SectionList> iterator() {
      return lists.iterator();
   }
   
   /////// METHODS ///////
   
   // Populates a list of sections according to given code
   public void buildSections(String code) {
      if (!code.isEmpty()) {
         SectionList list = new SectionList(code.charAt(0));
         String tempCode = code.substring(1);
         int division = 0;
      
         while (division != -1) {
            division = tempCode.indexOf(SECT_SEP);
            if (division == -1) {
               list.add(new Section(tempCode.substring(0)));
            }
            else {
               list.add(new Section(tempCode.substring(0, division)));
               tempCode = tempCode.substring(division + 1);
            }
         }
         lists.add(list);
      }
   }
   
   public void add(SectionList sectionList) {
      lists.add(sectionList);
   }
   
   public void buildStats() {
      stats = new StatMap();
   
      for (SectionList list : lists) {
         list.buildStats();
         stats.add(list.getStats());
      }
   }
   
   /////// GETTERS ///////
   
   public LinkedList<SectionList> getLists() {
      return lists;
   }
   
   public String getVenue() {
      return venue;
   }
   
   public String getSoloCode() {
      return soloCode;
   }
   
   public String getMelodyCode() {
      return melodyCode;
   }
   
   public int getTempo() {
      return tempo;
   }
   
   public StatMap getStats() {
      return stats;
   }
}