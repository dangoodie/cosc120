/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class FindOldestV1 {
    /**
     * This program demonstrates how a for loop/index values can be used to
     * iterate through an array
     * @param args none required
     */
    public static void main(String[] args){

        //create an array of integers
        int[] geekAges = {34,43,24,19,20,21,41,53,33,42,21,20};

        //assign the youngest possible age to the oldestGeek variable
        int oldestGeek = 0;

        //iterate through all the geek ages, and compare them to the oldest geek
        for (int geekAge : geekAges) {
            //if geekAges[i] is greater than the previous oldest geek, update it
            if (geekAge > oldestGeek) oldestGeek = geekAge;
        }
        System.out.println("The oldest geek on SeekAGeek is "+oldestGeek+" years old.");
    }

}
