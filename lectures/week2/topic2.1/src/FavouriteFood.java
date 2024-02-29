/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Scanner;

public class FavouriteFood {

    /**
     * asks user for favourite food, and at least one other favourite food
     * @param args none required
     */
    public static void main(String[] args){
        //we want the user to input at least one favourite food...
        System.out.print("Please enter your favourite food:");
        Scanner scanner = new Scanner(System.in);
        String food=scanner.nextLine(); //the contents of this var could be stored in an array (covered later this week)

        //we want to ask them at least once for their next favorite food - thus the do/while loop
        do{
            System.out.println("What's your next favourite food? (Enter 0 to exit) ");
            food = scanner.nextLine();
        } while(!food.equals("0"));

        System.out.println("Thank you for providing your favourite foods!");
    }
}
