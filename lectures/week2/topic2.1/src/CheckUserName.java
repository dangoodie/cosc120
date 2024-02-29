/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Scanner;

    public class CheckUserName {
        /**
         * This program demonstrates how a for loop can be used to alter values in an array
         * Re-enforces knowledge/usage of Scanner for user input
         * demonstrates use of the keyword continue
         * @param args none required
         */
        public static void main(String[] args){

            //Create an array of Strings (more on that in Topic 2.2)
            String[] geeks = {"greek_geek","marlondjango","hexy_scripter", "non_binary","hexsymbol",
                    "jimmyneutron","quanton_clint", "idiotproof","beta_version","ascii_challenged"};

            //Create a scanner object to get user input
            Scanner keyboard = new Scanner(System.in);

            //loop through the array of geeks, and check if 1) their usernames include an underscore 2) whether they'd like to amend their username
            //use a for loop here because we want to change the contents of the array - you can't do this with a foreach loop
            for(int i=0;i<geeks.length;i++){
                String userName = geeks[i]; //tell the user what their current username is and offer for them to amend it
                System.out.println("Your username is: "+userName+". Would you like to edit it? (yes/no)");
                String response = keyboard.next(); //take in user input
                //if they don't want to change it, and it is valid (contains an underscore), move on to the next username
                if(geeks[i].contains("_") && response.equalsIgnoreCase("no")) continue;
                //if they do want to change it, take in their input
                else if(response.equalsIgnoreCase("yes")){
                    System.out.println("Please enter your new username: ");
                    userName = keyboard.next();
                }
                //while the old/new username doesn't include an underscore, request that they amend it
                while(!userName.contains("_")) {
                    System.out.println(userName + ", please amend your username. Ensure to include an underscore. Thank you!");
                    userName = keyboard.next(); //next() does not allow whitespace
                }
                //update the geek username to the user's new input where relevant
                if(!geeks[i].equals(userName)) geeks[i]=userName;
                }
            //print all the geek names to view the changes that have been made
            System.out.println("All usernames");
            for (String geekName:geeks) {
                System.out.println(geekName);
            }
        }
}

