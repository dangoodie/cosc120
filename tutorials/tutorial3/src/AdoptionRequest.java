import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class is used to create an adoption request object
 */
public class AdoptionRequest {

    public static void main(String[] args) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        Map <Integer, Dog> dogMap = adoptionRequest.loadDogData("./allDogs.txt");

    }
    /**
     * A method to load dog data from a file
     * The key/value pair is microchip/dog
     * @param fileName a String representing the name of the file to load the data from
     * @return a Map of Dog objects
     * @throws Exception if there is an error loading the data
     */
    public Map<Integer, Dog> loadDogData(String fileName) {
        Map<Integer, Dog> dogMap = new HashMap<>();
        try {
            // code to load dog data from a file
            Path path = Path.of(fileName);
            String fileContents;
            fileContents = Files.readString(path);

            // split the file contents into individual dog data
            String[] dogData = fileContents.split("\n");
            for (int i = 0; i < dogData.length; i++) {
                dogData[i] = dogData[i].toLowerCase();
            }

            // iterate through the dog data and create a Dog object for each dog
            // skipping the headers
            for (int i = 1; i < dogData.length; i++) {
                // split the dog data into its components
                String[] dogDetails = dogData[i].split(",");

                // store the dog details in variables
                String name = dogDetails[0];
                int microchipNumber = Integer.parseInt(dogDetails[1]);
                String sex = dogDetails[2];
                boolean desexed = yesNoBoolean(dogDetails[3]);
                int age = Integer.parseInt(dogDetails[4]);
                String breed = dogDetails[5];

                // create a new Dog object
                Dog newDog = new Dog(name, microchipNumber, breed, sex, desexed, age);
                dogMap.put(microchipNumber, newDog);
            }

        } catch (Exception e) {
            System.out.println("Error loading dog data from file: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Dog data loaded successfully");
        return dogMap;
    }

    /**
     * A method to get user input for their preferred dog
     * @return a Map of the user's adoption request details
     */
    public Map <String, String> getUserInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the desired dog breed: ");
        String desiredBreed = keyboard.nextLine();

        String desiredSex = "";
        while (!desiredSex.equals("m") && !desiredSex.equals("f")) {
            System.out.println("What is your preferred sex? (M/F): ");
            desiredSex = keyboard.nextLine();
            desiredSex = desiredSex.toLowerCase();

            if (!desiredSex.equals("m") && !desiredSex.equals("f")) {
                System.out.println("Please enter a valid sex.");
            }
        }

        int maxAge = -1;
        while (maxAge < 0 || maxAge > 20) {
            System.out.println("Maximum age in years (max 20 years): ");
            maxAge = keyboard.nextInt();
            if (maxAge < 0 || maxAge > 20) {
                System.out.println("Invalid value for maximum age: " + maxAge);
            }
        }

        int minAge = -1;
        while (minAge < 0 || minAge > 20 || minAge > maxAge) {
            System.out.println("Minimum age in years (min 0 years): ");
            minAge = keyboard.nextInt();
            if (minAge < 0 || minAge > 20) {
                System.out.println("Invalid value for minimum age: " + minAge);
            }
            if (minAge > maxAge) {
                System.out.println("Minimum age cannot be greater than maximum age");
            }
        }

        String desexed = "";

        while (!Objects.equals(desexed, "yes") || !Objects.equals(desexed, "no")) {
            System.out.println("Do you want the dog to be desexed? (yes/no): ");
            desexed = keyboard.nextLine();
            desexed = desexed.toLowerCase();
            if (!Objects.equals(desexed, "yes") || !Objects.equals(desexed, "no")) {
                System.out.println("Please enter a valid response.");
            }
        }
        keyboard.close();
        // return the user's adoption request
        Map <String, String> userRequest = new HashMap<>();
        userRequest.put("desiredBreed", desiredBreed);
        userRequest.put("desiredSex", desiredSex);
        userRequest.put("maxAge", String.valueOf(maxAge));
        userRequest.put("minAge", String.valueOf(minAge));
        userRequest.put("desexed", desexed);
        return userRequest;
    }


    /**
     * A method to convert yes or no string to a boolean
     * @param value a String representing the value to convert to a boolean
     * @return a boolean value
     */
    public static boolean yesNoBoolean(String value) {
        boolean result = false;
        if (value.equalsIgnoreCase("yes")) {
            result = true;
        } else if (value.equalsIgnoreCase("no")) {
            result = false;
        } else {
            System.out.println("Invalid value for yes/no: " + value);
            System.exit(0);
        }
        return result;
    }
}
