import java.sql.*;
import java.util.*;

public class Boneder {
   
   // assigned in main method
   private static Connection connection;
   public static DataAdapter dataAdapter;
   
   // assigned upon loadSongs() being called from DataAdapter
   public static LinkedList<Song> songs = new LinkedList<>();
   
   // assigned with buildStats()
   private static StatMap stats = new StatMap();

   public static void main(String[] args) {
      // create SQLite database connection
      try {
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:Boneder.db");
      }
      catch (ClassNotFoundException ex) {
         System.out.println("ERROR: SQLite is not installed");
         System.exit(1);
      }
      catch (SQLException ex) {
         System.out.println("ERROR: SQLite database is not ready");
         System.exit(2);
      }
      dataAdapter = new DataAdapter(connection);
      
      // import database
      dataAdapter.loadScales();
      dataAdapter.loadSongs();
      dataAdapter.loadChords();
      
      // calculate all the stats
      buildStats();
      
      summary();
      
      GUI.run();
   }
   
   /////// METHODS ///////
   
   // adds new song (and performance) to song list, adds new performance to song if song already exists
   public static void addSong(String titleIn, String artistIn, String keyIn, String venueIn, int tempoIn, String soloIn, String melodyIn) {
      boolean found = false;
      for (Song song : songs) {
         if (song.title.equals(titleIn)) {
            System.out.println("Existing " + titleIn + " found");
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
   
   // // Build children's stats & build own stats based on those
   public static void buildStats() {
      for (Song song : songs) {
         song.buildStats();
         stats.add(song.stats);
      }
      
      stats.calculatePercents();
   }
   
   // displays summary of boneder
   public static void summary() {
   
   // For each song
      for (Song song : songs) {
         System.out.print(song.title);
         for (Performance performance : song) {
            System.out.println(" - " + performance.venue + "\n");
            for (SectionList list : performance) {
               //System.out.println(list.getName() + "\n");
               for (Section section : list) {
                  section.stats.summary();
                  for (Measure measure : section) {
                     for (Note note : measure) {
                        //System.out.println("p: " + note.getPitchName() + ", o: " + note.getOctave() + ", i: " + note.getInterval() + note.getLengthName());
                     }
                  }
               }
            }
         }
         break;
      }
   }
}