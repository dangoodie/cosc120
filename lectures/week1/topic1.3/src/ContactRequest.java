/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
/*
A more fun version of the code sample in Topic 1.2
 */
public class ContactRequest {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args) {
        //geek user
        String geek1 = "James";

        //potential match
        String geek2 = "Matt";
        char geek2Gender = 'M';
        boolean geek2ContactStatus = true;

        String yesNo = JOptionPane.showInputDialog(geek1 + "! We have found you a match! \nWould you like to connect with " + geek2+"?");

        //try to read the user's input...
        try{
            //ignore upper/lowercase - this may trigger a NullPointerException
            if(yesNo.equalsIgnoreCase("yes")) {
                if (!geek2ContactStatus) JOptionPane.showMessageDialog(null, "Unfortunately "+geek2+" isn't in the mood for a chat right now. Let's try again later.");
                else {
                    String pronoun;
                    if (geek2Gender == 'F') pronoun = "her";
                    else if (geek2Gender == 'M') pronoun = "him";
                    else pronoun = "them";

                    String userInput = JOptionPane.showInputDialog("Awesome! " + geek2 + " is ready to chat! \nPlease enter your message and we will send it to " + pronoun + ".");

                    //this may trigger a NullPointerException however, we would already have caught it when ignoring case
                    if (userInput.length() > 0) JOptionPane.showMessageDialog(null, "We've sent your message to " + geek2 + "!");
                    else JOptionPane.showMessageDialog(null, "Sorry. We do not send empty messages!");
                }
            }
            //ignore upper/lowercase - this may trigger a NullPointerException
            else if(yesNo.equalsIgnoreCase("no")) {
                JOptionPane.showMessageDialog(null, "That's OK! Remember you can change your mind at any time. \nWe'll keep looking for suitable matches.");
            }
            //this handles non yes/no user input
            else {
                JOptionPane.showMessageDialog(null, "You must enter either 'yes' or 'no'. Aborting...");
            }
            //regardless of the user's entry, always exit when using JOptionPane
            System.exit(0);

        //if the user clicks 'cancel' then yesNo will be null - catch it here
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Cancelled. Message not sent.");
            System.exit(0);
        }
    }
}
