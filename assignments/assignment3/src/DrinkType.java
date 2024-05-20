public enum DrinkType {
    TEA, COFFEE;

    /**
     * Builds a string representation of the drink type.
     * For use in the GUI.
     * @return a string representation of the drink type
     */
    @Override
    public String toString() {
        return switch (this) {
            case TEA -> "Tea";
            case COFFEE -> "Coffee";
        };
    }

    /**
     * Converts a string to a drink type.
     * @param drinkType the string to convert
     * @return the drink type
     */
    public static DrinkType fromString(String drinkType) {
        drinkType = drinkType.strip().toUpperCase();
        return switch (drinkType) {
            case "TEA" -> TEA;
            case "COFFEE" -> COFFEE;
            default -> throw new IllegalStateException("Unexpected value: " + drinkType);
        };
    }
}
