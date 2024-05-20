public record Drink(int id, String name, Double price, String description, DreamDrink genericFeatures) {

    /**
     * Constructor used to create a Drink object
     * @param id
     * @param name
     * @param price
     * @param description
     * @param genericFeatures
     */
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

    /**
     * Returns the id of the drink
     * @return the id of the drink
     */
    public String getDrinkDescription() {
        return this.description + "\n" + this.genericFeatures.getDescription() + "Price: $" + String.format("%.2f", this.price) + "\n\n";
    }
}
