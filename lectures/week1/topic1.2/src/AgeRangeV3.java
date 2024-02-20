/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class AgeRangeV3 {
    public static void main(String[] args){
        //assign the max-min age values to two variables
        int minAge = 18;
        int maxAge = 130;

        //play around assigning different values to this variable
        int geeksAge = 340;

        //the if else-if else statement gives more output options for different conditions
        if(geeksAge < minAge)
            System.out.println("Account setup aborted. You do not meet the minimum age requirements.");
        else if(geeksAge > maxAge)
            System.out.println("Invalid input. Age is too high!");
        else
            System.out.println("Valid age provided. Thank you.");
    }
}

