/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class AgeRangeV2 {
    public static void main(String[] args){
        //assign the max-min age values to two variables
        int minAge = 18;
        int maxAge = 130;

        //play around assigning different values to this variable
        int geeksAge = 34;

        //if statements can handle multiple conditions where && requires all conditions to be true and || requires at least one to be true
        //if the user input meets the upper and lower limit age conditions, output a success message
        if(geeksAge >= minAge && geeksAge <= maxAge)
            System.out.println("You've provided a valid age. Thank you!");
        //if the age is either too low or too high, output a message indicating input did not meet requirements
        else
            System.out.println("Invalid input.");
        }

}
