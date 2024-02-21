/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.JOptionPane;

public class ValidateAccount {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args) {
        //account verification code
        String verificationCode = "23745";
        //ask the user to input their verification code
        String userInput = JOptionPane.showInputDialog("Please enter the verification code: ");

        if (verificationCode.equals(userInput)){
            //if the user's verification code matches verificationCode, print success message
            JOptionPane.showMessageDialog(null,"Thank you. Your account has been validated.");
        }
        else{
            //if the user's verification code does not match verificationCode, print failure message
            JOptionPane.showMessageDialog(null,"Account not validated. Your verification code is incorrect or was not entered.");
        }
        //terminate to prevent program from hanging
        System.exit(0);
    }

}
