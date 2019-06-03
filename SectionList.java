import java.util.LinkedList;
import java.util.Iterator;

public class SectionList implements Iterable<Section> {

   // assigned at construction
   public String name;
   
   // assigned upon buildSections() being called from parent Performance
   public LinkedList<Section> sections = new LinkedList<>();
   
   // assigned upon buildStats() being called from parent's buildStats()  
   public StatMap stats = new StatMap();

   // CONSTRUCTOR
   public SectionList (char id) {
      name = (id == 's') ? "Solos" : ((id == 'm') ? "Melodies" : "?");
   }
   
   /////// METHODS ///////
   
   // Build children's stats & build own stats based on those
   public void buildStats() {
      for (Section section : sections) {
         section.buildStats();
         stats.add(section.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Section> iterator() {
      return sections.iterator();
   }
}