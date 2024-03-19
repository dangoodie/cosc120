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
    //these are final because they are only ever initialised once (by the constructor)
    private final String username;
    private final String name;
    private final int age;
    private final String gender;
    private final String starSign;
    private final String statement;
    //these are not final because they can be changed after they are initialised
    private Set<String> favouriteGames = new HashSet<>(); //initialised to an empty Set by default
    private Set<String> blockedGeeks = new HashSet<>(); //initialised to an empty Set by default
    private final long phoneNumber;

    /**
     * Constructor used to create a Geek object
     * @param username a unique String value used to identify an individual geek
     * @param name a String value representing the Geek's first, last and any middle names
     * @param age an int value representing the Geek's age
     * @param gender a String value representing the Geek's gender (male, female or other)
     * @param starSign a String representing the geek's star sign
     * @param statement a String representing a statement about what makes this person a geek
     * @param favouriteGames a Set of Strings, representing the geek's favourite computer games
     * @param blockedGeeks a Set of Strings representing the usernames of blocked Geeks
     * @param phoneNumber a long value representing the geek's 10 digit phone number
     */
    public Geek(String username, String name, int age, String gender, String starSign,
                String statement, Set<String> favouriteGames, Set<String> blockedGeeks, long phoneNumber){
        //this.variableName refers to the field, i.e. the variable belongs to 'this' class.
        //The 'this' keyword is useful when parameters variables have the same name as field variables
        this.username=username;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.starSign=starSign;
        this.statement=statement;
        //use the new keyword when initialising mutable fields with existing mutable objects. This assigns a
        //copy of the mutable object assigned to the parameter, protecting the field from inadvertent changes
        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames); //assigns a new Set object containing games upon initialisation
        if(blockedGeeks!=null) this.blockedGeeks=new HashSet<>(blockedGeeks); //assigns a new Set object containing blocked geeks upon initialisation
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

    /**
     * gender: male, female or other
     * @return the Geek's gender
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * one of 12 western astrological star signs
     * @return the Geek's star sign
     */
    public String getStarSign(){
        return this.starSign;
    }

    /**
     * 10-digit phone number
     * @return the Geek's phone number
     */
    public long getPhoneNumber() {
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

    //other methods
    /**
     * adds a Geek's username to 'this' Geek's list of block Geeks
     * @param g a Geek object
     */
    public void blockGeek(Geek g){
        blockedGeeks.add(g.getUsername());
    }
}