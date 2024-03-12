/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Scanner;
import java.util.Set;


//the simplest way to create a record (the minimum you need) is:
//record GeekV5(String firstName, String surname, int age, Set<String> favouriteTVShows) {}

/**
 * this record demonstrates a more complex use case, where the 'data carrier' class is used for a little bit more than defining and accessing data
 * @param firstName a String representing the geek's first name
 * @param surname a String representing the geek's last name
 * @param age an int representing the geek's age
 * @param favouriteTVShows a Set of Strings representing the geek's favourite tv shows
 */
record GeekV5(String firstName, String surname, int age, Set<String> favouriteTVShows) {

    //no need for fields, constructors or getters - they are implicitly handled

    /**
     * if you need a method like this, perhaps a class is the better way to go, as records should
     * be used for data carrier classes that have minimum behaviours.
     * Regardless, the demonstration of this method shows that records can have
     */
    public void addFavouriteTVShows(){
        System.out.println(firstName()+ ", please enter your favourite TV shows. Enter q to quit.");
        Scanner keyboard = new Scanner(System.in);
        String tvShow = keyboard.nextLine();
        while(!tvShow.equals("q")){
            favouriteTVShows.add(tvShow);
            tvShow = keyboard.nextLine();
        }
    }

    //records have default toString, equals and hashCode methods, but you can override them
    @Override
    public String toString() {
        return firstName + " " + surname + " is " + age + " years old. " +
                "Their favourite TV shows are: "+ favouriteTVShows;
    }
}
