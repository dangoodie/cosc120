public enum DrinkType {
    SELECT_DRINK_TYPE, COFFEE, TEA;

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
            case SELECT_DRINK_TYPE -> "Select drink type";
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
            case "SELECT DRINK TYPE" -> SELECT_DRINK_TYPE;
            default -> throw new IllegalStateException("Unexpected value: " + drinkType);
        };
    }
}
