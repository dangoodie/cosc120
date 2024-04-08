import java.util.Set;

public record Drink(int id, String name, Double price, String description, DreamDrink genericFeatures) {

    public Drink {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (genericFeatures == null) {
            throw new IllegalArgumentException("Generic features cannot be null");
        }

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

    public String getDescription() {
        return description;
    }

    public DreamDrink getGenericFeatures() {
        return genericFeatures;
    }
}
