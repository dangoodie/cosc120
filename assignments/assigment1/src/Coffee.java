import java.util.Set;

public class Coffee {
    private final int id;
    private final String name;
    private final Double price;
    private Double minPrice;
    private Double maxPrice;
    private final int numberOfShots;
    private final boolean sugar;
    private final Set<MilkOptions> milkOptions;
    private final Set<Extras> extras;
    private final String description;

    public Coffee(int id, String name, Double price, int numberOfShots, boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberOfShots = numberOfShots;
        this.sugar = sugar;
        this.milkOptions = milkOptions;
        this.extras = extras;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public int getNumberOfShots() {
        return numberOfShots;
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

    public String getDescription() {
        return description;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isInPriceRange(Double minPrice, Double maxPrice) {
        return this.price >= minPrice && this.price <= maxPrice;
    }

    public boolean hasExtra(Set<Extras> extras) {
        return this.extras.containsAll(extras);
    }

    public boolean hasMilkOption(Set<MilkOptions> milkOption) {
        return milkOptions.contains(milkOption);
    }
}
