/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.*;

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
    public String getDescription(Set<Criteria> criteriaToDisplay){ //EDIT 32 - add parameter
        StringBuilder description = new StringBuilder();
        //EDIT 32 - only show values of criteria in the new parameter (instead of all data)
        for(Criteria key: criteriaToDisplay){
            //EDIT 28: check whether the value at each key is NA. Only append it to the description if it isn’t NA.
            Object value = getCriteria(key);
            if(!value.toString().equalsIgnoreCase("NA"))
                description.append("\n").append(key).append(": ").append(value);
        }
        return description.toString();
    }

    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        for(Criteria key : realGeek.getAllCriteria().keySet()) {
            if(this.getAllCriteria().containsKey(key)){
                if(getCriteria(key) instanceof Collection<?> && realGeek.getCriteria(key) instanceof Collection<?>){
                    Set<Object> intersect = new HashSet<>((Collection<?>) realGeek.getCriteria(key));
                    intersect.retainAll((Collection<?>) getCriteria(key));
                    if(intersect.size()==0) return false;
                }
                else if(realGeek.getCriteria(key) instanceof Collection<?> && !(getCriteria(key) instanceof Collection<?>)){
                    if(!((Collection<?>) realGeek.getCriteria(key)).contains(getCriteria(key))) return false;
                }
                else if(!getCriteria(key).equals(realGeek.getCriteria(key))) return false;
            }
        }
        return true;
    }
}
