/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;

public class ChangeUsernameV2 {
    /**
     * This program illustrates use of a value-returning method and method documentation
     * Re-enforces use of input dialogs
     * @param args none required
     */
    public static void main(String[] args){
        String currentUsername = "marlondjango";
        //print the original value
        System.out.println("Argument value before method call: "+currentUsername);
        /*we can change the currentUsername variable value by assigning to it the value returned by changeUsername()
        Because String values are immutable, the marlondjango value is collected by the garbage collection
        and the new object created by the changeUsername method is assigned to the currentUsername variable
         */
        currentUsername = changeUsername();
        //notice that the value has now changed!
        System.out.println("Argument value after method call: "+currentUsername);
        System.exit(0);
    }

    /**
     * generates a JOptionPane that allows user to change the username
     * @return newUsername - a String value entered by the user, representing their username
     */
    private static String changeUsername(){
        return JOptionPane.showInputDialog("Please enter your new username");
    }

}
