/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StarSigns {
    /**
     * This program demonstrates how to use remove, add and set elements in an ArrayList
     * Re-enforces knowledge/usage of file input and output
     * @param args none required
     */
    public static void main(String[] args) throws IOException {
        String filePath = "./starSigns.txt";
        Path path = Path.of(filePath);

        //another way to read a file....
        List<String> starSigns = Files.readAllLines(path);

        //check the length of the array - there should be twelve star-signs.
        System.out.println("The file contains "+starSigns.size()+" entries");
        System.out.println("**\tList before changes\t** "+starSigns);

        //There is one missing, and it contains a smiley face!

        //let's remove the smiley face between virgo and libra
        starSigns.remove(8);
        System.out.println("**\tList after removal of smiley face\t** "+starSigns);

        //next, add Taurus into the 5th position
        starSigns.add(4,"taurus");
        System.out.println("**\tList after addition of taurus\t** "+starSigns);

        //Sagittarius has been spelled incorrectly, let's fix it
        starSigns.set(11,"sagittarius");
        System.out.println("**\tList after correcting spelling error - sagittarius\t** "+starSigns);

        //Now rewrite the file

        /* you can do it using String concatenation, like this:
        String contentToWrite = "";
        for(String ss:starSigns){
            contentToWrite+=ss+"\n";//ensure you add new line char
        }
         */

        //but the better way to do it is by using StringBuilder, like this:
        StringBuilder contentToWrite = new StringBuilder();
        for(String ss:starSigns){
            contentToWrite.append(ss).append("\n");//ensure you add new line char
        }
        Files.writeString(path, contentToWrite.toString());
    }
}
