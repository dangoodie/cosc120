import javax.lang.model.type.PrimitiveType;
import java.util.*;

/**
 * A class that represents a dream drink object, which contains a set of criteria.
 * and a min and max price which is used to filter drinks.
 */

public class DreamDrink {
    private final int minPrice;
    private final int maxPrice;
    private final Map<Criteria,Object> criteria;

    // constructors

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

    /**
     * @return the minimum price of the dream drink
     */
    public int getMinPrice() {
        return minPrice;
    }

    /**
     * @return the maximum price of the dream drink
     */
    public int getMaxPrice() {
        return maxPrice;
    }

    /**
     * @return a map of all the criteria of the dream drink
     */
    public Map<Criteria, Object> getAllCriteria() {
        return new HashMap<>(criteria);
    }

    /**
     * Returns the value of the criteria with the given key.
     * @param key the key of the criteria
     * @return the value of the criteria with the given key
     */
    public Object getCriteria(Criteria key){
        return getAllCriteria().get(key);
    }

    /**
     * Builds a string representation of the dream drink.
     * For use in the GUI.
     * @return a string representation of the dream drink
     */
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        for(Criteria key : criteria.keySet()) {
            Object items = criteria.get(key);
            if(items instanceof Collection<?>) { // if the value is a collection
                sb.append(key).append(": ");
                for(Object item : (Collection<?>) items) {
                    sb.append(item).append(", ");
                }
                sb.delete(sb.length()-2, sb.length());
                sb.append("\n");
            } else if (items instanceof Boolean) {
                sb.append(key).append(": ");
                if((Boolean) items) sb.append("Yes\n");
                else sb.append("No\n");
            } else sb.append(key).append(": ").append(criteria.get(key)).append("\n"); // if the value is not a collection
        }
        return sb.toString();
    }



    // methods
    /**
     * Checks if the dream drink matches the given drink.
     * This method is modified from matches in UNE COSC120 Topic 7.2 Dream Geek
     * @param realDrink the drink to check
     * @return true if the drink matches the dream drink, false otherwise
     */
    public boolean matches(DreamDrink realDrink) {
       for (Criteria key : realDrink.getAllCriteria().keySet()) {
           if(this.getAllCriteria().containsKey(key)) {
               if(getCriteria(key) instanceof Collection<?> && realDrink.getCriteria(key) instanceof Collection<?>){ // if both values are collections
                   Set<Object> intersect = new HashSet<>((Collection<?>) realDrink.getCriteria(key));
                     intersect.retainAll((Collection<?>) getCriteria(key));
                        if(intersect.isEmpty()) return false;
               } else if (getCriteria(key) instanceof Collection<?> && !(realDrink.getCriteria(key) instanceof Collection<?>)){ // if this value is a collection
                   if(!((Collection<?>) getCriteria(key)).contains(realDrink.getCriteria(key))) return false;
               } else if (!(getCriteria(key) instanceof Collection<?>) && realDrink.getCriteria(key) instanceof Collection<?>){ // if other value is a collection
                   if(!((Collection<?>) realDrink.getCriteria(key)).contains(getCriteria(key))) return false;
               } else if(!getCriteria(key).equals(realDrink.getCriteria(key))) return false; // if neither value is a collection
           }
       }
        return true;
    }
    /**
     * This method filters out extras that are not available for the selected drink
     */
    public void filterExtras(Drink drink) {
        List<String> intersect = new ArrayList<>((Collection) drink.genericFeatures().getCriteria(Criteria.EXTRAS));
        intersect.retainAll((Collection<?>) this.getCriteria(Criteria.EXTRAS));
        this.criteria.put(Criteria.EXTRAS, intersect);
    }
}
