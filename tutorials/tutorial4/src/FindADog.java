/**
 * @author Daniel Gooden (dan.gooden.dev@gmail.com)
 * created for COSC120 Tutorial 4
 */

import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;

/**
 * This class is used to guide the user through adopting a dog from a shelter database
 */
public class FindADog {

    public static void main(String[] args) {

        // load dog data from file
        AllDogs allDogs = loadDogsFromFile("allDogs.txt");
        Dog dreamDog = getUserSearchCriteria();

        // find a dog that matches the user's dream dog
        Dog foundDog = allDogs.searchDog(dreamDog);

        if (foundDog == null) {
            JOptionPane.showMessageDialog(null, "Sorry, no dogs match your criteria");
            System.exit(0);
        }

        boolean adopt = yesNoBoolean(JOptionPane.showInputDialog("Would you like to adopt " + foundDog.getName() + " (" + foundDog.getMicrochipNumber() + ")?"));
        if (!adopt) {
            JOptionPane.showMessageDialog(null, "Thank you for using the FindADog program");
            System.exit(0);
        }

        Person newOwner = getUserContactDetails();
        writeAdoptionToFile(newOwner, foundDog);

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
                String sex = dogDetails[2].trim();
                boolean desexed = yesNoBoolean(dogDetails[3]);
                int age = Integer.parseInt(dogDetails[4]);
                String breed = dogDetails[5].trim();

                // create a new Dog object
                Dog newDog = new Dog(name, microchipNumber, breed, sex, desexed, age);
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
     *
     * @return a Map of the user's adoption request details
     */
    // rebuild using Java Swing
    public static Dog getUserSearchCriteria() {
        String breed = null;
        String sex = null;
        boolean desexed = false;
        int age = 0;

        try {
            breed = JOptionPane.showInputDialog("What breed of dog are you looking for?");
            sex = JOptionPane.showInputDialog("Do you want a male or female dog?");
            desexed = yesNoBoolean(JOptionPane.showInputDialog("Do you want a desexed dog? (yes/no)"));
            age = Integer.parseInt(JOptionPane.showInputDialog("What age do you want the dog to be?"));

        } catch (NullPointerException e) {
            System.out.println("User cancelled the search");
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Invalid age entered");
            System.exit(0);
        }
        return new Dog("", -1, breed, sex, desexed, age);
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

        String phoneNumber = JOptionPane.showInputDialog("What is your phone number?");
        if (phoneNumber == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        String emailAddress = JOptionPane.showInputDialog("What is your email address?");
        if (emailAddress == null) {
            System.out.println("User cancelled the adoption");
            System.exit(0);
        }

        return new Person(name, phoneNumber, emailAddress);
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
}
