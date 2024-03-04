import java.util.Arrays;
import java.util.Scanner;

public class BreedAvailability {
    public static void main(String[] args) {
        String[] dogBreeds = {
                "Dalmatian",
                "Rotweiler",
                "German Shepherd",
                "Jack Russell Terrier",
                "Wolfhound",
                "Siberian Husky",
                "Chihuahua",
                "Moodle",
                "Poodle",
                "Labrador",
                "Maremma",
        };

        Scanner keyboard = new Scanner(System.in);

        String[] favouriteBreeds = new String[5];
        System.out.println("Enter your " +favouriteBreeds.length+ " favourite dog breeds: ");
        for (int i = 0; i < favouriteBreeds.length; i++) {
            favouriteBreeds[i] = keyboard.nextLine();
        }

        for (String desiredBreed : favouriteBreeds) {
            for (String availableBreed : dogBreeds) {
                if (desiredBreed.equalsIgnoreCase(availableBreed)) {
                    System.out.println(availableBreed + " is available!");
                }
            }
        }

        System.out.println("******** All Available Breeds ********");
        Arrays.sort(dogBreeds);
        System.out.println(Arrays.toString(dogBreeds));

        System.exit(0);
    }
}
