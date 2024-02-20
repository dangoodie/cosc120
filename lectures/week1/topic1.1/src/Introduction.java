/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//this is a line comment

/*
block comment
 */

/**
 * javadoc comment
 */

//class declaration
public class Introduction {//opening bracket indicates beginning of class scope
    /**
     * public makes the main method globally accessible
     * static prevents the JVM from making an object of class Introduction when it runs the main method
     * void means the main method doesn't return anything
     * @param args String args[] represents any command-line arguments that may be passed into main when it is run
     */
    public static void main(String[] args){//opening bracket indicates beginning of main method scope
        //System.out.print allows you to print data to the console (more on that in the next video)
        System.out.print("Hi, my name is Andrew");
    }//closing bracket indicates end of main method scope
}//closing bracket indicates end of class scope

