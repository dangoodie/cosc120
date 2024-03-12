/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FansV2 {

    /**
     * This program demonstrates how repetitive code can be reduced by using methods
     * Re-enforces knowledge of Collections, i.e. Sets and Iterator
     * @param args none required
     */
    public static void main(String[] args) {
        //create an array of rick and morty fans
        String[] knownRickAndMortyFans = {"rick_n_morty","greek_geek","marlondjango","hexy_scripter","non_binary","greek_geek"};
        //assign the return value of the createFanSet method to a new Set variable
        Set<String> rickAndMortyFans = createFanSet(knownRickAndMortyFans);

        //repeat for the Simpsons and Breaking Bad
        String[] knownSimpsonsFans = {"hexsymbol","marlondjango","idiotproof","jimmyneutron","marlondjango","quanton_clint"};
        Set<String> simpsonsFans = createFanSet(knownSimpsonsFans);
        String[] knownBreakingBadFans = {"idiotproof","beta_version","ascii_challenged","non_binary","jimmyneutron"};
        Set<String> breakingBadFans = createFanSet(knownBreakingBadFans);

        //declare iterators for each
        Iterator<String> rickAndMortyIterator = rickAndMortyFans.iterator();
        Iterator<String> simpsonsIterator = simpsonsFans.iterator();
        Iterator<String> breakingBadIterator = breakingBadFans.iterator();

        System.out.println("------Rick and Morty Fans------");
        System.out.println("Before: "+rickAndMortyFans);
        removeElementsWithoutUnderscore(rickAndMortyIterator); //call method
        System.out.println("After: "+rickAndMortyFans);

        System.out.println("------The Simpsons Fans------");
        System.out.println("Before: "+simpsonsFans);
        removeElementsWithoutUnderscore(simpsonsIterator); //call method
        System.out.println("After: "+simpsonsFans);

        System.out.println("------Breaking Bad Fans------");
        System.out.println("Before: "+breakingBadFans);
        removeElementsWithoutUnderscore(breakingBadIterator); //call method
        System.out.println("After: "+breakingBadFans);

/*      All of this repetitive code is now removed.....
        System.out.println("------Rick and Morty Fans------");
        while(rickAndMortyIterator.hasNext()){
            String userName = rickAndMortyIterator.next();
            //while iterating through, check if each fans username contains an underscore
            if(!userName.contains("_")){
                //if it doesn't, remove the username from the set
                rickAndMortyIterator.remove();
                //skip to the next username (don't print this one, as it has been removed)
                continue;
            }
            //if the username does contain an underscore, print it
            System.out.println(userName);
        }

        //repeat this for each of the sets (a method would be useful here - more on that next week!)
        System.out.println("------The Simpsons Fans------");
        while(simpsonsIterator.hasNext()){
            String userName = simpsonsIterator.next();
            if(!userName.contains("_")){
                simpsonsIterator.remove();
                continue;
            }
            System.out.println(userName);
        }

        System.out.println("------Breaking Bad Fans------");
        while(breakingBadIterator.hasNext()){
            String userName = breakingBadIterator.next();
            if(!userName.contains("_")){
                breakingBadIterator.remove();
                continue;
            }
            System.out.println(userName);
        }*/
    }
    /**
     * adds all the fan usernames from the array to a Set (HashSet), removing duplicates
     * @param fans an array of fan usernames
     * @return a HashSet of fans
     */
    private static Set<String> createFanSet(String [] fans){
        Set<String> allFans = new HashSet<>();
        Collections.addAll(allFans, fans);
        return allFans;
    }

    /**
     * removes elements of the Set that do not contain an underscore
     * @param i an Iterator for a collection of usernames
     */
    private static void removeElementsWithoutUnderscore(Iterator<String> i){
        while(i.hasNext()){
            String userName = i.next();
            if(!userName.contains("_"))i.remove();
        }
    }

}

