/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * demonstrates a basic class with 4 attributes (fields) and 1 method
 * a class from which a GeekV2 object can be created
 * PROBLEM: fields are not private! Always have private fields unless there is a very good reason not to
 */
public class GeekV2 {
    //class fields (you can initialise them on declaration)
    //if you don't specify an access modifier, the fields are by default public
    String name = "Joe Mulgani";
    String userName = "joey_gani";
    String password = "jo#42#34";
    //this field is vulnerable because Sets are mutable
    Set<String> favoriteTVShows = new HashSet<>();

    /**
     * populates the favoriteTVShows field with String values
     * String values are obtained via Scanner (from keyboard)
     */
    public void setFavouriteTVShows(){
        System.out.println("Please enter your favourite TV shows. Enter q to finish.");
        //create a Scanner object
        Scanner keyboard = new Scanner(System.in);
        //assign the user input to the tvShow variable
        String tvShow = keyboard.nextLine();
        while(!tvShow.equals("q")){ //for as long as the user doesn't input the exit char, keep requesting input
            favoriteTVShows.add(tvShow); //add the input to the Set field
            tvShow = keyboard.nextLine();
        }
    }
}
