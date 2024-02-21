public class DogInformation {
    public static void main(String[] args) {
        String name = "Cujo";
        String microchipNum = "52330648523343";
        char gender = 'X'; // F = female. M = male
        boolean desexed = false;
        int age = 5; // in years
        String breed = "Jack Russell";
        boolean purebred = true;
        double height = 54; // in cm
        double weight = 19.5; // in kg

        // check gender
        String pronoun = "";
        String pronounB = "";
        if (gender == 'F')
        {
            pronoun = "Her";
            pronounB = "She";
        } else if (gender == 'M')
        {
            pronoun = "His";
            pronounB = "He";
        } else
        {
            pronoun = "Their";
            pronounB = "They";
        }

        // build the system out string
        String output = ""; // declare blank string
        output += name+ " is a " +age+ " year old ";

        if (purebred) output += "purebred ";

        output += breed+ ". ";
        output += pronoun+ " microchip number is " +microchipNum+ ". ";

        if (desexed) {
            output += pronounB+ " is de-sexed. ";
        } else {
            output += pronounB+ " are not de-sexed. ";
        }

        output += pronoun+ " height is " +height+ " cm. ";
        output += pronoun+ " weight is " +weight+ " kg. ";

        System.out.println(output);
    }
}
