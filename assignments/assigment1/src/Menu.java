import java.util.HashSet;
import java.util.Set;

public class Menu {
    private final Set<Coffee> coffees;

    public Menu(Set<Coffee> coffees) {
        this.coffees = coffees;
    }

    public Set<Coffee> getAllCoffees() {
        return coffees;
    }

    public Coffee getCoffeeById(int id) {
        for (Coffee coffee : coffees) {
            if (coffee.getId() == id) {
                return coffee;
            }
        }
        return null;
    }

    public Set<Coffee> findDreamCoffees(Coffee dreamCoffee) {
        Set<Coffee> dreamCoffees = new HashSet<>();
        for (Coffee coffee : coffees) {
            if (
                coffee.getPrice() >= dreamCoffee.getMinPrice() &&
                coffee.getNumberOfShots() == dreamCoffee.getNumberOfShots() &&
                coffee.hasSugar() == dreamCoffee.hasSugar() &&
                coffee.getMilkOptions().containsAll(dreamCoffee.getMilkOptions()) &&
                dreamCoffee.getMilkOptions().containsAll(coffee.getMilkOptions()) &&
                coffee.getExtras().containsAll(dreamCoffee.getExtras()) &&
                dreamCoffee.getExtras().containsAll(coffee.getExtras())) {
                dreamCoffees.add(coffee);
            }
        }
        return dreamCoffees;
    }
}
