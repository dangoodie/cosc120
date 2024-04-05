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
        return switch (training) {
            case "None" -> NONE;
            case "Basic" -> BASIC;
            case "Intermediate" -> INTERMEDIATE;
            case "Advanced" -> ADVANCED;
            default -> throw new IllegalArgumentException("Invalid training level: " + training);
        };
    }
}
