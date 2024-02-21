/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//import Scanner to get user input from the keyboard
import java.util.Scanner;

//class declaration
public class UserAccountSetup {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args){
        //create a Scanner object - System.in refers to the standard input: the keyboard
        Scanner keyboard = new Scanner(System.in);

        //write an instructional message to standard output (the console)
        System.out.println("Please enter your full name: ");
        //nextLine() is used to read a full line (up to the point where you hit 'enter' or 'return'
        //nextLine() allows whitespaces, e.g., "Andrew Shepley"
        String fullName = keyboard.nextLine();
        //print the user's entry to standard output
        System.out.println("Input using nextLine(): "+fullName);

        System.out.println("\nPlease enter your full name: ");
        /* next() does not allow whitespaces - it ends input at whitespace,
          e.g., if "Andrew Shepley" is entered, only "Andrew" will be stored in firstName
          "Shepley" will be queued, and allocated to the next time the keyboard is used
          For demonstration purposes, I've created a surname var. and assigned keyboard.next()
          to it. You'll find "Shepley" is stored in surname without you having to enter it.
          This can cause bugs, so be careful how you use it.
        */
        String firstName = keyboard.next();
        System.out.println("User input using next(): "+firstName);
        String surname = keyboard.next();
        System.out.println("Queued user input using next(): "+surname);

        //it is useful when you don't want any whitespaces in user entry, e.g., inputting an email address
        System.out.println("\nPlease enter your email address: ");
        String emailAddress = keyboard.next();
        System.out.println("Input using next(): "+emailAddress);

        /*
        Please note that when using some of these methods, e.g. the three methods below,
        the return char (generated when you click enter or return, is queued.
        This means you have to use a blank nextLine() to 'use up' the return carriage (especially
        if you're planning on using nextLine() after nextInt(), nextDouble() or nextBoolean().
         */

        //nextInt() takes in integer input
        System.out.println("\nPlease enter your age: ");
        int age = keyboard.nextInt();
        System.out.println("Input using nextInt(): "+age);
        //'use up' the return carriage
        keyboard.nextLine();

        //nextFloat() takes in floating point input (you can also use nextDouble())
        System.out.println("\nPlease enter your height: ");
        float height = keyboard.nextFloat();
        //'use up' the return carriage
        keyboard.nextLine();
        System.out.println("Input using nextDouble(): "+height);

        //nextBoolean() takes in true/false input
        System.out.println("\nAre you willing to receive promotional material (true = yes/false = no): ");
        boolean promotionalMaterial = keyboard.nextBoolean();
        //'use up' the return carriage
        keyboard.nextLine();
        System.out.println("Input using nextBoolean(): "+promotionalMaterial);
    }
}
