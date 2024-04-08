/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public enum Criteria {
    GENDER,STAR_SIGN,RELIGION,ROMANTIC_ACTIVITY,
    VALENTINES_GIFT,INSTITUTION,COURSE,SUBJECT_AREA,
    FAVOURITE_COMPUTER_GAMES,FAVOURITE_TV_SHOWS,HOBBIES, //EDIT 3
    SCHOOL, GRADUATION_YEAR, //EDIT 8
    TYPE_OF_RELATIONSHIP; //EDIT 12

    /**
     * @return prettified representation of criteria search terms
     */
    public String toString() {
        return switch (this){
            case GENDER -> "Gender";
            case STAR_SIGN -> "Astrological star sign";
            case RELIGION -> "Religion";
            case ROMANTIC_ACTIVITY -> "Favourite romantic activity";
            case VALENTINES_GIFT -> "Dream Valentine's Day gift";
            case INSTITUTION -> "Studying at";
            case COURSE -> "Course or degree";
            case SUBJECT_AREA -> "Field/subject area";
            case FAVOURITE_COMPUTER_GAMES -> "Top computer games";
            case FAVOURITE_TV_SHOWS -> "Top TV shows";
            //EDIT 3: add hobbies
            case HOBBIES -> "Favourite hobbies";
            //EDIT 8:
            case SCHOOL -> "School";
            case GRADUATION_YEAR -> "Graduation year";
            //EDIT 11:
            case TYPE_OF_RELATIONSHIP -> "Looking for";
        };
    }
}
