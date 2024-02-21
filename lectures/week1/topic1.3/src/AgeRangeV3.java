/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
/*
A more fun version of the code sample in Topic 1.2
 */
public class AgeRangeV3 {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args){
        int minAge = 18;
        int maxAge = 130;
        int geeksAge = 0;

        String userInput = JOptionPane.showInputDialog("Please enter your age");

        //wrap the user input in a try catch statement, just in case they enter a non-digit character
        try {
            //only put in the try-catch what is relevant to the exception that might be thrown
            geeksAge = Integer.parseInt(userInput);
        }catch(NumberFormatException e){ //a NumberFormatException may be thrown if userInput cannot be parsed as an Int, e.g., if it contains special characters
            JOptionPane.showMessageDialog(null,"Invalid input");
            System.exit(0); //exit if catch block executes
        }
        /*
        the if else-if else statement gives more output options for different conditions
         */
        if(geeksAge < minAge) JOptionPane.showMessageDialog(null, "You do not meet the minimum age requirements.");
        else if(geeksAge > maxAge) JOptionPane.showMessageDialog(null,"Entry exceeds max age.");
        else JOptionPane.showMessageDialog(null,"Thank you.");
        System.exit(0);
    }
}

