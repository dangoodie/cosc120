/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class Friend extends DreamGeek{
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
    public Friend(int minAge, int maxAge, Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames, Set<String> favouriteTVShows) {
        super(minAge, maxAge, gender, starSign);
        this.religion = religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
    }

    /**
     * @param gender           an enum value representing the Geek's gender (male, female or other)
     * @param starSign         an enum representing the geek's star sign
     * @param religion         an enum constant representing the geek's religion
     * @param favouriteGames   a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     */
    //EDIT 12: create a constructor for objects created to represent real geeks - no age range
    public Friend(Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames, Set<String> favouriteTVShows) {
        super(gender, starSign);
        this.religion = religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
    }

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

    @Override
    public String getDescription(){
        return this.getReligion()+", "+ super.getDescription() +". \nFavourite games: "+this.getFavouriteGames().toString()+
        "\nFavourite tv shows: "+this.getFavouriteTVShows();
    }

    @Override
    public boolean matches(DreamGeek realGeek) {
        if(realGeek instanceof Friend geekFriend) {
            if (!super.matches(realGeek)) return false;
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
            return true;
        }
        return false;
    }
}