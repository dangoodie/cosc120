/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaEntry {

    //fields
    private JComboBox<Type> typeSelection;
    private Type selectedPetType;
    private JComboBox<String> breedSelection;
    private String selectedBreed;
    private boolean deSexedStatus;
    private Sex sex;

    private final JLabel feedbackMin = new JLabel(" "); //set to blank to start with
    private final JLabel feedbackMax = new JLabel(" ");
    private int minAge=0;
    private int maxAge=0;

    private JTextField name;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextArea message;

    private final Map<Type,Set<String>> petTypeToBreed;
    private final int oldestPet;

    /**
     * @param petTypeToBreed a mapping of pet type, e.g. dog to breed, e.g. bulldog, poodle, etc.
     * @param oldestPet the oldest pet in the database
     */
    public CriteriaEntry(Map<Type,Set<String>> petTypeToBreed, int oldestPet){
        this.petTypeToBreed = new HashMap<>(petTypeToBreed);
        this.oldestPet = oldestPet;
    }

    //brings all the JPanels below together
    /**
     * generates the search few for pet type and breed, age range, sex and de-sexed status
     * @return the described JPanel
     */
    public JPanel generateSearchView(){
        JPanel criteria = new JPanel();
        //set the layout to BoxLayout - Y axis, so that all the components are vertically stacked
        criteria.setLayout(new BoxLayout(criteria,BoxLayout.Y_AXIS));
        criteria.add(getUserInputPetTypeBreed());
        criteria.add(getUserInputAgeRange());
        criteria.add(getUserInputSex());
        criteria.add(getUserInputDesexed());
        return criteria;
    }

    //Part 1.1
    /**
     * generates a JPanel containing dropdown lists for pet type and breed
     * @return a JPanel that allows users to select both type and breed (interdependent)
     */
    public JPanel getUserInputPetTypeBreed(){
        //this populates the dropdown list with all the pet types
        typeSelection = new JComboBox<>(Type.values());
        typeSelection.setAlignmentX(0);
        typeSelection.setPreferredSize(new Dimension(150,30)); //sizes the dropdown list
        typeSelection.requestFocusInWindow();
        //this prevents the dropdown list from automatically selecting a type of pet
        typeSelection.setSelectedItem(Type.SELECT_TYPE);
        selectedPetType = Type.SELECT_TYPE; //initialise the user's type selection to the dummy value
        //if the user chooses a pet type, call ifTypeSelected - this will populate the breeds list based on the pet type selection
        typeSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) ifTypeSelected(); //Part 1.2
        });
        //request that the user select their preferred breed
        //when the panel is first drawn, ensure you initialise the breeds dropdown list (it'll be updated as soon as the user selects their preferred type)
        breedSelection = new JComboBox<>(); //you could replace this with a JList for multi-breed selection
        breedSelection.addItem("Select breed");//dummy value for prettifying the GUI
        breedSelection.setAlignmentX(0);
        breedSelection.setPreferredSize(new Dimension(150,30));
        if(selectedPetType.equals(Type.SELECT_TYPE)) breedSelection.setEnabled(false); //disable it for as long as the user hasn't selected a pet type
        //if the user selects/changes their selection of breed, update the selectedBreed field
        breedSelection.addItemListener(f -> {
            //if the user selects a breed, update the relevant field
            if (f.getStateChange() == ItemEvent.SELECTED) selectedBreed= (String) breedSelection.getSelectedItem();
        });

        //create a panel for the 2 dropdown lists
        JPanel breedTypeSelectionPanel = new JPanel(); //defaults to flow layout
        breedTypeSelectionPanel.setAlignmentX(0); //ensure that you left align it
        breedTypeSelectionPanel.setBorder(BorderFactory.createTitledBorder("Pet type and breed"));
        //add padding to make it look nicer
        breedTypeSelectionPanel.add(Box.createRigidArea(new Dimension(0,50))); //padding on top of the lists
        breedTypeSelectionPanel.add(Box.createRigidArea(new Dimension(10,0))); //padding on the left
        breedTypeSelectionPanel.add(typeSelection);
        breedTypeSelectionPanel.add(Box.createRigidArea(new Dimension(30,0))); //padding between the lists
        breedTypeSelectionPanel.add(breedSelection);
        breedTypeSelectionPanel.add(Box.createRigidArea(new Dimension(10,0))); //padding on the right
        return breedTypeSelectionPanel;
    }

    //Part 1.2
    /**
     * this method handles the situation where the user selects a pet type
     * it populates the breeds dropdown list with values based on the type
     * it disables the breeds list if no type has been selected
     */
    private void ifTypeSelected(){
        //set the field selectedPetType to the user's choice
        selectedPetType=(Type) typeSelection.getSelectedItem();
        //if the user hasn't selected the dummy value, get all the relevant breeds (from the map), assigning them to the relevant breeds field
        assert selectedPetType != null; //we know it isn't null
        Set<String> relevantBreeds;
        //only populate the breeds dropdown list if the type has been selected
        if(!selectedPetType.equals(Type.SELECT_TYPE)) {
            relevantBreeds = petTypeToBreed.get(selectedPetType);
            breedSelection.setEnabled(true); //enable it to allow the user to choose a breed
        }
        //if the user has selected the dummy value, set the relevant breeds field to an empty set
        else {
            breedSelection.setEnabled(false); //disable it
            relevantBreeds = Collections.emptySet();
        }
        //once the relevant breeds field has been initialised, populate the breeds drop down list
        breedSelection.setModel(new DefaultComboBoxModel<>(relevantBreeds.toArray(new String[0])));
        //set the default item in the breeds drop down list to the first element
        selectedBreed = null; //as the breed is optional, let's initialise the user's choice to null
        //request the program direct the user to the breeds
        breedSelection.requestFocusInWindow();
    }

    //Part 1.3
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
        min.setText(String.valueOf(0));
        max.setText(String.valueOf(oldestPet));
        maxAge=oldestPet;

        //set the font and color of the feedback messages
        feedbackMin.setFont(new Font("", Font. ITALIC, 12));
        feedbackMin.setForeground(Color.RED);
        feedbackMax.setFont(new Font("", Font. ITALIC, 12));
        feedbackMax.setForeground(Color.RED);

        //add the document listeners
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
        finalPanel.setBorder(BorderFactory.createTitledBorder("Age range"));
        finalPanel.setLayout(new BoxLayout(finalPanel,BoxLayout.Y_AXIS)); //stack elements vertically
        finalPanel.setAlignmentX(0);
        finalPanel.add(ageRangePanel);
        feedbackMin.setAlignmentX(0);
        feedbackMax.setAlignmentX(0);
        finalPanel.add(feedbackMin); //feedback below age entry text boxes
        finalPanel.add(feedbackMax);

        return finalPanel;
    }

    //Part 1.3
    /**
     * validates user input for min age
     * @param minEntry the JTextField used to enter min age
     * @return true if valid age, false if invalid
     */
    private boolean checkMin(JTextField minEntry){
        feedbackMin.setText("");
        try{
            int tempMin = Integer.parseInt(minEntry.getText());
            if(tempMin < 0 || tempMin>maxAge) {
                feedbackMin.setText("Min age must be >= "+0+" and <= "+maxAge+". Defaulting to "+minAge+" - "+maxAge+".");
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

    //Part 1.3
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

    //Part 1.4
    /**
     * method used to get user selection of sex - male female or na
     * @return a JPanel containing the relevant radio buttons and labels
     */
    public JPanel getUserInputSex(){
        ButtonGroup sexButtonGroup = new ButtonGroup();
        JRadioButton male = new JRadioButton(Sex.MALE.toString());
        JRadioButton female = new JRadioButton(Sex.FEMALE.toString());
        sexButtonGroup.add(male);
        sexButtonGroup.add(female);
        male.setActionCommand(Sex.MALE.toString());
        female.setActionCommand(Sex.FEMALE.toString());

        JPanel sexPanel = new JPanel();
        sexPanel.setAlignmentX(0);
        sexPanel.setBorder(BorderFactory.createTitledBorder("Which sex would you prefer your pet to be? (Optional)"));
        sexPanel.add(male);
        sexPanel.add(female);

        sex  = Sex.NA;
        //research how to de-select an already selected JRadioButton, and share your findings on the forums
        ActionListener actionListener = e-> sex = Sex.valueOf(sexButtonGroup.getSelection().getActionCommand().toUpperCase());
        male.addActionListener(actionListener);
        female.addActionListener(actionListener);
        return sexPanel;
    }

    //Part 1.5
    /**
     * method used to get user selection of de-sexed or not
     * @return a JPanel containing a checkbox - yes if they only want de-sexed pets
     */
    public JPanel getUserInputDesexed(){
        JCheckBox deSexed = new JCheckBox("Show de-sexed pets only");
        JPanel deSexedPanel = new JPanel();
        deSexedPanel.setAlignmentX(0);
        deSexedPanel.add(deSexed);
        //here's what a change listener looks like!
        ChangeListener changeListener = e -> deSexedStatus = deSexed.isSelected();

        deSexed.addChangeListener(changeListener);

        return deSexedPanel;
    }

    //Part 4 and 6.2
    /**
     * a method to generate a JPanel containing a name, email, ph num and message fields
     * It can be used if user has selected 'none' or if they are adopting a pet
     * @return a JPanel as described
     */
    public JPanel contactForm(){
        //create labels and text fields for users to enter contact info and message
        JLabel enterName = new JLabel("Full name");
        name = new JTextField(12);
        JLabel enterEmail = new JLabel("Email address");
        email = new JTextField(12);
        JLabel enterPhoneNumber = new JLabel("Phone number");
        phoneNumber = new JTextField(12);
        JLabel enterMessage = new JLabel("Type your query below");
        message = new JTextArea(6,12);

        //TODO: add input validation for above fields. HINT: use the 3 methods below

        JScrollPane jScrollPane = new JScrollPane(message);
        jScrollPane.getViewport().setPreferredSize(new Dimension(250,100));

        //create a new panel, add padding and user entry boxes/messages to the panel
        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new BoxLayout(userInputPanel,BoxLayout.Y_AXIS));
        userInputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        userInputPanel.setAlignmentX(0);
        enterName.setAlignmentX(0);
        name.setAlignmentX(0);
        userInputPanel.add(enterName);
        userInputPanel.add(name);
        userInputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        enterEmail.setAlignmentX(0);
        email.setAlignmentX(0);
        userInputPanel.add(enterEmail);
        userInputPanel.add(email);
        userInputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        enterPhoneNumber.setAlignmentX(0);
        phoneNumber.setAlignmentX(0);
        userInputPanel.add(enterPhoneNumber);
        userInputPanel.add(phoneNumber);
        userInputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        enterMessage.setAlignmentX(0);
        message.setAlignmentX(0);
        userInputPanel.add(enterMessage);
        jScrollPane.setAlignmentX(0);
        userInputPanel.add(jScrollPane);
        userInputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //return the panel to the calling method - could be either the send-message or adoption-request
        return userInputPanel;
    }

    //input validation for contact form
    /**
     * a very simple regex for full name in Firstname Surname format
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
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

    //getters - used to access values the user has entered when an object of this class is created
    //add javadoc
    public int getMinAge() {
        return minAge;
    }
    public int getMaxAge() {
        return maxAge;
    }
    public Type getSelectedPetType(){
        return selectedPetType;
    }
    public String getSelectedBreed(){
        return selectedBreed;
    }
    public boolean getDesexedStatus(){
        return deSexedStatus;
    }
    public Sex getSex(){
        return sex;
    }
    public String getName() {
        return name.getText();
    }
    public String getEmail() {
        return email.getText();
    }
    public String getPhoneNumber() {
        return phoneNumber.getText();
    }
    public String getMessage() {
        return message.getText();
    }
}



























