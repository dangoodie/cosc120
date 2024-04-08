import java.util.Set;

public class DreamTea extends DreamDrink{
    public final int temperature;
    public final int steepTime;

    public DreamTea(int minPrice, int maxPrice, boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, int temperature, int steepTime) {
        super(minPrice, maxPrice, sugar, milkOptions, extras);
        this.temperature = temperature;
        this.steepTime = steepTime;
    }

    public DreamTea(boolean sugar, Set<MilkOptions> milkOptions, Set<Extras> extras, int temperature, int steepTime) {
        super(sugar, milkOptions, extras);
        this.temperature = temperature;
        this.steepTime = steepTime;
    }

    // getters

    public int getTemperature() {
        return temperature;
    }

    public int getSteepTime() {
        return steepTime;
    }
}
