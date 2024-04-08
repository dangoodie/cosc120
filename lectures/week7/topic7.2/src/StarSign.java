/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

/*
 * enum representing the 12 western astrology star signs
 */
public enum StarSign {
    CAPRICORN, AQUARIUS, PISCES, ARIES, TAURUS, GEMINI, CANCER, LEO,
    VIRGO, LIBRA, SCORPIO, SAGITTARIUS, NA;

    /**
     * @return prettified representation of star sign constants
     */
    public String toString() {
        return switch (this) {
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
            case NA -> "Any star sign will do!";
        };
    }
}

