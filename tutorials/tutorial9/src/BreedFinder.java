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
import java.util.*;
import java.util.List;

public class BreedFinder {

    //fields
    private static JTextField userBreedEntry;
    private static JLabel feedback;
    private static Map<String, Integer> numEachBreed;

    /**
     * this is the main method - it creates the main frame, adds the image and
     * user input panel to the main frame and shows the main frame
     * @param args NA
     */
    public static void main(String[] args){
        numEachBreed=loadPets("./allPets.txt");
        //Part 2.3: create a main window (JFrame), ensuring you give it a title
        JFrame mainWindow = new JFrame("Dog Breed Finder");
        //handle the close operation
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new Dimension(100,100));
        //position it in the center of your screen and load the icon.
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.jpg").getImage());
        //Call the getUserInput method, and add the returned JPanel to the main window
        mainWindow.add(getUserInput(),BorderLayout.CENTER);
        mainWindow.pack();
        //ensure that you make it visible
        mainWindow.setVisible(true);
    }

    //Part 2.2
    /**
     * a method to get the user to enter their preferred breed in a text field
     * the method displays the results of calling the checkBreed method
     * @return a JPanel containing the instruction and feedback labels, text field and submit button
     */
    public static JPanel getUserInput(){
        //Part 2.2.1: Create an appropriate instructional JLabel, informing the user to enter their preferred breed.
        JLabel instruction = new JLabel("Please enter your preferred dog breed:");
        //Part 2.2.2: Create a text field for user entry
        userBreedEntry = new JTextField(12);
        //Part 2.2.3: Create a label to be used to give users feedback.
        feedback = new JLabel(" "); //set it to an empty string to start with
        //Part 2.2.4: Create a button for users to click to check availability.
        JButton checkBreedsButton = new JButton("Check availability");

        //Part 2.2.6: ...by writing and calling a separate method, check if the user's preferred breed is in the map.
        ActionListener listener = e -> checkBreed(numEachBreed);
        //Part 2.2.5: Attach an event listener to both the text field and the button.
        userBreedEntry.addActionListener(listener);
        checkBreedsButton.addActionListener(listener);

        //Part 2.2.9: Add these components to a JPanel, selecting an appropriate layout manager. Return this JPanel.
        JPanel userEntryPanel = new JPanel();
        userEntryPanel.setLayout(new FlowLayout());
        userEntryPanel.add(instruction);
        userEntryPanel.add(userBreedEntry);
        userEntryPanel.add(checkBreedsButton);
        userEntryPanel.add(feedback);

        return userEntryPanel;
    }

    //Part 2.2.6: ...by writing and calling a separate method, check if the user's preferred breed is in the map.
    /**
     * a method used by an action listener to output a String message as feedback
     * informing the user whether there are dogs of their chosen breed available
     * and if so, how many dogs are their chosen breed.
     * @param numEachBreed a mapping of breeds to number of dogs of each breed
     */
    public static void checkBreed(Map<String, Integer> numEachBreed){
        if(userBreedEntry.getText().length()==0){
            feedback.setForeground(Color.RED);
            feedback.setText("Field cannot be empty. Please enter a breed.");
            userBreedEntry.requestFocusInWindow();
        }
        //Part 2.2.6: check if the user's preferred breed is in the map.
        else if(numEachBreed.containsKey(userBreedEntry.getText())){
            //Part 2.2.7: If it is, let them know (via the feedback label)
            //how many dogs of that breed are available for adoption.
            feedback.setForeground(Color.BLUE);
            feedback.setText("Great news! We have "+numEachBreed.get(userBreedEntry.getText()) +
                    " "+userBreedEntry.getText()+"/s available for adoption!");
        }
        //Part 2.2.8: If it isn't, let the user know
        else{
            feedback.setForeground(Color.RED);
            feedback.setText("Unfortunately, we don't have any "+userBreedEntry.getText()+ "s available for adoption!");
            userBreedEntry.selectAll();
            userBreedEntry.requestFocusInWindow();
        }
    }

    //Part 2.1: create a method to load and read the allPets.txt file
    /**
     * a method to read the allPets file, to populate a mapping of breeds to number of dogs of that breed
     * @param filePath a String representing the path to the allPets file
     * @return a Map<String, Integer/> of breed name to number of that breed
     */
    public static Map<String, Integer> loadPets(String filePath){
        Path path = Path.of(filePath);
        Map<String,Integer> numOfEachBreed = new HashMap<>();
        List<String> eachPet = new ArrayList<>();
        try{
            eachPet = Files.readAllLines(path);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Could not load file! Username cannot be verified!");
            System.exit(0);
        }
        for (int i=1;i<eachPet.size();i++) {
            String[] elements = eachPet.get(i).split(",");
            String petType = elements[0].replaceAll("\n", "");

            if(petType.equalsIgnoreCase("dog")){
                String breed = elements[6].toLowerCase();
                if(!numOfEachBreed.containsKey(breed)) numOfEachBreed.put(breed,1);
                else numOfEachBreed.put(breed,numOfEachBreed.get(breed)+1);
            }
        }
        //return a mapping of dog breed names to how many dogs of that breed are in the file
        return numOfEachBreed;
    }
}
