/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

/**
 * class from which Geek objects can be created.
 * with some simplification, this class may be a good candidate for conversion to a record
 */
public class Geek {
    //fields
    private final String username;
    private final String name;
    private final int age;
    //EDIT 14: introduce age range variables
    private int minAge;
    private int maxAge;
    //EDIT 9: changed from String to enum Gender
    private final Gender gender;
    //EDIT 4: changed from String to enum StarSign
    private final StarSign starSign;
    private final String statement;
    private Set<String> favouriteGames = new HashSet<>();
    private Set<String> blockedGeeks = new HashSet<>();
    private final String phoneNumber;

    /**
     * Constructor used to create a Geek object
     * @param username a unique String value used to identify an individual geek
     * @param name a String value representing the Geek's first, last and any middle names
     * @param age an int value representing the Geek's age
     * @param gender a Gender value representing the Geek's gender (male, female or other)
     * @param starSign a StarSign representing the geek's star sign
     * @param statement a String representing a statement about what makes this person a geek
     * @param favouriteGames a Set of Strings, representing the geek's favourite computer games
     * @param blockedGeeks a Set of Strings representing the usernames of blocked Geeks
     * @param phoneNumber a String representing the geek's 10-digit phone number
     */
    public Geek(String username, String name, int age, Gender gender, StarSign starSign,//EDIT 4 and 9
                String statement, Set<String> favouriteGames, Set<String> blockedGeeks, String phoneNumber){
        this.username=username;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.starSign=starSign;
        this.statement=statement;
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        if(blockedGeeks!=null) this.blockedGeeks=new HashSet<>(blockedGeeks);
        this.phoneNumber=phoneNumber;
    }

    //getters
    /**
     * username is unique to the Geek object
     * @return the Geek's username
     */
    public String getUsername(){
        return username; //no need for 'this' here, because there is no ambiguity about which variable is being referred to
    }

    /**
     * full name, including first name, surname and any middle names
     * @return the Geek's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * int representing the Geek's age in years
     * @return the Geek's age
     */
    public int getAge(){
        return this.age;
    }

    //EDIT 14: getters for min and max age
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

    //EDIT 9: return type changed to Gender
    /**
     * gender: male, female or other
     * @return the Geek's gender
     */
    public Gender getGender(){
        return this.gender;
    }

    //EDIT 4: return type changed to StarSign
    /**
     * one of 12 western astrological star signs
     * @return the Geek's star sign
     */
    public StarSign getStarSign(){
        return this.starSign;
    }

    /**
     * 10-digit phone number
     * @return the Geek's phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * A String of phrases/sentences set by the user when they sign up. May contain any available character/s.
     * @return the Geek's statement of why they consider themselves to be a geek
     */
    public String getStatement(){
        return this.statement;
    }

    /**
     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
     * @return a Set (HashSet) of all this geek's favourite computer games
     */
    public Set<String> getFavouriteGames(){
        return new HashSet<>(favouriteGames);
    }

    /**
     * when a Geek blocks another Geek, the blocked Geek's username is added to this Set
     * @return a Set (HashSet) of all the usernames of geeks blocked by 'this' geek
     */
    public Set<String> getBlockedGeeks(){
        return new HashSet<>(blockedGeeks);
    }

    //setters
    //EDIT 14: setters for the min and max age
    /**
     * used to set min age of a user's dream geek
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
    /**
     * used to set max age of a user's dream geek
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    //other methods
    /**
     * adds a Geek's username to 'this' Geek's list of block Geeks
     * @param g a Geek object
     */
    public void blockGeek(Geek g){
        blockedGeeks.add(g.getUsername());
    }
}