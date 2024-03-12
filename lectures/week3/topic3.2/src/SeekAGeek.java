/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class SeekAGeek {
    /**
     * this program demonstrates how to create an object from a class
     * demonstrates how to access fields and methods of that class
     * @param args none required
     */
    public static void main(String[] args){

        //instantiating the Geek class to create the geek object
        Geek geek = new Geek();

        //give the geek a name
        geek.name = "Joe Mulgani";

        //give the geek a userName
        geek.userName = "joey_gani";

        //check if the geek's username contains an underscore
        System.out.println("Contains underscore: "+geek.checkUnderscoreUserName());

        //let's print geek1's name
        geek.printGeekName();
    }

}
