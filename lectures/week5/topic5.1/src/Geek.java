/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

/**
 * class from which Geek objects can be created
 */
public class Geek {
    //fields
    private final String username;
    private final String name;
    private final int age;
    //EDIT 1: move 'generic' dream geek fields to DreamGeek
//    private int minAge;
//    private int maxAge;
//    private final Gender gender;
//    private final StarSign starSign;
//    private Set<String> favouriteGames = new HashSet<>();
    private final String statement;
    private Set<String> blockedGeeks = new HashSet<>();
    private final String phoneNumber;
    //EDIT 1: add a DreamGeek field
    private final DreamGeek genericFeatures;

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
     * @param phoneNumber a String value representing the geek's 10 digit phone number
     */
    public Geek(String username, String name, int age, Gender gender, StarSign starSign,
                String statement, Set<String> favouriteGames, Set<String> blockedGeeks, String phoneNumber){
        this.username=username;
        this.name=name;
        this.age=age;
        //EDIT 4: use the parameters to initialise the DreamGeek genericFeatures field
        genericFeatures = new DreamGeek(0,0,gender, starSign,favouriteGames);
        //this.gender=gender;
        //this.starSign=starSign;
        //if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
        this.statement=statement;
        if(blockedGeeks!=null) this.blockedGeeks=new HashSet<>(blockedGeeks);
        this.phoneNumber=phoneNumber;
    }

    //getters
    /**
     * username is unique to the Geek object
     * @return the Geek's username
     */
    public String getUsername(){
        return username;
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

    //EDIT 5: add a getter for the DreamGeek generic features
    /**
     * @return the real geek's generic features inc. gender, star sign and favourite games
     */
    public DreamGeek getGenericFeatures(){
        return genericFeatures;
    }

    //EDIT 5: move relevant getters to DreamGeek
//    /**
//     * used to get min age of a user's dream geek
//     * @return the min age
//     */
//    public int getMinAge() {
//        return minAge;
//    }
//    /**
//     * used to get max age of a user's dream geek
//     * @return the max age
//     */
//    public int getMaxAge() {
//        return maxAge;
//    }
//
//    /**
//     * gender: male, female or other
//     * @return the Geek's gender
//     */
//    public Gender getGender(){
//        return this.gender;
//    }
//
//    /**
//     * one of 12 western astrological star signs
//     * @return the Geek's star sign
//     */
//    public StarSign getStarSign(){
//        return this.starSign;
//    }
//
//    /**
//     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
//     * @return a Set (HashSet) of all this geek's favourite computer games
//     */
//    public Set<String> getFavouriteGames(){
//        return new HashSet<>(favouriteGames);
//    }

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
     * when a Geek blocks another Geek, the blocked Geek's username is added to this Set
     * @return a Set (HashSet) of all the usernames of geeks blocked by 'this' geek
     */
    public Set<String> getBlockedGeeks(){
        return new HashSet<>(blockedGeeks);
    }

    //EDIT 3: remove setters that use to be used to initialise the age range for the 'dream' geek
//    /**
//     * used to set min age of a user's dream geek
//     */
//    public void setMinAge(int minAge) {
//        this.minAge = minAge;
//    }
//    /**
//     * used to set max age of a user's dream geek
//     */
//    public void setMaxAge(int maxAge) {
//        this.maxAge = maxAge;
//    }

    //other methods
    /**
     * adds a Geek's username to 'this' Geek's list of block Geeks
     * @param g a Geek object
     */
    public void blockGeek(Geek g){
        blockedGeeks.add(g.getUsername());
    }
}