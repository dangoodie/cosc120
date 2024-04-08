/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */
public enum Gender {
    MALE,FEMALE,OTHER,NA; //EDIT 1: add NA to allow skipping of gender based filtering

    /**
     * @return prettified representation of gender enum constants
     */
    public String toString(){
        return switch (this) {
            case FEMALE -> "Female";
            case MALE -> "Male";
            case OTHER -> "Other";
            //EDIT 1
            case NA -> "No preference";
        };
    }
}
