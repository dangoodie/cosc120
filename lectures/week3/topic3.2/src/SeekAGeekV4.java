/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class SeekAGeekV4 {

    /**
     * demonstrates instantiation of the GeekV4 class using non-default constructor
     * @param args none required
     */
    public static void main(String[] args){
        //create two geek objects, passing arguments into the constructor of the GeekV4 class
        GeekV4 mildred = new GeekV4("Mildred", "Jefferson");
        GeekV4 mitchell = new GeekV4("Mitchell", "Gates");

        //set the favourite tv shows for each geek
        mildred.addFavouriteTVShows();
        mitchell.addFavouriteTVShows();

        //find the tv shows they have in common
        Set<String> tvShowsInCommon = new HashSet<>(mildred.getFavouriteTVShows());
        tvShowsInCommon.retainAll(mitchell.getFavouriteTVShows());

        //print out how many, and which tv shows they have in common
        System.out.println(mildred.getFirstName()+" "+mildred.getSurname()+" and "+
                mitchell.getFirstName()+" "+mitchell.getSurname()+" have "+
                tvShowsInCommon.size()+" show(s) in common. "+ "They are: ");
        tvShowsInCommon.forEach(System.out::println);
    }

}
