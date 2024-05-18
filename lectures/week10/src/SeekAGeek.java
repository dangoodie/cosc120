/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeekAGeek {

    private static final String filePath = "./allGeeks.txt";
    private static AllGeeks allGeeks;
    private static final String appName = "Seek A Geek 8D";
    private final static String iconPath = "./icon.png";
    private static final ImageIcon icon = new ImageIcon(iconPath);
    //including profile pics is a new addition to COSC120 SeekAGeek - these were generated using AI - do you notice any bias/stereotyping in the images?
    private static final String imagesPath = "profile_pictures/";
    private static final int imageSizeResultsView = 200;

    //EDIT 29: create three sets to contain all the available games, tv shows and hobbies
    private static final Set<String> availableHobbies = new HashSet<>();
    private static final Set<String> availableTVShows = new HashSet<>();
    private static final Set<String> availableGames = new HashSet<>();

    //these Sets of Criteria control what geek details are based - generates descriptions based on relationship type sought by user
    private static final Set<Criteria> studyBuddyFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.SUBJECT_AREA,Criteria.INSTITUTION,Criteria.COURSE,
            Criteria.GENDER,Criteria.STAR_SIGN,Criteria.RELIGION));

    private static final Set<Criteria> friendFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.GENDER,Criteria.STAR_SIGN,Criteria.RELIGION,
            Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES));

    private static final Set<Criteria> moreThanAFriendFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.GENDER,Criteria.STAR_SIGN,Criteria.RELIGION,
            Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES,Criteria.VALENTINES_GIFT,Criteria.ROMANTIC_ACTIVITY));

    private static TypeOfDreamGeek type;
    //JFrame main window fields
    private static JFrame mainWindow=null; //main container
    private static JPanel searchView = null; //view 1
    //results view field/s
    private static JComboBox<String> optionsCombo = null;



    //EDIT 37 - the main method creates the main JFrame. It initialises it with the search view, sets
    //its minimum size and ensures that it exits on close.
    //You will notice that it no longer requests for or checks the user’s username.
    //As an extra challenge, add this functionality back in.
    /**
     * main method - initialises and creates main JFrame
     * @param args NA
     */
    public static void main(String[] args) {
        allGeeks = loadGeeks();
        mainWindow = new JFrame(appName);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setMinimumSize(new Dimension(300,300));
        searchView=generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    //EDIT 30: this method creates a new instance of SearchView – it can be used when first
    //creating the search view, and when ‘refreshing’ it.
    /**
     * method used to 'refresh' or set to empty the fields in the search view
     * @return a searchCriteria object (with default user-input)
     */
    public static SearchView refreshSearchView(){
        return new SearchView(availableGames,availableTVShows,availableHobbies);
    }

    //EDIT 31: this method is used to create the final search JPanel, with N-E-S-W layout,
    //adding the SearchView object to the center, a ‘submit’ button on the bottom,
    //and padding on the left and right.
    /**
     * method to generate the search view of the app, by using the searchCriteria object
     * @return a JPanel representing the search view (with submit button) in N-E-S-W format
     */
    public static JPanel generateSearchView(){
        //JPanel to contain search fields and button
        JPanel searchWindow = new JPanel();
        searchWindow.setLayout(new BorderLayout());
        //initialise the searchCriteria object with a fresh search view
        SearchView searchCriteria = refreshSearchView();
        JPanel searchCriteriaPanel = searchCriteria.generateSearchView();
        //add this panel to the main panel
        searchWindow.add(searchCriteriaPanel,BorderLayout.CENTER);
        //add a search button (which when clicked by the user leads to the database search)
        JButton search = new JButton("Search");
        ActionListener actionListener = e -> conductSearch(searchCriteria); //this will create a DreamGeek object from user input and search the database with it
        search.addActionListener(actionListener);
        searchWindow.add(search,BorderLayout.SOUTH);
        //add padding and return the search window (view 1 in main frame)
        searchWindow.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST);
        searchWindow.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.EAST);
        return searchWindow;
    }

    //EDIT 32: this method uses the SearchView getters to access the user’s choices. It populates a Map object
    //with these choices then creates a DreamGeek object (as previously done). It concludes by searching
    //the allGeeks database and calling the showResults method.
    /**
     * method used to extract user-entered data to create a DreamGeek object which will be used to search the database of real geeks for a match
     * @param searchCriteria an instance of the SearchCriteria class (used to generate JPanels for user to enter/select filters)
     */
    public static void conductSearch(SearchView searchCriteria){
        //a map containing the criteria:value pairs based on user input
        Map<Criteria,Object> criteria = new HashMap<>();
        type = searchCriteria.getTypeOfDreamGeek();
        if(type==TypeOfDreamGeek.SELECT_TYPE) {
            JOptionPane.showMessageDialog(mainWindow,"You MUST select a type of relationship.\n","Invalid search",JOptionPane.INFORMATION_MESSAGE,null);
            return;
        }
        criteria.put(Criteria.TYPE_OF_RELATIONSHIP,type);
        //add the user's selections to the map (if not NA)
        Gender gender = searchCriteria.getGender();
        if(!gender.equals(Gender.NA)) criteria.put(Criteria.GENDER,gender);
        Religion religion = searchCriteria.getReligion();
        if(!religion.equals(Religion.NA))criteria.put(Criteria.RELIGION,religion);
        Set<StarSign> chosenStarSigns = searchCriteria.getStarSign();
        if(!chosenStarSigns.isEmpty()) criteria.put(Criteria.STAR_SIGN,searchCriteria.getStarSign());
        //get the user's age range selection
        int minAge = searchCriteria.getMinAge();
        int maxAge = searchCriteria.getMaxAge();

        //depending on the type of relationship chosen by the user, get the values of their filter entries
        if (type.equals(TypeOfDreamGeek.FRIEND)||type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) {
            Set<String> hobbies = searchCriteria.getChosenHobbies();
            if(!hobbies.isEmpty()) criteria.put(Criteria.HOBBIES,hobbies);
            Set<String> tvShows = searchCriteria.getChosenTVShows();
            if(!tvShows.isEmpty()) criteria.put(Criteria.FAVOURITE_TV_SHOWS,tvShows);
            Set<String> games = searchCriteria.getChosenGames();
            if(!games.isEmpty()) criteria.put(Criteria.FAVOURITE_COMPUTER_GAMES,games);
        }
        if(type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)){
            RomanticActivities romanticActivities = searchCriteria.getRomanticActivity();
            if(!romanticActivities.equals(RomanticActivities.SELECT)) criteria.put(Criteria.ROMANTIC_ACTIVITY,romanticActivities);
            ValentinesGifts valentinesGifts = searchCriteria.getValentinesGift();
            if(!valentinesGifts.equals(ValentinesGifts.SELECT))criteria.put(Criteria.VALENTINES_GIFT,valentinesGifts);
        }
        if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)){
            String institution = searchCriteria.getInstitution();
            String course = searchCriteria.getCourse();
            String subjectArea = searchCriteria.getSubjectArea();
            if(institution!=null && course!=null) {
                criteria.put(Criteria.INSTITUTION,institution);
                criteria.put(Criteria.COURSE,course);
            }
            else if(subjectArea!=null) criteria.put(Criteria.SUBJECT_AREA,subjectArea);
        }
        //as previously done, create a DreamGeek object, and use it to search the real geek database
        DreamGeek dreamGeek = new DreamGeek(minAge,maxAge,criteria);
        List<Geek> potentialMatches = allGeeks.findDreamGeek(dreamGeek);
        //pass the result into the showResultsView method
        showResults(potentialMatches);
    }

    //EDIT 33: this method displays all potential matches (if there are none, it informs the user via a dialog
    //box – see noResults). It calls generateGeekDescriptions to display a non-editable text area containing
    //the geek descriptions. It also uses selectFromResultsPanel to generate a dropdown list, allowing the user
    //to select a geek of their choice, or alternatively, search again.
    /**
     * a method that brings together in a JPanel (view 2) the results, and user-selection options
     * @param potentialMatches an arraylist of geek objects that match the user's selection criteria
     */
    public static void showResults(List<Geek> potentialMatches){
        if(potentialMatches.size()==0){
            noResults();
            return;  //terminate the method if there are no results
        }
        //this is the overall panel (view 2) used to display the compatible pets and the dropdown list
        JPanel results = new JPanel();
        results.setLayout(new BorderLayout()); //N-E-S-W
        results.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.NORTH); //add padding to the top of the panel
        results.add(generateGeekDescriptions(potentialMatches),BorderLayout.CENTER); //add the scroll pane - containing geek descriptions
        results.add(selectFromResultsPanel(potentialMatches),BorderLayout.SOUTH); //add the dropdown list and search again button to the bottom
        results.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST); //add padding on the left/right sides of the panel
        results.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.EAST);
        mainWindow.setContentPane(results); //set main window (JFrame) to the results panel (view 2)
        mainWindow.revalidate();
    }

    //EDIT 35: code to load profile pictures for Geeks that meet search criteria
    //----------------Profile pictures------------------------//

    /**
     * loads a resized profile image given the image file path
     * @param path the path to an individual image/profile pic
     * @param imageSize assuming we want square images, one dimension is all we need
     * @return an ImageIcon object, representing our resized, scaled image
     */
    public static ImageIcon loadImageFromPath(String path, int imageSize){
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT));
    }

    /**
     * loads all user profile pictures, returning a mapping between usernames and profile pics
     * @param filesList a Set of image file paths
     * @param imageSize assuming we want square images, one dimension is all we need
     * @return a Mapping of username to profile picture
     */
    public static Map<String,JLabel> loadImages(Set<File> filesList, int imageSize){
        assert filesList != null;
        Map<String,JLabel> loadedImages = new HashMap<>();
        for(File file : filesList) {
            String username = file.getName().split("\\.")[0];
            ImageIcon imageIcon = loadImageFromPath(String.valueOf(file), imageSize);
            loadedImages.put(username,new JLabel(imageIcon));
        }
        return loadedImages;
    }

    /**
     * retrieves the username of each matching Geek, using it to load and return the File representing their profile picture
     * @param matching a List of all Geek objects that meet the user's search criteria
     * @return a Set of File objects (each profile picture is named according to username)
     */
    private static Set<File> optionsImages(List<Geek> matching){
        Set<File> optionsImages = new HashSet<>();
        for(Geek g: matching) {
            String matchingKey = g.getUsername();
            optionsImages.add(new File(imagesPath+matchingKey+".png"));
        }
        return optionsImages;
    }
    //-----------------end EDIT 35----------------------------

    //EDIT 34: rather than using JLabel to display the geek info, this method uses
    //uneditable text areas, allowing for nicer formatting, and use of a scroll bar.
    /**
     * method to generate JScrollPane containing descriptions of matching geeks
     * @param potentialMatches an arraylist of geek objects that match the user's selection criteria
     * @return JScrollPane a scroll pane containing a collection of non-editable JTextAreas each representing
     * a description of 1 matching geek
     */
    public static JScrollPane generateGeekDescriptions(List<Geek> potentialMatches){
        //this array will contain all the user's options - a collection of geek names they can choose from
        Map<String, Geek> options = new HashMap<>();
        Map<String,JLabel> optionsImages = loadImages(optionsImages(potentialMatches),imageSizeResultsView);

        //panel to contain one text area per geek (each text area describes 1 geek)
        JPanel geekDescriptions = new JPanel();
        geekDescriptions.setBorder(BorderFactory.createTitledBorder("Matches found!! The following geeks meet your criteria: "));
        geekDescriptions.setLayout(new BoxLayout(geekDescriptions,BoxLayout.Y_AXIS)); //stack vertically
        geekDescriptions.add(Box.createRigidArea(new Dimension(0,10))); //padding

        //loop through the matches, generating a description of each - description varies based on the type of relationship the user is after
        for (Geek geek: potentialMatches) {

            //create a JPanel just for the profile pic
            JPanel image = new JPanel();
            image.setBackground(Color.white);
            image.setLayout(new BorderLayout());
            image.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.WEST);
            image.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.EAST);
            JLabel itemImage = optionsImages.get(geek.getUsername());
            image.add(itemImage,BorderLayout.CENTER);

            JTextArea geekDescription = new JTextArea();
            if (type.equals(TypeOfDreamGeek.FRIEND)) geekDescription = new JTextArea("\n"+geek.getGeekDescription(friendFeatures));
            if (type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) geekDescription = new JTextArea("\n"+geek.getGeekDescription(moreThanAFriendFeatures));
            if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)) geekDescription = new JTextArea("\n"+geek.getGeekDescription(studyBuddyFeatures));
            geekDescription.setEditable(false); //ensure the description can't be edited!
            //this will ensure that if the description is long, it 'overflows'
            geekDescription.setLineWrap(true);
            geekDescription.setWrapStyleWord(true); //ensure words aren't broken across new lines

            //combine the text and image into 1 panel per geek
            JPanel textAndImage = new JPanel();
            textAndImage.setLayout(new BorderLayout());
            textAndImage.add(image,BorderLayout.WEST);
            textAndImage.add(geekDescription,BorderLayout.CENTER);

            //add the image and text panel to the main results panel
            geekDescriptions.add(textAndImage);
            geekDescriptions.add(Box.createRigidArea(new Dimension(0,5)));

            options.put(geek.getName(), geek); //populate the array used for the dropdown list
        }
        //next, initialise the combo box with the geek names (key set)
        optionsCombo = new JComboBox<>(options.keySet().toArray(new String[0]));


        //add a scroll pane to the results window, so that if there are many results, users can scroll as needed
        JScrollPane verticalScrollBar = new JScrollPane(geekDescriptions);
        verticalScrollBar.setPreferredSize(new Dimension(300, 450));
        verticalScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //this positions the scrollbar at the top (without it, the scrollbar loads part way through
        //adding of the text areas to the JPanel, resulting in the scrollbar being halfway down the results
        SwingUtilities.invokeLater(() -> verticalScrollBar.getViewport().setViewPosition( new Point(0, 0) ));
        return verticalScrollBar;
    }

    //EDIT 36: – this method generates a dropdown list of compatible geeks, and a button that allows the user
    //to search again if they don’t like their results. It contains 2 action listeners – the first regenerates
    //the search view if the user chooses to search again, and the second displays a dialog box containing their
    //chosen geek’s contact details if they select an option from the dropdown list.
    /**
     * @param potentialMatches an arraylist of geek objects that match the user's selection criteria
     * method to generate dropdown list (of geeks) and search again button panel
     */
    public static JPanel selectFromResultsPanel(List<Geek> potentialMatches){
        //give the user the option to search again if they don't like their results
        JLabel noneMessage = new JLabel("If none meet your criteria, close to exit, or search again with different criteria");
        JButton editSearchCriteriaButton = new JButton("Search again");
        ActionListener actionListenerEditCriteria = e -> reGenerateSearchView();
        editSearchCriteriaButton.addActionListener(actionListenerEditCriteria);

        //the user must choose from one of the real geeks - set the default string to instruct them
        String defaultOption = "Select geek";
        optionsCombo.addItem(defaultOption);
        optionsCombo.setSelectedItem(defaultOption);
        //if the user selects an option, e.g. a geek from the dropdown list, this action listener will pick up on it
        ActionListener actionListener = e -> checkUserSelection(potentialMatches);
        optionsCombo.addActionListener(actionListener);

        //create a panel for the button and the dropdown list - this has a flowlayout - left to right placement
        JPanel buttonOptionPanel = new JPanel();
        buttonOptionPanel.add(optionsCombo);
        buttonOptionPanel.add(editSearchCriteriaButton);

        //create and return a panel containing the panel with button/dropdown list, as well as a border/title and padding
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel,BoxLayout.Y_AXIS)); //stack vertically
        selectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Please select which geek you'd like to contact:"));
        selectionPanel.add(noneMessage);
        selectionPanel.add(buttonOptionPanel);
        selectionPanel.add(Box.createRigidArea(new Dimension(10,0)));
        return selectionPanel;
    }

    /**
     * method to reset the search view to blank, and reset the main frame to the search view
     */
    public static void reGenerateSearchView(){
        searchView = generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.revalidate();
    }

    /**
     * a method used to generate a popup box informing the user that their search returned no results
     * resets the search view to blank
     */
    public static void noResults(){
        JOptionPane.showMessageDialog(mainWindow,"Unfortunately your search returned no compatible geeks.\n","No Compatible Geeks",JOptionPane.INFORMATION_MESSAGE,icon);
        reGenerateSearchView();
    }

    /**
     * method used in action listener to get the contact details of the chosen geek, displaying them in a pop out window
     * @param potentialMatches an ArrayList of compatible geeks (user can only select one of these geeks)
     */
    public static void checkUserSelection(List<Geek> potentialMatches){
        String decision = (String) optionsCombo.getSelectedItem();
        assert decision != null; //we know it can't be null
        //if the user has selected a real geek, see which one it is and return their details
        for (Geek g : potentialMatches) {
            if (decision.equals(g.getName())) {
                JOptionPane.showMessageDialog(mainWindow, g.getContactDetailsDescription());
                break; //once the matching geek is found, no need to keep looping
            }
        }
    }

    //-------------------------------load data-----------------------------------------------------------

    /**
     * method to load all the geek info from a file, using the data to instantiate an AllGeeks object
     * @return an AllGeeks object - this is a dataset of Geek objects
     */
    private static AllGeeks loadGeeks() {
        AllGeeks allGeeks = new AllGeeks();
        Path path = Path.of(filePath);
        List<String> eachGeek = null;
        try{
            eachGeek = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("The file could not be loaded. Check file path is correct. Terminating.\nError message: "+io.getMessage());
            System.exit(0);
        }
        for (int i=1;i<eachGeek.size();i++) {

            String[] elements = eachGeek.get(i).split("\\[");
            String[] geekInfo = elements[0].split(",");
            String username = geekInfo[0].replaceAll("\n", "");
            String name = geekInfo[1];

            int age = -1;
            try{
                age = Integer.parseInt(geekInfo[2]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String phoneNumber=null;
            if(isValidPhoneNumber(geekInfo[3])) phoneNumber=geekInfo[3];
            else{
                System.out.println("Error in file. Phone number could not be parsed for geek on line "+(i+1)+". Terminating.");
                System.exit(0);
            }

            String emailAddress=null;
            if(isValidEmail(geekInfo[4])) emailAddress=geekInfo[4];
            else{
                System.out.println("Error in file. Email could not be parsed for geek on line "+(i+1)+". Terminating.");
                System.exit(0);
            }

            Gender gender = Gender.OTHER;
            try {
                gender = Gender.valueOf(geekInfo[5]);
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Gender could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }
            StarSign starSign = StarSign.CANCER;
            try{
                starSign = StarSign.valueOf(geekInfo[6].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Star sign could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            Religion religion = Religion.UNAFFILIATED;
            try{
                religion = Religion.valueOf(geekInfo[7].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Religion could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }
            RomanticActivities romanticActivity = RomanticActivities.BINGE_TV_SHOWS;
            try{
                romanticActivity = RomanticActivities.valueOf(geekInfo[8].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Romantic activity could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            ValentinesGifts valentinesGift = ValentinesGifts.CHESS_SET;
            try{
                valentinesGift = ValentinesGifts.valueOf(geekInfo[9].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Valentine's Day gift could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            String institution = geekInfo[10].toLowerCase();
            String course = geekInfo[11].toLowerCase();
            String subjectArea = geekInfo[12].toUpperCase();

            String school = geekInfo[13].toLowerCase();
            String graduationYear = geekInfo[14].toUpperCase();
            Set<String> typesOfRelationships = loadCollectionData(elements[1]);
            Set<TypeOfDreamGeek> typeOfDreamGeek = new HashSet<>();
            for(String type: typesOfRelationships) typeOfDreamGeek.add(TypeOfDreamGeek.valueOf(type.toUpperCase().replace(" ","_")));

            Set<String> hobbies = loadCollectionData(elements[2]);
            Set<String> faveCompGames = loadCollectionData(elements[3]);
            Set<String> favouriteTVShows = loadCollectionData(elements[4]);

            //EDIT 29: for each geek in the file, add all their games, tv shows and hobbies to each of three
            //new fields (at the top of the class). Is there another way this could be done?
            availableHobbies.addAll(hobbies);
            availableGames.addAll(faveCompGames);
            availableTVShows.addAll(favouriteTVShows);

            String statement = elements[5].replace("],","");

            Set<String> blockedGeeks = loadCollectionData(elements[6]);

            Map<Criteria,Object> criteriaMap = new LinkedHashMap<>();
            criteriaMap.put(Criteria.GENDER,gender);
            criteriaMap.put(Criteria.STAR_SIGN,starSign);
            criteriaMap.put(Criteria.RELIGION,religion);
            criteriaMap.put(Criteria.ROMANTIC_ACTIVITY,romanticActivity);
            criteriaMap.put(Criteria.VALENTINES_GIFT,valentinesGift);
            criteriaMap.put(Criteria.INSTITUTION,institution);
            criteriaMap.put(Criteria.COURSE,course);
            criteriaMap.put(Criteria.SUBJECT_AREA,subjectArea);
            criteriaMap.put(Criteria.FAVOURITE_COMPUTER_GAMES,faveCompGames);
            criteriaMap.put(Criteria.FAVOURITE_TV_SHOWS,favouriteTVShows);
            criteriaMap.put(Criteria.HOBBIES,hobbies);
            criteriaMap.put(Criteria.SCHOOL,school);
            criteriaMap.put(Criteria.GRADUATION_YEAR,graduationYear);
            criteriaMap.put(Criteria.TYPE_OF_RELATIONSHIP,typeOfDreamGeek);

            DreamGeek dreamGeek = new DreamGeek(criteriaMap);

            Geek geek = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeek);
            allGeeks.addGeek(geek);
        }
        return allGeeks;
    }

    /**
     * use to parse/read collection type data from the file
     * @param rawData a String straight from the file in the format item1, item2 etc. containing unwanted characters
     * @return a Set of Strings, each representing one 'cleaned up' item in a collection
     */
    private static Set<String> loadCollectionData(String rawData){
        String[] splitData = rawData.replace("]","").replace("\r","").split(",");
        Set<String> processedData = new HashSet<>();
        for(String item: splitData) processedData.add(item.toLowerCase().strip());
        return processedData;
    }

    /**
     * a regex matcher that ensures that the user's entry starts with a 0 and is followed by 9 digits
     * @param phoneNumber the candidate phone number entered by the user
     * @return true if phone number matches regex/false if not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry complies with RFC 5322
     * source: <a href="https://www.baeldung.com/java-email-validation-regex">...</a>
     * @param email the candidate email entered by the user
     * @return true if email matches regex/false if not
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
