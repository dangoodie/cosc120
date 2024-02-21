/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.JOptionPane;

public class VerifyAge {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args) {

        int minimumAge = 18;

        //create an input dialog, providing the user with instructions
        String age = JOptionPane.showInputDialog("Please enter your age: ");
        //use parseInt to convert the user input to an int, and assign it to the geeksAge var
        int geeksAge = Integer.parseInt(age);

        if (geeksAge >= minimumAge){
            JOptionPane.showMessageDialog(null,"Thank you. You meet the minimum age requirements.");
        }
        else{
            //if the user's verification code does not match verificationCode, print failure message
            JOptionPane.showMessageDialog(null,"Account setup aborted. You do not meet the minimum age requirements.");
        }
        //terminate to prevent program from hanging
        System.exit(0);
    }

}
