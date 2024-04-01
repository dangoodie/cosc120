/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class DreamGeek {
    //fields
    private final Gender gender;
    private final StarSign starSign;
    private final int minAge;
    private final int maxAge;
    private final Religion religion;
    private Set<String> favouriteGames = new HashSet<>();
    //EDIT 1: add field to represent favouriteTVShows
    private Set<String> favouriteTVShows = new HashSet<>();

    /**
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param religion an enum constant representing the geek's religion
     * @param favouriteGames a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     */
    public DreamGeek(int minAge, int maxAge, Gender gender, StarSign starSign, Religion religion,
                     Set<String> favouriteGames, Set<String> favouriteTVShows){ //EDIT 1
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.gender=gender;
        this.starSign=starSign;
        this.religion = religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        //EDIT 1: initialise the field in the constructor
        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
    }

    //getters
    /**
     * used to get min age of a user's dream geek
     * @return the min age
     */
    public int getMinAge() {
        return minAge;
    }
    /**
     * used to get max age of a user's dream geek
     * @return the max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * gender: male, female or other
     * @return the Geek's gender
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     * one of 12 western astrological star signs
     * @return the Geek's star sign
     */
    public StarSign getStarSign(){
        return this.starSign;
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

    //EDIT 1: add a getter for the new field
    /**
     * contains the String names/titles of the Geek's preferred tv shows (duplicates not allowed)
     * @return a Set (HashSet) of all this geek's favourite tv shows
     */
    public Set<String> getFavouriteTVShows(){
        return new HashSet<>(favouriteTVShows);
    }

    //other methods

    /**
     * @return a String description of the DreamGeek - religion, star sign, gender and games
     */
    public String getDescription(){
        return this.getReligion()+", "+this.getStarSign()+" "+
                this.getGender()+" who loves the following games: "+this.getFavouriteGames().toString()+
                //EDIT 3: add the favourite tv shows to the DreamGeek feature description String.
                " and the following tv shows: "+this.getFavouriteTVShows();
    }


    /**
     * @return a String of the DreamGeek data in the format required by the file
     */
    public String getDatabaseEntry(){
        return this.getGender()+","+ this.getStarSign()+","+ this.getReligion()+
                ","+ this.getFavouriteGames().toString().replace(", ",",")+","
                //EDIT 2: input favourite tv shows in the database entry string
                + this.getFavouriteTVShows().toString().replace(", ",",");
    }

    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        if (!this.getGender().equals(realGeek.getGender())) return false;
        //EDIT 10 - only consider religion if the user doesn't select NA
        if (!this.getStarSign().equals(StarSign.NA)){
            if (!this.getStarSign().equals(realGeek.getStarSign())) return false;
        }
        //EDIT 10 - only consider religion if the user doesn't select NA
        if (!this.getReligion().equals(Religion.NA)){
            if (!this.getReligion().equals(realGeek.getReligion())) return false;
        }
        Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
        if (this.getFavouriteGames().size() > 0) {
            gamesInCommon.retainAll(realGeek.getFavouriteGames());
            if(gamesInCommon.size()==0) return false;
        }
        //EDIT 4: alter this method to consider compatibility in terms of favouriteTVShows.
        Set<String> tvShowsInCommon = new HashSet<>(this.getFavouriteTVShows());
        if (this.getFavouriteTVShows().size()>0){
            tvShowsInCommon.retainAll(realGeek.getFavouriteTVShows());
            return tvShowsInCommon.size() != 0;
        }
        return true;
    }
}
