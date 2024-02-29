/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Fans {
    /**
     * This program demonstrates the use of Iterator to iterate through and modify Sets
     * Demonstrates more Set functions, e.g., intersection of two sets
     * Illustrates the need to use methods to minimise repetitive code
     * @param args none required
     */
    public static void main(String[] args) {
        //create one Set for each set of fans
        Set<String> rickAndMortyFans = new HashSet<>();
        Set<String> simpsonsFans = new HashSet<>();
        Set<String> breakingBadFans = new HashSet<>();

        //populate the sets with fans
        rickAndMortyFans.add("rick_n_morty");
        rickAndMortyFans.add("greek_geek");
        rickAndMortyFans.add("marlondjango");
        rickAndMortyFans.add("hexy_scripter");
        rickAndMortyFans.add("non_binary");
        simpsonsFans.add("hexsymbol");
        simpsonsFans.add("idiot_proof");
        simpsonsFans.add("jimmy_neutron");
        simpsonsFans.add("marlondjango");
        simpsonsFans.add("quanton_clint");
        breakingBadFans.add("idiot_proof");
        breakingBadFans.add("beta_version");
        breakingBadFans.add("ascii_challenged");
        breakingBadFans.add("non_binary");
        breakingBadFans.add("jimmy_neutron");

        //declare iterators for each
        Iterator<String> rickAndMortyIterator = rickAndMortyFans.iterator();
        Iterator<String> simpsonsIterator = simpsonsFans.iterator();
        Iterator<String> breakingBadIterator = breakingBadFans.iterator();

        //use the iterators to print all the fans in each set
        System.out.println("------Rick and Morty Fans------");
        while(rickAndMortyIterator.hasNext()){
            String userName = rickAndMortyIterator.next();
            //while iterating through, check if each fans username contains an underscore
            if(!userName.contains("_")){
                //if it doesn't, remove the username from the set
                System.out.println("removed "+userName);
                rickAndMortyIterator.remove();
                //skip to the next username (don't print this one, as it has been removed)
                continue;
            }
            //if the username does contain an underscore, print it
            System.out.println(userName);
        }

        //repeat this for each of the sets (a method would be useful here - more on that next week!)
        System.out.println("\n------The Simpsons Fans------");
        while(simpsonsIterator.hasNext()){
            String userName = simpsonsIterator.next();
            if(!userName.contains("_")){
                System.out.println("removed "+userName);
                simpsonsIterator.remove();
                continue;
            }
            System.out.println(userName);
        }

        System.out.println("\n------Breaking Bad Fans------");
        while(breakingBadIterator.hasNext()){
            String userName = breakingBadIterator.next();
            if(!userName.contains("_")){
                System.out.println("removed "+userName);
                breakingBadIterator.remove();
                continue;
            }
            System.out.println(userName);
        }

        //Let see who is a fan of both Rick and Morty and Breaking Bad
        System.out.println("\n------Fans of both Rick and Morty and Breaking Bad------");
        //create a set, and populate it with the values in the rickAndMortyFans set
        Set<String> rickAndMortyBreakingBadFans = new HashSet<>(rickAndMortyFans);
        //use the retainAll() method to keep only the values that are also in breakingBadFans
        //this gives us the intersection of the sets
        rickAndMortyBreakingBadFans.retainAll(breakingBadFans);
        //create an Iterator for the intersection set, and print its contents
        for (String rickAndMortyBreakingBadFan : rickAndMortyBreakingBadFans)
            System.out.println(rickAndMortyBreakingBadFan);

        //Let see how many geeks are fans of both The Simpsons and Breaking Bad
        Set<String> simpsonsBreakingBadFans = new HashSet<>(simpsonsFans);
        simpsonsBreakingBadFans.retainAll(breakingBadFans);
        System.out.println("\nThere are "+simpsonsBreakingBadFans.size()+" fans of both The Simpsons and Breaking Bad");
    }
}
