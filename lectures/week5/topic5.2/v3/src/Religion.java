/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//EDIT 1: create an enum for religion
/**
 * enum of the top 7 religions + unaffiliated
 */
public enum Religion{
    CHRISTIAN,MUSLIM,UNAFFILIATED,HINDU,BUDDHIST,SIKH,JEWISH,BAHAI;
    public String toString() {
        return switch (this) {
            case CHRISTIAN -> "Christian";
            case MUSLIM -> "Muslim";
            case UNAFFILIATED -> "Unaffiliated";
            case HINDU -> "Hindu";
            case BUDDHIST -> "Buddhist";
            case SIKH -> "Sikh";
            case JEWISH -> "Jewish";
            case BAHAI -> "Bahai";
        };
    }
}
