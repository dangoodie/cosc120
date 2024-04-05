/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllDogs {
    //fields
    private final List<Dog> allDogs = new ArrayList<>();

    //default constructor used, therefore no need to declare it

    //methods
    /**
     * method to add a Dog object to the database (allDogs)
     * @param dog a Dog object
     */
    public void addDog(Dog dog){
        this.allDogs.add(dog);
    }

    /**
     * a method to return a set of all breeds in the dataset (no duplicates)
     * @return Set</String> of available breeds
     */
    public Set<String> getAllBreeds(){
        Set<String> allBreeds = new HashSet<>();
        for(Dog d: allDogs){
            allBreeds.add(d.getDreamDog().getBreed());
        }
        return allBreeds;
    }

    /**
     * returns a collection of Dog objects that meet all the user's requirements
     * @param dogCriteria a Dog object representing a user's preferred Dog
     * @return a Dog object
     */
     public List<Dog> findMatch(DreamDog dogCriteria) {
         List<Dog> compatibleDogs = new ArrayList<>();
            for (Dog d : allDogs) {
                if (d.getDreamDog().compareDreamDogs(dogCriteria) &&
                        d.getAge() >= dogCriteria.getMinAge() &&
                        d.getAge() <= dogCriteria.getMaxAge()) {
                    compatibleDogs.add(d);
                }
         }
         return compatibleDogs;
     }
}
