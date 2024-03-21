import java.util.Set;

public enum MilkOptions {
    FULL_CREAM, SKIM, SOY, ALMOND, OAT, NONE;

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
