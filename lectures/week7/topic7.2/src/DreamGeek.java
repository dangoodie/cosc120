import java.util.*;

/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class DreamGeek {
    private final int minAge;
    private final int maxAge;
    private final Map<Criteria,Object> criteria;

    /**
     * This constructor is used to create a user's dream geek
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param criteria a Map containing the criteria used to compare DreamGeeks
     */
    public DreamGeek(int minAge, int maxAge, Map<Criteria,Object> criteria){
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria);
        this.minAge=minAge;
        this.maxAge=maxAge;
    }

    /**
     * This constructor is used to create the 'dream' aspects of real geeks
     * @param criteria a Map containing the criteria used to compare DreamGeeks
     */
    public DreamGeek(Map<Criteria,Object> criteria){
        if(criteria==null) this.criteria=new LinkedHashMap<>();
        else this.criteria = new LinkedHashMap<>(criteria);
        minAge=-1;
        maxAge=-1;
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

    /**
     * @return the entire map of criteria keys, and values
     */
    public Map<Criteria, Object> getAllCriteria() {
        return new HashMap<>(criteria);
    }

    /**
     * @param key a Criteria (enum) constant representing a search criteria
     * @return a value stored in the map at a given key
     */
    public Object getCriteria(Criteria key){return getAllCriteria().get(key);}

    /**
     * @return a String describing all the 'dream' features of a geek
     */
    public String getDescription(){
        StringBuilder description = new StringBuilder();
        for(Criteria key: criteria.keySet()) description.append("\n").append(key).append(": ").append(getCriteria(key));
        return description.toString();
    }

    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        for(Criteria key : realGeek.getAllCriteria().keySet()) {
            //AS PREVIOUSLY DONE: if the real geek and user have the same key, compare the values
            if(this.getAllCriteria().containsKey(key)){
                //AS PREVIOUSLY DONE: if both values (at the same key) are collections, check if there's a subset (compatibility)
                //this is used to check if there's commonality in tv shows, games and hobbies
                if(getCriteria(key) instanceof Collection<?> && realGeek.getCriteria(key) instanceof Collection<?>){
                    Set<Object> intersect = new HashSet<>((Collection<?>) realGeek.getCriteria(key));
                    intersect.retainAll((Collection<?>) getCriteria(key));
                    if(intersect.size()==0) return false; //if there's no subset, return false
                }
                //EDIT 14: if the real geek's value is a collection, but the user's value is not, check if the user's value is
                //contained in the real geek's collection
                else if(realGeek.getCriteria(key) instanceof Collection<?> && !(getCriteria(key) instanceof Collection<?>)){
                    if(!((Collection<?>) realGeek.getCriteria(key)).contains(getCriteria(key))) return false;
                }
                //SUGGESTION: you could add logic hear to see if a real geek's singular value is contained in a dream geek's collection value
                //AS PREVIOUSLY DONE: finally, if they're both singular, check for equality
                else if(!getCriteria(key).equals(realGeek.getCriteria(key))) return false;
            }
        }
        return true;
    }
}
