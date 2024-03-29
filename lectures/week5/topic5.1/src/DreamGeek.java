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
    //EDIT 2: these can now be declared as final
    private final int minAge;
    private final int maxAge;

    /**
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param favouriteGames a Set of Strings, representing the geek's favourite computer games
     */
    //EDIT 2: note we can now set the min age and max age in the constructor, removing the need for the setters
    public DreamGeek(int minAge, int maxAge, Gender gender, StarSign starSign, Set<String> favouriteGames){
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.gender=gender;
        this.starSign=starSign;
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

}
