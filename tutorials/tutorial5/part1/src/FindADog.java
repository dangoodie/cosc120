/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindADog {

    //fields
    private final static String appName = "Pinkman's Pets Dog Finder";
    private final static String filePath = "./allDogs.txt";
    private final static String iconPath = "./icon.jpg";

    private static final ImageIcon icon = new ImageIcon(iconPath);
    private static AllDogs allDogs;

    /**
     * main method used to allow the user to search Pinkman's database of dogs, and place an adoption request
     * @param args none required
     */
    public static void main(String[] args) {
        allDogs = loadDogs();

        JOptionPane.showMessageDialog(null, "Welcome to Pinkman's Pets Dog Finder!\n\tTo start, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
        DreamDog dogCriteria = getUserCriteria();
        List<Dog> potentialMatches = allDogs.findMatch(dogCriteria);
        if(potentialMatches.size()>0){
            Map<String,Dog> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following dogs meet your criteria: \n");
            for (Dog potentialMatch : potentialMatches) {
                infoToShow.append(potentialMatch.getName()).append(" (").append(potentialMatch.getMicrochipNumber()).append(") is a ").append(potentialMatch.getAge()).append(" year old ").append(potentialMatch.getDreamDog().getSex()).append(" ").append(potentialMatch.getDreamDog().getBreed()).append(". De-sexed: ").append(potentialMatch.getDreamDog().isDeSexed()).append("\n");
                options.put(potentialMatch.getName() + " (" + potentialMatch.getMicrochipNumber() + ")", potentialMatch);
            }
            String adopt = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) dog you'd like to adopt:","Pinkman's Pets Dog Finder", JOptionPane.QUESTION_MESSAGE,null,options.keySet().toArray(), "");
            if(adopt==null) System.exit(0);
            else{
                Dog chosenDog = options.get(adopt);
                Person applicant = getUserDetails();
                writeAdoptionRequestToFile(applicant, chosenDog);
                JOptionPane.showMessageDialog(null, "Thank you! Your adoption request has been submitted. " +
                        "One of our friendly staff will be in touch shortly.", appName, JOptionPane.QUESTION_MESSAGE, icon);
            }
        } else JOptionPane.showMessageDialog(null, "Unfortunately none of our pooches meet your criteria :(" +
                "\n\tTo exit, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
        System.exit(0);
    }

    /**
     * method to get user to input name, ph num and email, with appropriate input validation
     * @return a Person object representing the user of the program
     */
    private static Person getUserDetails(){
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
            if(name==null) System.exit(0);
        } while(!isValidFullName(name));

        String phoneNumber;
        do{
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));

        String email;
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
            if (email == null) System.exit(0);
        }while(!isValidEmail(email));

        return new Person(name, phoneNumber, email);
    }

    /**
     * a very simple regex for full name in Firstname Surname format
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry starts with a 0 and is followed by 9 digits
     * @param phoneNumber the candidate phone number entered by the user
     * @return true if phone number matches regex/false if not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * a regex matcher that ensures that the user's entry complies with RFC 5322
     * source: <a href="https://www.baeldung.com/java-email-validation-regex">...</a>
     * @param email the candidate email entered by the user
     * @return true if email matches regex/false if not
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * generates JOptionPanes requesting user input for dog breed, sex, de-sexed status and age
     * @return a Dog object representing the user's desired dog criteria
     */
    private static DreamDog getUserCriteria(){
        String breed  = (String) JOptionPane.showInputDialog(null,"Please select your preferred breed.",appName, JOptionPane.QUESTION_MESSAGE,icon,allDogs.getAllBreeds().toArray(), "");
        if(breed==null) System.exit(0);

        Sex sex = (Sex) JOptionPane.showInputDialog(null,"Please select your preferred sex:",appName, JOptionPane.QUESTION_MESSAGE,icon,Sex.values(),Sex.FEMALE);
        if(sex==null) System.exit(0);

        DeSexed deSexed = (DeSexed) JOptionPane.showInputDialog(null,"Would you like your dog to be de-sexed or not?",appName, JOptionPane.QUESTION_MESSAGE,icon,DeSexed.values(),DeSexed.YES);
        if(deSexed==null) System.exit(0);

        int minAge = -1, maxAge = -1;
        while(minAge==-1) {
            try {
                minAge = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the age (years) of the youngest dog you'd like to adopt ",appName,JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.");
            }
        }
        while(maxAge<minAge) {
            try {
                maxAge = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the age (years) of the oldest dog you'd be willing to adopt ",appName,JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.");
            }
            if(maxAge<minAge) JOptionPane.showMessageDialog(null,"Max age must be >= min age.");
        }
        DreamDog dogCriteria = new DreamDog(minAge,maxAge,breed,sex,deSexed);

        return dogCriteria;
    }

    /**
     * method to load all dog data from file, storing it as Dog objects in an instance of AllDogs
     * @return an AllDogs object - functions as database of Dogs, with associated methods
     */
    private static AllDogs loadDogs() {
        AllDogs allDogs = new AllDogs();
        Path path = Path.of(filePath);

        List<String> dogData = null;
        try{
            dogData = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("Could not load the file. \nError message: "+io.getMessage());
            System.exit(0);
        }

        for (int i=1;i<dogData.size();i++) {
            String[] elements = dogData.get(i).split(",");
            String name = elements[0];
            long microchipNumber = 0;
            try{
                microchipNumber = Long.parseLong(elements[1]);
            }
            catch (NumberFormatException n){
                System.out.println("Error in file. Microchip number could not be parsed for dog on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            Sex sex = Sex.valueOf(elements[2].toUpperCase());
            DeSexed deSexed = DeSexed.valueOf(elements[3].toUpperCase());

            int age = 0;
            try{
                age = Integer.parseInt(elements[4]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for dog on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }
            String breed = elements[5].toLowerCase();

            Dog dog = new Dog(name, microchipNumber,age, breed, sex, deSexed);
            allDogs.addDog(dog);
        }
        return allDogs;
    }

    /**
     * provides Pinkman's Pets with a file containing the user's adoption request
     * @param person a Person object representing the user
     * @param dog a Dog object representing the dog that the user wants to adopt
     */
    private static void writeAdoptionRequestToFile(Person person, Dog dog) {
        String filePath = person.name().replace(" ","_")+"_adoption_request.txt";
        Path path = Path.of(filePath);
        String lineToWrite = person.name()+" wishes to adopt "+dog.getName()+" ("+dog.getMicrochipNumber()+
                "). Their phone number is 0"+person.phoneNumber()+" and their email address is "+person.emailAddress();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

}
