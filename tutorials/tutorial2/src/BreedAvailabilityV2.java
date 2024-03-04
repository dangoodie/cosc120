import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class BreedAvailabilityV2 {
    public static void main(String[] args) {
        String filePath = "availableBreeds.txt";
        Path path = Path.of(filePath);

        String fileContents;

        // Read the file
        try {
            fileContents = Files.readString(path);
            String[] dogBreeds = fileContents.split(",");
            // convert all the dog breeds to lowercase
            for (int i = 0; i < dogBreeds.length; i++) {
                dogBreeds[i] = dogBreeds[i].toLowerCase();
            }

            List<String> availableBreeds = new ArrayList<>(Arrays.asList(dogBreeds));

            // Print contents of the arraylist
            System.out.println("******** All Available Breeds ********");
            System.out.println(availableBreeds);

            // Add new dogs that have become available
            availableBreeds.add("dalmatian");
            availableBreeds.add("greyhound");

            // Fix the spelling of "rotweiler" to "rottweiler"
            int index = availableBreeds.indexOf("rotweiler");
            availableBreeds.set(index, "rottweiler");

            // Use collections to sort the list in alphabetical order
            Collections.sort(availableBreeds);
            System.out.println("Alphabetically sorted breeds: " + availableBreeds);

            Set<String> availableBreedsHashSet = new HashSet<>(availableBreeds);
            System.out.println("HashSet: " + availableBreedsHashSet);

            Set<String> availableBreedsTreeSet = new TreeSet<>(availableBreeds);
            System.out.println("TreeSet: " + availableBreedsTreeSet);



        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.exit(0);
        }
    }
}
