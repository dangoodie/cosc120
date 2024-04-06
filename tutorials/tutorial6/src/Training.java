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

    public String getTrainingLevel() {
        return switch (this) {
            case NONE -> "no training at all";
            case BASIC -> "sit, stay, lay down on instruction";
            case INTERMEDIATE -> "play fetch, and will stop barking if instructed";
            case ADVANCED -> "perform tricks - handshake, play dead, jump over a bar";
        };
    }
}
