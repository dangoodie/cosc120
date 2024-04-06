import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAPet {
    //fields
    private final static String appName = "Pinkman's Pets Pet Finder";
    private final static String filePath = "./allPets.txt";
    private final static String iconPath = "./icon.jpg";

    private static final ImageIcon icon = new ImageIcon(iconPath);
    private static AllPets allPets = new AllPets();

    public static void main(String[] args) {
        // load all pets
        allPets = loadPets();

        JOptionPane.showMessageDialog(null, "Welcome to Pinkman's Pets Pet Finder!\n\tTo start, click OK.", appName, JOptionPane.QUESTION_MESSAGE, icon);
        String type = (String) JOptionPane.showInputDialog(null, "Would you like to find a cat or a dog?", appName, JOptionPane.QUESTION_MESSAGE, icon, new String[]{"Cat", "Dog"}, null);
        if (type == null) System.exit(0);

        DreamPet dreamPet = getUserCriteria(type);
        Pet pet = processSearchResults(dreamPet);
        if (pet == null) {
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.", appName, JOptionPane.ERROR_MESSAGE, icon);
            System.exit(1);
        }

        Person person = getUserDetails();
        writeAdoptionRequestToFile(person, pet);
        JOptionPane.showMessageDialog(null, "Thank you for using Pinkman's Pets Pet Finder!\n\tYour adoption request has been saved.", appName, JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    /**
     * method to load all pets from the file
     * @return an AllPets object containing all pets
     */
    private static AllPets loadPets() {
        Path path = Paths.get(filePath);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (int i = 1; i < lines.size(); i++) {
            String[] petDetails = lines.get(i).split(",");

            String type = petDetails[0];
            if (type.equalsIgnoreCase("cat")) {
                String name = petDetails[1];
                long microchipNumber = Long.parseLong(petDetails[2]);
                Sex sex = Sex.fromString(petDetails[3]);
                DeSexed deSexed = DeSexed.fromString(petDetails[4]);
                int age = Integer.parseInt(petDetails[5]);
                String breed = petDetails[6].toLowerCase().trim();
                Purebred purebred = Purebred.fromString(petDetails[7]);
                Double adoptionFee = Double.parseDouble(petDetails[8]);
                Hair hairless = Hair.fromString(petDetails[9]);

                DreamCat cat = new DreamCat(age, age, breed, sex, deSexed, purebred, hairless);
                Pet pet = new Pet(name, microchipNumber, age, adoptionFee, cat);
                allPets.addPet(pet);
            }

            if (type.equalsIgnoreCase("dog")) {
                String name = petDetails[1];
                long microchipNumber = Long.parseLong(petDetails[2]);
                Sex sex = Sex.fromString(petDetails[3]);
                DeSexed deSexed = DeSexed.fromString(petDetails[4]);
                int age = Integer.parseInt(petDetails[5]);
                String breed = petDetails[6].trim().toLowerCase();
                Purebred purebred = Purebred.fromString(petDetails[7]);
                Double adoptionFee = Double.parseDouble(petDetails[8]);
                Training trainingLevel = Training.fromString(petDetails[10]);
                int dailyExercise = Integer.parseInt(petDetails[11]);

                DreamDog dog = new DreamDog(age, age, breed, sex, deSexed, purebred, trainingLevel, dailyExercise);
                Pet pet = new Pet(name, microchipNumber, age, adoptionFee, dog);
                allPets.addPet(pet);
            }
        }
        return allPets;
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
     * method to get user to input their dream pet, with appropriate input validation
     * @return a DreamPet object representing the user's dream pet
     */
    public static DreamPet getUserCriteria(String type) {
        String breed = (String) JOptionPane.showInputDialog(null, "Please select a breed", appName, JOptionPane.QUESTION_MESSAGE, icon, allPets.getAllBreeds(type).toArray(), null);
        if (breed == null) System.exit(0);

        Sex sex = (Sex) JOptionPane.showInputDialog(null, "Please select a gender", appName, JOptionPane.QUESTION_MESSAGE, icon, Sex.values(), null);
        if (sex == null) System.exit(0);

        DeSexed deSexed = (DeSexed) JOptionPane.showInputDialog(null, "Would you like a de-sexed " + type + "?", appName, JOptionPane.QUESTION_MESSAGE, icon, DeSexed.values(), null);
        if (deSexed == null) System.exit(0);

        Purebred purebred = (Purebred) JOptionPane.showInputDialog(null, "Would you like a purebred?", appName, JOptionPane.QUESTION_MESSAGE, icon, Purebred.values(), null);
        if (purebred == null) System.exit(0);

        int minAge = -1, maxAge = -1;
        while (minAge == -1) {
            try {
                minAge = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the minimum age", appName, JOptionPane.QUESTION_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", appName, JOptionPane.ERROR_MESSAGE, icon);
            }
        }
        while (maxAge == -1) {
            try {
                maxAge = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the maximum age", appName, JOptionPane.QUESTION_MESSAGE));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number", appName, JOptionPane.ERROR_MESSAGE, icon);
            }
        }

        DreamPet dreamPet = null;

        if (type.equalsIgnoreCase("cat")) {
            Hair hairless = (Hair) JOptionPane.showInputDialog(null, "Would you like a hairless cat?", appName, JOptionPane.QUESTION_MESSAGE, icon, Hair.values(), null);
                if (hairless == null) System.exit(0);
                dreamPet = new DreamCat(minAge, maxAge, breed, sex, deSexed, purebred, hairless);
        }

        if (type.equalsIgnoreCase("dog")) {
            dreamPet = new DreamDog(minAge, maxAge, breed, sex, deSexed, purebred, null, 0);
        }

        return dreamPet;

    }

    public static Pet processSearchResults(DreamPet dreamPet) {
        List<Pet> compatiblePets = allPets.findMatch(dreamPet);
        if (compatiblePets.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sorry, no pets match your criteria.", appName, JOptionPane.INFORMATION_MESSAGE, icon);
            System.exit(0);
        }

        String messageText = "Matches found! The following pets match your criteria:\n\n";
        for (Pet p : compatiblePets) {
            messageText += p.getPetDescription() + "\n";
        }

        Set<String> petDropdown = new HashSet<>();
        for (Pet p : compatiblePets) {
            String selection = p.getName() + " (" + p.getMicrochipNumber() + ")";
            petDropdown.add(selection);
        }

        String selection = (String) JOptionPane.showInputDialog(null, messageText, appName, JOptionPane.QUESTION_MESSAGE, icon, petDropdown.toArray(), null);
        if (selection == null) System.exit(0);

        long microchipNumber = Long.parseLong(selection.substring(selection.indexOf("(") + 1, selection.indexOf(")")));

        for (Pet p : compatiblePets) {
            if (p.getMicrochipNumber() == microchipNumber) {
                return p;
            }
        }
        return null;
    }

    public static void writeAdoptionRequestToFile(Person person, Pet pet) {
        String adoptionRequest = person.getName() + "," + person.getPhoneNumber() + "," + person.getEmailAddress() + "," + pet.getName() + "," + pet.getMicrochipNumber();
        String name = person.getName().trim().replace(" ", "_");
        String fileName = "./" + name + "_adoption_requests.txt";
        try {
            Files.writeString(Paths.get(fileName), adoptionRequest);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
