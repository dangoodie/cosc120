public enum Extras {
    CHOCOLATE_POWDER, CINNAMON, VANILLA_SYRUP, WHIPPED_CREAM, VANILLA_ICE_CREAM, CHOCOLATE_SYRUP, CARAMEL_SYRUP,NONE;

    @Override
    public String toString() {
        return switch (this) {
            case CHOCOLATE_POWDER -> "Chocolate Powder";
            case CINNAMON -> "Cinnamon";
            case VANILLA_SYRUP -> "Vanilla Syrup";
            case WHIPPED_CREAM -> "Whipped Cream";
            case VANILLA_ICE_CREAM -> "Vanilla Ice Cream";
            case CHOCOLATE_SYRUP -> "Chocolate Syrup";
            case CARAMEL_SYRUP -> "Caramel Syrup";
            case NONE -> "None";
        };
    }

    public static Extras fromString(String extra) {
        return switch (extra) {
            case "Chocolate powder" -> CHOCOLATE_POWDER;
            case "Cinnamon" -> CINNAMON;
            case "Vanilla syrup" -> VANILLA_SYRUP;
            case "Whipped cream" -> WHIPPED_CREAM;
            case "Vanilla ice cream" -> VANILLA_ICE_CREAM;
            case "Chocolate syrup" -> CHOCOLATE_SYRUP;
            case "Caramel syrup" -> CARAMEL_SYRUP;
            case "None" -> NONE;
            default -> throw new IllegalStateException("Unexpected value: " + extra);
        };
    }
}
