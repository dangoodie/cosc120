/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

import java.util.HashSet;
import java.util.Set;

/**
 * Enum class for the extras that can be added to a drink.
 * Each extra has a string representation and a method to convert a string to an extra.
 */
public enum Extras {
    SKIP, NONE, CHOCOLATE_POWDER, CINNAMON, VANILLA_SYRUP, WHIPPED_CREAM, VANILLA_ICE_CREAM, CHOCOLATE_SYRUP, CARAMEL_SYRUP,
    HONEY, AGAVE_SYRUP, CINNAMON_STICK,LEMON, LEMON_ZEST, GINGER_SLICE, VANILLA_EXTRACT, MAPLE_SYRUP, JASMINE_FLOWERS, MINT, BASIL, CHOCOLATE_SHAVINGS, CREAM, ICE;

    /**
     * Returns a string representation of the extra.
     * @return a string representation of the extra
     */
    @Override
    public String toString() {
        return switch (this) {
            case SKIP -> "Skip";
            case CHOCOLATE_POWDER -> "Chocolate powder";
            case CINNAMON -> "Cinnamon";
            case VANILLA_SYRUP -> "Vanilla syrup";
            case WHIPPED_CREAM -> "Whipped cream";
            case VANILLA_ICE_CREAM -> "Vanilla ice cream";
            case CHOCOLATE_SYRUP -> "Chocolate syrup";
            case CARAMEL_SYRUP -> "Caramel syrup";
            case HONEY -> "Honey";
            case AGAVE_SYRUP -> "Agave syrup";
            case CINNAMON_STICK -> "Cinnamon stick";
            case LEMON -> "Lemon";
            case LEMON_ZEST -> "Lemon zest";
            case GINGER_SLICE -> "Ginger slice";
            case VANILLA_EXTRACT -> "Vanilla extract";
            case MAPLE_SYRUP -> "Maple syrup";
            case JASMINE_FLOWERS -> "Jasmine flowers";
            case MINT -> "Mint";
            case BASIL -> "Basil";
            case CHOCOLATE_SHAVINGS -> "Chocolate shavings";
            case CREAM -> "Cream";
            case ICE -> "Ice";
            case NONE -> "None";
        };
    }

    /**
     * Converts a string to an extra.
     * @param extra the string to convert
     * @return the extra
     */
    public static Extras fromString(String extra) {
        extra = extra.strip().toUpperCase();
        return switch (extra) {
            case "SKIP" -> SKIP;
            case "CHOCOLATE POWDER" -> CHOCOLATE_POWDER;
            case "CINNAMON" -> CINNAMON;
            case "VANILLA SYRUP" -> VANILLA_SYRUP;
            case "WHIPPED CREAM" -> WHIPPED_CREAM;
            case "VANILLA ICE CREAM" -> VANILLA_ICE_CREAM;
            case "CHOCOLATE SYRUP" -> CHOCOLATE_SYRUP;
            case "CARAMEL SYRUP" -> CARAMEL_SYRUP;
            case "HONEY" -> HONEY;
            case "AGAVE SYRUP" -> AGAVE_SYRUP;
            case "CINNAMON STICK" -> CINNAMON_STICK;
            case "LEMON" -> LEMON;
            case "LEMON ZEST" -> LEMON_ZEST;
            case "GINGER SLICE" -> GINGER_SLICE;
            case "VANILLA EXTRACT" -> VANILLA_EXTRACT;
            case "MAPLE SYRUP" -> MAPLE_SYRUP;
            case "JASMINE FLOWERS" -> JASMINE_FLOWERS;
            case "MINT" -> MINT;
            case "BASIL" -> BASIL;
            case "CHOCOLATE SHAVINGS" -> CHOCOLATE_SHAVINGS;
            case "CREAM" -> CREAM;
            case "ICE" -> ICE;
            case "NONE", "" -> NONE;
            default -> throw new IllegalStateException("Unexpected value: " + extra);
        };
    }

    public static Set<Extras> fromStringSet(Set<String> extras) {
        Set<Extras> extrasSet = new HashSet<>();
        for (String extra : extras) {
            extrasSet.add(fromString(extra));
        }
        return extrasSet;
    }
}
