/**
 * @author Daniel Gooden (dan.gooden.dev@gmail.com)
 * created for COSC120 Tutorial 4
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * This class is used to guide the user through adopting a dog from a shelter database
 */
public class FindADog {

    public static void main(String[] args) {

        // load dog data from file
        AllDogs allDogs = loadDogsFromFile("allDogs.txt");
        Dog dreamDog = getUserSearchCriteria(allDogs.getAvailableBreeds());

        // find a dog that matches the user's dream dog
        Map<Integer, Dog> foundDogs = allDogs.searchDogs(dreamDog);

        if (foundDogs == null) {
            JOptionPane.showMessageDialog(null, "Sorry, no dogs match your criteria");
            System.exit(0);
        }

        // convert the Map to a Set for the JOptionPane
        Set <String> foundDogNamesAndNumbers = new HashSet<>();
        for (Dog dog : foundDogs.values()) {
            foundDogNamesAndNumbers.add(dog.getName() + " (" + dog.getMicrochipNumber() + ") is a " + dog.getAge() + " year old " + dog.getGender() + " " + dog.getBreed() + " dog. Desexed: " + dog.getDesexedStatus());
        }
        String selectedDog = null;
        try {
            selectedDog = (String) JOptionPane.showInputDialog(null, "Please select a dog to adopt: ", null, JOptionPane.QUESTION_MESSAGE, null, foundDogNamesAndNumbers.toArray(), foundDogNamesAndNumbers.toArray()[0]);
            if (selectedDog == null) {
                JOptionPane.showMessageDialog(null, "Thank you for using the FindADog program");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        // get the dog object from the selected dog microchip number
        String selectedDogNumber = selectedDog.substring(selectedDog.indexOf("(") + 1, selectedDog.indexOf(")"));
        Dog foundDog = foundDogs.get(Integer.parseInt(selectedDogNumber));

        // get the user's contact details
        Person newOwner = getUserContactDetails();
        writeAdoptionToFile(newOwner, foundDog);

        // display a message to the user to confirm their adoption request has been submitted
        JOptionPane.showMessageDialog(null, "Thank you! Your adoption request has been submitted. One of our friendly staff will be in touch shortly.");

        System.exit(0);
    }

    /**
     * A method to load dog data from a file
     * The key/value pair is microchip/dog
     *
     * @param fileName a String representing the name of the file to load the data from
     * @return a Map of Dog objects
     * @throws Exception if there is an error loading the data
     */
    public static AllDogs loadDogsFromFile(String fileName) {
        AllDogs allDogs = new AllDogs();
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
                Gender gender = Gender.valueOf(dogDetails[2].trim().toUpperCase());
                Desexed desexed = Desexed.valueOf(dogDetails[3].trim().toUpperCase());
                int age = Integer.parseInt(dogDetails[4]);
                String breed = dogDetails[5].trim();

                // create a new Dog object
                Dog newDog = new Dog(name, microchipNumber, breed, gender, desexed, age);
                allDogs.addDog(newDog);
            }

        } catch (Exception e) {
            System.out.println("Error loading dog data from file: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Dog data loaded successfully");
        return allDogs;
    }

    /**
     * A method to get user input for their preferred dog
     * @param availableBreeds a Set of Strings representing the available dog breeds
     * @return a Map of the user's adoption request details
     */
    // rebuild using Java Swing
    public static Dog getUserSearchCriteria(Set<String> availableBreeds) {
        String breed = (String) JOptionPane.showInputDialog(null, "Please select your preferred breed: ", null, JOptionPane.QUESTION_MESSAGE, null, availableBreeds.toArray(), availableBreeds.toArray()[0].toString());
        if (breed == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        Gender gender = (Gender) JOptionPane.showInputDialog(null, "Please select your preferred gender: ", null,JOptionPane.QUESTION_MESSAGE, null,Gender.values(), Gender.MALE);
        if (gender == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        Desexed desexed = (Desexed) JOptionPane.showInputDialog(null, "Would you like the dog to be desexed: ", null,JOptionPane.QUESTION_MESSAGE, null,Desexed.values(), Desexed.YES);
        if (desexed == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        int minAge = -1;
        do {
            String ageInput = JOptionPane.showInputDialog("What is the minimum age of the dog are you looking for? (0 to 20)");
            if (ageInput == null) {
                System.out.println("User cancelled the adoption");
                System.exit(0);
            }
            try {
                minAge = Integer.parseInt(ageInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid age");
            }
        } while (minAge < 0 || minAge > 20);

        int maxAge = -1;
        do {
            String ageInput = JOptionPane.showInputDialog("What is the maximum age of the dog are you looking for? (0 to 20)");
            if (ageInput == null) {
                System.out.println("User cancelled the adoption");
                System.exit(0);
            }
            try {
                maxAge = Integer.parseInt(ageInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid age");
            }
            if (maxAge < minAge) {
                JOptionPane.showMessageDialog(null, "The maximum age must be greater than or equal to the minimum age");
                maxAge = -1;
            }

        } while (maxAge < 0 || maxAge > 20);

        Dog dreamDog = new Dog("", -1, breed, gender, desexed, -1);
        dreamDog.setMinAge(minAge);
        dreamDog.setMaxAge(maxAge);

        return dreamDog;
    }

    /**
     * A method to get user input for their contact details
     * @return a Person object of the user's contact details
     */

    public static Person getUserContactDetails() {
        String name = JOptionPane.showInputDialog("What is your name?");
        if (name == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }
        String phoneNumber;
        do {
            phoneNumber = JOptionPane.showInputDialog("What is your phone number?");
            if (phoneNumber == null) {
                System.out.println("User cancelled the adoption");
                System.exit(0);
            }
            if (!isValidPhoneNumber(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number");
            }
        } while (!isValidPhoneNumber(phoneNumber));
        String emailAddress;
        do {
            emailAddress = JOptionPane.showInputDialog("What is your email address?");
            if (emailAddress == null) {
                System.out.println("User cancelled the adoption");
                System.exit(0);
            }
            if (!isValidEmail(emailAddress)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address");
            }
        } while (!isValidEmail(emailAddress));

        return new Person(name, phoneNumber, emailAddress);
    }

    /**
     * A method to write the adoption request to a file
     * @param newOwner a Person object representing the new owner
     * @param dog      a Dog object representing the dog being adopted
     */
    public static void writeAdoptionToFile(Person newOwner, Dog dog) {
        try {
            // code to write the adoption request to a file
            String fileName = newOwner.getFirstName() + "_" + newOwner.getLastName() + "_adoption_request.txt";

            Path path = Path.of(fileName);
            String adoptionRequest = newOwner.getName() + " wishes to adopt " + dog.getName() + " (" + dog.getMicrochipNumber() + "). Their phone number is " + newOwner.getPhoneNumber() + " and their email address is " + newOwner.getEmail();
            Files.writeString(path, adoptionRequest);
        } catch (Exception e) {
            System.out.println("Error writing adoption request to file: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Adoption request written to file successfully");
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

    /**
     * A method to validate an email address
     * @param email a String representing the email address
     * @return a boolean indicating whether the email address is valid
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

