/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class AverageAge {

    /**
     * demonstrates use of for each loop for running total calculation, using average age as an example
     * @param args none required
     */
    public static void main(String[] args){
        //more on arrays in the next lecture (topic 2.2)!
        int[] ages = {23,27,21,29,31,30,33,45,23,23,21};
        int sumOfAges = 0;

        //loop through a list of ages and calculates the running total using the combined assignment operator +=.
        for (int age : ages) {
            sumOfAges+=age;
        }
        //uses String concatenation to output a message, and the average age of geeks
        System.out.println("The average geek is "+sumOfAges/ages.length+" years of age.");
    }
}
