/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Arrays;

public class AverageAge {

    /**
     * this program demonstrates how to use highly specific methods to break up a program
     * demonstrates use of void and value-returning methods
     * illustrates the use of parameters, including a parameters list
     * demonstrates the use of local variables
     * re-enforces use of the Arrays class
     * @param args none required
     */
    public static void main(String[] args){
        //create a list of ages
        int[] ages = {23,45,43,21,23,33,76,34,34,55,22};
        /*
        call the methods defined below to calculate the average age, identify the age of the oldest
        geek and identify the age of the youngest geek
        these three methods are value-returning, because they return a value that can be stored in a variable
        the ages field is passed as an argument into each method
         */
        int averageAge = calculateAverageAge(ages);
        int oldest = getOldestGeek(ages);
        int youngest = getYoungestGeek(ages);
        /*
        call the method defined below to print the geek data
        this method is void-returning, as it performs a task without returning a value
        this method has 3 arguments
         */
        //call the output method to print the above info - this method call contains 3 arguments
        printGeekAgeData(youngest,oldest,averageAge);
    }

    //note: the methods below are private because they are only ever used inside this class

    //whenever you define a method, include javadoc documentation
    /**
     * calculates the mean average of a set of integers (geek age values)
     * @param ages an array of ages for which the average will be calculated
     * @return the average age (the running total of ages divided by its length)
     */
    private static int calculateAverageAge(int[] ages){
        //this is a local variable. It only exists in this method.
        int sumOfAges = 0;
        for(int i: ages) sumOfAges+=i;
        //a mathematical expression can be assigned to the return statement. The value of the expression will be returned
        return sumOfAges/ages.length;
    }

    /**
     * determines the minimum int in a set of integers (youngest geek from a set of geeks)
     * @param ages an array of different age values
     * @return the minimum value in the array
     */
    private static int getYoungestGeek(int[] ages){
        Arrays.sort(ages);
        //a value in an array can be returned
        return ages[0];
    }

    /**
     * determines the maximum int in a set of integers (the oldest geek from a set of geeks)
     * @param ages an array of different age values
     * @return the maximum value in the array
     */
    private static int getOldestGeek(int[] ages){
        Arrays.sort(ages);
        return ages[ages.length-1]; //last age after sorting
    }

    /**
     * prints a readable statement to standard output indicating the age of the youngest, oldest and average geek
     * @param youngest the youngest geek
     * @param oldest the oldest geek
     * @param averageAge the average age of the geeks
     */
    private static void printGeekAgeData(int youngest, int oldest, int averageAge){
        System.out.println("The youngest geek is "+youngest+
                ". The oldest geek is "+oldest+" and the average geek age is "+averageAge+" years of age.");
    }

}
