import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Subscription {
    String appName = "Pinkman's Pets Pet Finder";
    String iconPath = "./icon.jpg";
    ImageIcon icon = new ImageIcon(iconPath);

    double premiumSubscriptionFee = 14.99;
    double premiumDiscount = 0.1;
    double voucher = 100;

    DreamPet getUserInput(Set<String> availableBreeds, PetType petType);

    Pet displayResults(ArrayList<Pet> pets, Criteria[] criteria);

    void placeAdoptionRequest(Pet pet);

    default PetType getUserType() {
           PetType type = (PetType) JOptionPane.showInputDialog(null,"Please select the type of pet you'd like to adopt.",appName, JOptionPane.QUESTION_MESSAGE,icon,PetType.values(), PetType.DOG);                
           if(type==null) System.exit(0);
           return type;
    }

    default String getUserBreed(Set<String> allBreeds) {
        String breed  = (String) JOptionPane.showInputDialog(null,"Please select your preferred breed.",appName, JOptionPane.QUESTION_MESSAGE,icon,allBreeds.toArray(),"");
        if(breed==null) System.exit(0);
        return breed;
    }

    default Sex getUserSex() {
       Sex sex = (Sex) JOptionPane.showInputDialog(null,"Please select your preferred sex:",appName, JOptionPane.QUESTION_MESSAGE,icon,Sex.values(),Sex.FEMALE);
       if(sex==null) System.exit(0);
       return sex;
    }
    default DeSexed getUserDesexed() {
        DeSexed desexed = (DeSexed) JOptionPane.showInputDialog(null,"Would you like your Pet to be de-sexed or not?",appName, JOptionPane.QUESTION_MESSAGE,icon,DeSexed.values(),DeSexed.YES);
        if(desexed==null) System.exit(0);
        return desexed;
    }
    default Purebred getUserPurebred() { 
        Purebred purebred  = (Purebred) JOptionPane.showInputDialog(null,"Would you like the Pet to be a purebred?",appName, JOptionPane.QUESTION_MESSAGE,null,Purebred.values(), "");
        if(purebred==null) System.exit(0);
        return purebred;
    }

    /**
     * a method to get the user to select what hair type they want, e.g. short, long, hairless
     * @return Hair enum representing user's choice
     */
    default Hair getUserHairType() {
        Hair hair = (Hair) JOptionPane.showInputDialog(null, "Please select from the following options", "Pinkman's Pet Finder", JOptionPane.QUESTION_MESSAGE, null, Hair.values(), "");
        if (hair == null) System.exit(0);
        return hair;
    }
    
    default String getUserName() {
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
            if(name==null) System.exit(0);
        } while(!isValidFullName(name));
        return name;
    }

    default String getUserPhoneNumber() {
        String phoneNumber;
        do{
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    default String getUserEmail() {
        String email;
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
            if (email == null) System.exit(0);
        }while(!isValidEmail(email));
        return email;
    }

    default void writeAdoptionRequestToFile(Person person, Pet Pet) {
        String filePath = person.name().replace(" ","_")+"_adoption_request.txt";
        Path path = Path.of(filePath);
        String lineToWrite = person.name()+" wishes to adopt "+Pet.name()+" ("+Pet.microchipNumber()+"). Their phone number is "+person.phoneNumber()+" and their email address is "+person.emailAddress();
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

    /**
     * method to get user to select age range (min - max)
     * @return an int array where [0] is min age and [1] is max age
     */
    default double[] getUserAgeRange(){
        return minMaxValues("What is the age (years) of the youngest Pet you'd like to adopt?","What is the age (years) of the oldest Pet you'd be willing to adopt?");
    }

    /**
     * method to get user to select adoption fee range (min - max)
     * @return an int array where [0] is min fee and [1] is max fee
     */
    default double[] getUserFeeRange(){
        return minMaxValues("What is the lowest adoption fee you're interested in? ","What is the max. adoption fee you're willing to pay?");
    }

    /**
     * a method to get the user to enter a value range (min - max)
     * @param minMessage the message to the user asking them to input a min value
     * @param maxMessage the message to the user asking them to input a max value
     * @return an int[] array where [0] is min and [1] is max
     */
    private double[] minMaxValues(String minMessage, String maxMessage){ //private methods are allowed in interfaces since Java 9
        double[] range = {-1,-1};
        while(range[0]<0) {
            String input = JOptionPane.showInputDialog(null, minMessage, appName, JOptionPane.QUESTION_MESSAGE);
            if(input==null) System.exit(0);
            try {
                range[0] = Double.parseDouble(input);
                if(range[0]<0) JOptionPane.showMessageDialog(null,"Min. must be >= 0.",appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        while(range[1]<range[0]) {
            String input = JOptionPane.showInputDialog(null, maxMessage, appName, JOptionPane.QUESTION_MESSAGE);
            if(input==null) System.exit(0);
            try {
                range[1] = Double.parseDouble(input);
                if(range[1]<range[0]) JOptionPane.showMessageDialog(null,"Max must be >= "+range[0],appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return range;
    }

    /**
     * a very simple regex for full name in Firstname Surname format
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    private boolean isValidFullName(String fullName) {
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
    default boolean isValidPhoneNumber(String phoneNumber) {
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
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
