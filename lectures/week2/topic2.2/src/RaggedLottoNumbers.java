/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class RaggedLottoNumbers {
    /**
     * This program demonstrates how to use a 2-dimensional ragged array
     * Re-enforces knowledge/usage of file input and data parsing
     * @param args none required
     */
    public static void main (String[] args) throws IOException {

        //string representing path to file
        String filePath = "./lottoNumbers.txt";

        //load the file
        Path path = Path.of(filePath);
        //reads content of file as a String
        String fileContents = Files.readString(path);

        //split on new line to separate geeks
        String[] games = fileContents.split("\n");

        //the number of games is = to the number of lines in the file
        int numberOfGames = games.length;
        //this is an example of a ragged array - the size of each sub-array in the second dimension can be different
        //the size of the second dimension will be the number of digits in each row of numbers
        int[][] lottoNumbers = new int[numberOfGames][];

        //loop through each line (each game)
        for(int i=0;i<numberOfGames;i++) {
            //split the lines at the comma (the numbers are separated by commas)
            String[] gameNumbers = games[i].split(",");
            //determine how many digits are in this game
            int numberOfNumbers=gameNumbers.length;
            //set the size of the sub-array in lottoNumbers
            lottoNumbers[i]=new int[numberOfNumbers];
            //loop through each digit, ensuring there are no trailing carriage returns (or new line)
            for (int j = 0; j < numberOfNumbers; j++) {
                String digit = gameNumbers[j].replace("\r","");
                //parse  the digit to convert it to an integer
                // (a try catch might be a good idea to avoid a NumberFormatException!)
                lottoNumbers[i][j]=Integer.parseInt(digit);
            }
        }

        //let's print the games!
        for(int i=0;i<lottoNumbers.length;i++){
            //note: to add numbers in a print statement, wrap the operation in brackets
            System.out.println("Game "+(i+1)+": "+ Arrays.toString(lottoNumbers[i]));
        }
    }
}
