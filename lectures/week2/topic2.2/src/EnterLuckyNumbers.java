/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class EnterLuckyNumbers {
    /**
     * This program demonstrates how to create a numerical arraylist, sort with Collections and empty an Arraylist
     * Re-enforces knowledge/usage of Standard input/output
     * @param args none required
     */
    public static void main(String[] args){

        //we can predefine the length of an ArrayList
        int numberOfNumbers = 5;
        //primitive values cannot be used in an ArrayList, so we must use Integer rather than int
        ArrayList<Integer> luckyNumbers = new ArrayList<>(numberOfNumbers);

        //request user input
        System.out.println("Please enter your luckiest number: ");
        Scanner keyboard = new Scanner(System.in);

        //allow users to input 5 numbers to fill the array
        for (int i=0;i<numberOfNumbers;i++){
            if(i!=0) System.out.println("Please enter your next luckiest number: ");
            luckyNumbers.add(keyboard.nextInt());
        }
        //sort the lucky numbers from largest to smallest
        Collections.sort(luckyNumbers);

        //print the contents of the array
        System.out.println("Your lucky numbers are: "+luckyNumbers);

        //now empty the list of numbers
        System.out.println("Emptying..........");
        luckyNumbers.clear();

        //print the size of the array
        System.out.println("The number of lucky numbers is: "+luckyNumbers.size());

        System.exit(0);
    }

}
