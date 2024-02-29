/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class FavouriteTVShows {
    /**
     * This program compares the HashSet, LinkedHashSet and TreeSet
     * Re-enforces knowledge/usage of JOptionPane input and processing
     * Re-enforces knowledge/usage of do while/while loops
     * @param args none required
     */
    public static void main(String[] args) {

        //Set data structures
        Set<String> favoriteTVShowsHashSet = new HashSet<>();
        Set<String> favoriteTVShowsLinkedHashSet = new LinkedHashSet<>();
        Set<String> favoriteTVShowsTreeSet = new TreeSet<>();

        //request that the user input their favourite tv show
        String favoriteShow = JOptionPane.showInputDialog("Please enter your favorite TV show. Click cancel or close the dialog to exit. ");

        //if the user clicks close or cancel, show a message dialog confirming exit.
        if (favoriteShow == null) {
            JOptionPane.showMessageDialog(null, "Process cancelled.");
            System.exit(0);//terminate the program
        }

        //a try catch is necessary in case the user doesn't enter anything, and clicks 'cancel'
        try {
            /*as long as the user isn't finished entering fave tv shows, continue requesting input
            we could use a do - while for this, as there is duplicate code, however we want the first message
            to the user to be different to the ensuing messages, i.e. the second/third etc. times, the message
            reads "next tv show", which means the while loop is more appropriate
             */
            while (!favoriteShow.equals("done")) {
                System.out.println("User input: " + favoriteShow);
                //ensuring length of user input is more than 0 ensures an empty string isn't added to the set
                //this allows the user to 'bump' the enter button without consequences
                if (favoriteShow.length() > 0) {
                    //add the user entry to each of the Sets
                    favoriteTVShowsHashSet.add(favoriteShow);
                    favoriteTVShowsLinkedHashSet.add(favoriteShow);
                    favoriteTVShowsTreeSet.add(favoriteShow);
                }
                favoriteShow = JOptionPane.showInputDialog("Please enter your next favorite TV show." +
                        "\n Type \"done\" when you've finished or \"q\" to exit. ");
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Process cancelled.");
            favoriteTVShowsHashSet.clear();
            favoriteTVShowsLinkedHashSet.clear();
            favoriteTVShowsTreeSet.clear();
            System.exit(0); //terminate the program normally
        }

        //the HashSet doesn't sort or preserve insertion order - but it is the fastest!
        System.out.println("HashSet - " + favoriteTVShowsHashSet);
        //the LinkedHashSet preserves insertion order - good for ordering from most favorite to least favorite - no other way to sort
        System.out.println("LinkedHashSet - " + favoriteTVShowsLinkedHashSet);
        //the TreeSet is sorted in alphabetical order
        System.out.println("TreeSet - " + favoriteTVShowsTreeSet);
        System.exit(0); //terminate the program normally
    }
}