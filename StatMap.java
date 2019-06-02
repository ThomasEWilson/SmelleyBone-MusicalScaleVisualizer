import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class StatMap {
   private Map<String, Map<String, Map<String, Integer>>> map;
   private int noteCount;

   // CONSTRUCTOR
   public StatMap() {
      map = new HashMap<String, Map<String, Map<String, Integer>>>();
      noteCount = 0;
   
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
   
   public Map<String, Map<String, Integer>> get(String key) {
      return map.get(key);
   }
   
   public Map<String, Map<String, Map<String, Integer>>> getMap() {
      return map;
   }
   
   public void add(StatMap map2) {
   // each stat (total, percentage)
      map2.getMap().forEach(
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
      noteCount += map2.getNoteCount();
   }
   
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
                           
                           }
                           else if (category.equals("octaves")) {
                           
                           }
                           else if (category.equals("intervals")) {
                           
                           }
                           else if (category.equals("lengths")) {
                           
                           }
                           System.out.println(key + ":     " + value);
                        }
                     });
               });
         });
   }
   
   public void incrementNoteCount() {
      noteCount++;
   }
   
   public int getNoteCount() {
      return noteCount;
   }
}