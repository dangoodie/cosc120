public class DogBreeds {
    public static void main(String[] args) {
        String[] dogBreeds = {
                "bulldog",
                "dalmatian",
                "jack Russell terrier",
                "moodle",
                "Yorkshire terrier",
                "golden retriever",
                "BULL terrier",
                "pomeranian",
                "pit bull terrier",
                "Siberian husky",
        };

        // Convert all the dog breeds to lowercase
        for (int i = 0; i < dogBreeds.length; i++) {
            String breed = dogBreeds[i];
            dogBreeds[i] = breed.toLowerCase();
        }

        // Print the dog breeds
        System.out.println("******** All Breeds ********");
        for (String breed : dogBreeds) {
            System.out.println(breed);
        }

        // Print Terrrier Breeds
        System.out.println("******** Terrier Breeds ********");
        for (String breed : dogBreeds) {
            if (breed.contains("terrier")) {
                // Unless it's a pit bull terrier
                if (!breed.contains("pit bull terrier")){
                    System.out.println(breed);
                }
            }
        }

        System.exit(0);
    }
}
