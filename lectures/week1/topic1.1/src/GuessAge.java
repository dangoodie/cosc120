/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//import the Random class from the package java.util
import java.util.Random;

public class GuessAge {
    public static void main(String[] args){
        //name is a local variable, that can be defined and initialised in one line
        String name = "Matthew";
        //variable declaration
        int age;
        //create an object of the class Random using the 'new' keyword
        Random guessed_age = new Random();
        //use the nextInt() function to generate a random number between 18 and 85
        //variable assignment
        age = guessed_age.nextInt(18,85);
        //print the output to your console

        //Note the use of String concatenation using the + operator
        System.out.print("I bet "+name+" is "+age+" years old!");
    }
}
