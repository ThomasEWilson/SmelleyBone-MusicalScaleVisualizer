import java.util.LinkedList;
import java.util.Iterator;

public class Catalog implements Iterable<Album> {
   
   // assigned upon loadSongs() being called from DataAdapter
   public static LinkedList<Album> albums = new LinkedList<>();
   
   // assigned with buildStats()
   public static Stats stats = new Stats();
   
   /////// METHODS ///////
   
   // adds new song to corresponding album, adds new album to catalog if it doesn't exist yet
   public static void add(String titleIn, String artistIn, String albumIn, String venueIn, String keyIn, int tempoIn, String soloIn, String melodyIn) {
      boolean found = false;
      for (Album album : albums) {
         // album exists
         if (album.name.equals(albumIn)) {
            System.out.println("Existing Album '" + albumIn + "' found");
            album.add(titleIn, artistIn, venueIn, keyIn, tempoIn, soloIn, melodyIn);
            found = true;
            break;
         }
      }
   
   // album doesn't exist yet
      if (!found) {
      
      // create it
         Album newAlbum = new Album(albumIn, artistIn);
         newAlbum.add(titleIn, artistIn, venueIn, keyIn, tempoIn, soloIn, melodyIn);
         albums.add(newAlbum);
      }
   }
   
   // Build children's stats & build own stats based on those
   public static void buildStats() {
      for (Album album : albums) {
         album.buildStats();
         stats.add(album.stats);
      }
      
      stats.calculatePercents();
   }
   
   // displays summary of catalog
   public static void summary() {
      System.out.println("-----------\n  Catalog\n-----------");
      stats.summary();
      for (Album album : albums) {
         //System.out.println("Album: " + album.name + "\nArtist: " + album.artist);
         //album.stats.summary();
         for (Song song : album) {
            //System.out.println("Song: " + song.title);
            //song.stats.summary();
            for (Performance performance : song) {
               //System.out.println("Venue: " + performance.venue);
               //performance.stats.summary();
               for (Part part : performance) {
                  //System.out.println("Part: " + part.name);
                  //part.stats.summary();
                  for (Measure measure : part) {
                        //System.out.println();
                     for (Note note : measure) {
                           //System.out.println("p: " + note.pitch + "   l: " + note.length + "   o: " + note.octave + "   i: " + note.interval);
                     }
                  }
               }
            }
            break;
         }
      }
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Album> iterator() {
      return albums.iterator();
   }
}