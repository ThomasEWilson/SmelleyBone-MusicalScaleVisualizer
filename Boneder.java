import java.sql.*;
import java.util.*;

public class Boneder {
   
   public static LinkedList<Song> songs;
   public static LinkedList<Scale> scales;
   
   private static Connection connection;
   private static StatMap stats;
   
   // Screen Controllers
   //private static Controller login_c;
   
   public static DataAdapter dataAdapter;

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
      scales = new LinkedList<Scale>();
      songs = new LinkedList<Song>();
      dataAdapter = new DataAdapter(connection);
      dataAdapter.loadScales();
      dataAdapter.loadSongs();
      
      // For each song
      for (Song song : songs) {
         System.out.print(song.getTitle());
         for (Performance performance : song) {
            System.out.println(" - " + performance.getVenue() + "\n");
            for (SectionList list : performance) {
               //System.out.println(list.getName() + "\n");
               for (Section section : list) {
                  section.getStats().summary();
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
      
      GUI.run();
   }
   
   /////// METHODS ///////
   
   public static void addSong(String titleIn, String artistIn, String keyIn, String venueIn, int tempoIn, String soloIn, String melodyIn) {
      boolean found = false;
      for (Song song : songs) {
         if (song.getTitle().equals(titleIn)) {
            System.out.println("Existing " + titleIn + " found");
            song.add(new Performance(venueIn, tempoIn, soloIn, melodyIn));
            found = true;
         }
      }
      if (!found) {
         Song newSong = new Song(titleIn, artistIn, keyIn);
         newSong.add(new Performance(venueIn, tempoIn, soloIn, melodyIn));
         songs.add(newSong);
      }
   }
   
   public static void addScale(String nameIn, String degreesIn, String formulaIn, String infoIn) {
      boolean found = false;
      if (scales != null) {
         for (Scale scale : scales) {
            if (scale.getName().equals(nameIn)) {
               System.out.println("Existing " + nameIn + " Scale found");
               found = true;
            }
         }
      }
      if (!found) {
         Scale scale = new Scale(nameIn, degreesIn, formulaIn, infoIn);
         scales.add(scale);
      }
   }
   
   public void buildStats() {
      stats = new StatMap();
   
      for (Song song : songs) {
         song.buildStats();
         stats.add(song.getStats());
      }
   }
   
   /////// GETTERS ///////
}