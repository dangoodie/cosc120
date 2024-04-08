/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.*;

public class DreamGeek {
    //EDIT 5: Delete all but the minAge and maxAge fields
    //private final Gender gender;
    //private final StarSign starSign;
    private final int minAge;
    private final int maxAge;

    //EDIT 6: Add a Map field and Map parameters to the two constructors.
    private final Map<Criteria,Object> criteria;

    /**
     * This constructor is used to create a user's dream geek
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param criteria a Map containing the criteria used to compare DreamGeeks
     */
    //EDIT 6: Add Map parameters to the two constructors.
    public DreamGeek(int minAge, int maxAge, Map<Criteria,Object> criteria){
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria); //protect mutable objects
        this.minAge=minAge;
        this.maxAge=maxAge;
        //this.gender=gender;  EDIT 5
        //this.starSign=starSign;  EDIT 5
    }

    /**
     * This constructor is used to create the 'dream' aspects of real geeks
     * @param criteria a Map containing the criteria used to compare DreamGeeks
     */
    //EDIT 6: Add Map parameters to the two constructors.
    public DreamGeek(Map<Criteria,Object> criteria){
        if(criteria==null) this.criteria=new LinkedHashMap<>(); //preserve insertion order
        else this.criteria = new LinkedHashMap<>(criteria); //protect mutable objects
        //initialise fields with dummy values
        minAge=-1;
        maxAge=-1;
        //this.gender=gender; EDIT 5
        //this.starSign=starSign; EDIT 5
    }

    /**
     * used to get min age of a user's dream geek
     * @return the min age
     */
    public int getMinAge() {
        return minAge;
    }
    /**
     * used to get max age of a user's dream geek
     * @return the max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /* EDIT 5: delete the getters too.
    public Gender getGender(){
        return this.gender;
    }
    public StarSign getStarSign(){
        return this.starSign;
    }*/

    //EDIT 7: Create 2 new getters, one to get all the criteria

    /**
     * @return the entire map of criteria keys, and values
     */
    public Map<Criteria, Object> getAllCriteria() {
        return new HashMap<>(criteria);
    }

    // ...and the other to get the value of a criteria given the key.

    /**
     * @param key a Criteria (enum) constant representing a search criteria
     * @return a value stored in the map at a given key
     */
    public Object getCriteria(Criteria key){return getAllCriteria().get(key);}
    //end EDIT 7

    /*EDIT 8
    public String getDescription(){
        return this.getStarSign()+" "+ this.getGender();
    }*/

    //EDIT 15: new getDescription method - more verbose
    /**
     * @return a String describing all the 'dream' features of a geek
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        for(Criteria key: criteria.keySet()) description.append("\n").append(key).append(": ").append(getCriteria(key));
        return description.toString();
    }

    //EDIT 9: altered matches method that compares a user's Map to a real geek's Map
    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        //if (!this.getGender().equals(realGeek.getGender())) return false;
        //if (!this.getStarSign().equals(StarSign.NA)) return this.getStarSign().equals(realGeek.getStarSign());

        //iterate through all the real geek's features criteria
        for(Criteria key : realGeek.getAllCriteria().keySet()) {
            //if the user's map contains the same key as the real geek...
            if(this.getAllCriteria().containsKey(key)){ //if the user is interested in a feature
                //if the criteria is a collection, e.g. a Set of games or hobbies
                if(getCriteria(key) instanceof Collection<?>){
                    //check whether there is any overlap
                    Set<Object> intersect = new HashSet<>((Collection<?>) realGeek.getCriteria(key));
                    intersect.retainAll((Collection<?>) getCriteria(key));
                    //if there are no common elements, return false
                    if(intersect.size()==0) return false;
                }
                //if the criteria is not a collection, check for equality
                else if(!getCriteria(key).equals(realGeek.getCriteria(key))) return false;
            }
        }
        return true;
    }
}
