import java.sql.*;
import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;

public class DataAdapter {
   // assigned at construction
   private Connection connection;
   
   // CONSTRUCTOR
   public DataAdapter(Connection connection) {
      this.connection = connection;
   }
   
   /////// METHODS ///////
   
   // load songs from database
   public void loadSongs() {
      try {
         String query = "SELECT * FROM Songs";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            Boneder.addSong(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8));
         }
         resultSet.close();
         statement.close();
      } 
      catch (SQLException e) {
         System.out.println("Database access error!");
         e.printStackTrace();
      }
   }
   
   // load scales from database
   public void loadScales() {
      try {
         String query = "SELECT * FROM Scales";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            Music.addScale(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
         }
         resultSet.close();
         statement.close();
      } 
      catch (SQLException e) {
         System.out.println("Database access error!");
         e.printStackTrace();
      }
   }
   
   // load chords from database
   public void loadChords() {
      try {
         String query = "SELECT * FROM Chords";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            Music.addChord(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
         }
         resultSet.close();
         statement.close();
      } 
      catch (SQLException e) {
         System.out.println("Database access error!");
         e.printStackTrace();
      }
   }

    // Save product info
   /*public boolean saveProduct(Product product) {
      try {
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE ID = ?");
         statement.setInt(1, product.getID());
      
         ResultSet resultSet = statement.executeQuery();
      
         if (resultSet.next()) { // Product exists - edit info
            statement = connection.prepareStatement("UPDATE Products SET Name = ?, Unit = ?, Price = ?, Tax = ?, Quantity = ?, ExpDate = ?, Vendor = ?, Contact = ?  WHERE ID = ?");
            statement.setString(1, product.getName());
            statement.setString(2, product.getUnit());
            statement.setDouble(3, product.getPrice());
            statement.setDouble(4, product.getTax());
            statement.setDouble(5, product.getQuantity());
            statement.setString(6, product.getExpDate());
            statement.setString(7, product.getVendor());
            statement.setString(8, product.getContact());
            statement.setInt(9, product.getID());
         }
         else { // Product does not exist - create new
            statement = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, product.getName());
            statement.setInt(2, product.getID());
            statement.setString(3, product.getUnit());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getTax());
            statement.setDouble(6, product.getQuantity());
            statement.setString(7, product.getExpDate());
            statement.setString(8, product.getVendor());
            statement.setString(9, product.getContact());
         }
         statement.execute();
      
         resultSet.close();
         statement.close();
         return true;
      } 
      catch (SQLException e) {
         return false;
      }
   }*/
   
    // Save order info
   /*public boolean saveOrder(Order order) {
      try {
         PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders VALUES (?, ?, ?, ?, ?, ?, ?)");
         statement.setInt(1, order.getID());
         statement.setDouble(2, order.getTotal());
         statement.setDouble(3, order.getTax());
         statement.setString(4, order.getDate());
         statement.setString(5, order.getTime());
         statement.setString(6, order.getPayType());
         statement.setString(7, order.getEmployee());
         statement.execute();
         statement.close();
         return (saveOrderDetails(order.getDetails()) && updateStock(order.getProducts()));
      } 
      catch (SQLException e) {
         return false;
      }
   }*/
   
    // Get latest ID of indicated table to determine what newest ID should be
   /*public String getLastID(String table) {
      ResultSet resultSet;
      try {
         PreparedStatement statement = connection.prepareStatement("SELECT MAX(ID) FROM " + table);
         resultSet = statement.executeQuery();
         return resultSet.getString(1);
      }
      catch (SQLException e) {
         return "-1";
      }
   }*/
   
   // Get user info
   /*public User loadUser(String name) {
      try {
         String query = "SELECT * FROM Users WHERE Username = '" + name + "'";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         if (resultSet.next()) {
            User load = new User(resultSet.getString(1), resultSet.getString(2), Boolean.parseBoolean(resultSet.getString(3)), resultSet.getInt(4));
            resultSet.close();
            statement.close();
         
            return load;
         }
      } 
      catch (SQLException e) {
         System.out.println("Database access error!");
         e.printStackTrace();
      }
      return null;
   }*/
   
    // Save user info (assuming username is new - check beforehand with findUser)
   /*public boolean saveUser(User user) {
      try {
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
         statement.setString(1, user.getName());
      
         ResultSet resultSet = statement.executeQuery();
         
         if (resultSet.next()) { // User exists - edit password or pic
            statement = connection.prepareStatement("UPDATE Users SET Password = ?, Pic = ?  WHERE Username = ?");
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getPic());
            statement.setString(3, user.getName());
         }
         else { // User does not exist - create new
            statement = connection.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isManager());
            statement.setInt(4, user.getPic());
         }
         
         statement.execute();
         statement.close();
         return true;
      } 
      catch (SQLException e) {
         return false;
      }
   }*/
   
   // Save user pic
   /*public boolean savePic() {
      try {
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
         statement.setString(1, user.getName());
      
         ResultSet resultSet = statement.executeQuery();
         
         statement = connection.prepareStatement("UPDATE Users SET Pic = ?  WHERE Username = ?");
         statement.setInt(1, user.getPic());
         statement.setString(2, user.getName());
         
         statement.execute();
         statement.close();
         return true;
      } 
      catch (SQLException e) {
         return false;
      }
   }*/
   
    // Check if password is correct for given username
   /*public boolean validateLogin(String password) {
      if (temp.getPassword().equals(password)) {
         this.user = temp;
         GUI.changeWelcome();
         return true;
      }
      else {
         return false;
      }
   }*/
   
    // Check string against current user's password
   /*public boolean checkPass(String password) {
      return password.equals(user.getPassword());
   }*/
   
    // Check if username already exists
   /*public boolean findUser(String name) {
      temp = loadUser(name);
      return temp != null;
   }*/

    // Check if user is manager
   /*public boolean isManager() {
      return user.isManager();
   }*/

    // Return current user's name
   /*public String getName() {
      return user.getName();
   }*/

    // Return current user's pic #
   /*public int getPic() {
      return user.getPic();
   }*/

    // Set current user's pic #
   /*public void setPic(int newPic) {
      user.setPic(newPic);
   }*/

    // Return updated report list based on indicated date
   /*public List<Product> updateReportList(String date) {
      try {
         PreparedStatement statement = connection.prepareStatement("SELECT Products.Name, OrderDetails.ProductID, Products.Unit, Products.Price, SUM(OrderDetails.Quantity) FROM Orders, OrderDetails, Products WHERE Orders.Date = ? AND OrderDetails.OrderID = Orders.ID AND OrderDetails.ProductID = Products.ID GROUP BY Products.ID");
         statement.setString(1, date);
      
         ResultSet resultSet = statement.executeQuery();
         List<Product> reportList = new ArrayList<Product>();
         while (resultSet.next()) {
            reportList.add(new Product(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getDouble(4)*resultSet.getDouble(5), resultSet.getDouble(5), "NONE", "NONE", "NONE"));
         }
         resultSet.close();
         statement.close();
         
         return reportList;
      } 
      catch (SQLException e) {
         System.out.println("Database access error!");
         e.printStackTrace();
      }
      return null;
   }*/

   /*public void receipt(Order order, String extension) {
      Charset utf8 = StandardCharsets.UTF_8;
      List<String> lines = new ArrayList<String>();
      String fileName = order.getID() + "." + extension;
      List<Product> products = Store.getCheckoutController().getProducts();
      if (extension.equals("txt")) {
         lines.add("       Smith's Groceries\n\n     " 
            + order.getDate() + "\t" + order.getTime() 
            + "\n\n       Cashier: " + user.getName()
            + "\n          Order # " + order.getID()
            + "\n");
         int i = 0;
         while (i < products.size()) {
            lines.add("  " + products.get(i).getQuantity() + " " + products.get(i).getName() + "\t$" + Double.valueOf(GUI.formatter2.format(products.get(i).getPrice())));
            i++;
         }
         lines.add("\n\n      SUBTOTAL\t$" + Double.valueOf(GUI.formatter2.format((order.getTotal() - order.getTax())))
            + "\n           TAX\t$" + order.getTax()
            + "\n         TOTAL\t$" + order.getTotal()
            + "\n\nThank you for shopping with us!\n       Please come again");
      }
      else if (extension.equals("html")) {
         lines.add("<!DOCTYPE html><html lang=\"en\"><head>" 
            + "<title>Order # " + order.getID() + "</title>" 
            + "<link href=\"receipt.css\" rel=\"StyleSheet\" type=\"text/css\"/>" 
            + "</head><body><img src=\"images/Smith's Groceries Logo.png\" alt=\"Smith's Groceries Logo\" style=\"width: 250px; height: 250px;\" align=\"middle\">" 
            + "<h1>" + order.getDate() + "&nbsp&nbsp&nbsp&nbsp&nbsp" + order.getTime() 
            + "<br><br>Cashier: " + user.getName()
            + "<br>Order # " + order.getID()
            + "<br>" + "</h1>" + "<h2>"); 
         int i = 0;
         while (i < products.size()) {
            lines.add("<br>" + products.get(i).getQuantity() + "&nbsp&nbsp&nbsp&nbsp&nbsp" + products.get(i).getName() + "&nbsp&nbsp&nbsp&nbsp&nbsp$" + Double.valueOf(GUI.formatter2.format(products.get(i).getPrice())));
            i++;
         }
         lines.add("<br><br>   SUBTOTAL&nbsp&nbsp&nbsp&nbsp&nbsp$" + Double.valueOf(GUI.formatter2.format((order.getTotal() - order.getTax())))
            + "<br>&nbsp&nbsp&nbsp&nbsp&nbspTAX&nbsp&nbsp&nbsp&nbsp&nbsp$" + order.getTax()
            + "<br>&nbsp&nbsp&nbspTOTAL&nbsp&nbsp&nbsp&nbsp&nbsp$" + order.getTotal() + "</h2>" 
            + "<h3>Thank you for shopping with us!<br>Please come again</h3></body></html>");
      }
      else {
         
      }
         
      try {
         Files.write(Paths.get(fileName), lines, utf8);
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }*/

    // Update quantity of product after some amount is bought
   /*private boolean updateStock(List<Product> productList) {
      int i = 0;
      while (i < productList.size()) {
         Product product = productList.get(i);
         try {
            PreparedStatement statement = connection.prepareStatement("SELECT Quantity FROM Products WHERE ID = ?");
            statement.setInt(1, product.getID());
         
            ResultSet resultSet = statement.executeQuery();
         
            statement = connection.prepareStatement("UPDATE Products SET Quantity = ? WHERE ID = ?");
            statement.setDouble(1, resultSet.getDouble(1) - product.getQuantity());
            statement.setInt(2, product.getID());
            statement.execute();
            resultSet.close();
            statement.close();
         }
         catch (SQLException e) {
            return false;
         }
         i++;
      }
      return true;
   }*/
   
    // Save Order Details
   /*private boolean saveOrderDetails(List<OrderDetail> detailList) {
      int i = 0;
      while (i < detailList.size()) {
         OrderDetail detail = detailList.get(i);
         try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO OrderDetails VALUES (?, ?, ?, ?)");
            statement.setInt(1, detail.getID());
            statement.setInt(2, detail.getOrderID());
            statement.setInt(3, detail.getProductID());
            statement.setDouble(4, detail.getQuantity());
            statement.execute();
            statement.close();
         }
         catch (SQLException e) {
            return false;
         }
         i++;
      }
      return true;
   }*/

}