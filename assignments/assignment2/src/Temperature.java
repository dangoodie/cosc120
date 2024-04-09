public enum Temperature {

    SKIP, EIGHTY, EIGHTY_FIVE, NINETY, NINETY_FIVE, ONE_HUNDRED;

    public String toString() {
        return switch (this) {
            case SKIP -> "Skip";
            case EIGHTY -> "80 degrees: For a mellow, gentler taste";
            case EIGHTY_FIVE -> "85 degrees: For a slightly sharper than mellow";
            case NINETY -> "90 degrees: Balanced, strong but not too strong";
            case NINETY_FIVE -> "95 degrees: Strong, but not acidic";
            case ONE_HUNDRED -> "100 degrees: For a bold, strong flavour";
        };
    }

    public static Temperature fromString(String temperature) {
        temperature = temperature.strip();
        return switch (temperature) {
            case "SKIP" -> SKIP;
            case "80" -> EIGHTY;
            case "85" -> EIGHTY_FIVE;
            case "90" -> NINETY;
            case "95" -> NINETY_FIVE;
            case "100" -> ONE_HUNDRED;
            default -> throw new IllegalStateException("Unexpected value: " + temperature);
        };
    }
}
