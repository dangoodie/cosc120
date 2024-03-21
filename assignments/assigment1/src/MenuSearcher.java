import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MenuSearcher {
    public static void main(String[] args) {
        Menu menu = loadMenuFromFile("menu.txt");
        if (menu == null) {
            System.out.println("Error loading menu");
            System.exit(1);
        }
        System.out.println("Menu loaded successfully");



      // Print out the menu
        Set<Coffee> coffees = menu.getAllCoffees();
        for (Coffee coffee : coffees) {
            System.out.println(coffee.getName() + " - " + coffee.getMilkOptions());
        }
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

                String description = descriptionBuilder(coffeeData);

                coffees.add(new Coffee(id, name, price, numberOfShots, sugar, milkOptionsSet, extras, description));
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
        return new HashSet<>(Arrays.asList(options));
    }

}
