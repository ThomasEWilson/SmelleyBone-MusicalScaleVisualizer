import java.util.LinkedList;
import java.util.Iterator;

public class Album implements Iterable<Song> {
   private LinkedList<Song> songs;

   // CONSTRUCTOR
   public Album() {
      songs = new LinkedList<Song>();
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Song> iterator() {
      return songs.iterator();
   }
   
   public LinkedList<Song> getSongs() {
      return songs;
   }
}