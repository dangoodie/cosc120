public enum ExtraOptions {
    CHOCLATE_POWDER, CINNAMON, VANILLA_SYRUP, WHIPPED_CREAM, VANILLA_ICE_CREAM, CHOCOLATE_SYRUP, CARAMEL_SYRUP;

    @Override
    public String toString() {
        return switch (this) {
            case CHOCLATE_POWDER -> "Chocolate Powder";
            case CINNAMON -> "Cinnamon";
            case VANILLA_SYRUP -> "Vanilla Syrup";
            case WHIPPED_CREAM -> "Whipped Cream";
            case VANILLA_ICE_CREAM -> "Vanilla Ice Cream";
            case CHOCOLATE_SYRUP -> "Chocolate Syrup";
            case CARAMEL_SYRUP -> "Caramel Syrup";
        };
    }
}
