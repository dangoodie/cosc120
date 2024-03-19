/**
 * Enumerates the sex of a dog
 */
public enum Gender {
    MALE, FEMALE;

    /**
     * Method to convert to a string
     * @return a String
     */
    public String toString() {
        return switch (this) {
            case MALE -> "Male";
            case FEMALE -> "Female";
        };
    }
}
