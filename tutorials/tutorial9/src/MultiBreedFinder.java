/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class MultiBreedFinder {
    private static JLabel feedback;
    //private static Map<String, Integer> numEachBreed;
    private static Map<Breed, Integer> numEachBreed;

    /**
     * this is the main method - it creates the main frame, adds the image and
     * user input panel to the main frame and shows the main frame
     * @param args NA
     */
    public static void main(String[] args){
        numEachBreed=loadPets("./allPets.txt");
        JFrame mainWindow = new JFrame("Dog Breed Finder");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setMinimumSize(new Dimension(400,200));
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.jpg").getImage());
        mainWindow.add(getUserInput(),BorderLayout.CENTER);

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
        feedback = new JLabel(" ");
        //Part 4.1: Remove the JComboBox, replacing it with a JList. Populate the JList with the Map keyset (all the breeds)
        //JList<String> userBreedSelection = new JList<>(numEachBreed.keySet().toArray(new String[0]));
        JList<Breed> userBreedSelection = new JList<>(Breed.values());
        //Part 4.2: Ensure that you enable multi-selection
        userBreedSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //create a scroll pane to limit the visible size of the JList and enable scrolling
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(userBreedSelection);
        userBreedSelection.setLayoutOrientation(JList.VERTICAL); //vertical scrollbar

        //set the size of the scroll pane
        scrollPane.setPreferredSize(new Dimension(200, 70));
        //always show the scrollbar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //set the position of the scroll bar  to the top of the scrollable area
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition( new Point(0, 0) ));

        //Part 4.3: Use a ListSelectionListener to listen for user breed selection.
        ListSelectionListener listener = e -> checkBreeds(userBreedSelection,numEachBreed);
        userBreedSelection.addListSelectionListener(listener);

        JPanel userEntryPanel = new JPanel();
        userEntryPanel.setBorder(BorderFactory.createTitledBorder("Please select your preferred dog breeds:"));
        userEntryPanel.add(scrollPane);
        userEntryPanel.add(feedback);

        return userEntryPanel;
    }

    /**
     * a method used by an action listener to output a String message as feedback
     * informing the user of how many dogs are their chosen breed
     * this method calls the sound playing method if there are dogs of the chosen breed
     * @param numEachBreed a mapping of breeds to number of dogs of each breed
     */
    //public static void checkBreeds(JList<String> userBreedSelection, Map<String, Integer> numEachBreed){
    public static void checkBreeds(JList<Breed> userBreedSelection, Map<Breed, Integer> numEachBreed){
        //List<String> favouriteBreeds = userBreedSelection.getSelectedValuesList();
        List<Breed> favouriteBreeds = userBreedSelection.getSelectedValuesList();
        //Set<String> availableDesiredBreeds = new HashSet<>(favouriteBreeds);
        Set<Breed> availableDesiredBreeds = new HashSet<>(favouriteBreeds);
        //Part 4.4: In the checkBreed method, use retainAll to get the
        //overlap between the user's selections, and the available breeds.
        availableDesiredBreeds.retainAll(numEachBreed.keySet());

        StringBuilder tempFeedback = new StringBuilder("Great news! We have");
        if(availableDesiredBreeds.size()==0){
            feedback.setForeground(Color.RED);
            feedback.setText("Unfortunately, we don't have any of your selected breeds available for adoption!");
            userBreedSelection.requestFocusInWindow();
        }
        else{
            feedback.setForeground(Color.BLUE);
            //Part 4.5: Iterate over all these breeds, creating a String that informs
            //the user how many of each of their desired breeds are available.
            for(Breed breed: availableDesiredBreeds){
            //for(String breed: availableDesiredBreeds){
                int numberOfDogs = numEachBreed.get(breed);
                if(numberOfDogs==0) continue;
                tempFeedback.append(" ").append(numberOfDogs);
                if(numberOfDogs==1) tempFeedback.append(" ").append(breed).append(",");
                else tempFeedback.append(" ").append(breed).append("s,");
            }
            tempFeedback.append(" available for adoption!");
            feedback.setText(String.valueOf(tempFeedback));
        }
    }

    /**
     * a method to read the allPets file, to populate a mapping of breeds to number of dogs of that breed
     * @param filePath a String representing the path to the allPets file
     * @return a Map<String, Integer/> of breed name to number of that breed
     */
    //public static Map<String, Integer> loadPets(String filePath){
    public static Map<Breed, Integer> loadPets(String filePath){
        //Map<String,Integer> numOfEachBreed = new HashMap<>();
        Map<Breed,Integer> numOfEachBreed = new HashMap<>();
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
                Breed ebreed = Breed.valueOf(breed.toUpperCase().replace(" ","_"));
                if(!numOfEachBreed.containsKey(ebreed)) numOfEachBreed.put(ebreed,1);
                else numOfEachBreed.put(ebreed,numOfEachBreed.get(ebreed)+1);
                //if(!numOfEachBreed.containsKey(breed)) numOfEachBreed.put(breed,1);
                //else numOfEachBreed.put(breed,numOfEachBreed.get(breed)+1);
            }
        }
        return numOfEachBreed;
    }

}
