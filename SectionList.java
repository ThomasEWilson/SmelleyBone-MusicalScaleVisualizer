import java.util.LinkedList;
import java.util.Iterator;

public class SectionList  implements Iterable<Section> {
   private LinkedList<Section> sections;
   private String name;
   private StatMap stats;

   // CONSTRUCTOR
   public SectionList (char id) {
      sections = new LinkedList<Section>();
      name = (id == 's') ? "Solos" : ((id == 'm') ? "Melodies" : "?");
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Section> iterator() {
      return sections.iterator();
   }
   
   /////// METHODS ///////
   
   public void add(Section section) {
      sections.add(section);
   }
   
   public void buildStats() {
      stats = new StatMap();
   
      for (Section section : sections) {
         section.buildStats();
         stats.add(section.getStats());
      }
   }
   
   /////// GETTERS ///////
   
   public LinkedList<Section> getSections() {
      return sections;
   }
   
   public String getName() {
      return name;
   }
   
   public StatMap getStats() {
      return stats;
   }
}