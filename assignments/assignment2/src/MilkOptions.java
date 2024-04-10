/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
    * Enum class for the milk options that can be added to a drink.
    * Each milk option has a string representation and a method to convert a string to a milk option.
    */
public enum MilkOptions {
   SKIP, FULL_CREAM, SKIM, SOY, ALMOND, OAT, COCONUT, NONE;

    /**
     * Returns a string representation of the milk option.
     * @return a string representation of the milk option
     */
    @Override
    public String toString() {
        return switch (this) {
            case SKIP -> "Skip";
            case FULL_CREAM -> "Full-cream";
            case SKIM -> "Skim";
            case SOY -> "Soy";
            case ALMOND -> "Almond";
            case OAT -> "Oat";
            case COCONUT -> "Coconut";
            case NONE -> "None";
        };
    }

    /**
     * Converts a string to a milk option.
     * @param milkOption the string to convert
     * @return the milk option
     */
    public static MilkOptions fromString(String milkOption) {
        milkOption = milkOption.strip().toUpperCase();
        return switch (milkOption) {
            case "SKIP" -> SKIP;
            case "FULL-CREAM" -> FULL_CREAM;
            case "SKIM" -> SKIM;
            case "SOY" -> SOY;
            case "ALMOND" -> ALMOND;
            case "OAT" -> OAT;
            case "COCONUT" -> COCONUT;
            case "NONE","" -> NONE;
            default -> throw new IllegalStateException("Unexpected value: " + milkOption);
        };
    }

    public static List<MilkOptions> fromStringList(List<String> milkOptions) {
        List<MilkOptions> milkOptionsSet = new LinkedList<>();
        for (String milkOption : milkOptions) {
            milkOptionsSet.add(fromString(milkOption));
        }
        return milkOptionsSet;
    }

}
