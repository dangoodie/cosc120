/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class AgeRange {

    public static void main(String[] args){
        //assign the max-min age values to two variables
        int minAge = 18;
        int maxAge = 130;

        //play around assigning different values to this variable
        int geeksAge = 19;

        //if statement with curly brackets
        //test whether the geek's age is too low
        if(geeksAge < minAge){
            System.out.println("Account setup aborted. You do not meet the minimum age requirements.");
        }

        //if statement without curly brackets (only one statement to execute)
        //test whether their age is unreasonably high
        if(geeksAge > maxAge) System.out.println("Invalid input. Age too high.");

        if(geeksAge>=minAge&&geeksAge<=maxAge) System.out.println("Valid age of "+geeksAge+" provided.");
    }

}
