public enum DrinkType {
    TEA, COFFEE;

    public String toString() {
        return switch (this) {
            case TEA -> "Tea";
            case COFFEE -> "Coffee";
        };
    }

    public static DrinkType fromString(String drinkType) {
        drinkType = drinkType.strip().toUpperCase();
        return switch (drinkType) {
            case "TEA" -> TEA;
            case "COFFEE" -> COFFEE;
            default -> throw new IllegalStateException("Unexpected value: " + drinkType);
        };
    }
}
