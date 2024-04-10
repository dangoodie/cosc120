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

        // Get the user's final coffee order
        Drink drinkOrder = getDrinkOrder();

        // Get the geek info
        Geek geek = getGeekInfo();

        // Write the order to a file
        writeOrderToFile(geek, drinkOrder);
        JOptionPane.showMessageDialog(null, "Order written to file", "Success", JOptionPane.INFORMATION_MESSAGE);

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
        List<MilkOptions> milkOptions = MilkOptions.fromStringList(parseOptions(drinkData.get(8)));
        List<Extras> extras = Extras.fromStringList(parseOptions(drinkData.get(9)));
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
        List<MilkOptions> milkOptions = MilkOptions.fromStringList(parseOptions(drinkData.get(8)));
        List<Extras> extras = Extras.fromStringList(parseOptions(drinkData.get(9)));
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
    private static List<String> parseOptions(String field) {
        field = field.trim(); // Remove leading/trailing whitespace
        if (field.equals("[]")) {
            return new LinkedList<>();
        }

        field = field.substring(1, field.length() - 1); // Remove the surrounding brackets
        String[] options = field.split(",");
        for (int i = 0; i < options.length; i++) {
            options[i] = options[i].trim();
        }

        return new LinkedList<>(Arrays.asList(options));
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
            Set<String> steepTimeOptions = Set.of("Skip", "1", "2", "3", "4", "5", "6", "7", "8");
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

        MilkOptions milkOptions = (MilkOptions) JOptionPane.showInputDialog(null, "What type of milk would you like?", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, MilkOptions.values(), MilkOptions.NONE);
        if (milkOptions == null) {
            System.exit(0);
        }
        if (milkOptions != MilkOptions.SKIP) {
            criteria.put(Criteria.MILK_TYPE, List.of(milkOptions));
        }

        List<String> sugarOptions = List.of("Yes", "No", "Skip");
        String sugar = (String) JOptionPane.showInputDialog(null, "Would you like sugar?", "Sugar", JOptionPane.QUESTION_MESSAGE, icon, sugarOptions.toArray(), sugarOptions.toArray()[0]);
        if (sugar == null) {
            System.exit(0);
        }
        if (!sugar.equalsIgnoreCase("SKIP")) {
            criteria.put(Criteria.SUGAR, sugar.equalsIgnoreCase("Yes"));
        }

        List<Extras> extras = findExtras(drinkType);
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

        if (!selectedExtras.contains(Extras.SKIP)) {
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
     * A method to find the extras that are available for a drink of a given type
     * It checks to make sure that there is no duplication of extras
     * @param drinkType a DrinkType object representing the type of drink
     * @return a List of Extras objects representing the extras available for the drink
     */
    private static List<Extras> findExtras(DrinkType drinkType) {
        List<Extras> extras = new LinkedList<>();
        for (Drink drink : menu.getMenu()) {
            if (drink.getGenericFeatures().getCriteria(Criteria.DRINK_TYPE) == drinkType) {
                List<Extras> e = (List<Extras>) drink.getGenericFeatures().getCriteria(Criteria.EXTRAS);
                for (Extras extra : e) {
                    if (!extras.contains(extra)) {
                        extras.add(extra);
                    }
                }
            }
        }
        return extras;
    }

    private static List<Extras> findExtras(Drink drink) {
        return (List<Extras>) drink.getGenericFeatures().getCriteria(Criteria.EXTRAS);
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
     * @return a Coffee object representing the user's final coffee order
     */

    private static Drink getDrinkOrder() {
        List<String> drinkOptions = new LinkedList<>();
        for (Drink drink : menu.getMenu()) {
            drinkOptions.add(drink.getName() + " (" + drink.getId() + ") - $" + String.format("%.2f", drink.getPrice()));
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

        Map<Criteria, Object> criteria = new HashMap<>(drinkOrder.getGenericFeatures().getAllCriteria());

        List<MilkOptions> milkOptions = new LinkedList<>((List<MilkOptions>) drinkOrder.getGenericFeatures().getCriteria(Criteria.MILK_TYPE));
        MilkOptions selectedMilk = (MilkOptions) JOptionPane.showInputDialog(null, "Select a milk option", "Milk Options", JOptionPane.QUESTION_MESSAGE, icon, milkOptions.toArray(), milkOptions.toArray()[0]);
        if (selectedMilk == null) {
            System.exit(0);
        }
        criteria.put(Criteria.MILK_TYPE, List.of(selectedMilk));

        List<Extras> extras = findExtras(drinkOrder);
        extras.add(Extras.NONE);
        extras.add(Extras.SKIP);

        List<Extras> selectedExtras = new LinkedList<>();

        while (true) {
            if (!selectedExtras.isEmpty()) {
                extras.remove(Extras.NONE);
            }
            Extras selectedExtra = (Extras) JOptionPane.showInputDialog(null, "Select an extra", "Extras", JOptionPane.QUESTION_MESSAGE, icon, extras.toArray(), extras.toArray()[0]);
            if (selectedExtra == null) {
                System.exit(0);
            }

            if (selectedExtra == Extras.SKIP || selectedExtra == Extras.NONE) {
                if (selectedExtras.isEmpty()) {
                    selectedExtras.add(Extras.NONE);
                }
                break;
            }
            selectedExtras.add(selectedExtra);
            extras.remove(selectedExtra);
        }

        criteria.put(Criteria.EXTRAS, selectedExtras);

        return new Drink(id, drinkOrder.getName(), drinkOrder.getPrice(), drinkOrder.getDescription(), new DreamDrink(criteria));
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
            orderDetails.append("\tName: ").append(geek.getName()).append("\n");
            orderDetails.append("\tOrder number: ").append(geek.getPhoneNumber()).append("\n");
            orderDetails.append("\tItem: ").append(drinkOrder.getName()).append(" (").append(drinkOrder.getId()).append(")\n");
            orderDetails.append("\tMilk: ").append(drinkOrder.getGenericFeatures().getCriteria(Criteria.MILK_TYPE)).append("\n");
            List<Extras> extras = (List<Extras>) drinkOrder.getGenericFeatures().getCriteria(Criteria.EXTRAS);
            if (extras.contains(Extras.NONE) || extras.isEmpty()){
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

    /**
     * A method to build a message showing the coffees found.
     * @param dreamCoffees a Set of Coffee objects representing the coffees found
     * @return a String representing the message
     */


}


