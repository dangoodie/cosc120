import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.*;
import javax.swing.*;

public class BreedAvailabilityV3 {
    public static void main(String[] args) {
        String filePath = "availableBreeds.txt";
        Path path = Path.of(filePath);

        String fileContents;
        try {

            fileContents = Files.readString(path);
            String[] dogBreeds = fileContents.split(",");
            for (int i = 0; i < dogBreeds.length; i++) {
                dogBreeds[i] = dogBreeds[i].toLowerCase();
            }
            Map<String, Integer> availableBreeds = new HashMap<>();
            for (String breed : dogBreeds) {
                if (availableBreeds.containsKey(breed)) {
                    availableBreeds.put(breed, availableBreeds.get(breed) + 1);
                } else {
                    availableBreeds.put(breed, 1);
                }
            }

            System.out.println("******** All Available Breeds ********");
            System.out.println(availableBreeds);

            availableBreeds.put("dalmatian", availableBreeds.get("dalmatian") + 1);
            availableBreeds.put("greyhound", 1);

            // fix the spelling of "rotweiler" to "rottweiler"
            int count = availableBreeds.get("rotweiler");
            availableBreeds.remove("rotweiler");
            availableBreeds.put("rottweiler", count);

            System.out.println("******** All Available Breeds ********");
            System.out.println(availableBreeds);

            // Ask the user for a breed and check if it's available
            String desiredBreed = JOptionPane.showInputDialog("Enter a breed to check if it's available: ").toLowerCase();

            // exit if the breed is not available
            if (!availableBreeds.containsKey(desiredBreed)) {
                JOptionPane.showMessageDialog(null, desiredBreed + " is not available");
                System.exit(0);
            }

            // Confirm if the user wants to adopt the breed
            String confirm = JOptionPane.showInputDialog(desiredBreed + " is available! Would you like to adopt it? (yes/no)").toLowerCase();
            while (!confirm.equalsIgnoreCase("yes") && !confirm.equalsIgnoreCase("no")) {
                confirm = JOptionPane.showInputDialog("Invalid input. Please enter 'yes' or 'no'").toLowerCase();
            }
            if (confirm.equalsIgnoreCase("yes")) {
                JOptionPane.showMessageDialog(null, "Congratulations! You have adopted a " + desiredBreed);
            } else {
                JOptionPane.showMessageDialog(null, "Maybe next time!");
                System.exit(0);
            }

            // Decrement the count of the breed
            availableBreeds.put(desiredBreed, availableBreeds.get(desiredBreed) - 1);
            if (availableBreeds.get(desiredBreed) == 0) {
                availableBreeds.remove(desiredBreed);
            }
            System.out.println("******** All Available Breeds ********");
            System.out.println(availableBreeds);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.exit(0);
        }
    }
}
