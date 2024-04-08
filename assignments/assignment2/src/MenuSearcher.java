/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 2
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.*;

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
     * This method loads the menu from a file, gets the user's coffee order, finds coffees that match the order,
     * allows the user to select a coffee, milk option, and extras, gets the user's geek info, and writes the order to a file.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Load the menu from a file
        Menu menu = loadMenuFromFile("menu.txt");
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");
        System.out.println("Printing the menu now");
        Set<Drink> allDrinks = menu.getMenu().stream().sorted(Comparator.comparing(Drink::name)).collect(Collectors.toCollection(LinkedHashSet::new));

        for (Drink drink : allDrinks) {
            System.out.println("ID: " + drink.id());
            System.out.println("Name: " + drink.name());
            System.out.println("Price: " + drink.price());
            System.out.println("Description: " + drink.description());

            Map<Criteria, Object> criteria = drink.getGenericFeatures().getAllCriteria();
            for (Criteria criterion : criteria.keySet()) {
                System.out.println(criterion + ": " + criteria.get(criterion));
            }
            System.out.println("-------------------------------------------------");
        }

        /*
        // Get the user's dream coffee order
        Coffee dreamCoffee = getDreamCoffee();

        // Get the user's final coffee order
        Coffee coffeeOrder = getCoffeeOrder(menu, dreamCoffee);

        // Get the geek info
        Geek geek = getGeekInfo();

        // Write the order to a file
        writeOrderToFile(geek, coffeeOrder);
        JOptionPane.showMessageDialog(null, "Order written to file", "Success", JOptionPane.INFORMATION_MESSAGE);
        */
        System.exit(0);
    }

    // Utility methods
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
                    Drink drink = handleTea(drinkData);
                    menu.add(drink);
                }

                if (type == DrinkType.COFFEE) {
                    Drink drink = handleCoffee(drinkData);
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

    private static Drink handleTea(List<String> drinkData) {
        // common features
        int id = Integer.parseInt(drinkData.get(1).trim());
        String name = drinkData.get(2).trim();
        Double price = Double.parseDouble(drinkData.get(3).trim());
        String description = drinkData.get(10).trim().replace("[", "").replace("]", "");
        Set<MilkOptions> milkOptions = MilkOptions.fromStringSet(parseOptions(drinkData.get(8)));
        Set<Extras> extras = Extras.fromStringSet(parseOptions(drinkData.get(9)));
        Boolean sugar = drinkData.get(7).trim().equalsIgnoreCase("YES");

        // tea specific features
        Temperature temperature = Temperature.fromString(drinkData.get(5));
        int steepTime = Integer.parseInt(drinkData.get(6).trim());


        Map<Criteria, Object> criteriaObjectMap = new LinkedHashMap<>();
        criteriaObjectMap.put(Criteria.SUGAR, sugar);
        criteriaObjectMap.put(Criteria.MILK_TYPE, milkOptions);
        criteriaObjectMap.put(Criteria.EXTRAS, extras);
        criteriaObjectMap.put(Criteria.TEMPERATURE, temperature);
        criteriaObjectMap.put(Criteria.STEEP_TIME, steepTime);

        DreamDrink dreamDrink = new DreamDrink(criteriaObjectMap);
        return new Drink(id, name, price, description, dreamDrink);
    }

    private static Drink handleCoffee(List<String> drinkData) {
        // common features
        int id = Integer.parseInt(drinkData.get(1).trim());
        String name = drinkData.get(2).trim();
        Double price = Double.parseDouble(drinkData.get(3).trim());
        String description = drinkData.get(10).trim().replace("[", "").replace("]", "");
        Set<MilkOptions> milkOptions = MilkOptions.fromStringSet(parseOptions(drinkData.get(8)));
        Set<Extras> extras = Extras.fromStringSet(parseOptions(drinkData.get(9)));
        Boolean sugar = drinkData.get(7).trim().equalsIgnoreCase("YES");

        // coffee specific features
        int numberOfShots = Integer.parseInt(drinkData.get(4).trim());

        Map<Criteria, Object> criteriaObjectMap = new LinkedHashMap<>();
        criteriaObjectMap.put(Criteria.SUGAR, sugar);
        criteriaObjectMap.put(Criteria.MILK_TYPE, milkOptions);
        criteriaObjectMap.put(Criteria.EXTRAS, extras);
        criteriaObjectMap.put(Criteria.NUM_OF_SHOTS, numberOfShots);

        DreamDrink dreamDrink = new DreamDrink(criteriaObjectMap);
        return new Drink(id, name, price, description, dreamDrink);
    }
    /**
     * A method to parse the milk and extras options from a field.
     * @param field a String representing the field to parse
     * @return a Set of Strings representing the options
     */
    private static Set<String> parseOptions(String field) {
        field = field.trim(); // Remove leading/trailing whitespace
        if (field.equals("[]")) {
            return new HashSet<>();
        }

        field = field.substring(1, field.length() - 1); // Remove the surrounding brackets
        String[] options = field.split(",");
        for (int i = 0; i < options.length; i++) {
            options[i] = options[i].trim();
        }

        return new HashSet<>(Arrays.asList(options));
    }

    /**
     * A method to get the user's dream drink order.
     * @return a Dream drink object representing the user's dream drink order
     */

    // create here

    /**
     * A method to get the user's final drink order.
     * @param menu a Menu object representing the coffee menu
     * @param dreamDrink a Coffee object representing the user's dream coffee order
     * @return a Coffee object representing the user's final coffee order
     */

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
     * @param selectedCoffee a Coffee object representing the selected coffee
     */

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

    /**
     * A method to build a message showing the coffees found.
     * @param dreamCoffees a Set of Coffee objects representing the coffees found
     * @return a String representing the message
     */

    /**
     * A method to remove the square brackets from a string.
     * Useful for the array fields in the Coffee class.
     * @param description a String representing the description
     * @return a String representing the description with the square brackets removed
     */

}


