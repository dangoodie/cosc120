/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 * a class from which a GeekV3 object can be created
 * demonstrates importance of access modifiers and getters and setters
 * demonstrates use of final keyword for fields that are only assigned a value once
 */
public class GeekV3 {

    //private class fields
    private final String userName = "joey_gani";
    private String password = "jo#42#34";
    private final Set<String> favouriteTVShows = new HashSet<>();

    //class methods
    /**
     * Provides in-class access to the surname field
     * @return the username
     */
    private String getUserName(){
        return userName;
    }

    /**
     * Allows in-class access to the password field
     * @return the password
     */
    private String getPassword(){
        return password;
    }

    /**
     * Allows in-class modification of the password field
     * @param newPassword the new password
     */
    private void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     * Method to allow user to sign in
     * @param userName the username entered by the user trying to sign in
     * @param password the password entered by the user trying to sign in
     * @return true if sign in successful, false if sign in unsuccessful
     */
    public boolean signIn(String userName, String password){
        if(userName.equals(getUserName()) && password.equals(getPassword())){
            System.out.println("Successfully signed in!");
            return true;
        }
        else System.out.println("Invalid credentials!");
        return false;
    }

    /**
     * Method to allow user to change their password
     * Authentication required - both valid username and password
     * Only requests username/password once
     */
    public void changePassword() {
        System.out.println("Please sign in: ");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Username: ");
        String candidateUsername = keyboard.next();
        System.out.println("Password: ");
        String candidatePassword = keyboard.next();
        //methods can use other methods to perform tasks
        if (signIn(candidateUsername, candidatePassword)){
            System.out.println("Please enter your new password: ");
            String newPassword = keyboard.next();
            setPassword(newPassword);
            System.out.println("Password successfully changed to "+getPassword());
        }
    }

    /**
     * populates the favoriteTVShows field with String values obtained via Scanner (from keyboard)
     */
    public void addFavouriteTVShows(){
        System.out.println("Please enter your favourite TV shows. Enter q to quit.");
        Scanner keyboard = new Scanner(System.in);
        String tvShow = keyboard.nextLine();
        while(!tvShow.equals("q")){
            favouriteTVShows.add(tvShow);
            tvShow = keyboard.nextLine();
        }
    }

    /**
     * method to provide safe access to object's favourite tv shows
     * the new keyword creates a new object, copying the contents of the field into it
     * if this new object is changed by the code that calls the getter, it doesn't matter
     * because those changes won't be reflected in the field
     * this is VERY IMPORTANT for mutable fields
     * @return a copy of the object's favourite tv shows
     */
    public Set<String> getFavouriteTVShows(){
        return new HashSet<>(favouriteTVShows);
    }
}
