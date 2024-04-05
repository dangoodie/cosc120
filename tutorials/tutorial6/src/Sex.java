/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum Sex {
    MALE, FEMALE;

    /**
     * @return a prettified version of the relevant enum constant
     */
    public String toString() {
        return switch (this) {
            case MALE -> "Male";
            case FEMALE -> "Female";
        };
    }
    
    public static Sex fromString(String sex) {
        sex = sex.toUpperCase();
        return switch (sex) {
            case "MALE" -> MALE;
            case "FEMALE" -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid sex input: " + sex);
        };
    }
}
