/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 2
 */

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

    /**
     * The main method of the program.
     * This method loads the menu from a file, gets the user's drink order, finds drinks that match the order,
     * allows the user to select a drink, all relevant options, gets the user's geek info, and writes the order to a file.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Load the menu from a file
        menu = loadMenuFromFile(MENU_FILE);
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");

        int response = JOptionPane.showConfirmDialog(null, "Welcome to " + appName, appName, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
        if (response == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // Get the user's dream drink order for searching
        DreamDrink dreamDrink = getDreamDrink();

        // Get the user's final drink order
        Drink drinkOrder = getDrinkOrder(dreamDrink);

        // Get the geek info
        Geek geek = getGeekInfo();

        // Write the order to a file
        writeOrderToFile(geek, drinkOrder);
        JOptionPane.showMessageDialog(null, "Order written to file", "Success", JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
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

        List<String> extras = menu.findExtras(drinkType);
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
        MilkOptions selectedMilk = (MilkOptions) JOptionPane.showInputDialog(null, "Select a milk option", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, milkOptions.toArray(), milkOptions.toArray()[0]);
        if (selectedMilk == null) {
            System.exit(0);
        }
        criteria.put(Criteria.MILK_TYPE, List.of(selectedMilk));

        DrinkType drinkType = (DrinkType) drinkOrder.genericFeatures().getCriteria(Criteria.DRINK_TYPE);

        List<String> extras = menu.findExtras(drinkType);
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
                    selectedExtras.add("None");
                }
                break;
            }
            selectedExtras.add(selectedExtra);
            extras.remove(selectedExtra);
        }

        criteria.put(Criteria.EXTRAS, selectedExtras);
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


