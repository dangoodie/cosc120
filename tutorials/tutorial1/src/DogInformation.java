public class DogInformation {
    public static void main(String[] args) {
        String name = "Frankie";
        String microchipNum = "647388745360623";
        char gender = 'F'; // F = female. M = male
        boolean desexed = true;
        int age = 2; // in years
        String breed = "Dalmatian";
        boolean purebred = false;
        double height = 74.5; // in cm
        double weight = 25.7; // in kg

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
        output += name+ " is a " +age+ " year old " +breed+ ". ";
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
