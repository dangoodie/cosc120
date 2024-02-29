/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class AllGeeks {
    /**
     * This program demonstrates how to use ArrayList to create an array of variable length
     * Re-enforces knowledge/usage of file input
     * @param args none required
     */
    public static void main(String[] args) throws IOException {
        //relative file path
        String filePath = "./geeks.txt";

        //load the file
        Path path = Path.of(filePath);
        //reads content of file as a String
        String fileContents = Files.readString(path);
        //visualise the data
        System.out.println(fileContents);

        //create a String array of each geek in the file (each geek is separated by a newline character)
        String[] eachGeek = fileContents.split("\n");

        //create an ArrayList to contain the geeks
        ArrayList<String> geeks = new ArrayList<>();

        //add each geek to the ArrayList, printing the number of geeks as they are added
        for(String g: eachGeek){
            geeks.add(g);
            System.out.println("There are now "+geeks.size()+" geeks in the array...");
        }

        //let's add a new geek to the list
        String newGeek = "0475869723,Chen,Cynthia,11/06/1999";
        geeks.add(newGeek);

        //visualise results
        for(String g: geeks){
            System.out.println(g);
        }

        System.out.println("There are now "+geeks.size()+" geeks in the array.");
    }
}
