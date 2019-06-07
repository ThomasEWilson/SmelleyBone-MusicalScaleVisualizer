import java.util.LinkedList;
import java.util.Iterator;

public class Album implements Iterable<Song> {

   // assigned at construction
   public String name, artist;
   public LinkedList<Song> songs = new LinkedList<>();
   
   // assigned when buildStats() is called
   public Stats stats = new Stats();

   // CONSTRUCTOR
   public Album(String nameIn, String artistIn) {
      name = nameIn;
      artist = artistIn;
   }
   
   /////// METHODS ///////
   
   // adds new song (and performance) to song list, adds new performance to song if song already exists
   public void add(String titleIn, String artistIn, String venueIn, String keyIn, int tempoIn, String soloIn, String melodyIn) {
      boolean found = false;
      for (Song song : songs) {
         if (song.title.equals(titleIn)) {
            System.out.println("Existing Song '" + titleIn + "' found");
            song.performances.add(new Performance(venueIn, tempoIn, soloIn, melodyIn));
            found = true;
         }
      }
      if (!found) {
         Song newSong = new Song(titleIn, artistIn, keyIn);
         newSong.performances.add(new Performance(venueIn, tempoIn, soloIn, melodyIn));
         songs.add(newSong);
      }
   }
   
   
   // Build stats based on children
   public void buildStats() {
      for (Song song : songs) {
         song.buildStats();
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