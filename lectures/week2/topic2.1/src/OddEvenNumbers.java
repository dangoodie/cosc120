/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class OddEvenNumbers {

    /**
     * prints/labels all integers between 1 and 10 as odd or even
     * @param args none required
     */
    public static void main(String args []){
        //Both i and j are control variables
        for(int i = 1, j = 2; i <= 9 && j <= 10; i+=2, j+=2){ //+=2 increments the control variables by 2
            System.out.print(i+" (odd) "+j+" (even) ");
        }
    }
}
