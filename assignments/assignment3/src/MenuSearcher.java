/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 2
 */

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
import javax.swing.*;
import javax.swing.text.*;

/**
 * A class that represents a menu searcher object.
 * The menu searcher allows the user to search for coffees based on their preferences and order a coffee.
 * This class contains the main method.
 */

public class MenuSearcher {
    private static final String MENU_FILE = "menu.txt";
    private static Menu menu;
    private static final String appName = "The Caffeinated Geek";
    private static final String iconPath = "the_caffeinated_geek.png";
    private static final ImageIcon icon = new ImageIcon(iconPath);
    private static final String imagesPath = "drinks_images/";
    private static final int imageSizeResultsView = 100;

    private static DrinkType type = null;
    private static JFrame mainWindow = null;
    private static JPanel searchView = null;
    private static JComboBox<String> optionsCombo = null;

    private static Drink selectedDrink = null;

    /**
     * The main method of the program.
     * This method loads the menu from a file, gets the user's drink order, finds drinks that match the order,
     * allows the user to select a drink, all relevant options, gets the user's geek info, and writes the order to a file.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Load the menu from a file
        menu = loadMenuFromFile(MENU_FILE);
        mainWindow = new JFrame(appName);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setMinimumSize(new Dimension(500, 300));
        mainWindow.setPreferredSize(new Dimension(500, 500));
        searchView = generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public static SearchView refreshSearchView() {
        return new SearchView(menu);
    }

    public static JPanel generateSearchView() {
        JPanel searchWindow = new JPanel();
        searchWindow.setLayout(new BorderLayout());

        SearchView searchCriteria = refreshSearchView();
        JPanel searchCriteriaPanel = searchCriteria.generateSearchView();

        searchWindow.add(searchCriteriaPanel, BorderLayout.CENTER);

        JButton search = new JButton("Search");
        ActionListener actionListener = e -> conductSearch(searchCriteria);
        search.addActionListener(actionListener);
        searchWindow.add(search, BorderLayout.SOUTH);

        searchWindow.add(Box.createRigidArea(new Dimension(20,0)), BorderLayout.WEST);
        searchWindow.add(Box.createRigidArea(new Dimension(20,0)), BorderLayout.EAST);

        return searchWindow;
    }

    public static void conductSearch(SearchView searchCriteria) {
        Map<Criteria, Object> criteria = new HashMap<>();
        type = searchCriteria.getDrinkType();
        if (type == DrinkType.SELECT_DRINK_TYPE) {
            JOptionPane.showMessageDialog(null, "Please select a drink type", appName, JOptionPane.ERROR_MESSAGE);
            return;
        }

        criteria.put(Criteria.DRINK_TYPE, type);

        int minPrice = searchCriteria.getUserMinPrice();
        int maxPrice = searchCriteria.getUserMaxPrice();
        MilkOptions milk = searchCriteria.getUserMilkOption();
        String sugarString = searchCriteria.getUserSugar();
        Set<String> extras = searchCriteria.getUserExtras();

        if (type.equals(DrinkType.COFFEE)) {
            int numberOfShots = searchCriteria.getUserNumOfShots();
            criteria.put(Criteria.NUM_OF_SHOTS, numberOfShots);
        } else if (type.equals(DrinkType.TEA)) {
            Temperature temperature = searchCriteria.getUserTemperature();
            int steepTime = searchCriteria.getUserSteepTime();
            if (temperature != Temperature.SKIP) {
                criteria.put(Criteria.TEMPERATURE, temperature);
            }
            if (steepTime != -1) {
                criteria.put(Criteria.STEEP_TIME, steepTime);
            }
        }

        if (milk != MilkOptions.SKIP) {
            criteria.put(Criteria.MILK_TYPE, milk);
        }

        if (!sugarString.equalsIgnoreCase("Skip")) {
            criteria.put(Criteria.SUGAR, sugarString.equalsIgnoreCase("Yes"));
        }

        if (!extras.isEmpty()) {
            criteria.put(Criteria.EXTRAS, extras);
        }

        DreamDrink dreamDrink = new DreamDrink(minPrice, maxPrice, criteria);
        List<Drink> matches = menu.findDreamDrink(dreamDrink);
        showResults(matches);
    }

    public static void showResults(List<Drink> potentialMatches) {
        if (potentialMatches.isEmpty()) {
            noResults();
            return;
        }

        JPanel results = new JPanel();
        results.setLayout(new BorderLayout());
        results.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        results.add(generateDrinkDescription(potentialMatches), BorderLayout.CENTER);
        results.add(selectFromResultsPanel(potentialMatches), BorderLayout.SOUTH);
        results.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        results.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        mainWindow.setContentPane(results);
        mainWindow.revalidate();
    }

    public static ImageIcon loadImageFromPath(String path, int imageSize) {
        ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static Map<String, JLabel> loadImages(Set<File> filesList, int imageSize) {
        assert filesList != null;
        Map<String, JLabel> images = new HashMap<>();
        for (File file : filesList) {
            ImageIcon image = loadImageFromPath(file.getPath(), imageSize);
            JLabel label = new JLabel(image);
            images.put(file.getName(), label);
        }
        return images;
    }

    private static Set<File> optionsImages(List<Drink> matching) {
        Set<File> optionsImages = new HashSet<>();
        for (Drink drink : matching) {
            String matchingKey = drink.name().toLowerCase().replace(" ", "_");
            optionsImages.add(new File(imagesPath+ matchingKey + ".png"));
        }
        return optionsImages;
    }

    public static JScrollPane generateDrinkDescription(List<Drink> potentialMatches) {
        Map<String, Drink> options = new HashMap<>();
        Map<String, JLabel> optionsImages = loadImages(optionsImages(potentialMatches), imageSizeResultsView);

        JPanel drinkDescriptions = new JPanel();
        drinkDescriptions.setBorder(BorderFactory.createTitledBorder("Matches found:"));
        drinkDescriptions.setLayout(new GridLayout(0, 2));

        for (Drink drink : potentialMatches) {
            JPanel image = new JPanel();
            image.add(optionsImages.get(drink.name().toLowerCase().replace(" ", "_") + ".png"));

            JTextArea description = new JTextArea(drink.getDrinkDescription());
            description.setEditable(false);
            description.setLineWrap(true);
            description.setWrapStyleWord(true);

            JPanel textAndImage = new JPanel();
            textAndImage.setLayout(new BorderLayout());
            textAndImage.add(image, BorderLayout.WEST);
            textAndImage.add(description, BorderLayout.CENTER);

            drinkDescriptions.add(textAndImage);
            drinkDescriptions.add(Box.createRigidArea(new Dimension(0, 10)));

            options.put(drink.name(), drink);
        }

        optionsCombo = new JComboBox<>(options.keySet().toArray(new String[0]));
        optionsCombo.addItem("See the full menu");

        JScrollPane scrollPane = new JScrollPane(drinkDescriptions);
        scrollPane.setPreferredSize(new Dimension(300, 450));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
        return scrollPane;
    }

    public static JPanel selectFromResultsPanel(List<Drink> potentialMatches) {
        JPanel selectFromResults = new JPanel();
        selectFromResults.setLayout(new BorderLayout());

        JButton select = new JButton("Select");
        ActionListener actionListener = e -> selectFromResults(potentialMatches);
        select.addActionListener(actionListener);

        selectFromResults.add(optionsCombo, BorderLayout.CENTER);
        selectFromResults.add(select, BorderLayout.EAST);

        return selectFromResults;
    }

    public static void selectFromResults(List<Drink> potentialMatches) {
        String selected = (String) optionsCombo.getSelectedItem();

        if (selected.equalsIgnoreCase("See the full menu")) {
            showFullMenu();
            return;
        }

        selectedDrink = null;
        for (Drink drink : potentialMatches) {
            if (drink.name().equals(selected)) {
                selectedDrink = drink;
                break;
            }
        }
        if (selectedDrink == null) {
            JOptionPane.showMessageDialog(null, "Error: Drink not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        customiseDrink();
    }

    public static void reGenerateSearchView() {
        searchView = generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.revalidate();
    }

    public static void noResults() {
        // give user the option to search again or see the full menu
        JPanel noResults = new JPanel();
        noResults.setLayout(new BorderLayout(10, 10)); // Adding gaps between components

        // Title
        JLabel title = new JLabel("No results found", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16)); // Setting a larger font for the title
        noResults.add(title, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centering buttons with gaps

        JButton searchAgain = new JButton("Search Again");
        ActionListener actionListener = e -> reGenerateSearchView();
        searchAgain.addActionListener(actionListener);

        JButton fullMenu = new JButton("See the full menu");
        ActionListener fullMenuActionListener = e -> showFullMenu();
        fullMenu.addActionListener(fullMenuActionListener);

        // Add buttons to the button panel
        buttonPanel.add(searchAgain);
        buttonPanel.add(fullMenu);

        noResults.add(buttonPanel, BorderLayout.CENTER);

        mainWindow.setContentPane(noResults);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    /**
     * Show the full menu to the user
     */

    private static void showFullMenu() {
        JPanel fullMenuPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Full Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        fullMenuPanel.add(titleLabel, BorderLayout.NORTH);

        List<Drink> drinks = new ArrayList<>(menu.getMenu());
        JScrollPane descriptionScrollPane = generateDrinkDescription(drinks);

        // Increase the scroll speed
        descriptionScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        descriptionScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        fullMenuPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ArrayList<String> listModel = new ArrayList<>();
        for (Drink drink : drinks) {
            listModel.add(drink.name() + " (" + drink.id() + ")");
        }

        JComboBox<String> drinkComboBox = new JComboBox<>(listModel.toArray(new String[0]));
        drinkComboBox.setPreferredSize(new Dimension(400, 25));
        bottomPanel.add(drinkComboBox);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            String selectedDrinkString = (String) drinkComboBox.getSelectedItem();
            if (selectedDrinkString != null) {
                int id = Integer.parseInt(selectedDrinkString.substring(selectedDrinkString.indexOf("(") + 1, selectedDrinkString.indexOf(")")));
                selectedDrink = menu.getDrinkById(id);
                if (selectedDrink != null) {
                    customiseDrink();
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Error: Drink not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bottomPanel.add(selectButton);

        fullMenuPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainWindow.setContentPane(fullMenuPanel);
        mainWindow.revalidate();
    }

    /**
     * A method to customise the drink after selecting from the menu.
     * Uses the selectedDrink field to customise the drink.
     */

    private static void customiseDrink() {
        if (selectedDrink == null) {
            JOptionPane.showMessageDialog(mainWindow, "No drink selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel customizePanel = new JPanel();
        customizePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Customize Your Drink");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        customizePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Drink options (Milk options + Extras)
        JPanel drinkOptionsPanel = new JPanel();
        drinkOptionsPanel.setLayout(new BoxLayout(drinkOptionsPanel, BoxLayout.Y_AXIS));
        drinkOptionsPanel.setBorder(BorderFactory.createTitledBorder("Drink Options"));

        // Milk options
        List<MilkOptions> milkOptions = (List<MilkOptions>) selectedDrink.genericFeatures().getCriteria(Criteria.MILK_TYPE);
        if (!milkOptions.isEmpty()) {
            JPanel milkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            milkPanel.add(new JLabel("Milk Options:"));
            JComboBox<MilkOptions> milkComboBox = new JComboBox<>(milkOptions.toArray(new MilkOptions[0]));
            milkPanel.add(milkComboBox);
            drinkOptionsPanel.add(milkPanel);
        }

        // Extras with checkboxes
        List<String> extras = (List<String>) selectedDrink.genericFeatures().getCriteria(Criteria.EXTRAS);
        if (!extras.isEmpty()) {
            JPanel extrasPanel = new JPanel();
            extrasPanel.setLayout(new BoxLayout(extrasPanel, BoxLayout.Y_AXIS));

            JLabel extrasLabel = new JLabel("Extras:");
            extrasPanel.add(extrasLabel);

            for (String extra : extras) {
                JCheckBox extraCheckBox = new JCheckBox(extra);
                extrasPanel.add(extraCheckBox);
            }

            drinkOptionsPanel.add(extrasPanel);
        }

        contentPanel.add(drinkOptionsPanel);

        // Geek info
        JPanel geekInfoPanel = getGeekInfoPanel();
        contentPanel.add(geekInfoPanel);

        Map<Criteria, Object> criteria = new HashMap<>(selectedDrink.genericFeatures().getAllCriteria());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (!milkOptions.isEmpty()) {
                JComboBox<MilkOptions> milkComboBox = (JComboBox<MilkOptions>) ((JPanel) drinkOptionsPanel.getComponent(0)).getComponent(1);
                MilkOptions selectedMilk = (MilkOptions) milkComboBox.getSelectedItem();
                criteria.put(Criteria.MILK_TYPE, List.of(selectedMilk));
            }

            if (!extras.isEmpty()) {
                JPanel extrasPanel = (JPanel) drinkOptionsPanel.getComponent(0);
                List<String> selectedExtras = new ArrayList<>();
                for (Component comp : extrasPanel.getComponents()) {
                    if (comp instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) comp;
                        if (checkBox.isSelected()) {
                            selectedExtras.add(checkBox.getText());
                        }
                    }
                }
                criteria.put(Criteria.EXTRAS, selectedExtras);
            }

            Drink customisedDrink = new Drink(selectedDrink.id(), selectedDrink.name(), selectedDrink.price(), selectedDrink.description(), new DreamDrink(criteria));

            // Get geek info from fields
            JTextField nameField = (JTextField) ((JPanel) geekInfoPanel.getComponent(0)).getComponent(1);
            JTextField phoneField = (JTextField) ((JPanel) geekInfoPanel.getComponent(1)).getComponent(1);

            String name = nameField.getText().trim();
            String phoneNumber = phoneField.getText().trim();

            if (name.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter a valid phone number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Geek geek = new Geek(name, phoneNumber);
            writeOrderToFile(geek, customisedDrink);
            JOptionPane.showMessageDialog(mainWindow, "Order saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        contentPanel.add(buttonPanel);

        customizePanel.add(contentPanel, BorderLayout.CENTER);

        mainWindow.setContentPane(customizePanel);
        mainWindow.revalidate();
    }

    private static JPanel getGeekInfoPanel() {
        JPanel geekInfoPanel = new JPanel();
        geekInfoPanel.setLayout(new BoxLayout(geekInfoPanel, BoxLayout.Y_AXIS));
        geekInfoPanel.setBorder(BorderFactory.createTitledBorder("Geek Info"));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(20);
        namePanel.add(nameField);
        geekInfoPanel.add(namePanel);

        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phonePanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField(20);
        phonePanel.add(phoneField);
        geekInfoPanel.add(phonePanel);

        return geekInfoPanel;
    }
    /**
     * A method to write the order to a file.
     * @param geek a Geek object representing the user's geek info
     * @param drinkOrder a Coffee object representing the selected coffee
     */

    private static void writeOrderToFile(Geek geek, Drink drinkOrder) {
        // Write Geek info and coffee order to file
        try {
            Path path = Path.of("order.txt");

            StringBuilder orderDetails = new StringBuilder("Order details:\n");
            orderDetails.append("\tName: ").append(geek.name()).append("\n");
            orderDetails.append("\tOrder number: ").append(geek.phoneNumber()).append("\n");
            orderDetails.append("\tItem: ").append(drinkOrder.name()).append(" (").append(drinkOrder.id()).append(")\n");
            orderDetails.append("\tMilk: ").append(drinkOrder.genericFeatures().getCriteria(Criteria.MILK_TYPE)).append("\n");
            List<String> extras = (List<String>) drinkOrder.genericFeatures().getCriteria(Criteria.EXTRAS);
            if (extras.contains("None") || extras.isEmpty()){
                orderDetails.append("\tExtras: None\n");
            } else {
                orderDetails.append("\tExtras: ").append(extras).append("\n");
            }
            Files.writeString(path, orderDetails.toString());
            System.out.println("Order written to file");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }


    /**
     * A method to load the menu from a file.
     * Adapted from the UNE COSC120 Topic 4.2 SeekAGeek.java loadGeeks() method.
     * @param filename a String representing the name of the file to load the menu from
     * @return a Menu object representing the menu loaded from the file
     */
    private static Menu loadMenuFromFile(String filename) {
        Set<Drink> menu = new HashSet<>();

        try {
            Path path = Path.of(filename);
            String fileContents = Files.readString(path);
            String[] lines = fileContents.split("\n");

            for (int i = 1; i < lines.length; i++) {
                // This regex splits the line by commas, but ignores commas inside square brackets
                String regex = ",(?![^\\[]*\\])";
                List<String> drinkData = List.of(lines[i].split(regex));

                DrinkType type = DrinkType.fromString(drinkData.getFirst().trim());

                // build type functions
                if (type == DrinkType.TEA) {
                    Drink drink = handleLoadingTea(drinkData);
                    menu.add(drink);
                }

                if (type == DrinkType.COFFEE) {
                    Drink drink = handleLoadingCoffee(drinkData);
                    menu.add(drink);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        if (menu.isEmpty()) {
            return null;
        }
        return new Menu(menu);
    }

    /**
     * A method to handle the tea type drinks.
     * @param drinkData a List of Strings representing the drink data
     * @return a Drink object representing the tea drink
     */

    private static Drink handleLoadingTea(List<String> drinkData) {
        // common features
        int id = Integer.parseInt(drinkData.get(1).trim());
        String name = drinkData.get(2).trim();
        Double price = Double.parseDouble(drinkData.get(3).trim());
        String description = drinkData.get(10).trim().replace("[", "").replace("]", "");
        List<MilkOptions> milkOptions = MilkOptions.fromStringList(parseArray(drinkData.get(8)));
        List<String> extras = parseArray(drinkData.get(9));
        Boolean sugar = drinkData.get(7).trim().equalsIgnoreCase("YES");

        // tea specific features
        Temperature temperature = Temperature.fromString(drinkData.get(5));
        int steepTime = Integer.parseInt(drinkData.get(6).trim());

        Map<Criteria, Object> criteriaObjectMap = new LinkedHashMap<>();
        criteriaObjectMap.put(Criteria.DRINK_TYPE, DrinkType.TEA); // Add the drink type (tea)
        criteriaObjectMap.put(Criteria.SUGAR, sugar);
        criteriaObjectMap.put(Criteria.MILK_TYPE, milkOptions);
        criteriaObjectMap.put(Criteria.EXTRAS, extras);
        criteriaObjectMap.put(Criteria.TEMPERATURE, temperature);
        criteriaObjectMap.put(Criteria.STEEP_TIME, steepTime);

        DreamDrink dreamDrink = new DreamDrink(criteriaObjectMap);
        return new Drink(id, name, price, description, dreamDrink);
    }

    /**
     * A method to handle the coffee type drinks.
     * @param drinkData a List of Strings representing the drink data
     * @return a Drink object representing the coffee drink
     */

    private static Drink handleLoadingCoffee(List<String> drinkData) {
        // common features
        int id = Integer.parseInt(drinkData.get(1).trim());
        String name = drinkData.get(2).trim();
        Double price = Double.parseDouble(drinkData.get(3).trim());
        String description = drinkData.get(10).trim().replace("[", "").replace("]", "");
        List<MilkOptions> milkOptions = MilkOptions.fromStringList(parseArray(drinkData.get(8)));
        List<String> extras = parseArray(drinkData.get(9));
        Boolean sugar = drinkData.get(7).trim().equalsIgnoreCase("YES");

        // coffee specific features
        int numberOfShots = Integer.parseInt(drinkData.get(4).trim());

        Map<Criteria, Object> criteriaObjectMap = new LinkedHashMap<>();
        criteriaObjectMap.put(Criteria.DRINK_TYPE, DrinkType.COFFEE); // Add the drink type (coffee)
        criteriaObjectMap.put(Criteria.SUGAR, sugar);
        criteriaObjectMap.put(Criteria.MILK_TYPE, milkOptions);
        criteriaObjectMap.put(Criteria.EXTRAS, extras);
        criteriaObjectMap.put(Criteria.NUM_OF_SHOTS, numberOfShots);

        DreamDrink dreamDrink = new DreamDrink(criteriaObjectMap);
        return new Drink(id, name, price, description, dreamDrink);
    }
    /**
     * A method to parse the array of options from a field.
     * @param field a String representing the field to parse
     * @return a List of Strings representing the options
     */
    private static List<String> parseArray(String field) {
        field = field.trim(); // Remove leading/trailing whitespace
        if (field.equals("[]")) {
            return new ArrayList<>();
        }

        field = field.substring(1, field.length() - 1); // Remove the surrounding brackets
        String[] options = field.split(",");
        for (int i = 0; i < options.length; i++) {
            options[i] = sanitiseOption(options[i].trim());
        }

        return new ArrayList<>(Arrays.asList(options));
    }

    /**
     * A method to sanitise a string to capitalise the first letter of a word.
     * This allows for bad string input from the file to be capitalised correctly.
     * @param s a String representing the string to sanitiseOption
     * @return a String representing the capitalised string
     */

    private static String sanitiseOption(String s) {
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }

    
    /**
     * A method to validate a phone number
     * Sourced from the UNE COSC120 Topic 4.2 SeekAGeek.java isValidPhoneNumber() method.
     * @param phoneNumber a String representing the phone number
     * @return a boolean indicating whether the phone number is valid
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}


