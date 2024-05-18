/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerifyAccountV2 {

    //fields
    private JTextField usernameEntry;
    private JButton verifyAccountButton;
    private JLabel feedback;
    private JPanel imagePanel;

    /**
     * main method that loads the geek data, and creates an instance of this class (loading the GUI)
     * @param args NA
     */
    public static void main(String[] args){
        Set<String> usernames = loadGeeks("./allGeeks.txt");
        VerifyAccountV2 simpleLogin = new VerifyAccountV2();
        simpleLogin.verifyAccount(usernames);
    }

    /**
     * creates the main frame and components, adding an action listener to the button and text field
     * @param usernames a Set of usernames
     */
    public void verifyAccount(Set<String> usernames){
        JFrame mainWindow = new JFrame("Seek A Geek");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.png").getImage());

        feedback = new JLabel(" "); //set it to an empty string to start with
        //load up the first image
        JLabel feedbackImage = new JLabel(new ImageIcon("login.png"));

        JLabel instruction = new JLabel("Please enter your username");
        usernameEntry = new JTextField(20);
        verifyAccountButton = new JButton("Verify account");

        ActionListener listener = e -> checkUsername(usernames);
        //add the action listener to both the text field and button
        usernameEntry.addActionListener(listener);
        verifyAccountButton.addActionListener(listener);

        JPanel mainWindowContents = new JPanel();
        mainWindowContents.setLayout(new BoxLayout(mainWindowContents,BoxLayout.Y_AXIS));
        mainWindowContents.setBorder(BorderFactory.createTitledBorder("Verify your account"));
        mainWindowContents.add(instruction);
        mainWindowContents.add(usernameEntry);
        mainWindowContents.add(feedback);

        //add the image to its own panel
        imagePanel = new JPanel();
        imagePanel.add(feedbackImage);

        //add the image to the top of the JFrame
        mainWindow.add(imagePanel,BorderLayout.NORTH);
        //add the panel to the centre of the JFrame
        mainWindow.add(mainWindowContents,BorderLayout.CENTER);
        //add the button to the bottom of the JFrame
        mainWindow.add(verifyAccountButton,BorderLayout.SOUTH);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    /**
     * method used in action listener when user submits their username to be checked
     * this method controls which image is displayed, depending on user input
     * @param usernames a Set of String usernames against which the user's entry will be tested
     */
    public void checkUsername(Set<String> usernames){
        if(usernames.contains(usernameEntry.getText())){
            feedback.setForeground(Color.BLUE);
            feedback.setText("Welcome!");
            //remove the login image, replacing it with the greeting image
            imagePanel.removeAll();
            imagePanel.add(new JLabel(new ImageIcon("greeting.png")));
            //turn the button off
            verifyAccountButton.setEnabled(false);
        }
        else {
            feedback.setText("Invalid username. Please try again.");
            feedback.setForeground(Color.RED);
            //remove the login image, replacing it with the invalid image
            imagePanel.removeAll();
            imagePanel.add(new JLabel(new ImageIcon("invalid.png")));
            usernameEntry.selectAll();
            usernameEntry.requestFocusInWindow();
        }
    }

    /**
     * a method to load and extract info from allGeeks.txt
     * @param filePath the path to allGeeks.txt
     * @return a Set of String usernames
     */
    public static Set<String> loadGeeks(String filePath){
        Set<String> usernames = new HashSet<>();
        try{
            Path path = Path.of(filePath);
            List<String> eachGeek = Files.readAllLines(path);
            for (int i=1;i<eachGeek.size();i++) {
                String[] elements = eachGeek.get(i).split("\\[");
                String[] geekInfo = elements[0].split(",");
                String username = geekInfo[0].replaceAll("\n", "");
                usernames.add(username);
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Could not load file! Username cannot be verified!");
            System.exit(0);
        }
        return usernames;
    }
}
