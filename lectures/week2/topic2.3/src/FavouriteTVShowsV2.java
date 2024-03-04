/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FavouriteTVShowsV2 {
    /**
     * This program demonstrates use of Map data structures, including
     * adding values and looping through keys, values and key-value pairs
     * Re-enforces knowledge/usage of file input and Sets
     * Introduces forEach method to print Collections
     * @param args none required
     */
    public static void main(String[] args) throws IOException {

        //create a hashmap to store key (geek's name) and value (set of tv shows) pairs
        Map<String, Set<String>> favoriteTVShowsHashMap = new HashMap<>();

        //string representing path to file
        String filePath = "TVshows.txt";
        //load the file
        Path path = Path.of(filePath);
        //reads content of file as a String
        List<String> starSigns = Files.readAllLines(path);

        //loop the data for each geek, creating a new HashSet for each geek's shows
        for (String geek:starSigns) {
            //split the geek's data based on the colon (what's on the left of the colon is the name, to the right are the shows)
            String geeksName = geek.split(":")[0];
            String allGeeksShows = geek.split(":")[1];
            //now split the shows based on comma, and add each show to the hashset
            String[] eachGeekShow = allGeeksShows.split(",");
            Set<String> favoriteTVShowsHashSet = new HashSet<>(Arrays.asList(eachGeekShow));
            //now add the geek name as key, and the hashset as value, into the map
            favoriteTVShowsHashMap.put(geeksName,favoriteTVShowsHashSet);
        }

        //print all the geeks' names (keys)
        System.out.println("---------------KEYS-------------------");
        for (String geek:favoriteTVShowsHashMap.keySet()) {
            System.out.println(geek);
        }

        //print all the geeks' favourite shows (values)
        System.out.println("---------------VALUES-------------------");
        for (Set<String> shows:favoriteTVShowsHashMap.values()) {
            //the forEach method can be used to loop over a Set in one line, printing each value
            shows.forEach(System.out::println);
        }

        System.out.println("---------------KEYS AND VALUES-------------------");
        //now let's print both the key and values
        for (String geek:favoriteTVShowsHashMap.keySet()) {
            System.out.println(geek); //prints geek name
            Set<String> shows = favoriteTVShowsHashMap.get(geek); //gets the set of shows for the geek
            shows.forEach(System.out::println); //prints one show on each line
        }
    }
}
