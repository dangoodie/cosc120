/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashMap;
import java.util.Map;

public class DreamPet {

    private final Map<Criteria,Object> petCriteria;
    private final int minAge;
    private final int maxAge;

    /**
     * constructor used to create a user's conceptual 'dream pet', with an age range
     * @param petCriteria a Map representing the criteria used to compare pets
     * @param minAge lowest age user would be willing to adopt
     * @param maxAge highest age user would be willing to adopt
     */
    public DreamPet(Map<Criteria,Object> petCriteria,int minAge, int maxAge) {
        if(petCriteria==null) this.petCriteria=new HashMap<>();
        else this.petCriteria = new HashMap<>(petCriteria);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * create DreamPet object without age-range,representing real pet's generic features, read from file
     * @param petCriteria a Map representing the criteria used to compare pets
     */
    public DreamPet(Map<Criteria,Object> petCriteria) {
        if(petCriteria==null) this.petCriteria=new HashMap<>();
        else this.petCriteria = new HashMap<>(petCriteria);
        this.minAge=-1;
        this.maxAge=-1;
    }

    //getters
    /**
     * access all the key-value pairs, e.g. breed: jack russell, sex: female, etc.
     * @return the entire mapping of criteria and their values
     */
    public Map<Criteria, Object> getAllPetCriteriaAndValues() {
        return new HashMap<>(petCriteria);
    }

    /**
     * @param key a criteria, e.g. breed
     * @return the value at a specified criteria, e.g. jack russell
     */
    public Object getValueAtCriteria(Criteria key){
        return getAllPetCriteriaAndValues().get(key);
    }

    /**
     * @return a 'dream' Pet's min age
     */
    public double getMinAge() {
        return minAge;
    }
    /**
     * @return a 'dream' Pet's max age
     */
    public double getMaxAge() {
        return maxAge;
    }

    /**
     * method to return a description of generic DreamPet features
     * @return a String description of the dream pet criteria
     */
    public String getDreamPetDescription(Criteria[] criteria){
        StringBuilder description= new StringBuilder();
        for(Criteria key: criteria) description.append("\n").append(key).append(": ").append(getValueAtCriteria(key));
        return description.toString();
    }

    /**
     * method to compare two DreamPet objects against each other for compatibility
     * @param petCriteria an imaginary pet representing the user's criteria
     * @return true if matches, false if not
     */
    public boolean compareDreamPets(DreamPet petCriteria) {
        for(Criteria key : petCriteria.getAllPetCriteriaAndValues().keySet()) {
            if(!getValueAtCriteria(key).equals(petCriteria.getValueAtCriteria(key))) return false;
        }
        return true;
    }

}

