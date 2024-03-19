import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class is used to create an adoption request object
 */
public class FindADog {

    public static void main(String[] args) {
        FindADog adoptionRequest = new FindADog();
        Map<Integer, Dog> dogMap = adoptionRequest.loadDogData("allDogs.txt");

        // update desexed status for a dog
        Dog dog = dogMap.get(989343556);
        dog.setDesexedStatus(true);

        // get user input
        Map<String, String> userRequest = adoptionRequest.getUserInput();

        // find dogs with matching breed
        Map<Integer, Dog> matchingBreed = adoptionRequest.findMatchingBreed(dogMap, userRequest);
        System.out.println("Dogs with matching breed: ");
        adoptionRequest.printDogDetails(matchingBreed);

        // find dogs with matching breed in the age range
        Map<Integer, Dog> matchingAge = adoptionRequest.findMatchingAge(matchingBreed, userRequest);
        System.out.println("Dogs with matching breed and age: ");
        adoptionRequest.printDogDetails(matchingAge);

        // find dogs with matching breed, age, and sex
        Map<Integer, Dog> matchingSex = adoptionRequest.findMatchingSex(matchingAge, userRequest);
        System.out.println("Dogs with matching breed, age, and sex: ");
        adoptionRequest.printDogDetails(matchingSex);

        // find dogs with matching breed, age, sex, and desexed status
        Map<Integer, Dog> matchingDesexed = adoptionRequest.findMatchingDesexed(matchingSex, userRequest);
        System.out.println("Dogs with matching breed, age, sex, and desexed status: ");
        adoptionRequest.printDogDetails(matchingDesexed);

        System.exit(0);
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

            // iterate through the dog data and create a Dog object for each dog
            // skipping the headers
            for (int i = 1; i < dogData.length; i++) {
                // split the dog data into its components
                String[] dogDetails = dogData[i].split(",");

                // store the dog details in variables
                String name = dogDetails[0].trim();
                int microchipNumber = Integer.parseInt(dogDetails[1]);
                String sex = dogDetails[2].trim();
                boolean desexed = yesNoBoolean(dogDetails[3]);
                int age = Integer.parseInt(dogDetails[4]);
                String breed = dogDetails[5].trim();

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
        String desiredBreed = keyboard.nextLine().toLowerCase().trim();

        String desiredSex = "";
        while (!desiredSex.equals("male") && !desiredSex.equals("female")) {
            System.out.println("What is your preferred sex? (male/female): ");
            desiredSex = keyboard.nextLine();
            desiredSex = desiredSex.toLowerCase().trim();

            if (!desiredSex.equals("male") && !desiredSex.equals("female")) {
                System.out.println("Please enter a valid sex.");
            }

        }

        int minAge = -1;
        while (minAge < 0 || minAge > 20) {
            System.out.println("Minimum age in years (min 0 years): ");
            minAge = keyboard.nextInt();
            // capture keyboard return carriage
            keyboard.nextLine();
            if (minAge < 0 || minAge > 20) {
                System.out.println("Invalid value for minimum age: " + minAge);
            }

        }

        int maxAge = -1;
        while (maxAge < 0 || maxAge > 20 || minAge > maxAge) {
            System.out.println("Maximum age in years (max 20 years): ");
            maxAge = keyboard.nextInt();
            // capture keyboard return carriage
            keyboard.nextLine();
            if (maxAge < 0 || maxAge > 20) {
                System.out.println("Invalid value for maximum age: " + maxAge);
            }
            if (minAge > maxAge) {
                System.out.println("Maximum age must be greater than minimum age.");
            }
        }

        String desexed = "";

        while (!desexed.equals("yes") && !desexed.equals("no")) {
            System.out.println("Do you want the dog to be desexed? (yes/no): ");
            desexed = keyboard.nextLine();
            desexed = desexed.toLowerCase().trim();
            if (!desexed.equals("yes") && !desexed.equals("no")) {
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
     * A method to find dogs that match the user's breed preference
     * @param dogMap a Map of Dog objects
     * @param userRequest a Map of the user's adoption request details
     * @return a Set of Dog objects that match the user's breed preference
     */
    public Map<Integer, Dog> findMatchingBreed(Map<Integer, Dog> dogMap, Map<String, String> userRequest) {
        Map<Integer, Dog> matchingDogs = new HashMap<>();
        String desiredBreed = userRequest.get("desiredBreed");
        // create dummy dog object to access the isSameBreed method
        Dog dummyDog = new Dog("", 0, desiredBreed, "", false, 0);
        // iterate through the dog map and add dogs with matching breed to the set
        for (Dog dog : dogMap.values()) {
            if (dummyDog.isSameBreed(dog)) {
                matchingDogs.put(dog.getMicrochipNumber(), dog);
            }
        }
        return matchingDogs;
    }

    /**
     * A method to find dogs that match the user's sex preference
     * @param dogMap a Map of Dog objects
     * @param userRequest a Map of the user's adoption request details
     * @return a Set of Dog objects that match the user's sex preference
     */
    public Map<Integer, Dog> findMatchingSex(Map<Integer, Dog> dogMap, Map<String, String> userRequest) {
        Map<Integer, Dog> matchingDogs = new HashMap<>();
        String desiredSex = userRequest.get("desiredSex");
        // create dummy dog object to access the isSameSex method
        Dog dummyDog = new Dog("", 0, "", desiredSex, false, 0);
        // iterate through the dog map and add dogs with matching sex
        for (Dog dog : dogMap.values()) {
            if (dummyDog.isSameSex(dog)) {
                matchingDogs.put(dog.getMicrochipNumber(), dog);
            }
        }
        return matchingDogs;
    }

    /**
     * A method to find dogs that match the user's age preference
     * @param dogMap a Map of Dog objects
     * @param userRequest a Map of the user's adoption request details
     * @return a Set of Dog objects that match the user's age preference
     */
    public Map<Integer, Dog> findMatchingAge(Map<Integer, Dog> dogMap, Map<String, String> userRequest) {
        Map<Integer, Dog> matchingDogs = new HashMap<>();
        int minAge = Integer.parseInt(userRequest.get("minAge"));
        int maxAge = Integer.parseInt(userRequest.get("maxAge"));
        for (Dog dog : dogMap.values()) {
            if (dog.isAgeInRange(minAge, maxAge)) {
                matchingDogs.put(dog.getMicrochipNumber(), dog);
            }
        }
        return matchingDogs;
    }

    /**
     * A method to find dogs that match the user's desexed preference
     * @param dogMap a Map of Dog objects
     * @param userRequest a Map of the user's adoption request details
     * @return a Set of Dog objects that match the user's desexed preference
     */
    public Map<Integer, Dog> findMatchingDesexed(Map<Integer, Dog> dogMap, Map<String, String> userRequest) {
        Map<Integer, Dog> matchingDogs = new HashMap<>();
        boolean userValue = yesNoBoolean(userRequest.get("desexed"));
        for (Dog dog : dogMap.values()) {
            if (dog.getDesexedStatus() ==  userValue){
                matchingDogs.put(dog.getMicrochipNumber(), dog);
            }
        }
        return matchingDogs;
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

    /**
     * A method to print the details of a set of dogs
     * @param dogs a Set of Dog objects
     */
    private void printDogDetails(Map<Integer, Dog> dogs) {
        for (Dog dog : dogs.values()) {
            System.out.println(dog.getName() + " (" + dog.getMicrochipNumber() + ") is a " + dog.getAge() + " year old " + dog.getBreed() + " " + dog.getSex() + ". Desexed: " + dog.getDesexedStatus() + ".");
        }
        System.out.println();
    }
}
