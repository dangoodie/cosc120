/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

/*
* Remains unchanged
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

    private final Set<Geek> geeks = new HashSet<>();

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
     * @param dreamGeek a Geek object representing a desired Geek - attributes username, name etc. should be empty Strings
     * @param username the program user's username
     * @param type type of dream geek the user is looking for (study buddy, friend or date)
     * @return a List of Geek object that fit the geek's dream geek criteria
     */
    //EDIT 5: add the type of geek to the parameter list
    public List<Geek> findDreamGeek(DreamGeek dreamGeek,String username, TypeOfDreamGeek type){
        List<Geek> matches = new ArrayList<>();
        Set<String> blockedGeeks = new HashSet<>();
        if(getGeek(username)!=null) blockedGeeks = getGeek(username).getBlockedGeeks();
        for(Geek g: geeks){
            DreamGeek genericFeatures = g.getGenericFeatures();
            //EDIT 6: if the user is after a friend, but the geek's generic features are that of MoreThanAFriend, skip them
            //This avoids printing both the Friend and MoreThanAFriend variants (which is because MoreThanAFriend is an instance of Friend)
            if(type.equals(TypeOfDreamGeek.FRIEND) && (genericFeatures instanceof MoreThanAFriend)) continue;

            if(username.equals(g.getUsername())) continue;
            if(blockedGeeks.contains(g.getUsername())) continue;
            if(g.getAge()<dreamGeek.getMinAge() || g.getAge()>dreamGeek.getMaxAge()) continue;
            if(dreamGeek.matches(genericFeatures)) matches.add(g);
        }
        return matches;
    }
}
