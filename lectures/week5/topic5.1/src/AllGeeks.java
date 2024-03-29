/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * a class used to create a database of Geek objects
 * Note: default constructor used
 */
public class AllGeeks {
    //fields
    private final Set<Geek> geeks = new HashSet<>();

    //methods
    /**
     * adds a Geek object to the database of Geeks
     * @param g a Geek object
     */
    public void addGeek(Geek g){
        geeks.add(g);
    }

    /**
     * method used to get a Geek object, given their username
     * @param username a String value representing a Geek's username
     * @return a Geek object if the username is valid, else null
     */
    public Geek getGeek(String username){
        for(Geek g:geeks) {
            if (g.getUsername().equals(username)) return g;
        }
        return null;
    }

    /**
     * method that compares a dream geek (a geek's set of criteria) against all the geeks in the database
     * and returns a collection of matching Geeks
     * @param username the program user's username
     * @param dreamGeek a Geek object representing a desired Geek - attributes username, name etc. should be empty Strings
     * @return a List of Geek object that fit the geek's dream geek criteria
     */
    //EDIT 7: change the first parameter datatype to DreamGeek.
    public List<Geek> findDreamGeek(DreamGeek dreamGeek,String username){
        List<Geek> matches = new ArrayList<>();
        Set<String> blockedGeeks = new HashSet<>();
        if(getGeek(username)!=null) blockedGeeks = getGeek(username).getBlockedGeeks();
        for(Geek g: geeks){
            //EDIT 7: create a DreamGeek object to represent each geek's generic features
            DreamGeek genericFeatures = g.getGenericFeatures();
            if(username.equals(g.getUsername())) continue;
            if(blockedGeeks.contains(g.getUsername())) continue;
            //EDIT 7: access the real geek's generic features via the genericFeatures variable
            if(!genericFeatures.getGender().equals(dreamGeek.getGender())) continue;
            if(g.getAge()<dreamGeek.getMinAge() || g.getAge()>dreamGeek.getMaxAge()) continue;
            //EDIT 7: access the real geek's generic features via the genericFeatures variable
            if(!genericFeatures.getStarSign().equals(dreamGeek.getStarSign())) continue;
            if(dreamGeek.getFavouriteGames().size()==0) matches.add(g);
            else{
                Set<String> gamesInCommon = new HashSet<>(dreamGeek.getFavouriteGames());
                //EDIT 7: access the real geek's generic features via the genericFeatures variable
                gamesInCommon.retainAll(genericFeatures.getFavouriteGames());
                if(gamesInCommon.size()>0) matches.add(g);
            }
        }
        return matches;
    }
}
