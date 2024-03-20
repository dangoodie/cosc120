import java.util.Set;

public class Order {
    private final Geek geek;
    private final Coffee coffeeOrder;
    private final String name;
    private final String orderNumber;
    private MilkOptions milkSelection;
    private Set<String> extraSelections;

    public Order(Geek geek, Coffee coffeeOrder) {
        this.geek = geek;
        this.coffeeOrder = coffeeOrder;
        this.name = geek.getName();
        this.orderNumber = geek.getPhoneNumber();
    }

    public void setMilkSelection(MilkOptions milkSelection) {
        this.milkSelection = milkSelection;
    }

    public void setExtraSelections(Set<String> extraSelections) {
        this.extraSelections = extraSelections;
    }

    public String getName() {
        return name;
    }

    public MilkOptions getMilkSelection() {
        return milkSelection;
    }

    public Set<String> getExtraSelections() {
        return extraSelections;
    }
}
