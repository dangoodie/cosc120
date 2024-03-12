/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;

public class ChangeUsername {

    /**
     * This program illustrates how Strings are immutable
     * Re-enforces use of input dialogs
     * @param args none required
     */
    public static void main(String[] args){
        //original username
        String currentUsername = "marlondjango";
        //print the original value
        System.out.println("Argument value before method call: "+currentUsername);
        //pass currentUsername into changeUsername
        changeUsername(currentUsername);
        //notice that the value has not changed - this is because Strings are immutable
        //immutability means that
        System.out.println("Argument value after method call: "+currentUsername);
        System.exit(0);
    }

    /**
     * generates a JOptionPane that allows user to change the username
     * useless method, because String is immutable, meaning whatever changes are made to the parameter
     * won't be reflected in the original variable passed in as an argument
     * @param currentUsername a predefined String username
     */
    private static void changeUsername(String currentUsername){
        String newUsername = JOptionPane.showInputDialog("Please enter your new username");
        //print the value of the parameter before it is assigned with a new value
        System.out.println("Parameter value before change: "+currentUsername);
        //assign the parameter variable with a new value
        currentUsername = newUsername;
        //the value has changed! (a new String object has been created)
        System.out.println("Parameter value after change: "+currentUsername);
    }

}
