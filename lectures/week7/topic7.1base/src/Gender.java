/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

/*
 * example of the simplest possible enum
 */
public enum Gender {
    MALE,FEMALE,OTHER;

    /**
     * @return prettified representation of gender enum constants
     */
    public String toString(){
        return switch (this) {
            case FEMALE -> "Female";
            case MALE -> "Male";
            case OTHER -> "Other";
        };
    }
}
