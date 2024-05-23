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

    private static Set<String> availableExtras = new HashSet<>();

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
        Boolean sugar = searchCriteria.getUserSugar();
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

        if (sugar != null) {
            criteria.put(Criteria.SUGAR, sugar);
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

        JScrollPane scrollPane = new JScrollPane(drinkDescriptions);
        scrollPane.setPreferredSize(new Dimension(300, 450));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
        Drink selectedDrink = null;
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
        Drink drinkOrder = customiseDrink(selectedDrink);
        Geek geek = getGeekInfo();
        writeOrderToFile(geek, drinkOrder);
        JOptionPane.showMessageDialog(null, "Order written to file", appName, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void reGenerateSearchView() {
        searchView = generateSearchView();
        mainWindow.setContentPane(searchView);
        mainWindow.revalidate();
    }

    public static void noResults() {
        JOptionPane.showMessageDialog(null, "No matches found", appName, JOptionPane.INFORMATION_MESSAGE);
        reGenerateSearchView();
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
     * A method to get the user's dream drink order.
     * @return a Dream drink object representing the user's dream drink order
     */

    private static DreamDrink getDreamDrink() {
        Map<Criteria, Object> criteria = new HashMap<>();

        // Get the user's dream drink order
        DrinkType drinkType = (DrinkType) JOptionPane.showInputDialog(null, "What type of drink would you like? (Coffee/Tea)", "Drink Type", JOptionPane.QUESTION_MESSAGE, icon, DrinkType.values(), DrinkType.COFFEE);
        if (drinkType == null) {
            System.exit(0);
        }
        criteria.put(Criteria.DRINK_TYPE, drinkType);

        if (drinkType == DrinkType.COFFEE) {
            int numberOfShots = -1;
            while (numberOfShots < 0) {
                String numberOfShotsString = JOptionPane.showInputDialog("Enter the number of shots: ");
                if (numberOfShotsString == null) {
                    System.exit(0);
                }
                try {
                    numberOfShots = Integer.parseInt(numberOfShotsString);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (numberOfShots < 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            criteria.put(Criteria.NUM_OF_SHOTS, numberOfShots);
        }

        if (drinkType == DrinkType.TEA) {
            Temperature temperature = (Temperature) JOptionPane.showInputDialog(null, "What temperature would you like?", "Temperature", JOptionPane.QUESTION_MESSAGE, icon, Temperature.values(), Temperature.SKIP);
            if (temperature == null) {
                System.exit(0);
            }
            if (temperature != Temperature.SKIP) {
                criteria.put(Criteria.TEMPERATURE, temperature);
            }

            int steepTime = -1;
            List<String> steepTimeOptions = List.of("1", "2", "3", "4", "5", "6", "7", "8", "Skip");
            String steepTimeString = null;
            while (steepTime < 0) {
                steepTimeString = (String) JOptionPane.showInputDialog(null, "Enter the steep time (minutes): ", "Steep Time", JOptionPane.QUESTION_MESSAGE, icon, steepTimeOptions.toArray(), steepTimeOptions.toArray()[0]);
                if (steepTimeString == null) {
                    System.exit(0);
                }
                if (steepTimeString.equalsIgnoreCase("SKIP")) break;
                try {
                    steepTime = Integer.parseInt(steepTimeString);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (steepTime < 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!steepTimeString.equalsIgnoreCase("SKIP")) {
                criteria.put(Criteria.STEEP_TIME, steepTime);
            }
        }

        MilkOptions selectedMilkOption = (MilkOptions) JOptionPane.showInputDialog(null, "What type of milk would you like?", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, MilkOptions.values(), MilkOptions.FULL_CREAM);
        if (selectedMilkOption == null) {
            System.exit(0);
        }
        if (selectedMilkOption != MilkOptions.SKIP) {
            criteria.put(Criteria.MILK_TYPE, List.of(selectedMilkOption));
        }

        List<String> sugarOptions = List.of("Yes", "No", "Skip");
        String sugar = (String) JOptionPane.showInputDialog(null, "Would you like sugar?", "Sugar", JOptionPane.QUESTION_MESSAGE, icon, sugarOptions.toArray(), sugarOptions.toArray()[0]);
        if (sugar == null) {
            System.exit(0);
        }
        if (!sugar.equalsIgnoreCase("SKIP")) {
            criteria.put(Criteria.SUGAR, sugar.equalsIgnoreCase("Yes"));
        }

        Set<String> extras = menu.findExtras(drinkType);
        extras.add("None");
        extras.add("Skip");

        List<String> selectedExtras = new ArrayList<>();

        while (true) {
            if (!selectedExtras.isEmpty()) {
                extras.remove("None");
            }
            String selectedExtra = (String) JOptionPane.showInputDialog(null, "Select an extra", "Extras", JOptionPane.QUESTION_MESSAGE, icon, extras.toArray(), extras.toArray()[0]);
            if (selectedExtra == null) {
                System.exit(0);
            }

            if (selectedExtra.equalsIgnoreCase("Skip") || selectedExtra.equalsIgnoreCase("None")) {
                if (selectedExtras.isEmpty()) {
                    selectedExtras.add(selectedExtra);
                }
                break;
            }
            selectedExtras.add(selectedExtra);
            extras.remove(selectedExtra);
        }

        if (!selectedExtras.contains("Skip")) {
            criteria.put(Criteria.EXTRAS, selectedExtras);
        }

        // Get min and max price
        int minPrice = -1;
        while (minPrice < 0) {
            String minPriceString = JOptionPane.showInputDialog("Enter the minimum price: ");
            if (minPriceString == null) {
                System.exit(0);
            }
            try {
                minPrice = Integer.parseInt(minPriceString);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (minPrice < 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        int maxPrice = -1;
        while (maxPrice < minPrice) {
            String maxPriceString = JOptionPane.showInputDialog("Enter the maximum price: ");
            if (maxPriceString == null) {
                System.exit(0);
            }
            try {
                maxPrice = Integer.parseInt(maxPriceString);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (maxPrice < minPrice) {
                JOptionPane.showMessageDialog(null, "Please enter a number greater than or equal to the minimum price", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return new DreamDrink(minPrice, maxPrice, criteria);
    }

    /**
     * A method to build a message showing the drinks found.
     * @param matches a List of Drink objects representing the drinks found
     * @return a String representing the message
     */

    private static JTextPane buildFoundMessage(List<Drink> matches) {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        // define a bold attribute
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);

        // define a bold and italic attribute
        SimpleAttributeSet boldItalic = new SimpleAttributeSet();
        StyleConstants.setBold(boldItalic, true);
        StyleConstants.setItalic(boldItalic, true);

        // define a regular attribute
        SimpleAttributeSet regular = new SimpleAttributeSet();
        StyleConstants.setBold(regular, false);

        // Add the message to the text pane
        try {
            doc.insertString(doc.getLength(), "Matches found:\n\n", bold);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        for (Drink drink : matches) {
           try{
               // Add title with bold attribute
               doc.insertString(doc.getLength(), drink.name() + " (" + drink.id() + ")\n", boldItalic);

               // Add description with regular attribute
               doc.insertString(doc.getLength(), drink.getDrinkDescription() + "\n\n", regular);
           } catch (BadLocationException e) {
               e.printStackTrace();
           }
        }
        return textPane;
    }

    /**
     * A method to get the user's final drink order.
     * @return a Coffee object representing the user's final coffee order
     */

    private static Drink getDrinkOrder(DreamDrink dreamDrink) {

        // Find the coffees that match the user's dream drink order
        List<Drink> matches = menu.findDreamDrink(dreamDrink);

        // If there are matches then show the user the matches and let them select a coffee or see the full menu
        Drink selection = null;
        if (!matches.isEmpty()) {
            JTextPane message = buildFoundMessage(matches);

            List<String> matchesMenu = new ArrayList<>();
            matchesMenu.add("See the full menu");
            for (Drink drink : matches) {
                matchesMenu.add(drink.name() + " (" + drink.id() + ") - $" + String.format("%.2f", drink.price()));
            }
            String selectionString = (String) JOptionPane.showInputDialog(null, message, appName, JOptionPane.QUESTION_MESSAGE, icon, matchesMenu.toArray(), matchesMenu.get(0));
            if (selectionString == null) {
                System.exit(0);
            }

            if (!selectionString.equalsIgnoreCase("See the full menu")) {
                Drink drinkSelection = menu.getDrinkById(Integer.parseInt(selectionString.substring(selectionString.indexOf("(") + 1, selectionString.indexOf(")"))));
                selection = new Drink(drinkSelection.id(), drinkSelection.name(), drinkSelection.price(), drinkSelection.description(), dreamDrink);
            }

        } else { // No matches found - show a message and let the user see the full menu
            JOptionPane.showMessageDialog(null, "No matches!", appName, JOptionPane.INFORMATION_MESSAGE);
        }

        Drink drinkOrder = null;
        if (selection != null) { // user selected a drink from the matches
            drinkOrder = menu.getDrinkById(selection.id());

        } else {
            drinkOrder = showFullMenu();
        }

        if (selection != null) { // offer to keep the drink as the dream drink or customise it
            int customise = JOptionPane.showConfirmDialog(null, "Do you want to keep your previous selections?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

            if (customise == JOptionPane.NO_OPTION) {
                drinkOrder = customiseDrink(drinkOrder);
            } else {
                dreamDrink.filterExtras(drinkOrder); // filters excess extras carried over from DreamDrink selection not available for that drink
                drinkOrder = new Drink(drinkOrder.id(), drinkOrder.name(), drinkOrder.price(), drinkOrder.description(), dreamDrink);
            }
        } else { // user selected a drink from the full menu
            drinkOrder = customiseDrink(drinkOrder);
        }

        return drinkOrder;
    }

    /**
     * A method to customise the drink after selecting from the menu.
     * @param drinkOrder a Drink object representing the selected drink
     * @return a Drink object representing the customised drink
     */

    private static Drink customiseDrink(Drink drinkOrder) {
        Map<Criteria, Object> criteria = new HashMap<>(drinkOrder.genericFeatures().getAllCriteria());

        List<MilkOptions> milkOptions = new ArrayList<>((List<MilkOptions>) drinkOrder.genericFeatures().getCriteria(Criteria.MILK_TYPE));
        if (milkOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No milk available for this drink");
        } else {
            MilkOptions selectedMilk = (MilkOptions) JOptionPane.showInputDialog(null, "Select a milk option", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, milkOptions.toArray(), milkOptions.toArray()[0]);
            if (selectedMilk == null) {
                System.exit(0);
            }
            criteria.put(Criteria.MILK_TYPE, List.of(selectedMilk));
        }

        DrinkType drinkType = (DrinkType) drinkOrder.genericFeatures().getCriteria(Criteria.DRINK_TYPE);

        List<String> extras = (List<String>) drinkOrder.genericFeatures().getCriteria(Criteria.EXTRAS);
        if (extras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No extras available for this drink");
        } else {
            extras.add("None");
            extras.add("Skip");

            List<String> selectedExtras = new ArrayList<>();

            while (true) {
                if (!selectedExtras.isEmpty()) {
                    extras.remove("None");
                }
                if (extras.size() == 1 && extras.contains("Skip")) { // no more extras to select
                    JOptionPane.showMessageDialog(null, "All available extras have been selected.");
                    break;
                }
                String selectedExtra = (String) JOptionPane.showInputDialog(null, "Select an extra", "Extras", JOptionPane.QUESTION_MESSAGE, icon, extras.toArray(), extras.toArray()[0]);
                if (selectedExtra == null) {
                    System.exit(0);
                }

                if (selectedExtra.equalsIgnoreCase("Skip") || selectedExtra.equalsIgnoreCase("None")) {
                    if (selectedExtras.isEmpty()) {
                        selectedExtras.add("None");
                    }
                    break;
                }
                selectedExtras.add(selectedExtra);
                extras.remove(selectedExtra);
            }

            criteria.put(Criteria.EXTRAS, selectedExtras);
        }

        return new Drink(drinkOrder.id(), drinkOrder.name(), drinkOrder.price(), drinkOrder.description(), new DreamDrink(criteria));
    }

    /**
     * Show the full menu to the user
     * @return a Drink object representing the user's selected drink
     */

    private static Drink showFullMenu() {
        // User wants to see the full menu
        List<String> drinkOptions = new ArrayList<>();
        for (Drink drink : menu.getMenu()) {
            drinkOptions.add(drink.name() + " (" + drink.id() + ") - $" + String.format("%.2f", drink.price()));
        }

        String selectedDrink = (String) JOptionPane.showInputDialog(null, "Select a drink", appName, JOptionPane.QUESTION_MESSAGE, icon, drinkOptions.toArray(), drinkOptions.toArray()[0]);
        if (selectedDrink == null) {
            System.exit(0);
        }

        int id = Integer.parseInt(selectedDrink.substring(selectedDrink.indexOf("(") + 1, selectedDrink.indexOf(")")));
        Drink drinkOrder = menu.getDrinkById(id);
        if (drinkOrder == null) {
            JOptionPane.showMessageDialog(null, "Error: Drink not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return drinkOrder;
    }

    /**
     * A method to get the user's geek info.
     * @return a Geek object representing the user's geek info
     */
    private static Geek getGeekInfo() {
        String name;
        while (true) {
            name = JOptionPane.showInputDialog("Enter your name: ");
            if (name == null) {
                System.exit(0);
            }
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }

        String phoneNumber;
        while (true) {
            phoneNumber = JOptionPane.showInputDialog("Enter your phone number: ");
            if (phoneNumber == null) {
                System.exit(0);
            }
            if (!isValidPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
        return new Geek(name, phoneNumber);
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


