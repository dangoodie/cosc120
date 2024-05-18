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

    private final Set<Geek> geeks = new HashSet<>();

    /**
     * adds a Geek object to the database of Geeks
     * @param g a Geek object
     */
    public void addGeek(Geek g){
        geeks.add(g);
    }

    /**
     * method that compares a dream geek (a geek's set of criteria) against all the geeks in the database
     * and returns a collection of matching Geeks
     * @param dreamGeek a Geek object representing a desired Geek - attributes username, name etc. should be empty Strings
    // * @param username the program user's username
     * @return a List of Geek object that fit the geek's dream geek criteria
     */
    public List<Geek> findDreamGeek(DreamGeek dreamGeek){//EDIT 36 },String username){
        List<Geek> matches = new ArrayList<>();
        //EDIT 36 Set<String> blockedGeeks = new HashSet<>();
        //EDIT 36 if(getGeek(username)!=null) blockedGeeks = getGeek(username).getBlockedGeeks();
        for(Geek g: geeks){
            DreamGeek genericFeatures = g.getGenericFeatures();
            //EDIT 36 if(username.equals(g.getUsername())) continue;
            //EDIT 36 if(blockedGeeks.contains(g.getUsername())) continue;
            if(g.getAge()<dreamGeek.getMinAge() || g.getAge()>dreamGeek.getMaxAge()) continue;
            if(dreamGeek.matches(genericFeatures)) matches.add(g);
        }
        return matches;
    }
}
