/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */


/**
 * enum representing the 12 western astrology star signs
 */
public enum StarSign {
    //enums are written in capital letters
    CAPRICORN, AQUARIUS, PISCES, ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS;

    /*
    the toString() method can be used to return a friendlier representation of an Enum, e.g. title case instead of uppercase
    when you use the enum, Java will automatically visualise your enum constants according to the toString method, e.g., if you
    use print StarSign.values, Java will output the constants in title case, i.e. Capricorn, Aquarius, etc...
     */

    /**
     * a toString method used to prettify the enum constants
     * @return a prettified String representation of the uppercase constant
     */
    public String toString() {
        return switch (this) { //'this' represents this Enum datatype - the switch statement will check which constant
            //we're interested in (or all of them) and simply return it/them in title case.
            case CAPRICORN -> "Capricorn";
            case AQUARIUS -> "Aquarius";
            case PISCES -> "Pisces";
            case ARIES -> "Aries";
            case TAURUS -> "Taurus";
            case GEMINI -> "Gemini";
            case CANCER -> "Cancer";
            case LEO -> "Leo";
            case VIRGO -> "Virgo";
            case LIBRA -> "Libra";
            case SCORPIO -> "Scorpio";
            case SAGITTARIUS -> "Sagittarius";
        };
    }
}

