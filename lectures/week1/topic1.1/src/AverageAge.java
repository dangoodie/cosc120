/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class AverageAge {

    public static void main(String[] args){
        //variable declaration and assignment
        int geek1Age = 35;
        int geek2Age = 26;
        int geek3Age = 42;

        //calculate the mean average age using arithmetic operators
        float averageAge = (geek1Age + geek2Age + geek3Age) /3f;

        //use String concatenation to join together a String literal (in "") and a double
        System.out.print("The average geek age is "+averageAge);
    }
}
