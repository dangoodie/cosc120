public enum Purebred {
    YES, NO, NA;

    @Override
    public String toString() {
        String prettified = "NA";
        switch (this) {
            case YES -> prettified = "Yes";
            case NO -> prettified = "No";
            case NA -> prettified = "N/A";
        }
        return prettified;
    }

    public static Purebred fromString(String purebred) {
        purebred = purebred.toUpperCase();
        return switch (purebred) {
            case "YES" -> YES;
            case "NO" -> NO;
            case "NA", "N/A" -> NA;
            default -> throw new IllegalArgumentException("Invalid purebred input: " + purebred);
        };
    }
}
