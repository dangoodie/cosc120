/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class Friend extends DreamGeek{
    //These used to be in DreamGeek
    private final Religion religion;
    private Set<String> favouriteGames = new HashSet<>();
    private Set<String> favouriteTVShows = new HashSet<>();

    /**
     * @param minAge           the lowest age a user desires
     * @param maxAge           the highest age a user desires
     * @param gender           an enum value representing the Geek's gender (male, female or other)
     * @param starSign         an enum representing the geek's star sign
     * @param religion         an enum constant representing the geek's religion
     * @param favouriteGames   a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     */
    //The Friend constructor must contain the DreamGeek fields, so that they can be initialised
    public Friend(int minAge, int maxAge, Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames, Set<String> favouriteTVShows) {
        //this is how you initialise the superclass fields
        super(minAge, maxAge, gender, starSign);
        this.religion = religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
    }

    //These getters used to be in DreamGeek
    /**
     * @return an enum constant representing the dream geek's religion
     */
    public Religion getReligion() {
        return religion;
    }
    /**
     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
     * @return a Set (HashSet) of all this geek's favourite computer games
     */
    public Set<String> getFavouriteGames(){
        return new HashSet<>(favouriteGames);
    }
    /**
     * contains the String names/titles of the Geek's preferred tv shows (duplicates not allowed)
     * @return a Set (HashSet) of all this geek's favourite tv shows
     */
    public Set<String> getFavouriteTVShows(){
        return new HashSet<>(favouriteTVShows);
    }

    //This is how you override DreamGeek's getDescription method
    //Note the use of super.getDescription() invokes DreamGeek's getDescription() method, to print the geek's star sign and gender
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    @Override
    public String getDescription(){
        return this.getReligion()+", "+ super.getDescription() +". \nFavourite games: "+this.getFavouriteGames().toString()+
        "\nFavourite tv shows: "+this.getFavouriteTVShows();
    }

    //This is how you override DreamGeek's matches method
    //Note the use of super.matches() invokes DreamGeek's matches() method, to ensure the age range, star sign and gender are compatible
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    //This method takes advantage of polymorphism = because a Friend IS-A DreamGeek, a DreamGeek can be passed in as a parameter and used as a Friend
    @Override
    public boolean matches(DreamGeek realGeek) {
        //If the realGeek is a Friend (not a StudyBuddy or MoreThanAFriend), check if the Friend fields are compatible with the realGeek
        if(realGeek instanceof Friend geekFriend) {
            //To be compatible, the realGeek's DreamGeek fields must be compatible, so invoke the superclass matches method
            if (!super.matches(realGeek)) return false;
            //if the superclass matches method returns true, test the Friend criteria compatibility
            if (!this.getReligion().equals(Religion.NA)) {
                if (!this.getReligion().equals(geekFriend.getReligion())) return false;
            }
            Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
            if (this.getFavouriteGames().size() > 0) {
                gamesInCommon.retainAll(geekFriend.getFavouriteGames());
                if (gamesInCommon.size() == 0) return false;
            }
            Set<String> tvShowsInCommon = new HashSet<>(this.getFavouriteTVShows());
            if (this.getFavouriteTVShows().size() > 0) {
                tvShowsInCommon.retainAll(geekFriend.getFavouriteTVShows());
                return tvShowsInCommon.size() != 0;
            }
            return true; //this means that all the DreamGeek and Friend criteria match
        }
        return false; //return false if the realGeek is not an instance of Friend
    }
}