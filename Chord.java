import java.util.LinkedList;

public class Chord {
   private LinkedList<Note> notes;
   
   private String name, symbol, quality, formula, notation, info;

   // CONSTRUCTOR
   public Chord(String nameIn, String symbolIn, String qualityIn, String formulaIn, String notationIn, String infoIn) {
      name = nameIn;
      symbol = symbolIn;
      quality = qualityIn;
      formula = formulaIn;
      notation = notationIn;
      info = infoIn;
   }
   
   /////// METHODS ///////
   
   public void getInRoot() {
   
   }
   
   /////// GETTERS ///////
   
   public String getName() {
      return name;
   }
   
   public String getSymbol() {
      return symbol;
   }
   
   public String getQuality() {
      return quality;
   }
   
   public String getFormula() {
      return formula;
   }
   
   public String getNotation() {
      return notation;
   }
   
   public String getInfo() {
      return info;
   }
   
   public LinkedList<Note> getNotes() {
      return notes;
   }
}