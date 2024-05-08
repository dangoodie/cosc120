import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class Premium implements Subscription {
    HashMap<Criteria, Object> userSelections;
    String premiumUserDataFile;
    HashMap<String, Person> premiumUsers;

    Premium(String premiumUserDataFile) {
        this.premiumUserDataFile = premiumUserDataFile;
        this.premiumUsers = parsePremiumUsers(this.premiumUserDataFile);
        this.userSelections = new HashMap<>();
    }

    @Override
    public DreamPet getUserInput(HashSet<String> availableBreeds, PetType petType) {
        return null;
    }

    @Override
    public Pet displayResults(ArrayList<Pet> pets, Criteria[] criteria) {
        return null;
    }

    @Override
    public void placeAdoptionRequest(Pet pet) {

    }

    private static HashMap<String, Person> parsePremiumUsers(String fileName) {
        Path filePath = Path.of(fileName)
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
