import java.sql.*;
import java.util.*;

public class App {

   // assigned in main method
   private static Connection connection;
   private static DataAdapter dataAdapter;
   
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
      
      // assign connection to adapter
      dataAdapter = new DataAdapter(connection);
      
      // import database
      dataAdapter.load();
      
      // display stats
      Catalog.summary();
      
      // open UI
      GUI.run();
   }
}