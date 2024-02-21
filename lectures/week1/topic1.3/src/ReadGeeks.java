/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadGeeks {

    /**
     * main method - all the code will be in here
     * @param args arguments that may be passed in when run on the command line
     */
    public static void main(String[] args) {
        String filePath = "./geeks.txt"; //relative path
        //create a Path object using the file path
        Path path = Path.of(filePath);
        //create an empty String, to contain the file contents
        String fileContents="";

        /*
        the try keyword attempts to run the code inside the try block
        generally, only put inside the try block code that may trigger the exception you're catching
        i.e., only the Files.readString method call may trigger the IOException, so only
        put that inside the try block
        other code should be outside the try block
        */
        try{
            fileContents = Files.readString(path);
        }
        //if the code doesn't execute, the catch block will be triggered
        catch(IOException e){
            System.out.println("The geeks file could not be loaded. \nError message: "+e);
        }
        //the code inside the finally block will run regardless of whether the code in the try block is successful
        finally {
            System.out.println("Ain't it great to be a geek!!!");
        }
        /*
        Any statements you put here will execute if possible, even if the try block fails
         */
        System.out.println("**FILE CONTENTS**\n"+fileContents);
    }
}
