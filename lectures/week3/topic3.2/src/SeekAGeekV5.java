/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class SeekAGeekV5 {
    public static void main(String[] args){
        //create two geek objects from the GeekV5 record, passing arguments into the default constructor
        GeekV5 mildred = new GeekV5("Mildred", "Jefferson", 23, new HashSet<>());
        GeekV5 mitchell = new GeekV5("Mitchell", "Gates", 25, new HashSet<>());

        //set the favourite tv shows for each geek
        mildred.addFavouriteTVShows();
        mitchell.addFavouriteTVShows();

        //find the tv shows they have in common
        //notice we don't use getFavouriteTVShows - records allow you to simply reference the attribute itself, i.e. favouriteTVShows
        Set<String> tvShowsInCommon = new HashSet<>(mildred.favouriteTVShows());
        tvShowsInCommon.retainAll(mitchell.favouriteTVShows());

        //print out how many, and which tv shows they have in common
        System.out.println(mildred.firstName()+" "+mildred.surname()+" and "+
                mitchell.firstName()+" "+mitchell.surname()+" have "+
                tvShowsInCommon.size()+" show(s) in common. "+ "They are: ");
        tvShowsInCommon.forEach(System.out::println);

        //let's visualise the record objects, using our over-ridden toString method
        System.out.println(mildred);
        System.out.println(mitchell);
    }
}
