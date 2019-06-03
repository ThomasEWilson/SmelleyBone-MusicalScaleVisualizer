import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.Icon;

public class GUI {
   final static int WIDTH = 960, HEIGHT = 540;
   final static Random rand = new Random();
   private static Scale scale;
   private static JLabel scaleNameLabel, scaleNotesLabel, scaleFormulaLabel, scaleInfoLabel;
   private static JButton showButton, randButton;
   private static JComboBox keyBox;
   private static int prevRand;
   public static DecimalFormat formatter2 = new DecimalFormat("##");
   public static DecimalFormat formatter = new DecimalFormat("0.00");
   private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

   public static void run() {
   
      //Schedule a job for the event-dispatching thread:
      //creating and showing this application's GUI
      javax.swing.SwingUtilities.invokeLater(
         new Runnable() {
            public void run() {
               createAndShowGUI();
            }
         });
   }
  
    //Create the GUI and show it.  
   private static void createAndShowGUI() {
   
    // Create and set up frame
      JFrame frame = new JFrame("Boneder");
      frame.setResizable(false);
      frame.setBounds(0,0, WIDTH, HEIGHT);
      frame.setBackground(Color.white);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      showButton = new JButton("Show");
      randButton = new JButton("Next");
      scaleNameLabel = new JLabel(" ");
      scaleNotesLabel = new JLabel(" ");
      scaleFormulaLabel = new JLabel(" ");
      scaleInfoLabel = new JLabel(" ");
      keyBox = new JComboBox(Music.FLATS);
      prevRand = rand.nextInt(Music.SCALES.size());
      
      addComponentsToPane(frame.getContentPane());
      
      // Display window
      //frame.pack();
      centerWindow(frame);
      frame.setVisible(true);
   }

   public static void addComponentsToPane(Container pane) {
      JButton button;
      pane.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
   
      c.fill = GridBagConstraints.HORIZONTAL;
      
      // Scale Name Label
      c.gridx = 0; c.gridy = 1; c.gridwidth = 3; // 0, 1, 3
      scaleNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
      scaleNameLabel.setHorizontalAlignment(JLabel.CENTER);
      pane.add(scaleNameLabel, c);
      
      // Scale Notes Label
      c.gridx = 0; c.gridy = 2; c.gridwidth = 3; // 0, 2, 3
      scaleNotesLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
      scaleNotesLabel.setHorizontalAlignment(JLabel.CENTER);
      pane.add(scaleNotesLabel, c);
      
      // Scale Formula Label
      c.gridx = 0; c.gridy = 3; c.gridwidth = 3; // 0, 3, 3
      scaleFormulaLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
      scaleFormulaLabel.setHorizontalAlignment(JLabel.CENTER);
      pane.add(scaleFormulaLabel, c);
      
      // Scale Info Label
      c.gridx = 0; c.gridy = 4; c.gridwidth = 3; // 0, 4, 3
      scaleInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
      scaleInfoLabel.setHorizontalAlignment(JLabel.CENTER);
      pane.add(scaleInfoLabel, c);
   
      // Key Combo Box
      c.gridx = 0; c.gridy = 0; c.gridwidth = 1; // 0, 0, 1
      c.insets = new Insets(20,20,0,0);
      c.gridwidth = 1;
      c.gridy = 0;
      keyBox.addActionListener(
         new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
               clearLabels();
            } 
         } );
      pane.add(keyBox, c);
      
      // Next Button
      c.gridx = 1; c.gridy = 0; c.gridwidth = 1; // 1, 0, 1
      c.insets = new Insets(20,20,0,0);
      randButton.addActionListener(
         new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
               int num = prevRand;
               while (num == prevRand) {
                  num = rand.nextInt(Music.SCALES.size());
               }
               prevRand = num;
               scale = Music.SCALES.get(num);
               scaleNameLabel.setText(scale.getName());
               showButton.setEnabled(true);
               clearLabels();
            } 
         } );
      pane.add(randButton, c);
      
      // Show Button
      c.gridx = 2; c.gridy = 0; c.gridwidth = 1; // 2, 0, 1
      c.insets = new Insets(20,20,0,20);
      showButton.setEnabled(false);
      showButton.addActionListener(
         new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
               scaleNotesLabel.setText(getScaleText());
               scaleFormulaLabel.setText(scale.getFormula());
               //scaleInfoLabel.setText(scale.getInfo());
            } 
         } );
      pane.add(showButton, c);
   }
   
   public static String getScaleText() {
      String[] notes = scale.getInKey((String)keyBox.getSelectedItem());
      String text = "";
      for (String str : notes) {
         if (!str.equals("-")) {
            text += str;
            text += " ";
         }
      }
      return text;
   }
   
   public static void clearLabels() {
      scaleNotesLabel.setText(" ");
      scaleFormulaLabel.setText(" ");
      scaleInfoLabel.setText(" ");
   }
   
   // center window
   public static void centerWindow(Window frame) {
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
      int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
      frame.setLocation(x, y);
   }
   
    // Return current date formatted as yyyy/MM/dd
   public static String getDate() {
      return dateFormat.format(new Date()).toString().substring(0,10);
   }
   
   // Return current time formatted as HH:mm:ss
   public static String getTime() {
      return dateFormat.format(new Date()).toString().substring(11,19);
   }
}