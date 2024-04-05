/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class MoreThanAFriend extends DreamGeek {

    //These used to be in DreamGeek - You should be noticing duplication between Friend and MoreThanAFriend
    private final Religion religion;
    private Set<String> favouriteGames = new HashSet<>();
    private Set<String> favouriteTVShows = new HashSet<>();
    //fields specific to MoreThanAFriend
    private final RomanticActivities mostRomanticActivity;
    private final ValentinesGifts favouriteValentinesGift;

    /**
     * @param minAge   the lowest age a user desires
     * @param maxAge   the highest age a user desires
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param religion         an enum constant representing the geek's religion
     * @param favouriteGames   a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     * @param mostRomanticActivity      an Enum representing the geek's favourite romantic activity (from a list)
     * @param favouriteValentinesGift   an Enum representing the geek's favourite Valentine's Day gift (from a list)
     */
    //You should be noticing duplication between Friend and MoreThanAFriend
    public MoreThanAFriend(int minAge, int maxAge, Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames,
                           Set<String> favouriteTVShows, RomanticActivities mostRomanticActivity, ValentinesGifts favouriteValentinesGift) {
        super(minAge, maxAge, gender, starSign);
        this.religion=religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
        this.mostRomanticActivity=mostRomanticActivity;
        this.favouriteValentinesGift=favouriteValentinesGift;
    }

    //getters
    //You should be noticing duplication between Friend and MoreThanAFriend
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
    /**
     * @return an enum constant representing the geek's preferred romantic activity
     */
    public RomanticActivities getMostRomanticActivity() {
        return mostRomanticActivity;
    }
    /**
     * @return an enum constant representing the geek's preferred valentine's gift
     */
    public ValentinesGifts getFavouriteValentinesGift() {
        return favouriteValentinesGift;
    }

    //This is how you override DreamGeek's getDescription method
    //Note the use of super.getDescription() invokes DreamGeek's getDescription() method, to print the geek's star sign and gender
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    //You should be noticing duplication between Friend and MoreThanAFriend
    @Override
    public String getDescription() {
        return this.getReligion()+", "+ super.getDescription() + " who loves the following games: "+this.getFavouriteGames().toString()+
                ". Their dream valentine's gift would be "+this.getFavouriteValentinesGift().toString()+
                ", and their favourite romantic activity is "+this.getMostRomanticActivity();
    }

    //This is how you override DreamGeek's matches method
    //Note the use of super.matches() invokes DreamGeek's matches() method, to ensure the age range, star sign and gender are compatible
    //Notice the method name, return type and parameter list are the same as the superclass (DreamGeek)
    //This method takes advantage of polymorphism = because a MoreThanAFriend IS-A DreamGeek, a DreamGeek can be passed in as a parameter and used as a MoreThanAFriend
    //You should be spotting a design flaw here - too much duplication between Friend and MoreThanAFriend
    @Override
    public boolean matches(DreamGeek realGeek) {
        //If the realGeek is a MoreThanAFriend (not a StudyBuddy or Friend), check if the MoreThanAFriend fields are compatible with the realGeek
        if(realGeek instanceof MoreThanAFriend geekDate) {
            if (!super.matches(realGeek)) return false;
            //if the superclass matches method returns true, test the MoreThanAFriend compatibility
            //Unique to more than a friend
            if (!this.getMostRomanticActivity().equals(geekDate.getMostRomanticActivity())) return false;
            if (!this.getFavouriteValentinesGift().equals(geekDate.getFavouriteValentinesGift())) return false;

            //duplication with Friend?
            if (!this.getReligion().equals(Religion.NA)) {
                if (!this.getReligion().equals(geekDate.getReligion())) return false;
            }
            Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
            if (this.getFavouriteGames().size() > 0) {
                gamesInCommon.retainAll(geekDate.getFavouriteGames());
                if (gamesInCommon.size() == 0) return false;
            }
            Set<String> tvShowsInCommon = new HashSet<>(this.getFavouriteTVShows());
            if (this.getFavouriteTVShows().size() > 0) {
                tvShowsInCommon.retainAll(geekDate.getFavouriteTVShows());
                return tvShowsInCommon.size() != 0;
            }
            return true; //this means that all the DreamGeek and MoreThanAFriend criteria match
        }
        return false;
    }
}
