import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class Premium implements Subscription {
    Map<Criteria, Object> criteria;
    String premiumUserDataFile;
    Map<String, Person> premiumUsers;

    Premium(String premiumUserDataFile) {
        this.premiumUserDataFile = premiumUserDataFile;
        this.premiumUsers = parsePremiumUsers(this.premiumUserDataFile);
        this.criteria = new HashMap<>();
    }

    @Override
    public DreamPet getUserInput(Set<String> availableBreeds, PetType petType) {
        criteria.put(Criteria.TYPE, petType);
        String breed = getUserBreed(availableBreeds);
        if(!breed.equals("NA")) criteria.put(Criteria.BREED, breed);
        Purebred purebred = getUserPurebred();
        if (!purebred.equals(Purebred.NA)) criteria.put(Criteria.PUREBRED, purebred);
        if(petType.equals(PetType.CAT) || petType.equals(PetType.GUINEA_PIG)) criteria.put(Criteria.HAIR,getUserHairType());
        Sex sex = getUserSex();
        if(!sex.equals(Sex.NA)) criteria.put(Criteria.SEX,sex);
        criteria.put(Criteria.DE_SEXED,getUserDesexed());
        double[] ageRange = getUserAgeRange();
        double[] feeRange = getUserFeeRange();
        return new DreamPet(criteria,ageRange[0],ageRange[1],feeRange[0], feeRange[1]);
    }

    @Override
    public Pet displayResults(ArrayList<Pet> pets, Criteria[] criteria) {
        return null;
    }

    @Override
    public void placeAdoptionRequest(Pet pet) {

    }

    private static HashMap<String, Person> parsePremiumUsers(String fileName) {
        Path filePath = Path.of(fileName);
        List<String> userData = null;

        try {
            userData = Files.readAllLines(filePath);
        } catch (IOException io) {
            System.out.println("Could not load the file. \nError message: " + io.getMessage());
            System.exit(0);
        }

        HashMap<String, Person> users = new HashMap<>();
        for (String line : userData) {
            List<String> fields = List.of(line.split(","));
            String email = fields.get(0);
            String name = fields.get(1);
            String phoneNum = fields.get(2);
            users.put(email, new Person(name, phoneNum, email));
        }

        return users;
    }


}
