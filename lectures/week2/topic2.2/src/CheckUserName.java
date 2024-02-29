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
         * @param args none required
         */
        public static void main(String[] args){

            //Create an array of Strings
            String[] geeks = {"greek_geek","marlondjango","hexy_scripter", "non_binary","hexsymbol",
                    "jimmyneutron","quanton_clint", "idiotproof","beta_version","ascii_challenged"};

            //Create a scanner object to get user input
            Scanner keyboard = new Scanner(System.in);

            //loop through the array of geeks, and check if their usernames include an underscore
            //use a for loop here because we want to change the contents of the array - you can't do this with a foreach loop
            for(int i=0;i<geeks.length;i++){
                //if the username does contain an underscore, move on to the next geek (skips code below continue)
                if (geeks[i].contains("_")) continue;
                //if the username doesn't include an underscore, request that they amend it
                System.out.println(geeks[i]+", please amend your username to include an underscore. Thank you!");
                String userName = keyboard.next(); //next() does not allow whitespace
                //change the geek username to the user's new input
                geeks[i]=userName;
            }

            //print all the geek names to view the changes that have been made
            System.out.println("\tGEEK DATA**");
            for (String geekName:geeks) {
                System.out.println(geekName);
            }
        }
}

