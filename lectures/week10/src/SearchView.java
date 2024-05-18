/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;

public class SearchView {

    //EDIT 2: create a field to store the user’s type of relationship selection.
    private TypeOfDreamGeek typeOfDreamGeek;

    //EDIT 5: create a CardLayout object
    private final CardLayout cardLayout  = new CardLayout();
    //EDIT 6: create final String labels (constants) to associate with each JPanel that will act as a card
    private final String FRIENDS_PANEL = "Friends";
    private final String MORE_THAN_FRIENDS_PANEL = "More than friends";
    private final String STUDY_BUDDY_PANEL = "Study buddy";
    private final String IMAGE_PANEL = "geek images";
    //EDIT 7: create a JPanel (container) field. Its layout will be set to CardLayout later,
    //so that it can act as a container of cards.
    private JPanel typeOfDreamGeekSpecificCriteriaPanel;

    //EDIT 9: add fields to store user choices for gender, religion and star sign.
    private Gender gender;
    private Religion religion;
    private Set<StarSign> starSigns;

    //EDIT 13: add two int vars for min and max age (and getters too).
    //Create another int var for minimum legal age (18) and default age range (10).
    //Also add two fields that will act as feedback labels for the user.
    private final int minimumLegalAge=18;
    private final int defaultAgeRange=10;
    private int minAge = minimumLegalAge;
    private int maxAge = minimumLegalAge+defaultAgeRange;
    private final JLabel feedbackMin = new JLabel(" "); //set to blank to start with
    private final JLabel feedbackMax = new JLabel(" ");

    //these are all the available games, tv shows and hobbies, obtained from the file
    private final Set<String> availableHobbies;
    private final Set<String> availableTVShows;
    private final Set<String> availableGames;

    //EDIT 26 – remember to create fields to store the user’s choices
    private Set<String> chosenGames;
    private Set<String> chosenTVShows;
    private Set<String> chosenHobbies;
    private ValentinesGifts valentinesGift;
    private RomanticActivities romanticActivity;
    private String institution;
    private String course;
    private String subjectArea;

    //EDIT 28: finally, create a constructor that will be used to instantiate this class, initializing the available games,
    //tv shows and hobbies data structures to the values derived from the inventory.txt file.
    //These will be used to populate the games, tv shows and hobbies JLists.
    /**
     * constructor used to initialise the search view
     * @param availableGames all the games the user can choose from - derived from inventory.txt
     * @param availableTVShows all the tv shows the user can choose from - derived from inventory.txt
     * @param availableHobbies all the hobbies the user can choose from - derived from inventory.txt
     */
    public SearchView(Set<String> availableGames,Set<String> availableTVShows,Set<String> availableHobbies){
        this.availableHobbies = availableHobbies;
        this.availableTVShows = availableTVShows;
        this.availableGames = availableGames;
        //also initialise the user-choice data structures to empty
        this.starSigns = new HashSet<>();
        this.chosenGames = new HashSet<>();
        this.chosenTVShows = new HashSet<>();
        this.chosenHobbies = new HashSet<>();
    }

    //EDIT 27: this method will bring together all the search panels in the class, placing the ‘type’ dropdown
    //list at the top of the search view, followed by the generic criteria panel. It then sets the layout manager
    //of the panel that varies based on type to CardLayout, adding all the cards (JPanels) to this ‘container’ JPanel.
    /**
     * a method to generate a JPanel that represents the search view
     * also initialises the variable JPanel layout to CardLayout, enabling 'swapping out' of cards based on relationship type
     * @return the JPanel described.
     */
    public JPanel generateSearchView(){
        //create a new JPanel to contain the other JPanels
        JPanel criteria = new JPanel();
        //this will stack the sub-containers vertically
        criteria.setLayout(new BoxLayout(criteria,BoxLayout.Y_AXIS));
        //add the type of relationship, and generic filters to the panel, along with some padding

        JPanel type = this.userInputTypeOfRelationship();
        type.setAlignmentX(0);
        criteria.add(type);
        JPanel generic = this.userInputGenericCriteria();
        generic.setAlignmentX(0);
        criteria.add(generic);

        criteria.add(Box.createRigidArea(new Dimension(0,20)));

        //initialise the JPanel that is to contain the relationship-specific filters
        typeOfDreamGeekSpecificCriteriaPanel = new JPanel();
        //set the layout to cardLayout, then add all the relationship-specific panels to it (we'll switch between the panels as we need them)
        //the string constants are necessary to keep track of the cards
        typeOfDreamGeekSpecificCriteriaPanel.setAlignmentX(0);
        typeOfDreamGeekSpecificCriteriaPanel.setLayout(cardLayout);
        typeOfDreamGeekSpecificCriteriaPanel.add(this.generateImagePanel(),IMAGE_PANEL);
        typeOfDreamGeekSpecificCriteriaPanel.add(this.userInputFriendCriteria(),FRIENDS_PANEL);
        typeOfDreamGeekSpecificCriteriaPanel.add(this.userInputMoreThanAFriend(),MORE_THAN_FRIENDS_PANEL);
        typeOfDreamGeekSpecificCriteriaPanel.add(this.userInputStudyBuddy(),STUDY_BUDDY_PANEL);
        //add the relationship-specific panel to the main search panel and return it
        criteria.add(typeOfDreamGeekSpecificCriteriaPanel);
        return criteria;
    }

    //EDIT 3: this method should return a JPanel that enables users to select the type of
    //relationship they’re after from a dropdown list.
    /**
     * a method to populate a dropdown list with types of relationships, prompt user selection, and listen for user selection of a type
     * @return a JPanel containing instruction to user and type of relationship dropdown list
     */
    public JPanel userInputTypeOfRelationship(){
        //create a combo box (drop-down list), populating it with the types of relationships available to the user
        JComboBox<TypeOfDreamGeek> typeOfDreamGeekJComboBox = new JComboBox<>(TypeOfDreamGeek.values());
        //set the program focus on this combo-box - selecting the type of relationship should be the user's first step
        typeOfDreamGeekJComboBox.requestFocusInWindow();
        //set the 'selected item' to SELECT_TYPE to prompt the user to select a type of relationship
        typeOfDreamGeekJComboBox.setSelectedItem(TypeOfDreamGeek.SELECT_TYPE);
        //initialise the data field
        typeOfDreamGeek = (TypeOfDreamGeek) typeOfDreamGeekJComboBox.getSelectedItem();
        //EDIT 3: Check for changes in user selection, by adding an ItemListener to the dropdown list.
        //This should point to a method (ifTypeSelected), which handles the program's response when a type is selected.
        typeOfDreamGeekJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) ifTypeSelected(typeOfDreamGeekJComboBox);
        });
        //create and return a new JPanel to contain the padding, instructional label and dropdown list
        JPanel typeOfRelationshipSelectionPanel = new JPanel();
        typeOfRelationshipSelectionPanel.setLayout(new BoxLayout(typeOfRelationshipSelectionPanel,BoxLayout.Y_AXIS));
        typeOfRelationshipSelectionPanel.add(Box.createRigidArea(new Dimension(0,20)));
        typeOfRelationshipSelectionPanel.add(typeOfDreamGeekJComboBox);
        typeOfRelationshipSelectionPanel.add(Box.createRigidArea(new Dimension(0,20)));
        return typeOfRelationshipSelectionPanel;
    }

    //EDIT 4: This method should handle the program’s response to a user selecting which relationship type they want.
    /**
     * method called if user selects type of relationship - used to display the appropriate search criteria panel
     * @param typeOfDreamGeekJComboBox the dropdown list from which user has selected a type of relationship
     */
    public void ifTypeSelected(JComboBox<TypeOfDreamGeek> typeOfDreamGeekJComboBox){
        //It should first update the typeOfDreamGeek field
        typeOfDreamGeek = (TypeOfDreamGeek) typeOfDreamGeekJComboBox.getSelectedItem();
        assert typeOfDreamGeek != null; //we know that typeOfDreamGeek won't be null
        //use the CardLayout object to show the appropriate JPanel 'card' based on the user's dropdown list choice
        //EDIT 8: you can then switch between the cards based on the user’s selection, using the show method.
        if (typeOfDreamGeek.equals(TypeOfDreamGeek.SELECT_TYPE)) cardLayout.show(typeOfDreamGeekSpecificCriteriaPanel,IMAGE_PANEL);
        else if (typeOfDreamGeek.equals(TypeOfDreamGeek.FRIEND)) cardLayout.show(typeOfDreamGeekSpecificCriteriaPanel,FRIENDS_PANEL);
        else if(typeOfDreamGeek.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) cardLayout.show(typeOfDreamGeekSpecificCriteriaPanel,MORE_THAN_FRIENDS_PANEL);
        else if(typeOfDreamGeek.equals(TypeOfDreamGeek.STUDY_BUDDY)) cardLayout.show(typeOfDreamGeekSpecificCriteriaPanel,STUDY_BUDDY_PANEL);
    }

    //EDIT 10: this method creates a ButtonGroup, adding 4 radio buttons to it, representing male,
    //female, other and NA.  These buttons are added to a JPanel, which is returned.
    /**
     * a method that allows users to select gender, from options - male, female, other (or NA)
     * @return a JPanel of radio buttons from which users can select only one option
     */
    public JPanel userInputGender(){
        //the button group ensures only one button is selected
        ButtonGroup genderButtonGroup = new ButtonGroup();
        //create one button for each option
        JRadioButton male = new JRadioButton(Gender.MALE.toString());
        JRadioButton female = new JRadioButton(Gender.FEMALE.toString());
        JRadioButton other = new JRadioButton(Gender.OTHER.toString());
        JRadioButton na = new JRadioButton(Gender.NA.toString(),true); //gender is initialised to NA by default
        male.requestFocusInWindow();
        gender = Gender.NA;
        //add the buttons to the group
        genderButtonGroup.add(male);
        genderButtonGroup.add(female);
        genderButtonGroup.add(other);
        genderButtonGroup.add(na);
        //set the action command - value when clicked
        male.setActionCommand(Gender.MALE.name());
        female.setActionCommand(Gender.FEMALE.name());
        other.setActionCommand(Gender.OTHER.name());
        na.setActionCommand(Gender.NA.name());
        //add an action listener to each button, where the action listener updates the gender field if a user changes their selection
        ActionListener actionListener = e-> gender = Gender.valueOf(genderButtonGroup.getSelection().getActionCommand().toUpperCase());
        male.addActionListener(actionListener);
        female.addActionListener(actionListener);
        other.addActionListener(actionListener);
        na.addActionListener(actionListener);

        //create and return a new JPanel (add all the buttons to it first)
        JPanel genderPanel = new JPanel();
        genderPanel.setAlignmentX(0);
        genderPanel.setBorder(BorderFactory.createTitledBorder("Which gender are you interested in?"));
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);
        genderPanel.add(na);

        return genderPanel;
    }

    //EDIT 11: this method creates a dropdown list of religions, allowing the user to select ONE option.
    //It uses an ItemListener to detect changes in user selection, updating the field accordingly.
    //It returns a JPanel containing an instruction, and the dropdown list.
    /**
     * a method that allows the user to select a preferred religion
     * @return a JPanel containing a dropdown list and instructional JLabel
     */
    public JPanel userInputReligion(){
        //a dropdown list of all religion options
        JComboBox<Religion> religionJComboBox = new JComboBox<>(Religion.values());
        religionJComboBox.setAlignmentX(0);
        //let's assume the user prefers atheist/agnostic
        religionJComboBox.setSelectedItem(Religion.NA);
        //initialise the field
        religion = (Religion) religionJComboBox.getSelectedItem();
        //update the field if user changes selection
        religionJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                religion = (Religion) religionJComboBox.getSelectedItem();
            }
        });
        //create and return JPanel containing instruction, and dropdown list and padding
        JPanel religionPanel = new JPanel();
        religionPanel.setLayout(new BoxLayout(religionPanel,BoxLayout.Y_AXIS));
        religionPanel.setAlignmentX(0);
        religionPanel.add(Box.createRigidArea(new Dimension(0,5)));
        JLabel instruction = new JLabel("Please select your preferred religion");
        instruction.setAlignmentX(0);
        religionPanel.add(instruction);
        religionPanel.add(religionJComboBox);
        religionPanel.add(Box.createRigidArea(new Dimension(0,5)));
        return religionPanel;
    }

    //EDIT 12: this method creates a JList of all the star sign constants, allowing the user to select one or more values.
    //It restricts the visible star signs to three values but has a scroll bar to enable users to see all available options.
    /**
     * a method that allows the user to select a preferred star sign (or NA)
     * @return a JPanel containing a dropdown list and instructional JLabel
     */
    public JPanel userInputStarSign(){
        //Create a JList of all the star signs
        JList<StarSign> selectStarSigns = new JList<>(StarSign.values());
        //ensure you enable multi-selection
        selectStarSigns.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //create a scroll pane to limit the visible size of the JList and enable scrolling
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(selectStarSigns);
        selectStarSigns.setLayoutOrientation(JList.VERTICAL); //vertical scrollbar
        //set the size of the scroll pane
        scrollPane.setPreferredSize(new Dimension(250, 60));
        //always show the scrollbar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //set the position of the scroll bar  to the top of the scrollable area
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition( new Point(0, 0) ));

        //update the star sign field if the user selects a new item
        ListSelectionListener listSelectionListener = e-> starSigns = new HashSet<>(selectStarSigns.getSelectedValuesList());
        selectStarSigns.addListSelectionListener(listSelectionListener);
        //add the dropdown list, and an instructional JLabel to a panel and return it
        JPanel starSignPanel = new JPanel();
        starSignPanel.setLayout(new BoxLayout(starSignPanel,BoxLayout.Y_AXIS));
        starSignPanel.add(Box.createRigidArea(new Dimension(0,5)));
        JLabel instruction = new JLabel("Please select your preferred star sign");
        instruction.setAlignmentX(0);
        starSignPanel.add(instruction);
        JLabel clarification = new JLabel("(To multi-select, hold Ctrl)");
        clarification.setAlignmentX(0);
        clarification.setFont(new Font("", Font. ITALIC, 12));
        starSignPanel.add(clarification);
        scrollPane.setAlignmentX(0);
        starSignPanel.add(scrollPane); //add the scrollable area to your JPanel as usual
        starSignPanel.add(Box.createRigidArea(new Dimension(0,5)));
        return starSignPanel;
    }

    //EDIT 14: this method will allow the user to enter a min age >= 18, and a max age >= to the min age.
    //It will provide feedback to the user as they type, using a DocumentListener.
    /**
     * method used to get and validate user input for age range
     * this is a very long method - perhaps the functionality of the action listeners could be delegated to one or two methods?
     * @return a JPanel containing instructions, text fields for age input and feedback for validation
     */
    public JPanel getUserInputAgeRange(){
        //labels for the text boxes
        JLabel minLabel = new JLabel("Min. age");
        JLabel maxLabel = new JLabel("Max. age");
        //create text boxes...
        JTextField min = new JTextField(4);
        JTextField max = new JTextField(4);
        //set default values for the age range text boxes (editable)
        min.setText(String.valueOf(minAge));
        max.setText(String.valueOf(maxAge));

        //this is how you change the font and size of text
        feedbackMin.setFont(new Font("", Font. ITALIC, 12));
        feedbackMin.setForeground(Color.RED);
        feedbackMax.setFont(new Font("", Font. ITALIC, 12));
        feedbackMax.setForeground(Color.RED);

        //EDIT 15: let’s add a document listener to the min and max age text fields.
        //You will see that the insertUpdate, removeUpdate and changedUpdate method declarations will
        //be automatically added. You must implement these (or leave them blank).
        //We’ll implement the first two, so that whenever the user enters or removes text from the fields,
        //we check whether the contents are valid.
        min.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //if the check min method returns false, request user addresses invalid input
                if(!checkMin(min)) min.requestFocus();
                checkMax(max); //after min has been updated, check max is still valid
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                //removing and inserting should be subjected to the same checks
                if(!checkMin(min))min.requestFocus();
                checkMax(max);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {} //NA
        });
        max.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(!checkMax(max)) max.requestFocusInWindow();
                checkMin(min);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(!checkMax(max))max.requestFocusInWindow();
                checkMin(min);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        //add the text fields and labels to a panel
        JPanel ageRangePanel = new JPanel(); //flowlayout by default
        ageRangePanel.add(minLabel);
        ageRangePanel.add(min);
        ageRangePanel.add(maxLabel);
        ageRangePanel.add(max);

        JPanel finalPanel = new JPanel();
        finalPanel.setBorder(BorderFactory.createTitledBorder("Enter desired age range"));
        finalPanel.setLayout(new BoxLayout(finalPanel,BoxLayout.Y_AXIS)); //stack elements vertically
        finalPanel.setAlignmentX(0);
        finalPanel.add(ageRangePanel);
        feedbackMin.setAlignmentX(0);
        feedbackMax.setAlignmentX(0);
        finalPanel.add(feedbackMin); //feedback below age entry text boxes
        finalPanel.add(feedbackMax);

        return finalPanel;
    }

    //EDIT 16: to minimize code repetition, let’s outsource the task of validating the user entry to these two methods.
    //They will return true if input is valid, and false if it isn’t. If input is not valid, we will request that
    //the user changes their input.
    /**
     * validates user input for min age
     * @param minEntry the JTextField used to enter min age
     * @return true if valid age, false if invalid
     */
    private boolean checkMin(JTextField minEntry){
        feedbackMin.setText("");
        try{
            int tempMin = Integer.parseInt(minEntry.getText());
            if(tempMin < minimumLegalAge || tempMin>maxAge) {
                feedbackMin.setText("Min age must be >= "+minimumLegalAge+" and <= "+maxAge+". Defaulting to "+minAge+" - "+maxAge+".");
                minEntry.selectAll();
                return false;
            }else {
                minAge=tempMin;
                feedbackMin.setText("");
                return true;
            }
        }catch (NumberFormatException n){
            feedbackMin.setText("Please enter a valid number for min age. Defaulting to "+minAge+" - "+maxAge+".");
            minEntry.selectAll();
            return false;
        }
    }

    /**
     * validates user input for max age
     * @param maxEntry the JTextField used to enter max age
     * @return true if valid age, false if invalid
     */
    private boolean checkMax(JTextField maxEntry){
        feedbackMax.setText("");
        try{
            int tempMax = Integer.parseInt(maxEntry.getText());
            if(tempMax < minAge) {
                feedbackMax.setText("Max age must be >= min age. Defaulting to "+minAge+" - "+maxAge+".");
                maxEntry.selectAll();
                return false;
            }else {
                maxAge = tempMax;
                feedbackMax.setText("");
                return true;
            }
        }catch (NumberFormatException n){
            feedbackMax.setText("Please enter a valid number for max age. Defaulting to "+minAge+" - "+maxAge+".");
            maxEntry.selectAll();
            return false;
        }
    }
    //END EDIT 16


    /*--------------------relationship specific panels------------------*/

    //Next, let’s add in functionality to filter based on the ‘friend’ relationship type. This will involve
    //created three, almost identical multi-select JLists, to allow users to choose games, tv shows and hobbies.
    // The best way to do this is to reduce repetition by creating helper methods.

    //EDIT 19: this method will act as a helper method, allowing a JList passed in as a
    //parameter to be added to a scroll pane.
    /**
     * gives a JList scroll pane functionality
     * @param selectItems the JList to embed in the scroll pane
     * @return a scroll pane of specified size and behaviour, containing a JList
     */
    public JScrollPane generateJListScrollPane(JList<String> selectItems){
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(selectItems);
        selectItems.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setPreferredSize(new Dimension(250, 60));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0) ));
        return scrollPane;
    }
    //EDIT 20:  this helper method will create a JPanel for each of hobbies,
    //tv shows and games, using the same formatting.
    /**
     * generates a JPanel containing the correcting padding, alignment and components
     * used for getting user input for games, tv shows and hobbies
     * @param instruction the text instruction to the user
     * @param scrollPane the scroll pane containing the JList
     * @return a formatted JPanel containing the instruction and scroll pane
     */
    public JPanel generateFinalScrollJPanel(String instruction, JScrollPane scrollPane){
        JLabel instructionLabel = new JLabel(instruction);
        instructionLabel.setAlignmentX(0);
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel,BoxLayout.Y_AXIS));
        itemsPanel.add(Box.createRigidArea(new Dimension(0,5)));
        itemsPanel.add(instructionLabel);
        scrollPane.setAlignmentX(0);
        itemsPanel.add(scrollPane);
        itemsPanel.add(Box.createRigidArea(new Dimension(0,5)));
        return itemsPanel;
    }
    //EDIT 21: these methods create the JLists from the available data (see constructor) and
    //call the above two methods to initialise each of the relevant fields with the user’s
    //selection, adding an appropriate ListSelectionListener event handler.
    /**
     * allows user selection of one of more games, including event handling
     * @return a JPanel featuring the games JList in a scroll pane
     */
    public JPanel userInputGames(){
        JList<String> selectItems = new JList<>(availableGames.toArray(new String[0]));
        selectItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = this.generateJListScrollPane(selectItems);
        ListSelectionListener listSelectionListener = e ->  chosenGames = new HashSet<>(selectItems.getSelectedValuesList());
        selectItems.addListSelectionListener(listSelectionListener);
        return generateFinalScrollJPanel("Select your favourite game/s (optional)",scrollPane);
    }
    /**
     * allows user selection of one of more tv shows, including event handling
     * @return a JPanel featuring the tv shows JList in a scroll pane
     */
    public JPanel userInputTVShows(){
        JList<String> selectItems = new JList<>(availableTVShows.toArray(new String[0]));
        selectItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = this.generateJListScrollPane(selectItems);
        ListSelectionListener listSelectionListener = e ->  chosenTVShows = new HashSet<>(selectItems.getSelectedValuesList());
        selectItems.addListSelectionListener(listSelectionListener);
        return generateFinalScrollJPanel("Select your favourite tv show/s (optional)",scrollPane);
    }
    /**
     * allows user selection of one of more hobbies, including event handling
     * @return a JPanel featuring the hobbies JList in a scroll pane
     */
    public JPanel userInputHobbies(){
        JList<String> selectItems = new JList<>(availableHobbies.toArray(new String[0]));
        selectItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = this.generateJListScrollPane(selectItems);
        ListSelectionListener listSelectionListener = e ->  chosenHobbies = new HashSet<>(selectItems.getSelectedValuesList());
        selectItems.addListSelectionListener(listSelectionListener);
        return generateFinalScrollJPanel("Select your favourite hobby/hobbies (optional)",scrollPane);
    }

    //Next, let’s add in functionality to filter based on the ‘more than a friend’ relationship type.

    //EDIT 23: these methods will allow the user to select their preferred romantic activity and
    // Valentine’s Day gift using a dropdown list (only one selection possible).
    //Can you think of a way to reduce code repetition here?
    /**
     * allows user selection of one Valentine's Day gift - includes event handling
     * @return a JPanel containing a dropdown list
     */
    public JPanel userInputValentinesGift(){
        //a dropdown list of all religion options
        JComboBox<ValentinesGifts> jComboBox = new JComboBox<>(ValentinesGifts.values());
        jComboBox.setAlignmentX(0);
        jComboBox.setMaximumSize(new Dimension(2500,40));
        //let's assume the user prefers atheist/agnostic
        jComboBox.setSelectedItem(ValentinesGifts.SELECT);
        //initialise the field
        valentinesGift = (ValentinesGifts) jComboBox.getSelectedItem();
        //update the field if user changes selection
        jComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                valentinesGift = (ValentinesGifts) jComboBox.getSelectedItem();
            }
        });
        //create and return JPanel containing instruction, and dropdown list and padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setAlignmentX(0);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(jComboBox);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        return panel;
    }
    /**
     * allows user selection of one romantic activity - includes event handling
     * @return a JPanel containing a dropdown list
     */
    public JPanel userInputRomanticActivity(){
        //a dropdown list of all religion options
        JComboBox<RomanticActivities> jComboBox = new JComboBox<>(RomanticActivities.values());
        jComboBox.setAlignmentX(0);
        jComboBox.setMaximumSize(new Dimension(2500,40));
        jComboBox.setSelectedItem(RomanticActivities.SELECT);
        //initialise the field
        romanticActivity = (RomanticActivities) jComboBox.getSelectedItem();
        //update the field if user changes selection
        jComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                romanticActivity = (RomanticActivities) jComboBox.getSelectedItem();
            }
        });
        //create and return JPanel containing instruction, and dropdown list and padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setAlignmentX(0);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(jComboBox);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        return panel;
    }
    //EDIT 24: this method will create a JPanel adding each of the panels returned the above two methods.
    /**
     * @return a JPanel containing all search parameters for 'more than a friend' type
     */
    public JPanel userInputMoreThanAFriend(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(0);
        jPanel.add(Box.createRigidArea(new Dimension(0,30)));
        JPanel valentines = userInputValentinesGift();
        valentines.setAlignmentX(0);
        jPanel.add(valentines);
        jPanel.add(Box.createRigidArea(new Dimension(0,30)));
        JPanel romanticActivity = userInputRomanticActivity();
        romanticActivity.setAlignmentX(0);
        jPanel.add(romanticActivity);
        jPanel.add(Box.createRigidArea(new Dimension(0,30)));
        return jPanel;
    }

    //Finally, let’s add in functionality to filter based on the ‘study buddy’ relationship type.

    //EDIT 25: this method will generate three formatted text fields, in which the user will enter their institution
    //and course, or their study area. It will use an action listener rather than a document listener to check
    //user input, so ensure that you click ‘enter’ once you’ve entered data. It is a suboptimal choice, but if you
    //can’t get DocumentListener working in your assignment, you can use this as an alternative.
    public JPanel userInputStudyBuddy(){
        //create 3 text fields for user to enter either 1) institution and course OR 2) subject area
        JTextField institutionEntry = new JTextField(12);
        institutionEntry.setMaximumSize(new Dimension(2500,40));
        JTextField courseEntry = new JTextField(12);
        courseEntry.setMaximumSize(new Dimension(2500,40));
        JTextField subjectAreaEntry = new JTextField(12);
        subjectAreaEntry.setMaximumSize(new Dimension(2500,40));
        //create an action listener for each of the institution/course and subject area
        //it might be a better idea to use a document listener here, as the user has to click 'enter' for action listener to be triggered
        //this is however a good alternative if you can't get the document listener working (HINT assignment 3)
        ActionListener institutionCourseListener = e -> {
            //if an entry is made in either institution or course, and 'enter' is clicked, subject area filter is disabled
            subjectAreaEntry.setEditable(false);
            //set the user values to lowercase for robust comparison
            institution = institutionEntry.getText().toLowerCase();
            course = courseEntry.getText().toLowerCase();
        };
        ActionListener subjectAreaListener = e -> {
            //if an entry is made in subject area, disable both institution and course
            institutionEntry.setEditable(false);
            courseEntry.setEditable(false);
            subjectArea = subjectAreaEntry.getText().toLowerCase();
        };
        //add the action listeners to the text fields (you could use a document listener for this
        institutionEntry.addActionListener(institutionCourseListener);
        courseEntry.addActionListener(institutionCourseListener);
        subjectAreaEntry.addActionListener(subjectAreaListener);

        //create and return a panel to contain the user instructions, the 3 text fields and padding
        JPanel studyBuddyInfo = new JPanel();
        studyBuddyInfo.setLayout(new BoxLayout(studyBuddyInfo,BoxLayout.Y_AXIS));
        studyBuddyInfo.add(Box.createRigidArea(new Dimension(0,20)));
        studyBuddyInfo.add(new JLabel("Please enter your institution:"));
        studyBuddyInfo.add(institutionEntry);
        studyBuddyInfo.add(Box.createRigidArea(new Dimension(0,20)));
        studyBuddyInfo.add(new JLabel("Please enter your course:"));
        studyBuddyInfo.add(courseEntry);
        studyBuddyInfo.add(Box.createRigidArea(new Dimension(0,20)));
        studyBuddyInfo.add(new JLabel("Please enter your study area:"));
        studyBuddyInfo.add(subjectAreaEntry);
        studyBuddyInfo.add(Box.createRigidArea(new Dimension(0,20)));
        return studyBuddyInfo;
    }

    /*--------------------overall panels------------------*/

    //EDIT 17: add all the general search filter panels (gender, religion, star sign and age range) into one larger
    //panel and return it. This panel will be displayed regardless of the type of relationship selected by the user.
    /**
     * method to bring together the JPanels containing generic search criteria, i.e. age, gender, religion and star sign
     * @return a JPanel containing sub-JPanels for each generic criteria
     */
    public JPanel userInputGenericCriteria(){
        JPanel genericCriteria = new JPanel();
        genericCriteria.setLayout(new BoxLayout(genericCriteria,BoxLayout.Y_AXIS));
        //use the methods below to generate the sub-panels
        genericCriteria.add(this.getUserInputAgeRange());
        genericCriteria.add(this.userInputGender());
        genericCriteria.add(this.userInputReligion());
        genericCriteria.add(this.userInputStarSign());
        return genericCriteria;
    }

    //EDIT 18: this method combines three geek images representing the types of geek relationships.
    //This is the first card in the CardLayout JPanel (typeOfDreamGeekSpecificCriteriaPanel).
    /**
     * a method to generate a panel of 3 images representing the 3 types of relationships
     * @return the described JPanel
     */
    public JPanel generateImagePanel(){
        //load the 3 images as JLabels
        JLabel friendGeek = new JLabel(new ImageIcon("images/friend.png"));
        JLabel moreThanAFriendGeek = new JLabel(new ImageIcon("images/morethanafriend.png"));
        JLabel studyBuddyGeek = new JLabel(new ImageIcon("images/studybuddy.png"));
        //create a new container panel, add the 3 images to it and return the panel
        JPanel imagePanel = new JPanel();
        imagePanel.add(friendGeek);
        imagePanel.add(moreThanAFriendGeek);
        imagePanel.add(studyBuddyGeek);
        return imagePanel;
    }

    //EDIT 22 – the above methods (SearchView userInputGames, userInputTVShows and userInputHobbies ) are brought
    //together in a JPanel (this will be one of the cards used in CardLayout)
    /**
     * @return a JPanel containing the three filters used to search for the 'friend' type
     */
    public JPanel userInputFriendCriteria(){
        JPanel games = userInputGames();
        JPanel tvShows = userInputTVShows();
        JPanel hobbies = userInputHobbies();
        JPanel overall = new JPanel();
        overall.setLayout(new BoxLayout(overall,BoxLayout.PAGE_AXIS));
        overall.add(games);
        overall.add(tvShows);
        overall.add(hobbies);
        return overall;
    }


    /*--------------------getters------------------*/
    //EDIT 2: Create a getter for typeOfDreamGeek. This is how the program accesses the user's selection
    /**
     * @return the user's type of relationship selection
     */
    public TypeOfDreamGeek getTypeOfDreamGeek() {
        return typeOfDreamGeek;
    }

    //EDIT 9: add getters to access user choices for gender, religion and star sign.
    /**
     * @return the user's desired gender
     */
    public Gender getGender() {
        return gender;
    }
    /**
     * @return the user's desired religion
     */
    public Religion getReligion() {
        return religion;
    }
    /**
     * @return the user's desired star signs
     */
    public Set<StarSign> getStarSign() {
        return starSigns;
    }

    //EDIT 13: create two getters for min and max age
    /**
     * @return the user's desired min age
     */
    public int getMinAge() {
        return minAge;
    }
    /**
     * @return the user's desired max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    //EDIT 26: remember to create getters to access the user's choices
    /**
     * @return the set of user's preferred games (can be empty)
     */
    public Set<String> getChosenGames() {
        return new HashSet<>(chosenGames);
    }
    /**
     * @return the set of user's preferred tv shows (can be empty)
     */
    public Set<String> getChosenTVShows() {
        return new HashSet<>(chosenTVShows);
    }
    /**
     * @return the set of user's preferred hobbies (can be empty)
     */
    public Set<String> getChosenHobbies(){
        return new HashSet<>(chosenHobbies);
    }

    /**
     * @return the user's preferred Valentine's Day gift
     */
    public ValentinesGifts getValentinesGift() {
        return valentinesGift;
    }
    /**
     * @return the user's preferred romantic activity
     */
    public RomanticActivities getRomanticActivity() {
        return romanticActivity;
    }

    /**
     * @return the user's subject area - text entry
     */
    public String getSubjectArea() {
        return subjectArea;
    }
    /**
     * @return the user's course - text entry
     */
    public String getCourse() {
        return course;
    }
    /**
     * @return the user's institution/university - text entry
     */
    public String getInstitution() {
        return institution;
    }
}

