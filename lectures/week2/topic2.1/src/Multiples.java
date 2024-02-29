/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class Multiples {

    /**
     * uses a nested for loop to print the first 12 multiples of the numbers from 1 - 10
     * @param args none required
     */
    public static void main(String args []) {

        int limit = 10;
        //outer loop
        for (int i = 1; i <= limit; i++) {
            System.out.print("\nThe multiples of " + i + " are: ");
            //inner loop
            for (int j = 1; j <= 12; j++) {
                System.out.print(i * j + " ");
            } //end of inner loop
        }//end of outer loop
    }
}
