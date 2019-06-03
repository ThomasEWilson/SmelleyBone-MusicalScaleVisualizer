import java.util.LinkedList;
import java.util.Iterator;

public class Album implements Iterable<Song> {

   // assigned at construction
   public String name, artist;
   public LinkedList<Song> songs = new LinkedList<>();
   
   // assigned when buildStats() is called
   public StatMap stats = new StatMap();

   // CONSTRUCTOR
   public Album(String nameIn, String artistIn) {
      name = nameIn;
      artist = artistIn;
   }
   
   /////// METHODS ///////
   
   // Build stats based on children
   public void buildStats() {
      for (Song song : songs) {
         stats.add(song.stats);
      }
      
      stats.calculatePercents();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Song> iterator() {
      return songs.iterator();
   }
}