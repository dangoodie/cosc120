/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//import the Scanner class to get user input
import java.util.Scanner;

public class SetPassword {

    /**
     * requests user inputs a password between 8-20 characters - provides feedback and opportunity
     * to re-enter if password not valid. Exit code: 0
     * @param args not required
     */
    public static void main(String[] args){
        //give the user instructions
        System.out.print("Please choose a password (Must be at least 8 characters long but cannot exceed 20 characters). To exit account setup, enter 0: ");
        //create a scanner object
        Scanner keyboard = new Scanner(System.in);
        //assign the user's input to a String variable
        String password = keyboard.next();

        // Validate user input and check if the exit condition is entered
        while (!password.equals("0") && password.length() < 8 || password.length() > 20) {
            //let the user know that their entry didn't meet the requirements
            System.out.println("Password invalid. Please choose another password");
            //request they reenter a password
            password = keyboard.next();
        }
        //once the loop conditions have been successfully met, print a relevant message
        if(!password.equals("0")) System.out.println("Success!");
        else System.out.println("Account setup aborted");
    }
}
