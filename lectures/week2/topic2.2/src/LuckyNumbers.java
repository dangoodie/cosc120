/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class LuckyNumbers {
    /**
     * This program demonstrates how a foreach loop cannot be used to alter
     * values in an array
     * @param args none required
     */
    public static void main(String[] args){

        //here is an array of numbers
        int[] luckyNumbers = {45,23,7,17,3,9,2};

        //print the numbers to standard output
        System.out.print("Original numbers: ");
        for (int number:luckyNumbers) {
            System.out.print(number+" ");
        }

        //double each of the numbers and print them
        System.out.print("\nChanged to: ");
        for (int number:luckyNumbers) {
            number*=2;
            System.out.print(number+" ");
        }

        //print the numbers in the array again. Have they changed? Why/why not?
        System.out.print("\nFinal: ");
        for (int number:luckyNumbers) {
            System.out.print(number+" ");
        }
    }
}
