import javax.swing.*;

public class HoundLifeExpectancy {
    public static void main(String[] args) {
        try {
            String dogBreed = JOptionPane.showInputDialog("Please enter a dog breed.").toLowerCase();
            String lifeExpectancy = "";

            switch (dogBreed.toLowerCase()) {
                case "afghan hound" -> lifeExpectancy = "12-18 years";
                case "american english coonhound" -> lifeExpectancy = "11-12 years";
                case "american foxhound" -> lifeExpectancy = "11-13 years";
                case "azawakh" -> lifeExpectancy = "12-15 years";
                case "basenji" -> lifeExpectancy = "13-14 years";
                case "basset hound" -> lifeExpectancy = "12-13 years";
                case "beagle" -> lifeExpectancy = "10-15 years";
                case "black and tan coonhound" -> lifeExpectancy = "10-12 years";
                case "bloodhound" -> lifeExpectancy = "10-12 years";
                case "bluetick coonhound" -> lifeExpectancy = "11-12 years";
                case "borzoi" -> lifeExpectancy = "9-14 years";
                case "cirneco dell'etna" -> lifeExpectancy = "12-14 years";
                case "dachshund" -> lifeExpectancy = "12-16 years";
                case "english foxhound" -> lifeExpectancy = "10-13 years";
                case "grand basset griffon vendéen" -> lifeExpectancy = "13-15 years";
                case "greyhound" -> lifeExpectancy = "10-13 years";
                case "harrier" -> lifeExpectancy = "12-15 years";
                case "ibizan hound" -> lifeExpectancy = "11-14 years";
                case "irish wolfhound" -> lifeExpectancy = "6-8 years";
                case "norwegian elkhound" -> lifeExpectancy = "12-15 years";
                case "otterhound" -> lifeExpectancy = "10-13 years";
                case "petit basset griffon vendéen" -> lifeExpectancy = "14-16 years";
                case "pharaoh hound" -> lifeExpectancy = "12-14 years";
                case "plott hound" -> lifeExpectancy = "12-14 years";
                case "portuguese podengo pequeno" -> lifeExpectancy = "12-15 years";
                case "redbone coonhound" -> lifeExpectancy = "12-15 years";
                case "rhodesian ridgeback" -> lifeExpectancy = "unknown";
                case "saluki" -> lifeExpectancy = "10-17 years";
                case "scottish deerhound" -> lifeExpectancy = "8-11 years";
                case "sloughi" -> lifeExpectancy = "10-15 years";
                case "treeing walker coonhound" -> lifeExpectancy = "12-13 years";
                case "whippet" -> lifeExpectancy = "12-15 years";
                default -> JOptionPane.showMessageDialog(null, "Invalid dog breed");
            }

            if (!lifeExpectancy.isEmpty()) {
                String output = "The " +dogBreed.toLowerCase()+ " has a life expectancy of " +lifeExpectancy+ ".";
                JOptionPane.showMessageDialog(null, output);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Cancelled");
        } finally {
            System.exit(0);
        }
    }
}
