/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */
public class Geek{
    /**
     * demonstrates a very basic class with 2 attributes (fields) and 2 methods
     * a class from which a Geek object can be created
     */
    //class fields
    String name;
    String userName;

    //class methods - each method should have a javadoc comment above it

    /**
     * a method to check if the username contains an underscore or not
     * @return true if username contains underscore, false if not
     */
    public boolean checkUnderscoreUserName(){
        return userName.contains("_"); //contains is a method that returns true or false
    }

    /**
     * a method to print the geek's name in a meaningful sentence
     */
    public void printGeekName(){
        System.out.println("The geek's name is "+name);
    }

}