/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadWriteGeeks {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args) throws IOException {
        //Absolute path: "C:/Users/andre/OneDrive/Documents/COSC120/Lectures/Topic 1/geeks.txt";
        //File path can also be "C:\\Users\\andre\\OneDrive\\Documents\\COSC120\\Lectures\\Topic 1\\geeks.txt";
        //Relative file path
        String filePath = "geeks.txt";

        //load the file
        Path path = Path.of(filePath);
        //reads content of file as a String
        String fileContents = Files.readString(path);
        //prints file contents in console
        System.out.println(fileContents);

        //new Geek to add to file (note \n puts the new geek on a new line)
        String newGeek = "\n0475869723,Chen,Cynthia,11/06/1999";
        //concatenate current file contents and new content
        String toWrite = fileContents+newGeek;
        //write the new geek to the file
        Files.writeString(path,toWrite);
    }

    /*
    But what if the file doesn't exist? As is, this will just 'throw' an exception....
    What if we want a more meaningful message to be printed?
     */

}
