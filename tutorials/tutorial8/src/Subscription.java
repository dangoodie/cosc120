import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public interface Subscription {
    static final String appName = "Pinkman's Pets Pet Finder";
    static final String filePath = "./allPets.txt";
    static final String iconPath = "./icon.jpg";
    static final ImageIcon icon = new ImageIcon(iconPath);
    
    static final double premiumSubscriptionFee = 14.99;
    static final double premiumDiscount = 0.1;
    static final double voucher = 100;

    DreamPet getUserInput(HashSet<String> availableBreeds, PetType petType);

    Pet displayResults(ArrayList<Pet> pets, Criteria[] criteria);

    void placeAdoptionRequest(Pet pet);

    default PetType getUserType() {
           PetType type = (PetType) JOptionPane.showInputDialog(null,"Please select the type of pet you'd like to adopt.",appName, JOptionPane.QUESTION_MESSAGE,icon,PetType.values(), PetType.DOG);                
           if(type==null) System.exit(0);
           return type;
    }

    default Breed getUserBreed(Set<String> allBreeds) {
        String breed  = (String) JOptionPane.showInputDialog(null,"Please select your preferred breed.",appName, JOptionPane.QUESTION_MESSAGE,icon,allPets.getAllBreeds(type).toArray(),"");
        if(breed==null) System.exit(0);
        return breed;
    }

    default Sex getUserSex() {
       Sex sex = (Sex) JOptionPane.showInputDialog(null,"Please select your preferred sex:",appName, JOptionPane.QUESTION_MESSAGE,icon,Sex.values(),Sex.FEMALE);
       if(sex==null) System.exit(0);
       return sex;
    }
    default Desexed getUserDesexed() {   
        DeSexed deSexed = (DeSexed) JOptionPane.showInputDialog(null,"Would you like your Pet to be de-sexed or not?",appName, JOptionPane.QUESTION_MESSAGE,icon,DeSexed.values(),DeSexed.YES);
        if(deSexed==null) System.exit(0);
        return deSexed;
    }
    default Purebred getUserPurebred() { 
        Purebred purebred  = (Purebred) JOptionPane.showInputDialog(null,"Would you like the Pet to be a purebred?",appName, JOptionPane.QUESTION_MESSAGE,null,Purebred.values(), "");
        if(purebred==null) System.exit(0);
        return purebred;
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
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }
}
