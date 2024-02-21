/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
/*
A more fun version of the code sample in Topic 1.2
 */
public class AgeRange {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args){
        int minAge = 18;
        int maxAge = 130;

        //ask the user to enter their age
        String userInput = JOptionPane.showInputDialog("Please enter your age");
        //parse their String age to an integer
        int geeksAge = Integer.parseInt(userInput);

        //test whether their age meets the minimum requirements
        if(geeksAge < minAge)
            JOptionPane.showMessageDialog(null,"Account setup aborted. You do not meet the minimum age requirements.");
        //test whether their age is unreasonably high - invalid or incorrect input
        if(geeksAge > maxAge)
            JOptionPane.showMessageDialog(null,"Invalid input.");

        //ensure you always terminate when using the input dialog
        System.exit(0);
    }

}
