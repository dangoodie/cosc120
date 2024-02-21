import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class AllDogs {
    public static void main(String[] args) throws IOException {
        // Read dogs.txt file
        String filePath = "dogs.txt";
        Path path = Path.of(filePath);

        String fileContents = "";
        fileContents = Files.readString(path);

        // Print file contents
        System.out.println("     **FILE CONTENTS**");
        System.out.println(fileContents);
        System.out.println("     **END OF FILE**");
        System.out.println("\n");

        // Read in new data
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Please enter the dog's name:");
        String name = keyboard.next();

        System.out.println("Please enter the dog's microchip number:");
        String microchipNum = keyboard.next();

        System.out.println("Please enter the dog's gender:");
        String gender = keyboard.next(); // F = female. M = male

        System.out.println("Is the dog desexed? (true/false):");
        boolean desexed = keyboard.nextBoolean();
        keyboard.nextLine(); // use up return carriage

        System.out.println("Please enter the dog's age:");
        int age = keyboard.nextInt(); // in years
        keyboard.nextLine(); // use up return carriage

        System.out.println("Please enter the dog's breed:");
        String breed = keyboard.next();

        System.out.println("Is the dog a purebred (true/false):");
        boolean purebred = keyboard.nextBoolean();
        keyboard.nextLine(); // use up return carriage

        System.out.println("Please enter the dog's height:");
        double height = keyboard.nextDouble(); // in cm
        keyboard.nextLine(); // use up return carriage

        System.out.println("Please enter the dog's weight:");
        double weight = keyboard.nextDouble(); // in kg
        keyboard.nextLine(); // use up return carriage

        // Write new data to file
        String newDog = "\n"+name+","+microchipNum+","+gender+","+desexed+","+age+","+breed+","+purebred+","+height+","+weight;
        String toWrite = fileContents+newDog;
        Files.writeString(path,toWrite);

        // Load written file to check contents
        fileContents = Files.readString(path);

        System.out.println("     **ALTERED FILE CONTENTS**");
        System.out.println(fileContents);
        System.out.println("     **END OF FILE**");

        System.exit(0);
    }
}
