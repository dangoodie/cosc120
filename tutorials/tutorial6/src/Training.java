public enum Training {
    NONE, BASIC, INTERMEDIATE, ADVANCED;

    @Override public String toString() {
        return switch (this) {
            case NONE -> "None";
            case BASIC -> "Basic";
            case INTERMEDIATE -> "Intermediate";
            case ADVANCED -> "Advanced";
        };
    }

    public static Training fromString(String training) {
        training = training.toUpperCase();
        return switch (training) {
            case "NONE", "NA" -> NONE;
            case "BASIC" -> BASIC;
            case "INTERMEDIATE" -> INTERMEDIATE;
            case "ADVANCED" -> ADVANCED;
            default -> throw new IllegalArgumentException("Invalid training level: " + training);
        };
    }
}
