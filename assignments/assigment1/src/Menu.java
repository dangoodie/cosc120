/**
 * @author Daniel Gooden (dgooden@une.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents a menu object, which contains a set of coffee objects.
 */
public class Menu {
    private final Set<Coffee> coffees;

    /**
     * Constructor used to create a Menu object
     * @param coffees a Set of Coffee objects representing the coffees available on the menu
     */
    public Menu(Set<Coffee> coffees) {
        this.coffees = coffees;
    }

    //getters
    /**
     * Returns a set of all the coffees on the menu.
     * @return a set of all the coffees on the menu
     */
    public Set<Coffee> getAllCoffees() {
        return coffees;
    }

    /**
     * Returns a coffee object with the given id.
     * @param id the id of the coffee to return
     * @return the coffee object with the given id
     */
    public Coffee getCoffeeById(int id) {
        for (Coffee coffee : coffees) {
            if (coffee.getId() == id) {
                return coffee;
            }
        }
        return null;
    }

    /**
     * Returns a set of coffee objects that match the given dream coffee.
     * @param dreamCoffee the coffee to match
     * @return a set of coffee objects that match the given dream coffee
     */
    public Set<Coffee> findDreamCoffees(Coffee dreamCoffee) {
        Set<Coffee> dreamCoffees = new HashSet<>();

        for (Coffee coffee : coffees) {
            if (coffee.getNumberOfShots() == dreamCoffee.getNumberOfShots() &&
                coffee.hasSugar() == dreamCoffee.hasSugar() &&
                coffee.hasMilkOption(dreamCoffee.getMilkOptions()) &&
                coffee.hasExtra(dreamCoffee.getExtras()) &&
                coffee.isInPriceRange(dreamCoffee.getMinPrice(), dreamCoffee.getMaxPrice())
                )
                    {
                dreamCoffees.add(coffee);
            }
        }
        if (dreamCoffees.isEmpty()) {
            return null;
        }
        return dreamCoffees;
    }
}
