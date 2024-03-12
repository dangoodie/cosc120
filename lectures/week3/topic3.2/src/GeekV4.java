/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * demonstrates use of a constructor
 */
public class GeekV4 {
    //class fields
    private final String firstName, surname;
    //fields can be initialised when declared
    private final Set<String> favouriteTVShows = new HashSet<>();

    /**
     * constructor for the GeekV4 class
     * @param fName String representing the geek's first name
     * @param sName String representing the geek's surname
     */
    public GeekV4(String fName, String sName){
        //fields can be initialised in a constructor
        firstName = fName;
        surname = sName;
    }

    //class methods
    /**
     * provides public access to first name field
     * @return name a String representing the geek's first name
     */
    public String getFirstName(){
        return firstName;
    }

    /**
     * provides public access to surname field
     * @return name a String representing the geek's surname
     */
    public String getSurname(){
        return surname;
    }

    /**
     * method to provide access to object's favourite tv shows
     * @return a copy of the object's favourite tv shows
     */
    public Set<String> getFavouriteTVShows(){
        return new HashSet<>(favouriteTVShows);
    }

    /**
     * populates the favoriteTVShows field with String values
     */
    public void addFavouriteTVShows(){
        System.out.println(getFirstName()+ ", please enter your favourite TV shows. Enter q to quit.");
        Scanner keyboard = new Scanner(System.in);
        String tvShow = keyboard.nextLine();
        while(!tvShow.equals("q")){
            favouriteTVShows.add(tvShow);
            tvShow = keyboard.nextLine();
        }
    }
}
