import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class MenuSearcher {

    public static void main(String[] args) {
        Menu menu = loadMenuFromFile("menu.txt");
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");

        Coffee dreamCoffee = getCoffeeOrder();

        // Find coffees that match the dream coffee
        Set<Coffee> dreamCoffees = menu.findDreamCoffees(dreamCoffee);
        if (dreamCoffees == null) {
            JOptionPane.showMessageDialog(null, "No coffees found matching your ideal coffee", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Show a message dialog showing the coffees found
            String message = buildFoundCoffees(dreamCoffees);
            JOptionPane.showMessageDialog(null, message, "Coffees found", JOptionPane.INFORMATION_MESSAGE);
        }

        // Build array of coffee names with their IDs from all coffees
        Set<Coffee> allCoffees = menu.getAllCoffees();
        Object[] coffeeArray = new Object[allCoffees.size()];
        for (int i = 0; i < allCoffees.size(); i++) {
            Coffee coffee = (Coffee) allCoffees.toArray()[i];
            coffeeArray[i] = coffee.getName() + " (" + coffee.getId() + ")";
        }

       String selectedCoffeeString = (String) JOptionPane.showInputDialog(null, "Select coffee: ", null, JOptionPane.QUESTION_MESSAGE, null, coffeeArray, coffeeArray[0]);
        if (selectedCoffeeString == null) {
            System.out.println("Error: Coffee not found");
            System.exit(1);
        }

        // extract the coffee ID from the selected coffee
        int selectedCoffeeId = Integer.parseInt(selectedCoffeeString.substring(selectedCoffeeString.indexOf("(") + 1, selectedCoffeeString.indexOf(")")));
        Coffee selectedCoffee = menu.getCoffeeById(selectedCoffeeId);

        Set<MilkOptions> selectedCoffeeMilkOptions = selectedCoffee.getMilkOptions();

        // offer milk selection based on the selected coffee
        MilkOptions selectedMilkOption = (MilkOptions) JOptionPane.showInputDialog(null, "Select milk option: ", null, JOptionPane.QUESTION_MESSAGE, null, selectedCoffeeMilkOptions.toArray(), selectedCoffeeMilkOptions.toArray()[0]);
        if (selectedMilkOption == null) {
            // Display a message and exit if the user cancels the dialog
            JOptionPane.showMessageDialog(null, "No milk option selected", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        selectedCoffee.setSelectedMilkOption(selectedMilkOption);

        // Option to select zero or more extras
        Set<Extras> selectedCoffeeExtras = selectedCoffee.getExtras();
        Set<Extras> selectedExtras = new HashSet<>();
        while (true) {
            Extras selectedExtra = (Extras) JOptionPane.showInputDialog(null, "Select extra: ", null, JOptionPane.QUESTION_MESSAGE, null, selectedCoffeeExtras.toArray(), selectedCoffeeExtras.toArray()[0]);
            if (selectedExtra == null) {
                break;
            }
            selectedExtras.add(selectedExtra);
            if (selectedExtra == Extras.NONE) {
                break;
            }
        }
        selectedCoffee.setSelectedExtras(selectedExtras);

        // Get the geek info
        Geek geek = getGeekInfo();

        // Write the order to a file
        writeOrderToFile(geek, selectedCoffee);
        JOptionPane.showMessageDialog(null, "Order written to file", "Success", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static Menu loadMenuFromFile(String filename) {
        Set<Coffee> coffees = new HashSet<>();

        try {
            Path path = Path.of(filename);
            String fileContents = Files.readString(path);
            String[] lines = fileContents.split("\n");

            for (int i = 1; i < lines.length; i++) {
                // This regex splits the line by commas, but ignores commas inside square brackets
                String regex = ",(?![^\\[]*\\])";
                List<String> coffeeData = List.of(lines[i].split(regex));

                int id = Integer.parseInt(coffeeData.get(0).trim());
                String name = coffeeData.get(1).trim();
                Double price = Double.parseDouble(coffeeData.get(2).trim());
                int numberOfShots = Integer.parseInt(coffeeData.get(3).trim());
                boolean sugar = coffeeData.get(4).trim().equalsIgnoreCase("yes");

                Set<String> milkOptions = parseOptions(coffeeData.get(5));

                // Convert the strings to Set<MilkOptions>
                Set<MilkOptions> milkOptionsSet = new HashSet<>();
                if (milkOptions.isEmpty()) {
                    milkOptionsSet.add(MilkOptions.NONE);
                } else {
                    for (String option : milkOptions) {
                        milkOptionsSet.add(MilkOptions.fromString(option));
                    }
                }

                Set<String> extras = parseOptions(coffeeData.get(6));

                // Convert the strings to Set<Extras>
                Set<Extras> extrasSet = new HashSet<>();
                if (extras.isEmpty()) {
                    extrasSet.add(Extras.NONE);
                } else {
                    for (String extra : extras) {
                        extrasSet.add(Extras.fromString(extra));
                    }
                }

                String description = descriptionBuilder(coffeeData);

                coffees.add(new Coffee(id, name, price, numberOfShots, sugar, milkOptionsSet, extrasSet, description));
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        if (coffees.isEmpty()) {
            return null;
        }
        return new Menu(coffees);
    }

    private static String descriptionBuilder(List<String> coffeeData) {
        StringBuilder descriptionBuilder = new StringBuilder();

        for (int j = 7; j < coffeeData.size(); j++) {
            descriptionBuilder.append(coffeeData.get(j).trim());
            if (j != coffeeData.size() - 1) {
                descriptionBuilder.append(", ");
            }
        }
        String description = descriptionBuilder.toString();
        description = removeArrayBrackets(description);

        return description;
    }

    // Helper method to parse options fields (milk, extras)
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

    private static Coffee getCoffeeOrder() {
        // Get the coffee order
        MilkOptions selectedMilkOption = (MilkOptions) JOptionPane.showInputDialog(null,"Select milk option: ",null,JOptionPane.QUESTION_MESSAGE, null, MilkOptions.values(), MilkOptions.FULL_CREAM);
        if (selectedMilkOption == null) {
            // Display a message and exit if the user cancels the dialog
            JOptionPane.showMessageDialog(null, "No milk option selected", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        Boolean hasSugar = JOptionPane.showConfirmDialog(null, "Would you like sugar?", "Sugar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        if (hasSugar == null) {
            System.exit(0);
        }
        String numberOfShots;
        String regexNumShots = "\\d+"; // regex for a number with no decimal point
        do {
            numberOfShots = JOptionPane.showInputDialog("How many shots would you like: ");
            if (numberOfShots == null) {
                System.exit(0);
            }
            if (!numberOfShots.matches(regexNumShots)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!numberOfShots.matches(regexNumShots));
        int numberOfShotsInt = Integer.parseInt(numberOfShots);

        // Option to select zero or more extras
        Set<Extras> selectedExtras = new HashSet<>();
        Object[] extras = Extras.values();
        while (true) {
            Extras selectedExtra = (Extras) JOptionPane.showInputDialog(null, "Select extra: ", null, JOptionPane.QUESTION_MESSAGE, null, extras, extras[0]);
            if (selectedExtra == null) {
                break;
            }
            selectedExtras.add(selectedExtra);
            if (selectedExtra == Extras.NONE) {
                break;
            }
        }

        // get the price range
        String regexMinMax = "\\d+(\\.\\d+)?"; // regex for a number with an optional decimal point
        String minPrice;
        do {
            minPrice = JOptionPane.showInputDialog("Enter the minimum price: ");
            if (minPrice == null) {
                System.exit(0);
            }
            if (!minPrice.matches(regexMinMax)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!minPrice.matches(regexMinMax));

        String maxPrice;
        do {
            maxPrice = JOptionPane.showInputDialog("Enter the maximum price: ");
            if (maxPrice == null) {
                System.exit(0);
            }
            if (!maxPrice.matches(regexMinMax)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!maxPrice.matches(regexMinMax));

        Coffee dreamCoffee = new Coffee(0, "Dream Coffee", 0.0, numberOfShotsInt, hasSugar, Set.of(selectedMilkOption), selectedExtras, "");
        dreamCoffee.setSelectedMilkOption(selectedMilkOption);
        dreamCoffee.setSelectedExtras(selectedExtras);
        dreamCoffee.setMinPrice(Double.parseDouble(minPrice));
        dreamCoffee.setMaxPrice(Double.parseDouble(maxPrice));
        return dreamCoffee;
    }

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

    private static void writeOrderToFile(Geek geek, Coffee selectedCoffee) {
        // Write Geek info and coffee order to file
        try {
            Path path = Path.of("order.txt");

            StringBuilder orderDetails = new StringBuilder("Order details:\n");
            orderDetails.append("\tName: ").append(geek.getName()).append("\n");
            orderDetails.append("\tOrder number: ").append(geek.getPhoneNumber()).append("\n");
            orderDetails.append("\tItem: ").append(selectedCoffee.getName()).append(" (").append(selectedCoffee.getId()).append(")\n");
            orderDetails.append("\tMilk: ").append(selectedCoffee.getSelectedMilkOption()).append("\n");
            if (selectedCoffee.getSelectedExtras().isEmpty()) {
                orderDetails.append("\tExtras: None\n");
            } else {
                orderDetails.append("\tExtras: ").append(removeArrayBrackets(selectedCoffee.getSelectedExtras().toString())).append("\n");
            }
            Files.writeString(path, orderDetails.toString());
            System.out.println("Order written to file");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * A method to validate a phone number
     * @param phoneNumber a String representing the phone number
     * @return a boolean indicating whether the phone number is valid
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private static String buildFoundCoffees(Set<Coffee> dreamCoffees) {
        StringBuilder message = new StringBuilder("Coffees found matching your order:\n\n");
        for (Coffee coffee : dreamCoffees) {
            message.append(coffee.getName() + " (" + coffee.getId() + ")\n");
            message.append(coffee.getDescription() + "\n");
            message.append("Ingredients:\n");
            message.append("Number of shots: " + coffee.getNumberOfShots() + "\n");
            message.append("Sugar: " + (coffee.hasSugar() ? "Yes" : "No") + "\n");
            message.append("Milk options: " + removeArrayBrackets(coffee.getMilkOptions().toString()) + "\n");
            message.append("Extra/s: " + removeArrayBrackets(coffee.getExtras().toString()) + "\n");
            message.append("Price: $" + String.format("%.2f", coffee.getPrice()) + "\n");
            message.append("\n");
        }
        return message.toString();
    }

    private static String removeArrayBrackets(String description) {
        if (description.startsWith("[") && description.endsWith("]")) {
            description = description.substring(1, description.length() - 1);
        }
        return description;
    }
}


