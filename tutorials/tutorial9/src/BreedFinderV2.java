/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedFinderV2 {

    private static JComboBox<String> userBreedSelection;
    private static JLabel feedback;
    private static Map<String, Integer> numEachBreed;

    /**
     * this is the main method - it creates the main frame, adds the image and
     * user input panel to the main frame and shows the main frame
     * @param args NA
     */
    public static void main(String[] args){
        numEachBreed=loadPets("./allPets.txt");
        JFrame mainWindow = new JFrame("Dog Breed Finder");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new Dimension(120,120));
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.jpg").getImage());
        mainWindow.add(getUserInput(),BorderLayout.CENTER);

        //Part 3.3: load an image of a dog on the far left of your frame
        JLabel dog = new JLabel(new ImageIcon("dog.png"));
        mainWindow.add(dog,BorderLayout.EAST);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    /**
     * a method to get the user to select a breed from a dropdown list
     * the method displays the results of calling the checkBreed method
     * @return a JPanel containing the instruction and feedback labels, and dropdown list
     */
    public static JPanel getUserInput(){
        feedback = new JLabel(" "); //set it to an empty string to start with
        JLabel instruction = new JLabel("Please select your preferred dog breed:");
        //Part 3.1: First, use a dropdown list for breed selection instead of a text field.
        //This will limit users' choice to those breeds that are known to be available.
        userBreedSelection = new JComboBox<>(numEachBreed.keySet().toArray(new String[0]));

        //Part 3.1: You won't need the button anymore, just attach the action listener to the dropdown list.
        ActionListener listener = e -> checkBreed(numEachBreed);
        userBreedSelection.addActionListener(listener);

        JPanel userEntryPanel = new JPanel();
        //Part 3.2: Next, add a border around the user selection and feedback message,
        //with the title "Breed selection"
        userEntryPanel.setBorder(BorderFactory.createTitledBorder("Breed selection"));
        userEntryPanel.add(instruction);
        userEntryPanel.add(userBreedSelection);
        userEntryPanel.add(feedback);

        return userEntryPanel;
    }

    /**
     * a method used by an action listener to output a String message as feedback
     * informing the user of how many dogs are their chosen breed
     * this method calls the sound playing method if there are dogs of the chosen breed
     * @param numEachBreed a mapping of breeds to number of dogs of each breed
     */
    public static void checkBreed(Map<String, Integer> numEachBreed){
        String breed = (String) userBreedSelection.getSelectedItem();
        if(numEachBreed.containsKey(breed)){
            feedback.setForeground(Color.BLUE);
            int numberOfDogs = numEachBreed.get(breed);
            feedback.setText("Great news! We have "+ numberOfDogs +
                    " "+userBreedSelection.getSelectedItem()+"/s available for adoption!");
            //play 1 dog bark sound for each available dog
            playSound("./bark.wav",numberOfDogs);
        }
        else{
            feedback.setForeground(Color.RED);
            feedback.setText("Unfortunately, we don't have any "+userBreedSelection.getSelectedItem()+ "s available for adoption!");
            userBreedSelection.requestFocusInWindow();
        }
    }

    /**
     * a method to play a dog bark sound once for every dog of a particular breed
     * e.g. if there are 3 chihuahuas, then the bark will play 3 times
     * @param filePath the path to the audio file to play
     * @param numberOfDogs the number of dogs of a particular breed
     */
    public static void playSound(String filePath, int numberOfDogs){
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
                clip.loop(numberOfDogs-1);
            }catch (Exception ex){
                System.out.println("Couldn't load sound file."+ ex);
            }
    }

    /**
     * a method to read the allPets file, to populate a mapping of breeds to number of dogs of that breed
     * @param filePath a String representing the path to the allPets file
     * @return a Map<String, Integer/> of breed name to number of that breed
     */
    public static Map<String, Integer> loadPets(String filePath){
        Map<String,Integer> numOfEachBreed = new HashMap<>();
        List<String> eachPet = new ArrayList<>();
        try{
            Path path = Path.of(filePath);
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
        return numOfEachBreed;
    }
}
