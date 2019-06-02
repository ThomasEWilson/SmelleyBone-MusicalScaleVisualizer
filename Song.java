import java.util.LinkedList;
import java.util.Iterator;

public class Song implements Iterable<Performance> {
   private LinkedList<Performance> performances;
   private String artist, title, key;
   private char[] keyPitches;
   private String[] codeByMeasure;
   private StatMap stats;
   
   // Totals
   private double inKey;
   
   // CONSTRUCTOR
   Song(String titleIn, String artistIn, String keyIn) {
      title = titleIn;
      artist = artistIn;
      key = keyIn;
      
      performances = new LinkedList<Performance>();
      keyPitches = Music.getKeyPitches(key);
   }
   
   // ITERATOR OVERRIDE
   @Override
   public Iterator<Performance> iterator() {
      return performances.iterator();
   }
   
   /////// METHODS ///////
   
   public void add(Performance performanceIn) {
      performances.add(performanceIn);
   }
   
   public void buildStats() {
      stats = new StatMap();
   
      for (Performance performance : performances) {
         performance.buildStats();
         stats.add(performance.getStats());
      }
   }
   
   
   /////// GETTERS ///////
   
   public LinkedList<Performance> getPerformances() {
      return performances;
   }
   
   public String getTitle() {
      return title;
   }
   
   public String getKey() {
      return key;
   }
   
   public String getArtist() {
      return artist;
   }
   
   public StatMap getStats() {
      return stats;
   }
   
   /*
   public void summary() {
      int measure = 0;
      String[] noteNames = (key.length() == 2 || key.equals("C") || key.equals("F")) ? Theory.FLATS : Theory.SHARPS;
      System.out.println(title + " - " + artist);
      
      // Note Totals
      for (int i = 0; i < totals.length; i++) {
         if (totals[i] > 0) {
            System.out.print("\n\n" + noteNames[i] + ": " + GUI.formatter.format(totals[i]/measures.length*100) + " %");
            if (i > 0) {
               System.out.print("\t(" + GUI.formatter.format(totals[i]/(measures.length - totals[0])*100) + " %)");
            }
         }
      }
   }
   
   private void sumNotes() {
      int pitch;
      double length;
      for (int i = 0; i < notes.size(); i++) {
         pitch = notes.get(i).pitch + 1;
         length = notes.get(i).length;
         totals[pitch] += length;
         if (pitch > 0 && keyPitches[pitch - 1] == 1) {
            inKey += length;
         }
      }
   }
*/
}