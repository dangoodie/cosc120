/**
 * @author Daniel Gooden (dgooden@une.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

/**
    * Enum class for the milk options that can be added to a drink.
    * Each milk option has a string representation and a method to convert a string to a milk option.
    */
public enum MilkOptions {
    FULL_CREAM, SKIM, SOY, ALMOND, OAT, NONE;

    /**
     * Returns a string representation of the milk option.
     * @return a string representation of the milk option
     */
    @Override
    public String toString() {
        return switch (this) {
            case FULL_CREAM -> "Full-cream";
            case SKIM -> "Skim";
            case SOY -> "Soy";
            case ALMOND -> "Almond";
            case OAT -> "Oat";
            case NONE -> "None";
        };
    }

    /**
     * Converts a string to a milk option.
     * @param milkOption the string to convert
     * @return the milk option
     */
    public static MilkOptions fromString(String milkOption) {
        return switch (milkOption) {
            case "Full-cream" -> FULL_CREAM;
            case "Skim" -> SKIM;
            case "Soy" -> SOY;
            case "Almond" -> ALMOND;
            case "Oat" -> OAT;
            case "None" -> NONE;
            default -> throw new IllegalStateException("Unexpected value: " + milkOption);
        };
    }

}
