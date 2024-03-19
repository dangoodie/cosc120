/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class instantiates the AllGeek and Geek classes, allowing a user to
 * 1) add their info to the Geek database/sign up
 * 2) search for a dream geek
 * 3) block an undesirable geek from contacting them or appearing in searches
 */
public class SeekAGeek {
    //fields
    private static final String filePath = "allGeeks.txt";
    private static AllGeeks allGeeks;

    /**
     * main method used to interact with user - optionally calls addGeekToFile, searchForGeek or blockAGeek
     * @param args none required
     */
    public static void main(String[] args) {
        //call the loadGeeks method in this class to create the Geek database
        allGeeks = loadGeeks();

        int validChoice=0;
        do{
            //ask the user what they want to do - use a text block for when you have a lot of information - it is neater than concatenation
            String userSelection = JOptionPane.showInputDialog("""
                Please choose from the following options:
                1: Add your info to the SeekAGeek database so other geeks can find you?
                2: Search for a compatible geek?
                3: Block a geek (only available if your info is in the database)?

                Please enter a number that corresponds with your choice (1, 2 or 3).""");
            if(userSelection==null)System.exit(0); //if they close/cancel the dialog, simply terminate the program
            try { //ensure appropriate exception handling
                validChoice = Integer.parseInt(userSelection);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid non-numeric input. Please try again..."); //output a meaningful message
            }
        }while(validChoice!=1 && validChoice!=2 && validChoice!=3);
        //if they choose to add their info to the database, call the signUp method to ask them to enter the relevant data
        //use the addGeekToFile method in this class to write the Geek returned by signUp to the file.
        if(validChoice==1) addGeekToFile(signUp());
            //if they choose to find their dream geek, ask them to enter the relevant data
        else if(validChoice==2) searchForGeek();
            //if they choose to block another geek, ask them to enter the relevant data
        else blockAGeek();
        System.exit(0);
    }

    //utility methods
    /**
     * method to load all the geek info from a file, using the data to instantiate an AllGeeks object
     * @return an AllGeeks object - this is a dataset of Geek objects
     */
    private static AllGeeks loadGeeks() {
        AllGeeks allGeeks = new AllGeeks();
        Path path = Path.of(filePath);
        List<String> eachGeek = null;
        try{
            eachGeek = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("The file could not be loaded. Check file path is correct. Terminating.\nError message: "+io.getMessage());
            System.exit(0);
        }

        for (int i=1;i<eachGeek.size();i++) {
            String[] elements = eachGeek.get(i).split("\\[");
            String[] geekInfo = elements[0].split(",");
            String username = geekInfo[0].replaceAll("\n", "");
            String name = geekInfo[1];

            //EDIT 5: simply pass the file contents (already uppercase) into valueOf to assign it to a Gender variable
            //String gender = geekInfo[2].toLowerCase();
            Gender gender = Gender.valueOf(geekInfo[2]);
            //END EDIT 5

            //EDIT 3: convert the file content to uppercase, and read it in as a StarSign enum constant using valueOf
            //String starSign = geekInfo[5];
            StarSign starSign = StarSign.valueOf(geekInfo[5].toUpperCase());
            //END EDIT 3

            int age = -1;
            try{
                age = Integer.parseInt(geekInfo[3]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            //EDIT 21
            String phoneNumber=null;
            if(isValidPhoneNumber(geekInfo[4])) phoneNumber=geekInfo[4];
            else{
                System.out.println("Error in file. Phone number could not be parsed for geek on line "+(i+1)+". Terminating.");
                System.exit(0);
            }

            String statement = elements[1].replace("],","");

            String[] favouriteGames = elements[2].replace("]","").replace("\r","").split(",");
            Set<String> faveCompGames = new HashSet<>();
            //EDIT 10: we can't use addAll anymore, as we have to convert the game names to lowercase before adding to the Set
            for(String game: favouriteGames) faveCompGames.add(game.toLowerCase().strip());
            //Collections.addAll(faveCompGames, favouriteGames);
            //END EDIT 10

            String blocked = elements[3].replace("]","").replace("\r","");
            Set<String> blockedGeeks = null;
            if(blocked.length()>0){
                blockedGeeks=new HashSet<>();
                String[] allBlocked = blocked.split(",");
                Collections.addAll(blockedGeeks, allBlocked);
            }

            Geek geek = new Geek(username,name,age,gender,starSign,statement,faveCompGames,blockedGeeks,phoneNumber);
            allGeeks.addGeek(username,geek);
        }
        return allGeeks;
    }

    /**
     * this method obtains user input for username, name, age, gender, star sign, phone number, geek statement and favourite games
     * this info is used to create and return a new Geek object
     * @return a Geek object representing a geek user's info
     */
    private static Geek signUp(){
        String username = JOptionPane.showInputDialog("Please create a unique username: ");
        if(username==null) System.exit(0);
        while(allGeeks.getGeek(username)!=null || username.isEmpty()) {
            username = JOptionPane.showInputDialog("This username is invalid or is already taken. Please try again. ");
            if(username==null) System.exit(0);
        }

        String name;
        do{
            name = JOptionPane.showInputDialog("Please enter your full name (in format firstname surname): ");
            if(name==null) System.exit(0);
        //EDIT 19: call the new method as the while condition - if it returns false, the name is invalid, and must be re-entered
        } while(!isValidFullName(name));

        int age = 0;
        do{
            String ageInput = JOptionPane.showInputDialog("Please enter your age (18+ only): ");
            if(ageInput==null) System.exit(0);
            try {
                age = Integer.parseInt(ageInput);
            }catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Invalid entry. You must enter a valid integer.");
            }
        }
        while (age<18 || age>130);

        //EDIT 6: use the Gender enum to populate a dropdown list, removing a lot of code, and making the program more robust
        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your gender: ",null, JOptionPane.QUESTION_MESSAGE,null,Gender.values(),Gender.OTHER);
        if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        //String gender;
        //do{
        //    gender = JOptionPane.showInputDialog("Please enter your gender (male/female/other): ");
        //    if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        //} while (gender.length()==0);
        //if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) gender="other"; //if the user enters anything other than male and female, assign 'other' to gender
        //END EDIT 6

        //EDIT 1: use the new StarSign enum to populate a dropdown list - this removes the need for input validation
        //and eliminates the possibility that a user might enter incorrect data
        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your star sign: ",null, JOptionPane.QUESTION_MESSAGE,null,StarSign.values(),StarSign.CAPRICORN);
        if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        //String starSign;
        //do { //can you think of a better way to achieve this?
        //    starSign = JOptionPane.showInputDialog("Please enter your star sign (Capricorn, Aquarius, Pisces, Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius): ");
        //    if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        //}while (starSign.length()==0);
        //END OF EDIT 1

        //EDIT 21: replace the complex logic with a simple call to our new method
        String phoneNumber;
        do{
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));

            /*try {
                phoneNumber = Long.parseLong(phoneNumberInput);
            }catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Invalid entry. You must enter a 10-digit number in the format 0412345678.");
            }
        }while (phoneNumber==0 && phoneNumberInput.length()!=10);
             */

        String statement = JOptionPane.showInputDialog("Please tell us why you think you're a geek: ");
        if(statement==null) System.exit(0);

        String faveGame = JOptionPane.showInputDialog("Please enter your favourite computer game or enter s to skip: ");
        if(faveGame==null) System.exit(0);
        Set<String> faveGames = new HashSet<>();
        while(!faveGame.equals("s") && !faveGame.equals("d")){
            //EDIT 11: convert user input to lower case
            faveGames.add(faveGame.toLowerCase());
            faveGame = JOptionPane.showInputDialog("Please enter your next favourite computer game or enter d to finish: ");
            if(faveGame==null) System.exit(0);
        }

        return new Geek(username,name,age,gender,starSign,statement,faveGames,null,phoneNumber);
    }

    //EDIT 18: new method for validating full name
    //experiment to see if you can improve it to include ' and - and middle names
    /**
     * a very simple regex for full name in Firstname Surname format
     * @param fullName the candidate full name entered by the user
     * @return true if name matches regex/false if not
     */
    public static boolean isValidFullName(String fullName) {
        String regex = "^[A-Z][a-z]+\\s[A-Z][a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    //EDIT 20: create a method to check the validity of the user's phone number
    /**
     * a regex matcher that ensures that the user's entry starts with a 0 and is followed by 9 digits
     * @param phoneNumber the candidate phone number entered by the user
     * @return true if phone number matches regex/false if not
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * method to add a new geek to the file containing all the geek data
     * @param newGeek a Geek object representing the current Geek user of the program
     */
    private static void addGeekToFile(Geek newGeek) {
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try{
            fileContents = Files.readAllLines(path);
        }catch (IOException ioRead){
            System.out.println("File could not be read. \nError message: "+ioRead.getMessage());
            System.exit(0);
        }

        //EDIT 8: no need for this, as enum constants are automatically uppercase
        //String gender = newGeek.getGender().toUpperCase();

        String geekLineToAdd = newGeek.getUsername()+","+newGeek.getName()+","+newGeek.getGender()+","+newGeek.getAge()+",0"+
                newGeek.getPhoneNumber()+","+newGeek.getStarSign()+",["+newGeek.getStatement()+
                "],"+newGeek.getFavouriteGames()+","+newGeek.getBlockedGeeks();
        fileContents.add(geekLineToAdd);

        try{
            Files.write(path,fileContents);
            JOptionPane.showMessageDialog(null,"Congratulations "+newGeek.getName().split(" ")[0]+"! You've successfully signed up to SeekAGeek!");
        }catch (IOException ioWrite){
            System.out.println("File could not be written. \nError message: "+ioWrite.getMessage());
            System.exit(0);
        }
    }

    /**
     * verify whether the user is in the dataset
     * @param username the username entered by the Geek
     * @return true if username is in the dataset, false if not
     */
    private static boolean checkUsernameExists(String username){
        if(allGeeks.getGeek(username)==null) {
            JOptionPane.showMessageDialog(null,"No such username exists. Please rerun the program and select option 1 to sign up.");
            return false;
        }
        return true;
    }

    /**
     * obtains user input for the criteria they desire in a companion geek including age, star sign, gender and favourite games
     * uses this information to search the Geek dataset for compatible geeks. If there is a compatible Geek, the user is given the option
     * to contact the compatible geek. If there isn't, an appropriate message is provided to the user.
     */
    private static void searchForGeek(){
        String username = JOptionPane.showInputDialog("Please enter your username:");
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {
            //EDIT 15: change from an age filter to an age range filter
            //int age = 0;
            //do {
            //    String preferredAge = JOptionPane.showInputDialog("Please enter your preferred age (must be >= 18): ");
            //    if (preferredAge == null) System.exit(0);
            //   try {
            //        age = Integer.parseInt(preferredAge);
            //    } catch (NumberFormatException n) {
            //        JOptionPane.showMessageDialog(null, "Invalid entry.");
            //    }
            //}while(age < 18);
            int minAge = 0, maxAge = 0;
            while(minAge==0) {
                try {
                    minAge = Integer.parseInt(JOptionPane.showInputDialog("What is the min age you desire? "));
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Invalid input. Please try again.");
                }
            }
            //max age cannot be less than min age
            while(maxAge<minAge) {
                try {
                    maxAge = Integer.parseInt(JOptionPane.showInputDialog("What is the max age you desire? "));
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Max age must be greater than min age. Please try again.");
                }
            }
            //END EDIT 15

            //EDIT 2: use the new StarSign enum to populate a dropdown list - this removes the need for input validation
            //and eliminates the possibility that a user might enter incorrect data
            StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your preferred star sign: ",null, JOptionPane.QUESTION_MESSAGE,null,StarSign.values(),StarSign.CAPRICORN);
            if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            //String starSign;
            //do { //can you think of a better way to achieve this?
            //    starSign = JOptionPane.showInputDialog("Please enter your preferred star sign (Capricorn, Aquarius, Pisces, Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius): ");
            //    if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            //}while (starSign.length()==0);
            //END EDIT 2

            //EDIT 7: use the Gender enum to populate a dropdown list, removing a lot of code, and making the program more robust
            Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",null, JOptionPane.QUESTION_MESSAGE,null,Gender.values(),Gender.OTHER);
            if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            //String gender;
            //do{
            //    gender = JOptionPane.showInputDialog("Please enter your preferred gender (male/female/other): ");
            //    if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            //} while (gender.length()==0);
            //if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) gender="other"; //if the user enters anything other than male and female, assign 'other' to gender
            //END EDIT 7

            Set<String> favouriteGames = new HashSet<>();
            String userInput = JOptionPane.showInputDialog("Would you like your dream geek to be into games? (yes/no)");
            if(userInput==null) System.exit(0);
            if (userInput.equalsIgnoreCase("yes")) {
                userInput = JOptionPane.showInputDialog("Which game would you like your dream geek to be into? (enter done to finish)");
                while (!userInput.equalsIgnoreCase("done")) {
                    //EDIT 11: convert user input to lower case
                    favouriteGames.add(userInput.toLowerCase());
                    userInput = JOptionPane.showInputDialog("If there is another game you'd like to add, enter it or enter done to finish");
                    if(userInput==null) System.exit(0);
                }
            } else favouriteGames = null;

            //EDIT 16: set the age argument to 0 then use the min and max age setters to set the user's age range criteria
            Geek dreamGeek = new Geek("", "", 0, gender, starSign, "", favouriteGames, null,null);
            dreamGeek.setMinAge(minAge);
            dreamGeek.setMaxAge(maxAge);
            //END EDIT 16

            //EDIT 13: replace the Geek potentialMatch with List<Geek> potentialMatches, so that the user can see all the geeks that match their criteria
            //Geek potentialMatch = allGeeks.findDreamGeek(dreamGeek, username);
            List<Geek> potentialMatches = allGeeks.findDreamGeek(dreamGeek, username);
            //instead of checking if one match is null, check if the List is empty (no matches)
            if (potentialMatches.isEmpty()) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.");
            //if (potentialMatch == null) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.");
            else {
                //create a String array to contain the names of all the potential matches - the user is to choose one of them
                String[] options = new String[potentialMatches.size()];
                //build a String message to show in the dialog, by iterating through potentialMatches, collecting relevant info and concatenating it
                //StringBuilder is more efficient than concatenating a String (because only one String object needs to be generated for StringBuilder,
                //while String concatenation results in multiple objects being created (and disposed of)
                StringBuilder infoToShow= new StringBuilder("You have the following potential matches: \n");
                for(int i=0;i<potentialMatches.size();i++){
                    infoToShow.append(potentialMatches.get(i).getName()).append(" is a/an ").append(potentialMatches.get(i).getAge()).append(" year old ")
                            .append(potentialMatches.get(i).getStarSign()).append(" ").append(potentialMatches.get(i).getGender()).append(" who loves the following games: ")
                            .append(potentialMatches.get(i).getFavouriteGames().toString()).append(".\nAbout ").append(potentialMatches.get(i).getName()).append(": ")
                            .append(potentialMatches.get(i).getStatement()).append("\n\n");
                    //add each potential match's name to the list of options
                    options[i]=potentialMatches.get(i).getName();
                }
                //present the output to the user using a JOptionPane use a dropdown list to allow the user to select whichever geek they prefer
                String decision = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",null, JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                //handle the situation where the user closes the dialog
                if(decision==null) {
                    JOptionPane.showMessageDialog(null, "No one will not be informed or contacted about your search.");
                    System.exit(0);
                }
                else{ //if the user selects a match, output their name and phone number
                    for (Geek match : potentialMatches) {
                        if (decision.equals(match.getName())) JOptionPane.showMessageDialog(null, match.getName() + "'s phone number is: " + match.getPhoneNumber());
                    }
                }
                //String decision = JOptionPane.showInputDialog("Potential match! " + potentialMatch.getName() + " is an " + potentialMatch.getAge() + " year old " + potentialMatch.getStarSign() + " " +
                //        potentialMatch.getGender() + " who loves the following games: " + potentialMatch.getFavouriteGames().toString() + ".\nAbout " + potentialMatch.getName() + ": " + potentialMatch.getStatement() +
                //        "\nWould you like to contact them? (yes/no)");
                //if (decision.equalsIgnoreCase("yes"))
                //    JOptionPane.showMessageDialog(null, potentialMatch.getName() + "'s phone number is: 0" + potentialMatch.getPhoneNumber());
                //else JOptionPane.showMessageDialog(null, potentialMatch.getName() + " will not be informed or contacted about your search.");
                //END EDIT 13
            }
        }
    }

    /**
     * obtains user input 1) user's username and 2) the username of the Geek to block
     * if username exists, call the addGeekToBlockedListOnFile method to write the username to the file
     * otherwise, let the user know that no such username exists
     */
    private static void blockAGeek(){
        String username = JOptionPane.showInputDialog("Please enter your username: ");
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {
            String geekToBlock = JOptionPane.showInputDialog("Please enter the username of the Geek you'd like to block: ");
            if (allGeeks.getGeek(geekToBlock) == null)
                JOptionPane.showMessageDialog(null, "A geek with username " + geekToBlock + " does not exist.");
            else if(username.equals(geekToBlock))
                JOptionPane.showMessageDialog(null, "You can't block yourself!");
            else {
                Geek user = allGeeks.getGeek(username);
                user.blockGeek(allGeeks.getGeek(geekToBlock));
                addGeekToBlockedListOnFile(user);
                JOptionPane.showMessageDialog(null, geekToBlock+" successfully blocked!");
            }
        }
    }

    /**
     * method to add a Geek's username to the Geek user's list of blocked geeks
     * @param user a Geek object representing the current Geek user of the program
     */
    private static void addGeekToBlockedListOnFile(Geek user) {
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try{
            fileContents = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("File could not be loaded. \nError message: "+io.getMessage());
            System.exit(0);
        }
        for (int i = 1; i < fileContents.size(); i++) {
            String[] splitData = fileContents.get(i).split(",");
            if (splitData[0].equals(user.getUsername())) {
                String gender = splitData[2];
                String newLine = user.getUsername() + "," + user.getName() + "," + gender + "," + user.getAge() + ",0" +
                        user.getPhoneNumber() + "," + user.getStarSign() + ",[" + user.getStatement() +
                        "]," + user.getFavouriteGames() + "," + user.getBlockedGeeks();
                fileContents.set(i, newLine);
                try{
                    Files.write(path,fileContents);
                }catch (IOException ioWrite){
                    System.out.println("File could not be written. \nError message: "+ioWrite.getMessage());
                    System.exit(0);
                }
                break;
            }
        }
    }

}
