import java.util.LinkedList;
import java.util.Iterator;

public class Song implements Iterable<Performance> {
   // assigned at construction
   public String artist, title, key;
   public char[] keyPitches;
   
   // assigned upon addSong() being called from parent Song (directly after construction as well as any additional ones added after)
   public LinkedList<Performance> performances = new LinkedList<>();
   
   // assigned upon buildStats() being called from parent's buildStats()  
   public Stats stats = new Stats();
   
   // CONSTRUCTOR
   Song(String titleIn, String artistIn, String keyIn) {
      title = titleIn;
      artist = artistIn;
      key = keyIn;
      
      keyPitches = Music.getKeyPitches(key);
   }
   
   /////// METHODS ///////
   
   // Build children's stats & build own stats based on those
   public void buildStats() {
      for (Performance performance : performances) {
         performance.buildStats();
         stats.add(performance.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Performance> iterator() {
      return performances.iterator();
   }
}