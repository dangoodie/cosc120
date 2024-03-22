import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuSearcher {
    public static void main(String[] args) {
        Menu menu = loadMenuFromFile("menu.txt");
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");



        // Create a dream coffee option to test the search function
        // Build the user ordering system here
        MilkOptions selectedMilkOption = MilkOptions.FULL_CREAM;
        Coffee dreamCoffee = new Coffee(-1, "", 0.0, 2, true, Set.of(selectedMilkOption), Set.of(Extras.CINNAMON), "");
        dreamCoffee.setMinPrice(2.0);
        dreamCoffee.setMaxPrice(8.0);

        // Find coffees that match the dream coffee
        Set<Coffee> dreamCoffees = menu.findDreamCoffees(dreamCoffee);
        if (dreamCoffees == null) {
            System.out.println("No coffees found matching the dream coffee");
        } else {
            System.out.println("Coffees found matching the dream coffee:");
            for (Coffee coffee : dreamCoffees) {
                System.out.println(coffee.getName());
            }
        }

        // Select a coffee from the menu
        Coffee selectedCoffee = menu.getCoffeeById(30210);
        if (selectedCoffee == null) {
            System.out.println("Error: Coffee not found");
            System.exit(1);
        } else {
            System.out.println("Coffee found: " + selectedCoffee.getName());
        }
        selectedCoffee.setSelectedMilkOption(selectedMilkOption);

        // Get the geek info
        Geek geek = getGeekInfo();

        // Write the order to a file
        writeOrderToFile(geek, selectedCoffee);

    }

    public static Menu loadMenuFromFile(String filename) {
        Set<Coffee> coffees = new HashSet<>();

        try {
            Path path = Path.of(filename);
            String fileContents = Files.readString(path);
            String[] lines = fileContents.split("\n");

            for (int i = 1; i < lines.length; i++) {
                // This regex splits the line by commas, but ignores commas inside square brackets
                List<String> coffeeData = List.of(lines[i].split(",(?![^\\[]*\\])"));

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
            if (j < coffeeData.size() - 1) descriptionBuilder.append(", ");
            descriptionBuilder.append(coffeeData.get(j).trim());
        }
        String description = descriptionBuilder.toString();
        if (description.startsWith("[") && description.endsWith("]")) {
            description = description.substring(1, description.length() - 1);
        }
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

    private static Geek getGeekInfo() {
        String name = "John Doe";
        String phoneNumber = "04757575";
        return new Geek(name, phoneNumber);
    }

    private static void writeOrderToFile(Geek geek, Coffee selectedCoffee) {
        // Write Geek info and coffee order to file
        try {
            Path path = Path.of("order.txt");
            Files.writeString(path,
                    "Order details:\n" +
                            "\tName: " + geek.getName() + "\n" +
                            "\tOrder number: " + geek.getPhoneNumber() + "\n" +
                            "\tItem: " + selectedCoffee.getName() + " (" + selectedCoffee.getId() + ")\n" +
                            "\tMilk: " + selectedCoffee.getSelectedMilkOption() + "\n");
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
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
