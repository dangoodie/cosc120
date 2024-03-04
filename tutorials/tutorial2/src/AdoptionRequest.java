import javax.swing.*;

public class AdoptionRequest {
    public static void main(String[] args) {

        try {
            // request desired dog breed
            String desiredBreed = JOptionPane.showInputDialog("Please enter the desired dog breed: ");
            while (desiredBreed.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid dog breed.");
                desiredBreed = JOptionPane.showInputDialog("Please enter the desired dog breed: ");
            }

            // do they only want a purebred?
            String purebred = JOptionPane.showInputDialog("Do you only want a purebred? (yes/no): ");
            while (!purebred.equals("yes") && !purebred.equals("no")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid response.");
                purebred = JOptionPane.showInputDialog("Do you only want a purebred? (yes/no): ");
            }

            // desired sex
            String desiredSex = JOptionPane.showInputDialog("Sex? (male/female): ");
            while (!desiredSex.equals("male") && !desiredSex.equals("female")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid response.");
                desiredSex = JOptionPane.showInputDialog("Sex? (male/female): ");
            }

            // maximum age in years (max 20 years)
            String maxAge = JOptionPane.showInputDialog("Maximum age in years (max 20 years): ");
            while (Integer.parseInt(maxAge) > 20 || Integer.parseInt(maxAge) < 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid max age.");
                maxAge = JOptionPane.showInputDialog("Maximum age in years (max 20 years): ");
            }

            // minimum age in years (min 0 years)
            String minAge = JOptionPane.showInputDialog("Minimum age in years (min 0 years): ");
            while (Integer.parseInt(minAge) > 20 || Integer.parseInt(minAge) < 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid min age.");
                minAge = JOptionPane.showInputDialog("Minimum age in years (min 0 years): ");
            }

            // contact details (name and phone number) (10 digits/chars)
            String contactName = JOptionPane.showInputDialog("Please enter your name (max 10 characters): ");
            while (contactName.length() > 10) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.");
                contactName = JOptionPane.showInputDialog("Please enter your name (max 10 characters): ");
            }

            String contactPhone = JOptionPane.showInputDialog("Please enter your phone number (max 10 numbers): ");
            while (contactPhone.length() > 10) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number.");
                contactPhone = JOptionPane.showInputDialog("Please enter your phone number (max 10 numbers): ");
            }

            // print the request
            System.out.println("******** Adoption Request ********");
            System.out.println("Desired Breed: " + desiredBreed);
            System.out.println("Purebred: " + purebred);
            System.out.println("Desired sex: " + desiredSex);
            System.out.println("Maximum age: " + maxAge);
            System.out.println("Minimum age: " + minAge);
            System.out.println("Contact Name: " + contactName);
            System.out.println("Contact Phone: " + contactPhone);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Process cancelled.");
            System.exit(0); //terminate the program normally
        } finally {
            System.exit(0); //terminate the program normally
        }
    }
}
