// Purpose: Desexed enum for the desexed field in the Dog class.

public enum Desexed {
    YES, NO;

    /**
     * Method to convert to a string
     * @return a String
     */
    public String toString() {
        return switch (this) {
            case YES -> "Yes";
            case NO -> "No";
        };
    }
}
