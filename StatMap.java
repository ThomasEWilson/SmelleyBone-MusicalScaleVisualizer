import java.util.Map;
import java.util.HashMap;

public class StatMap {

  // assigned when parent's buildStats() is called
   public Map<String, Map<String, Map<String, Integer>>> map = new HashMap<String, Map<String, Map<String, Integer>>>();;
   public int noteCount = 0;

   // CONSTRUCTOR
   public StatMap() {
      map.put("totals", new HashMap<String, Map<String, Integer>>());
      map.put("percents", new HashMap<String, Map<String, Integer>>());
      
      map.get("totals").put("pitches", new HashMap<String, Integer>());
      map.get("totals").put("octaves", new HashMap<String, Integer>());
      map.get("totals").put("intervals", new HashMap<String, Integer>());
      map.get("totals").put("lengths", new HashMap<String, Integer>());
      
      map.get("percents").put("pitches", new HashMap<String, Integer>());
      map.get("percents").put("octaves", new HashMap<String, Integer>());
      map.get("percents").put("intervals", new HashMap<String, Integer>());
      map.get("percents").put("lengths", new HashMap<String, Integer>());
   }
   
   /////// METHODS ///////
   
   // shorthand instead of typing stats.map.get()
   public Map<String, Map<String, Integer>> get(String key) {
      return map.get(key);
   }
   
   // adds the totals of a child's map to this map
   public void add(StatMap map2) {
   // each stat (total, percentage)
      map2.map.forEach(
         (stat, nestMap1) -> {
           // each category (pitch, octave, interval, length)
            nestMap1.forEach(
               (category, nestMap2) -> {
                 // each key/value pair
                  nestMap2.forEach(
                     (key, value) -> {
                        Map<String, Integer> temp = map.get(stat).get(category);
                        boolean exists = temp.containsKey(key);
                        temp.put(key, (exists) ? (temp.get(key) + value) : value);
                     });
               });
         });
      noteCount += map2.noteCount;
   }
   
   // displays totals & percents for everything
   public void summary() {
     // each stat (total, percentage)
      map.forEach(
         (stat, nestMap1) -> {
            System.out.println("\n--------\n" + stat.toUpperCase() + "\n--------");
           // each category (pitch, octave, interval, length)
            nestMap1.forEach(
               (category, nestMap2) -> {
                  System.out.println("\n[" + category + "]");
                 // each key/value pair
                  nestMap2.forEach(
                     (key, value) -> {
                        if (!key.equals("0")) {
                           String displayKey = key;
                           if (category.equals("pitches")) {
                              // change displayKey to note name for readability
                           }
                           else if (category.equals("octaves")) {
                           
                           }
                           else if (category.equals("intervals")) {
                              // change displayKey to interval name for readability
                           }
                           else if (category.equals("lengths")) {
                              // change displayKey to length names for readability
                           }
                           System.out.println(key + ":     " + value); // Consider sorting these before displaying as well
                        }
                     });
               });
         });
   }
  
   // calculate percents based on existing totals
   public void calculatePercents() {
      Map<String, Map<String, Integer>> totals = map.get("totals");
      Map<String, Map<String, Integer>> percents = map.get("percents");
      
      totals.forEach( 
         (category, nestMap) -> {
            nestMap.forEach(
               (key, value) -> {
                  double val = value; // cast to double
                  percents.get(category).put(key, (int)java.lang.Math.round(value/noteCount*100));
               });
         });
   }
}