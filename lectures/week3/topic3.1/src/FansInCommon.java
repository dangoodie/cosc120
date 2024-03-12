/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Set;

public class FansInCommon {

    /**
     * demonstrates possible sources of error when using mutable objects
     * @param args non required
     */
    public static void main(String[] args){
        Set<String> rickAndMortyFans = new HashSet<>();
        //add some elements
        rickAndMortyFans.add("greek_geek");
        rickAndMortyFans.add("non_binary");
        rickAndMortyFans.add("hexy_scripter");
        rickAndMortyFans.add("marlon_django");

        Set<String> simpsonsFans = new HashSet<>();
        simpsonsFans.add("marlon_django");
        simpsonsFans.add("quanton_clint");
        simpsonsFans.add("idiotproof");

        //print contents of both sets
        System.out.println("*******BEFORE METHOD CALL*********");
        System.out.println("rickAndMortyFans: "+rickAndMortyFans);
        System.out.println("simpsonsFans: "+simpsonsFans);

        //use our method to print the elements in common
        System.out.println("\n*********METHOD CALL***********");
        checkFansInCommon(rickAndMortyFans,simpsonsFans);

        //print contents of both sets
        System.out.println("\n*******AFTER METHOD CALL*********");
        System.out.println("rickAndMortyFans: "+rickAndMortyFans);
        System.out.println("simpsonsFans: "+simpsonsFans);

    }

    private static void checkFansInCommon(Set<String> fansI, Set<String> fansII){
        //get the subset of both sets
        fansI.retainAll(fansII);
        System.out.println("Fans in common: "+fansI);
        //to fix this issue, create a copy of the mutable object, comment out the above 2 lines, and rerun
        /*
        Set<String> subset = new HashSet<>(fansI);
        subset.retainAll(fansII);
        System.out.println("Fans in common: "+subset);
        */
    }
}
