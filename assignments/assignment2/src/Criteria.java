public enum Criteria {
    // generic
    DRINK_TYPE, MILK_TYPE, SUGAR, EXTRAS,
    // coffee
    NUM_OF_SHOTS,
    // tea
    TEMPERATURE, STEEP_TIME;

    public String toString() {
        return switch (this) {
            case DRINK_TYPE -> "Drink type";
            case MILK_TYPE -> "Milk type";
            case SUGAR -> "Sugar";
            case EXTRAS -> "Extras";
            case NUM_OF_SHOTS -> "Number of shots";
            case TEMPERATURE -> "Temperature";
            case STEEP_TIME -> "Steeping time";
        };
    }
}
