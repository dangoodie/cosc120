import java.util.*;

public class DreamDrink {
    private final int minPrice;
    private final int maxPrice;
    private final Map<Criteria,Object> criteria;

    public DreamDrink(int minPrice, int maxPrice, Map<Criteria,Object> criteria) {
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public DreamDrink(Map<Criteria,Object> criteria) {
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria);
        this.minPrice=-1;
        this.maxPrice=-1;
    }

    // getters
    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public Map<Criteria, Object> getAllCriteria() {
        return new HashMap<>(criteria);
    }

    public Object getCriteria(Criteria key){
        return getAllCriteria().get(key);
    }


    // methods
    public boolean matches(DreamDrink realDrink) {
        for(Criteria key : realDrink.getAllCriteria().keySet()) {
            //AS PREVIOUSLY DONE: if the real drink and user have the same key, compare the values
            if(this.getAllCriteria().containsKey(key)){
                //AS PREVIOUSLY DONE: if both values (at the same key) are collections, check if there's a subset (compatibility)
                //this is used to check if there's commonality between the user's dream drink and the real drink
                if(getCriteria(key) instanceof Collection<?> && realDrink.getCriteria(key) instanceof Collection<?>){
                    Set<Object> intersect = new HashSet<>((Collection<?>) realDrink.getCriteria(key));
                    intersect.retainAll((Collection<?>) getCriteria(key));
                    if(intersect.isEmpty()) return false; //if there's no subset, return false
                }
                //EDIT 14: if the real drink's value is a collection, but the user's value is not, check if the user's value is
                //contained in the real drink's collection
                else if(realDrink.getCriteria(key) instanceof Collection<?> && !(getCriteria(key) instanceof Collection<?>)){
                    if(!((Collection<?>) realDrink.getCriteria(key)).contains(getCriteria(key))) return false;
                }
                //SUGGESTION: you could add logic hear to see if a real drink's singular value is contained in a dream geek's collection value
                else if (getCriteria(key) instanceof Collection<?> && !(realDrink.getCriteria(key) instanceof Collection<?>)){
                    if(!((Collection<?>) getCriteria(key)).contains(realDrink.getCriteria(key))) return false;
                }
                //AS PREVIOUSLY DONE: finally, if they're both singular, check for equality
                else if(!getCriteria(key).equals(realDrink.getCriteria(key))) return false;
            }
        }
        return true;
    }
}
