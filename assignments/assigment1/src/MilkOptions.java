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
}
