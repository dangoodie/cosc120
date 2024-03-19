/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * a class used to create a dataset of Geek objects
 * Note: default constructor used
 */
public class AllGeeks {
    //fields
    //final because it is only initialised once, when the file is read, and transformed into a dataset
    private final Map<String, Geek> geeks = new HashMap<>();

    //methods
    /**
     * adds a Geek object to the dataset of Geeks
     * @param username a unique username identifying the geek
     * @param geek a Geek object
     */
    public void addGeek(String username, Geek geek){
        geeks.put(username,geek);
    }

    /**
     * method used to get a Geek object, given their username
     * @param username a String value representing a Geek's username
     * @return a Geek object if the username is valid, else null
     */
    public Geek getGeek(String username){
        //if the username is a key in the Map, return the associated geek object
        if(geeks.containsKey(username)) return geeks.get(username);
        return null;
    }

    /**
     * method that compares a dream geek (a geek's set of criteria) against all the 'real' geeks in the dataset
     * @param username the program user's username
     * @param dreamGeek a Geek object representing a desired Geek - attributes username, name etc. should be empty Strings
     * @return a Geek object that fits the geek's dream geek criteria
     */
    public Geek findDreamGeek(Geek dreamGeek,String username){
        Set<String> blockedGeeks = new HashSet<>();
        //if the program's user is a registered geek (therefore they're in the dataset), get their set of blockedGeeks
        Geek user = getGeek(username);
        if(user!=null) blockedGeeks = user.getBlockedGeeks();
        //iterate through all the geek usernames in the dataset
        for(String geekUsername: geeks.keySet()){
            //extract the geek object corresponding to the username
            Geek geek = geeks.get(geekUsername);
            //don't suggest a geek to themselves!
            if(username.equals(geek.getUsername())) continue;
            //if a geek is in the program user's blocked set, skip them
            if(blockedGeeks.contains(geek.getUsername())) continue;
            //if the geek's gender doesn't match the dream geek's gender, skip them
            if(!geek.getGender().equals(dreamGeek.getGender())) continue;
            //if the geek's age doesn't match the dream geek's age, skip them
            if(geek.getAge()!=dreamGeek.getAge()) continue;
            //if the geek's star sign doesn't match the dream geek's star sign, skip them
            if(!geek.getStarSign().equals(dreamGeek.getStarSign())) continue;
            //check if there are any games in common between the dreamGeek and the geek
            if(dreamGeek.getFavouriteGames().size()==0) return geek; //this will be empty if the user chose not to include games in their search criteria
            else{
                //create a copy of th dream geek's favourite games (to protect the mutable set)
                //Note: this is not essential, as the Geek class getter is already returning a copy, i.e.
                // public Set<String> getFavouriteGames(){
                //        return new HashSet<>(favouriteGames);
                //    }
                //however it is good practice to over-protect, e.g., you may be using a class created by someone else, who may not have protected their mutable types!
                Set<String> gamesInCommon = new HashSet<>(dreamGeek.getFavouriteGames());
                gamesInCommon.retainAll(geek.getFavouriteGames());
                if(gamesInCommon.size()>0) return geek; //if there are games in common, return the match
            }
        }
        //the method will return null if no geeks match the dreamGeek criteria
        return null;
    }
}
