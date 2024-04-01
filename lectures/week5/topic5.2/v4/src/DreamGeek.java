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
    private Set<String> favouriteGames = new HashSet<>();
    private final int minAge;
    private final int maxAge;
    private final Religion religion;

    /**
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param favouriteGames a Set of Strings, representing the geek's favourite computer games
     * @param religion an enum constant representing the geek's religion
     */
    public DreamGeek(int minAge, int maxAge, Gender gender, StarSign starSign, Set<String> favouriteGames, Religion religion){
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.gender=gender;
        this.starSign=starSign;
        this.religion = religion;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
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
     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
     * @return a Set (HashSet) of all this geek's favourite computer games
     */
    public Set<String> getFavouriteGames(){
        return new HashSet<>(favouriteGames);
    }

    /**
     * @return an enum constant representing the dream geek's religion
     */
    public Religion getReligion() {
        return religion;
    }

    //other methods

    //EDIT 3: output DreamGeek info using this method
    /**
     * @return a String description of the DreamGeek - religion, star sign, gender and games
     */
    public String getDescription(){
        return this.getReligion()+", "+this.getStarSign()+" "+
                this.getGender()+" who loves the following games: "+this.getFavouriteGames().toString();
    }

    //EDIT 3: output DreamGeek info using this method
    /**
     * @return a String of the DreamGeek data in the format required by the file
     */
    public String getDatabaseEntry(){
        return this.getGender()+","+ this.getStarSign()+","+ this.getReligion()+","+ this.getFavouriteGames().toString().replace(", ",",");
    }

    //EDIT 5: To decouple AllGeeks from DreamGeek, we can delegate the comparison of DreamGeek objects to the DreamGeek class.
    //Thus, regardless of the changes we make to DreamGeek, the findDreamGeek method will remain unaffected.
    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        //if the geek's gender doesn't match the dream geek's gender, skip them
        if (!this.getGender().equals(realGeek.getGender())) return false;
        //if the geek's star sign doesn't match the dream geek's star sign, skip them
        if (!this.getStarSign().equals(realGeek.getStarSign())) return false;
        //if the geek's religion doesn't match the dream geek's religion, skip them
        if (!this.getReligion().equals(realGeek.getReligion())) return false;
        //check if there are games in common (if relevant)
        if(this.getFavouriteGames().size()==0) return true;
        else{
            Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
            gamesInCommon.retainAll(realGeek.getFavouriteGames());
            return gamesInCommon.size() > 0;
        }
    }
}
