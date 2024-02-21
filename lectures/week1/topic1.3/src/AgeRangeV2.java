/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
/*
A more fun version of the code sample in Topic 1.2
 */
public class AgeRangeV2 {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args){
        int minAge = 18;
        int maxAge = 130;

        String userInput = JOptionPane.showInputDialog("Please enter your age");
        int geeksAge = Integer.parseInt(userInput);

        //if statements can handle multiple conditions where && requires all conditions to be true and || requires at least one to be true
        //if the user input meets the upper and lower limit age conditions, output a success message
        if(geeksAge >= minAge && geeksAge <= maxAge)
            JOptionPane.showMessageDialog(null,"Thank you!");
        //if the age is either too low or too high, output a message indicating input did not meet requirements
        else
            JOptionPane.showMessageDialog(null,"Invalid input.");

        System.exit(0);
    }

}
