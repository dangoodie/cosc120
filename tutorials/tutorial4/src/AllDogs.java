import java.util.*;

/**
 * A class to store all the dogs in the shelter
 */
public class AllDogs {
    ArrayList<Dog> dogs = new ArrayList<Dog>();

    /**
     * Method to add a dog to the list of dogs
     * @param dog a Dog object
     */
    public void addDog(Dog dog) {
        dogs.add(dog);
    }

    /**
     * Method to remove a dog from the list of dogs
     * @param dog a Dog object
     */
    public void removeDog(Dog dog) {
        dogs.remove(dog);
    }

    /**
     * Method to search for a dog in the list of dogs
     * @param dog a Dog object
     * @return a Dog object
     */
    public Map<Integer, Dog> searchDogs(Dog dog) {
        Map<Integer, Dog> foundDogs = new HashMap<>();
        for (Dog d : dogs) {
            if (d.isSameBreed(dog) && d.isSameSex(dog) && d.isSameDesexedStatus(dog) && d.isAgeInRange(dog.getMinAge(), dog.getMaxAge())) {
                foundDogs.put(d.getMicrochipNumber(), d);
            }
        }
        if (foundDogs.isEmpty()) {
            return null;
        } else {
            return foundDogs;
        }
    }

    /**
     * Method to return the list of available dog breeds
     * @return an ArrayList of Dog objects
     */
    public Set<String> getAvailableBreeds() {
        Set<String> breeds = new HashSet<>();

        for (Dog d : dogs) {
            String[] breedName = d.getBreed().split(" ");
            StringBuilder capitalisedBreed = new StringBuilder();
            for (String word : breedName) {
                capitalisedBreed.append(capitaliseString(word));
                capitalisedBreed.append(" ");
            }
            breeds.add(capitalisedBreed.toString().trim());
        }
        return breeds;
    }

    private static String capitaliseString(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
