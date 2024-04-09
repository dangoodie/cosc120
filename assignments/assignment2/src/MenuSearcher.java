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
import javax.lang.model.element.Name;
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
        menu = loadMenuFromFile("menu.txt");
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");

        JOptionPane.showMessageDialog(null, "Welcome to " + appName, appName, JOptionPane.INFORMATION_MESSAGE, icon);


        // Get the user's dream drink order
        DreamDrink dreamDrink = getDreamDrink();

        // Find the coffees that match the user's dream drink order
        List<Drink> matches = menu.findDreamDrink(dreamDrink);
        if (!matches.isEmpty()) {
            String message = buildMessage(matches);
            JOptionPane.showMessageDialog(null, message, appName, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No matches!", appName, JOptionPane.INFORMATION_MESSAGE);
        }


        /*
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

    private static DreamDrink getDreamDrink() {
        Map<Criteria, Object> criteria = new HashMap<>();

        // Get the user's dream drink order
        DrinkType drinkType = (DrinkType) JOptionPane.showInputDialog(null, "What type of drink would you like? (Coffee/Tea)", "Drink Type", JOptionPane.QUESTION_MESSAGE, icon, DrinkType.values(), DrinkType.COFFEE);
        if (drinkType == null) {
            System.exit(0);
        }
        criteria.put(Criteria.DRINK_TYPE, drinkType);

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

        MilkOptions milkOptions = (MilkOptions) JOptionPane.showInputDialog(null, "What type of milk would you like?", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, MilkOptions.values(), MilkOptions.NONE);
        if (milkOptions == null) {
            System.exit(0);
        }
        criteria.put(Criteria.MILK_TYPE, Set.of(milkOptions));

        Boolean sugar = JOptionPane.showConfirmDialog(null, "Would you like sugar?", "Sugar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        criteria.put(Criteria.SUGAR, sugar);

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
            Temperature temperature = (Temperature) JOptionPane.showInputDialog(null, "What temperature would you like?", "Temperature", JOptionPane.QUESTION_MESSAGE, icon, Temperature.values(), Temperature.ONE_HUNDRED);
            if (temperature == null) {
                System.exit(0);
            }
            criteria.put(Criteria.TEMPERATURE, temperature);

            int steepTime = -1;
            while (steepTime < 0) {
                String steepTimeString = JOptionPane.showInputDialog("Enter the steep time: ");
                if (steepTimeString == null) {
                    System.exit(0);
                }
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
            criteria.put(Criteria.STEEP_TIME, steepTime);
        }

        Set<Extras> extras = findExtras(drinkType);
        extras.add(Extras.NONE);
        extras.add(Extras.SKIP);

        Set<Extras> selectedExtras = new HashSet<>();

        while (true) {
            if (selectedExtras.size() > 0) {
                extras.remove(Extras.NONE);
            }
            Extras selectedExtra = (Extras) JOptionPane.showInputDialog(null, "Select an extra", "Extras", JOptionPane.QUESTION_MESSAGE, icon, extras.toArray(), extras.toArray()[0]);
            if (selectedExtra == null) {
                System.exit(0);
            }

            if (selectedExtra == Extras.SKIP || selectedExtra == Extras.NONE) {
                if (selectedExtras.size() == 0) {
                    selectedExtras.add(selectedExtra);
                }
                break;
            }
            selectedExtras.add(selectedExtra);
            extras.remove(selectedExtra);
        }

        criteria.put(Criteria.EXTRAS, selectedExtras);

        return new DreamDrink(minPrice, maxPrice, criteria);
    }

    /**
     * A method to find the extras that are available for a drink of a given type
     * @param drinkType a DrinkType object representing the type of drink
     * @return a Set of Extras objects representing the extras available for the drink
     */
    private static Set<Extras> findExtras(DrinkType drinkType) {
        Set<Extras> extras = new HashSet<>();
        for (Drink drink : menu.getMenu()) {
            if (drink.getGenericFeatures().getCriteria(Criteria.DRINK_TYPE) == drinkType) {
                extras.addAll((Set<Extras>) drink.getGenericFeatures().getCriteria(Criteria.EXTRAS));
            }
        }
        return extras;
    }

    /**
     * A method to build a message showing the drinks found.
     * @param matches a List of Drink objects representing the drinks found
     * @return a String representing the message
     */
    private static String buildMessage(List<Drink> matches) {
        StringBuilder message = new StringBuilder();
        message.append("Drinks found:\n");
        for (Drink drink : matches) {
            message.append("Name: ").append(drink.getName()).append("\n");
            message.append("Price: $").append(String.format("%.2f", drink.getPrice())).append("\n");
            message.append("Description: ").append(drink.getDescription()).append("\n");
            for (Criteria criteria : drink.getGenericFeatures().getAllCriteria().keySet()) {
                message.append(criteria).append(": ").append(drink.getGenericFeatures().getCriteria(criteria)).append("\n");
            }
            message.append("\n");
        }
        return message.toString();
    }

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


}


