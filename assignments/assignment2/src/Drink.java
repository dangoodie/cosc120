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

    public String getDrinkDescription() {
        return this.name + "\nPrice: $" + String.format("%.2f", this.price) +"\nDescription: "+ this.description + "\n" + this.genericFeatures.getDescription() + "\n\n";
    }
}
