/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

import java.util.*;

/**
 * A class that represents a menu object, which contains a set of drink objects.
 */
public class Menu {
    private final Set<Drink> drinks;

    /**
     * Constructor used to create a Menu object
     * @param drinks a Set of drink objects representing the drinks available on the menu
     */
    public Menu(Set<Drink> drinks) {
        this.drinks = drinks;
    }

    //getters
    /**
     * Returns a set of all the drink on the menu.
     * @return a set of all the drinks on the menu
     */
    public Set<Drink> getMenu() {
        return drinks;
    }

    /**
     * Returns a drink object with the given id.
     * @param id the id of the coffee to return
     * @return the drink object with the given id
     */
    public Drink getDrinkById(int id) {
        for (Drink drink : drinks) {
            if (drink.id() == id) {
                return drink;
            }
        }
        return null;
    }

    /**
     * Returns a set of drink objects that match the given dream drink.
     * @param dreamDrink the drink to match
     * @return a list of drink objects that match the given dream drink
     */
    public List<Drink> findDreamDrink(DreamDrink dreamDrink){
        List<Drink> matches = new ArrayList<>();
        for(Drink drink: drinks){
            // If the drink is not a match, break out of the loop
            if(!dreamDrink.matches(drink.genericFeatures())) continue;
            // If the price is not within the min and max price, break out of the loop
            if(drink.price() < dreamDrink.getMinPrice() || drink.price() > dreamDrink.getMaxPrice()) continue;
            // If it reaches this point, the drink is a match. Add it to the list of matches
            matches.add(drink);
        }
        return matches;
    }

    /**
     * A method to find the extras that are available for a drink of a given type
     * It checks to make sure that there is no duplication of extras
     * @param drinkType a DrinkType object representing the type of drink
     * @return a List of Extras objects representing the extras available for the drink
     */

    public Set<String> findExtras(DrinkType drinkType) {
        Set<String> extras = new HashSet<>();

        // If the drink type is SELECT_DRINK_TYPE, return empty set
        if (drinkType == DrinkType.SELECT_DRINK_TYPE) return new HashSet<>(extras);

        for (Drink drink : this.getMenu()) {
            if (drink.genericFeatures().getCriteria(Criteria.DRINK_TYPE) == drinkType) {
                List<String> e = (List<String>) drink.genericFeatures().getCriteria(Criteria.EXTRAS);
                extras.addAll(e);
            }
        }
        return new HashSet<>(extras);
    }




}
