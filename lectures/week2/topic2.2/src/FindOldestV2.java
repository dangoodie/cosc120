/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Arrays;

public class FindOldestV2 {
    /**
     * This program demonstrates how to use the Arrays class to facilitate array manipulation
     * @param args none required
     */
    public static void main(String[] args){

        int[] geekAges = {34,43,24,19,20,21,41,53,33,42,21,20};

        //use Arrays.toString to print the entire array in one line without a for loop!
        System.out.println("Here are the age values of all the geeks: "+Arrays.toString(geekAges));

        //sort the geeks from youngest to oldest (ascending order)
        Arrays.sort(geekAges);

        System.out.println("Here are the sorted geeks ages: "+Arrays.toString(geekAges));

        //now that it is sorted, the oldest geek is the last geek in the array
        int oldestGeek = geekAges[geekAges.length-1];

        System.out.println("The oldest geek on SeekAGeek is "+oldestGeek+" years old.");
    }

}
