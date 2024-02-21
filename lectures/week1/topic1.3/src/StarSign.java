/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Scanner;

public class StarSign {

    public static void main(String[] args) {
        //user input using Scanner
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter your star sign: ");
        String geeksStarSign = keyboard.nextLine();

        //switch statement
        switch (geeksStarSign) {
            case "capricorn" -> System.out.println("Most compatible star sign: Aquarius, Virgo, and Capricorn.");
            case "aquarius" -> System.out.println("Most compatible star sign: Capricorn, Sagittarius and Gemini.");
            case "pisces" -> System.out.println("Most compatible star sign: Taurus, Cancer and Scorpio.");
            //aries and libra are both compatible with the same star signs, so they can be combined
            case "aries", "libra" -> System.out.println("Most compatible star sign: Gemini, Leo, and Sagittarius.");
            case "taurus" -> System.out.println("Most compatible star sign: Pisces, Cancer, and Virgo.");
            case "gemini" -> System.out.println("Most compatible star sign: Aries, Leo, and Libra.");
            case "cancer" -> System.out.println("Most compatible star sign: Scorpio, Pisces, and Virgo.");
            case "leo" -> System.out.println("Most compatible star sign: Aries, Gemini, and Libra.");
            case "virgo" -> System.out.println("Most compatible star sign: Cancer, Taurus, and Capricorn");
            case "scorpio" -> System.out.println("Most compatible star sign: Taurus, Cancer, and Capricorn.");
            case "sagittarius" -> System.out.println("Most compatible star sign: Aries, Leo, and Aquarius.");
            //the default statement will catch any incorrect input
            default -> System.out.println("Invalid input.");
        }
    }
}
