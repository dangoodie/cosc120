/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class ContactRequest {

    public static void main(String[] args) {
        //user geek
        boolean geek1ContactStatus = true;

        //other geek
        String geek2 = "Matt";
        char geek2Gender = 'M';
        boolean geek2ContactStatus = true;

        //outer if-else statement
        if(geek1ContactStatus) { //if geek1ContactStatus is true
            //first nested if-else statement
            if (!geek2ContactStatus){ //if geek1ContactStatus is false
                System.out.println("Unfortunately "+geek2+" isn't in the mood for a chat right now. Let's try again later.");
            }
            else {
                String pronoun;
                //second nested if-else statement
                if (geek2Gender == 'F') pronoun = "her";
                else if (geek2Gender == 'M') pronoun = "him";
                else pronoun = "them";

                System.out.println("Awesome news! " + geek2 + " is keen to chat! \nPlease enter your message and we will send it to " + pronoun + ".");

                /*
                But how do we enter a message?!? And why do we have to edit code to change our selections?
                We need a better way to engage with programs....
                That is the topic of the next video - user input via the keyboard using Scanner and/or GUI dialogs!
                 */
            }
        }
        else {
            System.out.println("To chat to other geeks, set your contact status to true!");
        }
    }
}
