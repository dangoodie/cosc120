/**
 * @author Daniel Gooden (dgooden@une.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

/**
 * Enum class for the extras that can be added to a drink.
 * Each extra has a string representation and a method to convert a string to an extra.
 */
public enum Extras {
    CHOCOLATE_POWDER, CINNAMON, VANILLA_SYRUP, WHIPPED_CREAM, VANILLA_ICE_CREAM, CHOCOLATE_SYRUP, CARAMEL_SYRUP,NONE;

    /**
     * Returns a string representation of the extra.
     * @return a string representation of the extra
     */
    @Override
    public String toString() {
        return switch (this) {
            case CHOCOLATE_POWDER -> "Chocolate powder";
            case CINNAMON -> "Cinnamon";
            case VANILLA_SYRUP -> "Vanilla syrup";
            case WHIPPED_CREAM -> "Whipped cream";
            case VANILLA_ICE_CREAM -> "Vanilla ice cream";
            case CHOCOLATE_SYRUP -> "Chocolate syrup";
            case CARAMEL_SYRUP -> "Caramel syrup";
            case NONE -> "None";
        };
    }

    /**
     * Converts a string to an extra.
     * @param extra the string to convert
     * @return the extra
     */
    public static Extras fromString(String extra) {
        return switch (extra) {
            case "Chocolate powder" -> CHOCOLATE_POWDER;
            case "Cinnamon" -> CINNAMON;
            case "Vanilla syrup" -> VANILLA_SYRUP;
            case "Whipped cream" -> WHIPPED_CREAM;
            case "Vanilla ice cream" -> VANILLA_ICE_CREAM;
            case "Chocolate syrup" -> CHOCOLATE_SYRUP;
            case "Caramel syrup" -> CARAMEL_SYRUP;
            case "None" -> NONE;
            default -> throw new IllegalStateException("Unexpected value: " + extra);
        };
    }
}
