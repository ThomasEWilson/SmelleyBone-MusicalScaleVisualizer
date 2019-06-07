import java.util.Map;
import java.util.HashMap;

public class Stats {

  // assigned when parent's buildStats() is called
   public Map<String, Map<String, Integer>> totals = new HashMap<String, Map<String, Integer>>();
   public Map<String, Map<String, Double>> percents = new HashMap<String, Map<String, Double>>();
   public String[] categories = {"pitches", "octaves", "intervals", "lengths"};
   public int noteCount = 0;
   public double playLength = 0, restLength = 0, totalLength = 0;

   // CONSTRUCTOR
   public Stats() {
      for (int i = 0; i < categories.length; i++) {
         totals.put(categories[i], new HashMap<String, Integer>());
         percents.put(categories[i], new HashMap<String, Double>());
      }
   }
   
   /////// METHODS ///////
   
   // adds the totals of a child's map to this map
   public void add(Stats map2) {
      // each category (pitch, octave, interval, length)
      map2.totals.forEach(
               (category, nestMap) -> {
                 // each key/value pair
                  nestMap.forEach(
                     (key, value) -> {
                        Map<String, Integer> temp = totals.get(category);
                        boolean exists = temp.containsKey(key);
                        temp.put(key, (exists) ? (temp.get(key) + value) : value);
                     });
               });
      noteCount += map2.noteCount;
   }
   
   // displays totals & percents for everything
   public void summary() {
      // each category (pitch, octave, interval, length)
      totals.forEach(
         (category, nestMap) -> {
            System.out.println("\n[" + category + "]");
           // each key/value pair
            nestMap.forEach(
               (key, value) -> {
                  String percentDisplay = " (" + String.format("%1$6s", GUI.twoDecimal.format(percents.get(category).get(key))) + " %)";
                  if (value != 0) { // Consider sorting these before displaying as well
                     if (category.equals("pitches")) {
                        if (!key.equals("-1")) {System.out.println(String.format("%1$7s", Music.PITCHES[Integer.parseInt(key)]) + ":     " + String.format("%1$4s", value) + percentDisplay);}
                     }
                     else if (category.equals("octaves")) {
                        if (!key.equals("0")) {System.out.println(key + ":     " + String.format("%1$4s", value) + percentDisplay);}
                     }
                     else if (category.equals("intervals")) {
                        if (!key.equals("+") && !key.equals("-")) {
                           int interval = Integer.parseInt(key);
                           System.out.println(String.format("%1$7s", Music.INTERVALS[interval]) + String.format("%1$5s", " (" + key + ")") + ":     " + String.format("%1$4s", value) + percentDisplay);
                        }
                     }
                     else if (category.equals("lengths")) {
                        System.out.println(String.format("%1$6s", key) + ":     " + String.format("%1$4s", value) + percentDisplay);
                     }
                  }
               });
            if (category.equals("intervals")) {
               System.out.println("\n" + String.format("%1$12s", "(+)") + ":     " + String.format("%1$4s", nestMap.get("+")) + " (" + String.format("%1$6s", GUI.twoDecimal.format(percents.get(category).get("+"))) + " %)");
               System.out.println(String.format("%1$12s", "(-)") + ":     " + String.format("%1$4s", nestMap.get("-")) + " (" + String.format("%1$6s", GUI.twoDecimal.format(percents.get(category).get("-"))) + " %)");
            }
         });
   }
  
   // calculate percents based on existing totals
   public void calculatePercents() {
      totals.forEach( 
         (category, nestMap) -> {
            nestMap.forEach(
               (key, value) -> {
                  double val = (double)value/noteCount*100;
                  percents.get(category).put(key, val);
               });
         });
   }
}