import java.util.Set;

public class DreamDrink {
    private int minPrice;
    private int maxPrice;
    private final boolean sugar;
    private final Set<MilkOptions> milkOptions;
    private final Set<Extras> extras;

    public DreamDrink(int minPrice, int maxPrice, boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sugar = sugar;
        this.milkOptions = milkOptions;
        this.extras = extras;
    }

    public DreamDrink(boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras) {
        this.sugar = sugar;
        this.milkOptions = milkOptions;
        this.extras = extras;
    }

    // getters
    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public boolean hasSugar() {
        return sugar;
    }

    public Set<MilkOptions> getMilkOptions() {
        return milkOptions;
    }

    public Set<Extras> getExtras() {
        return extras;
    }

}
