/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.*;

/**
 * a class used to create a database of Geek objects
 * Note: default constructor used
 */
public class AllGeeks {
    //fields
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
     * method that compares a dream geek (a geek's set of criteria) against all the geeks in the database
     * and returns a collection of matching Geeks
     * @param username the program user's username
     * @param dreamGeek a Geek object representing a desired Geek - attributes username, name etc. should be empty Strings
     * @return a List of Geek objects that fit the geek's dream geek criteria
     */
    //EDIT 12: change the return type from Geek to List<Geek> to return more than one Geek
    public List<Geek> findDreamGeek(Geek dreamGeek,String username){
        //EDIT 12: create an empty List to contain any potential Geek match. A List is better than a Set
        //because we'll be iterating through it (more efficient)
        List<Geek> matches = new ArrayList<>();
        Set<String> blockedGeeks = new HashSet<>();
        if(getGeek(username)!=null) blockedGeeks = getGeek(username).getBlockedGeeks();
        for(String geekUsername: geeks.keySet()){
            //extract the geek object corresponding to the username
            Geek geek = geeks.get(geekUsername);
            if(username.equals(geek.getUsername())) continue;
            if(blockedGeeks.contains(geek.getUsername())) continue;
            if(!geek.getGender().equals(dreamGeek.getGender())) continue;

            //EDIT 17: search based on age range rather than age -> improves functionality
            //if(g.getAge()!=dreamGeek.getAge()) continue;
            if(geek.getAge()<dreamGeek.getMinAge() || geek.getAge()>dreamGeek.getMaxAge()) continue;
            //END EDIT 17

            if(!geek.getStarSign().equals(dreamGeek.getStarSign())) continue;

            //EDIT 12: instead of returning when a match is found, add it to the Set
            if(dreamGeek.getFavouriteGames().size()==0) matches.add(geek);//return g;
            else{
                Set<String> gamesInCommon = new HashSet<>(dreamGeek.getFavouriteGames());
                gamesInCommon.retainAll(geek.getFavouriteGames());
                //EDIT 12: instead of returning when a match is found, add it to the Set
                if(gamesInCommon.size()>0) matches.add(geek);//return g;
            }
        }
        return matches; //EDIT 12: return the List containing the matches
    }
}
