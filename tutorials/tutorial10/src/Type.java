/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum Type {
    CAT,DOG,GUINEA_PIG,SELECT_TYPE;

    public String toString(){
        return switch (this) {
            case CAT -> "Cat";
            case DOG -> "Dog";
            case GUINEA_PIG -> "Guinea pig";
            case SELECT_TYPE -> "Select pet type";
        };
    }
}
