import javax.swing.*;

public class HoundLifeExpectancy {
    public static void main(String[] args) {
        try {
            String dogBreed = JOptionPane.showInputDialog("Please enter a dog breed.");

            String lifeExpectancy = "";
            switch (dogBreed) {
                case "Afghan Hound" -> lifeExpectancy = "12-18 years";
                case "American English Coonhound" -> lifeExpectancy = "11-12 years";
                case "American Foxhound" -> lifeExpectancy = "11-13 years";
                case "Azawakh" -> lifeExpectancy = "12-15 years";
                case "Basenji" -> lifeExpectancy = "13-14 years";
                case "Basset Hound" -> lifeExpectancy = "12-13 years";
                case "Beagle" -> lifeExpectancy = "10-15 years";
                case "Black and Tan Coonhound" -> lifeExpectancy = "10-12 years";
                case "Bloodhound" -> lifeExpectancy = "10-12 years";
                case "Bluetick Coonhound" -> lifeExpectancy = "11-12 years";
                case "Borzoi" -> lifeExpectancy = "9-14 years";
                case "Cirneco dell'Etna" -> lifeExpectancy = "12-14 years";
                case "Dachshund" -> lifeExpectancy = "12-16 years";
                case "English Foxhound" -> lifeExpectancy = "10-13 years";
                case "Grand Basset Griffon Vendéen" -> lifeExpectancy = "13-15 years";
                case "Greyhound" -> lifeExpectancy = "10-13 years";
                case "Harrier" -> lifeExpectancy = "12-15 years";
                case "Ibizan Hound" -> lifeExpectancy = "11-14 years";
                case "Irish Wolfhound" -> lifeExpectancy = "6-8 years";
                case "Norwegian Elkhound" -> lifeExpectancy = "12-15 years";
                case "Otterhound" -> lifeExpectancy = "10-13 years";
                case "Petit Basset Griffon Vendéen" -> lifeExpectancy = "14-16 years";
                case "Pharaoh Hound" -> lifeExpectancy = "12-14 years";
                case "Plott Hound" -> lifeExpectancy = "12-14 years";
                case "Portuguese Podengo Pequeno" -> lifeExpectancy = "12-15 years";
                case "Redbone Coonhound" -> lifeExpectancy = "12-15 years";
                case "Rhodesian Ridgeback" -> lifeExpectancy = "unknown";
                case "Saluki" -> lifeExpectancy = "10-17 years";
                case "Scottish Deerhound" -> lifeExpectancy = "8-11 years";
                case "Sloughi" -> lifeExpectancy = "10-15 years";
                case "Treeing Walker Coonhound" -> lifeExpectancy = "12-13 years";
                case "Whippet" -> lifeExpectancy = "12-15 years";
                default -> JOptionPane.showMessageDialog(null, "Invalid dog breed");
            }

            if (!lifeExpectancy.isEmpty()) {
                String output = "The " +dogBreed.toLowerCase()+ " has a life expectancy of " +lifeExpectancy+ ".";
                JOptionPane.showMessageDialog(null, output);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Cancelled");
            System.exit(0);
        } finally {
            System.exit(0);
        }
    }
}
