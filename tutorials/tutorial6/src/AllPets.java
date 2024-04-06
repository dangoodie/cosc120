import java.util.*;

public class AllPets {

    private final List<Pet> allPets = new ArrayList<>();

    public void addPet(Pet pet){
        this.allPets.add(pet);
    }

    public List<Pet> getAllPets(){
        return allPets;
    }

    public Set<String> getAllBreeds(String type){
        Set<String> allBreeds = new HashSet<>();
        // if the user is looking for a cat
        if (type.equalsIgnoreCase("cat")) {
            for (Pet p : allPets) {
                if (p.getGenericFeatures() instanceof DreamCat) {
                    allBreeds.add(p.getGenericFeatures().getBreed());
                }
            }
        }
        // if the user is looking for a dog
        if (type.equalsIgnoreCase("dog")) {
            for (Pet p : allPets) {
                if (p.getGenericFeatures() instanceof DreamDog) {
                    allBreeds.add(p.getGenericFeatures().getBreed());
                }
            }
        }
        allBreeds.add("NA"); // add NA to the list of breeds
        return allBreeds;
    }

    public List<Pet> findMatch(DreamPet petCriteria) {
        List<Pet> compatiblePets = new ArrayList<>();
        for (Pet p : allPets) {
            if (p.getGenericFeatures().compareDreamPets(petCriteria) &&
                    p.getAge() >= petCriteria.getMinAge() &&
                    p.getAge() <= petCriteria.getMaxAge()){
                compatiblePets.add(p);
            }
        }
        return compatiblePets;
    }
}
