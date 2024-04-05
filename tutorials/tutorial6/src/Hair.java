public enum Hair {
    HAIRLESS, NA;

    @Override
    public String toString() {
        return switch (this) {
            case HAIRLESS -> "Hairless";
            case NA -> "N/A";
        };
    }

    public static Hair fromString(String hair) {
        hair = hair.toUpperCase();
        return switch (hair) {
            case "HAIRLESS", "YES" -> HAIRLESS;
            case "N/A", "NO" -> NA;
            default -> throw new IllegalArgumentException("Invalid hair type: " + hair);
        };
    }
}
