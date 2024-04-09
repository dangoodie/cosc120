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
    // this needs some fucking work
    // only works if they are both sets or both primitives
    public boolean matches(DreamDrink realDrink) {
       for (Criteria key : realDrink.getAllCriteria().keySet()) {
           if(this.getAllCriteria().containsKey(key)) {
               if(getCriteria(key) instanceof Collection<?>) {
                   Set<Object> intersect = new HashSet<>((Collection<?>) realDrink.getCriteria(key));
                     intersect.retainAll((Collection<?>) getCriteria(key));

                        if(intersect.isEmpty()) return false;
               }
                else if(!getCriteria(key).equals(realDrink.getCriteria(key))) return false;
           }
       }
        return true;
    }
}
