import java.util.Set;

public class DreamCoffee extends DreamDrink{

    public final int numberOfShots;

    public DreamCoffee(int minPrice, int maxPrice, boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, int numberOfShots) {
        super(minPrice, maxPrice, sugar, milkOptions, extras);
        this.numberOfShots = numberOfShots;
    }

    public DreamCoffee(boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, int numberOfShots) {
        super(sugar, milkOptions, extras);
        this.numberOfShots = numberOfShots;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }
}
