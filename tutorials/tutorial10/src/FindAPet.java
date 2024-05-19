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

public class FindAPet {

    //container fields
    private static JFrame mainWindow=null; //main container
    private static JPanel searchView = null; //view 1
    private static JPanel userInformationView = null; //view 3
    private static CriteriaEntry criteriaEntry; //object used for inputting user info/filters

    //app fields
    private static final String appName = "Pinkman's Pet Finder";
    private static final String iconFilePath = "./icon.jpg";
    private static ImageIcon icon = null;
    private static final String allPetsFilePath = "./allPets.txt";
    private static AllPets allPets = null;

    /**
     * main method - creates mainFrame and starts program, initialising key fields
     * @param args - NA
     */
    public static void main(String[] args) {
        allPets = loadPets(allPetsFilePath);
        mainWindow = new JFrame(appName);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        icon = new ImageIcon(iconFilePath);
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setMinimumSize(new Dimension(500,450));
        searchView = enterCriteria();
        mainWindow.setContentPane(searchView);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    //Part 3 and 6.1
    /**
     * this method creates a panel to be used as the only container in the main frame while users enter search criteria
     * It instantiates the CriteriaEntry class to get user input panel and input values
     * @return a JPanel representing the search view (data entry and submit button)
     */
    public static JPanel enterCriteria(){
        //create a local JPanel to contain all the elements of the search form
        JPanel searchWindow = new JPanel();
        //as we'll be using this panel as the only container in the main JFrame, set it to BorderLayout (N,E,S,W) - view 1
        searchWindow.setLayout(new BorderLayout());
        //create a criteria object (to use the search criteria class)
        criteriaEntry = new CriteriaEntry(allPets.getTypeToBreedMapping(), allPets.oldestPet());
        //add the panel of criteria panels to the main search window panel
        searchWindow.add(criteriaEntry.generateSearchView(),BorderLayout.CENTER);

        //add a button at the bottom of the search panel, when users click it, the conduct search method will be called
        JButton submitInfo = new JButton("Submit");
        ActionListener actionListener = e -> conductSearch(criteriaEntry);
        submitInfo.addActionListener(actionListener);
        //add the button to the bottom-most part of the search panel
        searchWindow.add(submitInfo,BorderLayout.SOUTH);
        //set the field value to the search panel (view 1)
        return searchWindow;
    }

    //Part 3 and 6.1
    /**
     * this method is called once the user has selected all the criteria they want to use
     * to find a pet. Like before, it creates a Map of criteria, used to instantiate DreamPet
     * The DreamPet is used to search the database of Pet objects as previously done.
     * @param criteriaEntry an object of the CriteriaEntry class, which has fields set via user selections/entries
     */
    public static void conductSearch(CriteriaEntry criteriaEntry){
        //this map will store the user's selections
        Map<Criteria,Object> specs = new HashMap<>();
        //for clarity, assign the criteriaEntry fields (via the getters) to appropriate local vars
        Type type = criteriaEntry.getSelectedPetType();
        String breed = criteriaEntry.getSelectedBreed();
        int minAge = criteriaEntry.getMinAge();
        int maxAge = criteriaEntry.getMaxAge();
        boolean isDeSexed = criteriaEntry.getDesexedStatus();
        Sex sex = criteriaEntry.getSex();

        //if the user hasn't selected a type, remind them
        if(type.equals(Type.SELECT_TYPE)){
            JOptionPane.showMessageDialog(mainWindow,"You MUST select a pet type.\n","Invalid search",JOptionPane.INFORMATION_MESSAGE,icon);
            return;
        }

        //add the user's choices to the map
        specs.put(Criteria.TYPE,type);
        //Part 1.1: breed is not mandatory - so if the user hasn't chosen a breed, it'll be null. If it is null, don't add it to the filters
        if(breed!=null) specs.put(Criteria.BREED,breed);
        if(isDeSexed) specs.put(Criteria.DESEXED,DeSexed.YES); //only add it if the user has checked the box
        if(!sex.equals(Sex.NA))specs.put(Criteria.SEX,sex); //only add it if the user has selected male/female

        //create a DreamPet object, initialising it with the map and the min-max age vars
        DreamPet dreamPet = new DreamPet(specs,minAge,maxAge);
        //use the AllPets findMatch method to find all compatible pets
        List<Pet> compatiblePets = allPets.findMatch(dreamPet);

        //call the show results method, passing in compatible pets. this method will create
        //the results display, initialising the resultsView JPanel (which will be used as the only
        //container in the JFrame (view 2)
        showResults(compatiblePets);
    }

    //Parts 3 and 4 and 6.2
    /**
     * this method displays the results, and populates a dropdown list of options the user can choose from
     * @param compatiblePets the arraylist of compatible pets returned by findMatch
     */
    public static void showResults(List<Pet> compatiblePets){
        //if there are compatible pets, create a HashMap to link the user's selections to Pet objects
        if(compatiblePets.size()>0) {
            Map<String, Pet> options = new LinkedHashMap<>();
            //add instructional item
            options.put("Select pet",null);
            //create a panel to contain the descriptions of the compatible pets
            JPanel petDescriptions = new JPanel();
            petDescriptions.setBorder(BorderFactory.createTitledBorder("Matches found!! The following pets meet your criteria: "));
            petDescriptions.setLayout(new BoxLayout(petDescriptions,BoxLayout.Y_AXIS));
            petDescriptions.add(Box.createRigidArea(new Dimension(0,10)));

            //create a new, non-editable text area for each pet, calling the getPetDescription method
            //to get a description of each pet
            for (Pet compatiblePet : compatiblePets) {
                //add the pet description to the above panel
                petDescriptions.add(describeIndividualPet(compatiblePet));
                //add each pet's name, and the pet to the options map
                options.put(compatiblePet.name() + " (" + compatiblePet.microchipNumber() + ")", compatiblePet);
            }
            //add a scroll pane to the results window, so that if there are many results, users can scroll as needed
            JScrollPane verticalScrollBar = new JScrollPane(petDescriptions);
            //set the default size of the window containing the pet descriptions
            verticalScrollBar.setPreferredSize(new Dimension(300, 450));
            //only show the scrollbar if it is needed
            verticalScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            //this positions the scrollbar at the top (without it, the scrollbar loads part way through
            //adding of the text areas to the JPanel, resulting in the scrollbar being halfway down the results
            SwingUtilities.invokeLater(() -> verticalScrollBar.getViewport().setViewPosition( new Point(0, 0) ));

            //next, initialise the combo box with the pet names (key set)
            JComboBox<String> optionsCombo = new JComboBox<>(options.keySet().toArray(new String[0]));
            //if the user selects a pet from the dropdown list, this action listener will pick up on it
            ActionListener actionListener = e -> checkUserPetSelection(options,optionsCombo);
            optionsCombo.addActionListener(actionListener);

            //give the user the option to return to view 1 (to search again)
            JButton goBackToSearch = new JButton("Back to search");
            goBackToSearch.addActionListener(e -> {
                mainWindow.setContentPane(searchView);
                mainWindow.revalidate();
            });

            //alternatively, give users the option to send a message to Pinkman
            JButton sendMessage = new JButton("Send message");
            //this action listener will send the user to a pet-free contact form (view 3)
            sendMessage.addActionListener(e -> userSendMessage());

            //this panel is for the dropdown list - it contains a border, with instructional title, the dropdown list and rigid areas for padding
            JPanel selectionPanel = new JPanel();
            selectionPanel.setLayout(new BoxLayout(selectionPanel,BoxLayout.LINE_AXIS));
            selectionPanel.add(Box.createRigidArea(new Dimension(0,20)));
            selectionPanel.add(Box.createRigidArea(new Dimension(10,0)));
            selectionPanel.add(optionsCombo);
            selectionPanel.add(Box.createRigidArea(new Dimension(10,0)));
            selectionPanel.add(goBackToSearch);
            selectionPanel.add(Box.createRigidArea(new Dimension(10,0)));
            selectionPanel.add(sendMessage);
            selectionPanel.add(Box.createRigidArea(new Dimension(10,0)));
            selectionPanel.add(Box.createRigidArea(new Dimension(0,20)));

            //this is the overall panel (view 2) used to display the compatible pets and the dropdown list
            JPanel results = new JPanel();
            results.setLayout(new BorderLayout());
            //add padding to the top of the panel
            results.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.NORTH);
            //add the scrollable pet descriptions to the center
            results.add(verticalScrollBar,BorderLayout.CENTER);
            //add the dropdown list to the bottom
            results.add(selectionPanel,BorderLayout.SOUTH);
            //set main window to the results panel (view 2)
            mainWindow.setContentPane(results);
            mainWindow.revalidate();
        }
        else{
            //if there are no compatible pets, let the user know using a popup window
            JOptionPane.showMessageDialog(searchView,"Unfortunately none of our cuties meet your criteria.\n","No Matching Pets",JOptionPane.INFORMATION_MESSAGE,icon);
        }
    }

    /**
     * method to describe an individual pet, within a non-editable JTextArea
     * @param pet the pet to describe
     * @return a JTextArea
     */
    public static JTextArea describeIndividualPet(Pet pet){
        //create a text area to contain the pet description
        JTextArea petDescription = new JTextArea(pet.toString(Criteria.values()));
        petDescription.setEditable(false);
        //this will ensure that if the description is long, it 'overflows'
        petDescription.setLineWrap(true);
        petDescription.setWrapStyleWord(true);
        return petDescription;
    }

    /**
     * this method checks whether the user has selected 'none' or an actual pet
     * it calls appropriate methods based on the user's selection.
     * @param options a Map that links pet names to pet objects
     */
    public static void checkUserPetSelection(Map<String, Pet> options, JComboBox<String> optionsCombo){
        //assign the user's selected pet name to the petName var
        String petName = (String) optionsCombo.getSelectedItem();
        //if the user selected 'none', then the Pet object will be null - so jump to view 4 (option to search again or send message)
        if(options.get(petName)!=null) {
            Pet chosenPet = options.get(petName);
            //this will switch the contents of the main frame to the user contact field with chosen pet's description
            placeAdoptionRequest(chosenPet);
        }
    }

    /**
     * call this method if user wants to send a message (after they select none from pet list)
     * This method will switch the main frame contents to a contact form, with button
     * It contains an action listener that waits for the user to send the message
     * It sends the message using the writeMessageToFile method
     */
    public static void userSendMessage(){
        //add the image of the dancing dog to the top of the panel
        JLabel dancingDog = new JLabel(new ImageIcon("sunday-happy_small.gif")); //image
        JPanel contactInfo = criteriaEntry.contactForm(); //user entry

        //create a button that allows the user to send their message (and contact details input into the contact form)
        JButton submit = new JButton("Send Message");
        //when they click the button, this line will be written to a file
        ActionListener actionListener = e -> {
            String lineToWrite = "Name: "+criteriaEntry.getName()+" \nEmail: "+criteriaEntry.getEmail()+
                    "\nPhone number: "+criteriaEntry.getPhoneNumber()+"\n\nMessage: "+criteriaEntry.getMessage();
            writeMessageToFile(lineToWrite);
        };
        submit.addActionListener(actionListener);

        JPanel combineImageAndForm = new JPanel(); //flow layout
        dancingDog.setAlignmentX(0);
        contactInfo.setAlignmentX(0);
        combineImageAndForm.add(dancingDog);
        combineImageAndForm.add(contactInfo);

        //create a new panel, and add to it the contact form
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(Box.createRigidArea(new Dimension(0,20)),BorderLayout.NORTH);
        messagePanel.add(combineImageAndForm,BorderLayout.CENTER);
        messagePanel.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.WEST);
        messagePanel.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.EAST);
        messagePanel.add(submit,BorderLayout.SOUTH);
        //this switches to view 3 (contact form)
        userInformationView = messagePanel;
        mainWindow.setContentPane(userInformationView);
        mainWindow.revalidate();
    }

    //Parts 4 and 6.3 and 7
    /**
     * @param lineToWrite the String to be written to the file
     * a method to write a user's message or adoption request to a file
     */
    public static void writeMessageToFile(String lineToWrite){
        String filePath = criteriaEntry.getName().replace(" ","_")+"_query.txt";
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, lineToWrite);
            JOptionPane.showMessageDialog(mainWindow,"Thank you for your message. \nOne of our friendly staff will be in touch shortly. \nClose this dialog to terminate."
                    ,"Message Sent", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }catch (IOException io){
            JOptionPane.showMessageDialog(mainWindow,"Error: Message could not be sent! Please try again!"
                    ,null, JOptionPane.ERROR_MESSAGE);
        }
    }

    //Parts 4 and 6.3
    /**
     * this method allows the user to place an adoption request by entering their details into a contact form
     * to place an adoption request. It writes the pet and person's details
     * to a file once the user clicks "submit"
     */
    public static void placeAdoptionRequest(Pet chosenPet){
        //instruct the user to fill out the form
        JLabel petMessage = new JLabel("To place an adoption request for "+chosenPet.name()+" fill in the form below");
        petMessage.setAlignmentX(0);
        JScrollPane jScrollPane = new JScrollPane(describeIndividualPet(chosenPet));
        jScrollPane.getViewport().setPreferredSize(new Dimension(300,150));
        jScrollPane.setAlignmentX(0);

        //add both the instruction and pet description to a new panel
        JPanel petDescriptionPanel = new JPanel();
        petDescriptionPanel.setAlignmentX(0);
        petDescriptionPanel.add(petMessage);
        petDescriptionPanel.add(jScrollPane);

        //use the contactForm method to get a panel containing components that allow the user to input info
        JPanel userInputPanel = criteriaEntry.contactForm();
        userInputPanel.setAlignmentX(0);
        //create a button, which when clicked, writes the user's request to a file
        JButton submit = new JButton("Submit");
        ActionListener actionListener = e -> {
            String lineToWrite = "Name: "+criteriaEntry.getName()+" \nEmail: "+criteriaEntry.getEmail()+"\nPhone number: "
                    +criteriaEntry.getPhoneNumber()+"\n\nMessage: "+criteriaEntry.getMessage()+
                    "\n\n"+criteriaEntry.getName()+" wishes to adopt "+chosenPet.name()+" ("+chosenPet.microchipNumber()+")";
            writeMessageToFile(lineToWrite);
        };
        submit.addActionListener(actionListener);

        //add the pet description panel, contact form panel and button to a new frame, and assign it to view 3
        JPanel mainFramePanel = new JPanel();
        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.add(petDescriptionPanel,BorderLayout.NORTH);
        mainFramePanel.add(userInputPanel,BorderLayout.CENTER);
        mainFramePanel.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST);
        mainFramePanel.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.EAST);
        mainFramePanel.add(submit,BorderLayout.SOUTH);

        userInformationView = mainFramePanel;
        mainWindow.setContentPane(userInformationView);
        mainWindow.revalidate();
    }

    /**
     * method to load all pet data from file
     * also used to map type to breed - this could be split into a separate method, or done afterward in AllPets
     * @param filePath a file path to the txt file containing all the pet info
     * @return an AllPets object - functions as database of Pets, with associated methods
     */
    public static AllPets loadPets(String filePath){
        AllPets allPets = new AllPets();
        List<String> eachPet = new ArrayList<>();//read the allPets file as a List of Strings
        try {
            Path path = Path.of(filePath);
            eachPet = Files.readAllLines(path);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error: Cannot load pets." +
                    "\nPlease contact Pinkman on 0485 848 584!",null, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        for (int i=1;i<eachPet.size();i++) {
            //split data on comma
            String[] elements = eachPet.get(i).split(",");
            //Pet data
            String name = elements[1];
            long microchipNumber = Long.parseLong(elements[2]);
            int age = Integer.parseInt(elements[5]);
            double adoptionFee = Double.parseDouble(elements[8]);
            //DreamPet data
            Type type = Type.valueOf(elements[0].toUpperCase().replace(" ","_"));
            Sex sex = Sex.valueOf(elements[3].toUpperCase());
            DeSexed deSexed = DeSexed.valueOf(elements[4].toUpperCase());
            String breed = elements[6].toLowerCase();
            Purebred purebred = Purebred.valueOf(elements[7].toUpperCase());
            Hair hair = Hair.valueOf(elements[9].toUpperCase());
            LevelOfTraining levelOfTraining = LevelOfTraining.valueOf(elements[10]);
            int amountOfExercise=0;
            if(!elements[11].equals("NA")) {
                amountOfExercise = Integer.parseInt(elements[11]);
            }
            //Map to store all DreamPet criteria
            Map<Criteria,Object> criteria = new HashMap<>();
            //add all the criteria as key/value pairs in the Map
            criteria.put(Criteria.TYPE,type);
            criteria.put(Criteria.SEX,sex);
            criteria.put(Criteria.DESEXED,deSexed);
            criteria.put(Criteria.BREED, breed);
            criteria.put(Criteria.PUREBRED,purebred);
            criteria.put(Criteria.HAIR,hair);
            criteria.put(Criteria.TRAINING_LEVEL,levelOfTraining);
            criteria.put(Criteria.DAILY_EXERCISE,amountOfExercise);

            //initialise the DreamPet object using the criteria Map
            DreamPet dreamPet = new DreamPet(criteria);
            //initialise the Pet object with pet-specific data, as well as the DreamPet object
            Pet pet = new Pet(name,microchipNumber,age,adoptionFee,dreamPet);
            allPets.addPet(pet);//add the pet to the database
        }
        return allPets;
    }

}