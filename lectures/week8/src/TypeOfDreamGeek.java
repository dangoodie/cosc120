/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum TypeOfDreamGeek {
    STUDY_BUDDY,FRIEND,MORE_THAN_A_FRIEND,OLD_SCHOOL_FRIEND;

    /**
     * @return a prettified description of the types of geek relationships
     */
    public String toString(){
        return switch (this) {
            case FRIEND -> "Friend or pal";
            case STUDY_BUDDY -> "Study buddy";
            case MORE_THAN_A_FRIEND -> "More than a friend";
            case OLD_SCHOOL_FRIEND -> "Old school friend";
        };
    }
}
