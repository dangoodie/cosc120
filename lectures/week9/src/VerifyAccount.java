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

public class VerifyAccount {

    //fields
    private JTextField usernameEntry;
    private JButton verifyAccountButton;
    private JLabel feedback;

    public static void main(String[] args){
        //load all the geek data, storing the usernames in a Set
        Set<String> usernames = loadGeeks("./allGeeks.txt");
        //create an instance of this class
        VerifyAccount simpleLogin = new VerifyAccount();
        //call the method that loads the GUI
        simpleLogin.verifyAccount(usernames);
    }

    /**
     * creates the main frame and components, adding an action listener to the button and text field
     * @param usernames a Set of usernames
     */
    public void verifyAccount(Set<String> usernames){
        //main window, with title of window (this can be done without a title too)
        JFrame mainWindow = new JFrame("Seek A Geek");
        //when the user clicks 'close', it will exit the program
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this will set the minimum size of the window to width = 500 and height = 100
        mainWindow.setMinimumSize(new Dimension(500,100));
        //this positions the window in the middle of your screen
        mainWindow.setLocationRelativeTo(null);

        //this is a label to tell the user what to do
        JLabel instruction = new JLabel("Please enter your username");
        //this creates a text box that the user can type in
        usernameEntry = new JTextField(20);
        //this is a button the user can click on to verify their account
        verifyAccountButton = new JButton("Verify account");

        //this listener will be used to 'listen' for any activity on the text box and button
        ActionListener listener = e -> checkUsername(usernames);
        //if the user clicks 'enter' the checkUsername method will be called
        usernameEntry.addActionListener(listener); //it would be more appropriate to use a document listener for the text field
        //if the button is clicked, the checkUsername method will be called
        verifyAccountButton.addActionListener(listener);

        //this is a label to welcome the user or tell them that the username they entered is not valid
        feedback = new JLabel(" "); //set it to an empty string to start with

        //this will contain the 'stuff' inside the window
        JPanel mainWindowContents = new JPanel();
        //change the background color like this
        mainWindowContents.setBackground(Color.lightGray);

        //add all the items to the panel
        mainWindowContents.add(instruction);
        mainWindowContents.add(usernameEntry);
        mainWindowContents.add(verifyAccountButton);
        mainWindowContents.add(feedback);

        //add the panel to the main window (frame)
        mainWindow.add(mainWindowContents);

        //make the frame visible
        mainWindow.setVisible(true);
    }

    /**
     * method used in action listener when user submits their username to be checked
     * displays a positive (blue) message in case of success
     * displays a negative (red) message in case of failure
     * @param usernames a Set of String usernames against which the user's entry will be tested
     */
    public void checkUsername(Set<String> usernames){
        if(usernames.contains(usernameEntry.getText())){
            //valid entry message is blue
            feedback.setForeground(Color.BLUE);
            //valid entry message
            feedback.setText("Welcome!");
            //disable the user from resubmitting a username (because what they entered is valid)
            verifyAccountButton.setEnabled(false);
        }
        else {
            //outputs message to tell the user their entry is invalid
            feedback.setText("Invalid username. Please try again.");
            //invalid entry message is red
            feedback.setForeground(Color.RED);
            //selects the user's entry, so that when they type, the entry is replaced
            usernameEntry.selectAll();
            //keeps the user's cursor in the text box, so they can enter a new username
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
        List<String> eachGeek = null;
        Path path = Path.of(filePath);
        try{
            eachGeek = Files.readAllLines(path);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Could not load file! Username cannot be verified!");
            System.exit(0);
        }
        for (int i=1;i<eachGeek.size();i++) {
            String[] elements = eachGeek.get(i).split("\\[");
            String[] geekInfo = elements[0].split(",");
            String username = geekInfo[0].replaceAll("\n", "");
            usernames.add(username);
        }
        return usernames;
    }
}

