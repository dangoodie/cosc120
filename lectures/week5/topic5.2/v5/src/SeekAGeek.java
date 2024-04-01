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
    private static final String filePath = "./allGeeks.txt";
    private static AllGeeks allGeeks;
    private static final String appName = "Seek A Geek 8D";

    /**
     * main method used to interact with user - optionally calls addGeekToFile, searchForGeek or blockAGeek
     * @param args none required
     */
    public static void main(String[] args) {
        allGeeks = loadGeeks();
        int validChoice=0;
        do{
            String userSelection = JOptionPane.showInputDialog("""
                Please choose from the following options:
                1: Add your info to the SeekAGeek database so other geeks can find you?
                2: Search for a compatible geek?
                3: Block a geek (only available if your info is in the database)?

                Please enter a number that corresponds with your choice (1, 2 or 3).""");
            if(userSelection==null)System.exit(0);
            try {
                validChoice = Integer.parseInt(userSelection);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid non-numeric input. Please try again..."); //output a meaningful message
            }
        }while(validChoice!=1 && validChoice!=2 && validChoice!=3);
        if(validChoice==1) addGeekToFile(signUp());
        else if(validChoice==2) searchForGeek();
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

            int age = -1;
            try{
                age = Integer.parseInt(geekInfo[2]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String phoneNumber=null;
            if(isValidPhoneNumber(geekInfo[3])) phoneNumber=geekInfo[3];
            else{
                System.out.println("Error in file. Phone number could not be parsed for geek on line "+(i+1)+". Terminating.");
                System.exit(0);
            }

            String emailAddress=null;
            if(isValidEmail(geekInfo[4])) emailAddress=geekInfo[4];
            else{
                System.out.println("Error in file. Email could not be parsed for geek on line "+(i+1)+". Terminating.");
                System.exit(0);
            }

            Gender gender = Gender.OTHER;
            try {
                gender = Gender.valueOf(geekInfo[5]);
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Gender could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }
            StarSign starSign = StarSign.CANCER;
            try{
                starSign = StarSign.valueOf(geekInfo[6].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Star sign could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            Religion religion = Religion.UNAFFILIATED;
            try{
                religion = Religion.valueOf(geekInfo[7].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Religion could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            String[] favouriteGames = elements[1].replace("]","").replace("\r","").split(",");
            Set<String> faveCompGames = new HashSet<>();
            for(String game: favouriteGames) faveCompGames.add(game.toLowerCase().strip());

            //EDIT 7: read the new favourite tv shows info from the file
            //the favourite tv shows are now in index position 2, right after the games
            String[] faveTVShows = elements[2].replace("]","").replace("\r","").split(",");
            Set<String> favouriteTVShows = new HashSet<>();
            for(String tvShow: faveTVShows) favouriteTVShows.add(tvShow.toLowerCase().strip());

            String statement = elements[3].replace("],","");

            String blocked = elements[4].replace("]","").replace("\r","");
            Set<String> blockedGeeks = null;
            if(blocked.length()>0){
                blockedGeeks=new HashSet<>();
                String[] allBlocked = blocked.split(",");
                Collections.addAll(blockedGeeks, allBlocked);
            }

            //EDIT 7: pass the favourite tv shows into the DreamGeek instantiation
            DreamGeek dreamGeek = new DreamGeek(0,0,gender,starSign,religion,faveCompGames,favouriteTVShows);
            Geek g = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeek);
            allGeeks.addGeek(g);
        }
        return allGeeks;
    }

    /**
     * this method obtains user input for username, name, age, gender, star sign, phone number, geek statement and favourite games
     * this info is used to create and return a new Geek object
     * @return a Geek object representing a geek user's info
     */
    private static Geek signUp(){
        String username = JOptionPane.showInputDialog(null,"Please create a unique username: ",appName, JOptionPane.QUESTION_MESSAGE);
        if(username==null) System.exit(0);
        while(allGeeks.getGeek(username)!=null || username.length()==0) {
            username = JOptionPane.showInputDialog(null,"This username is invalid or is already taken. Please try again. ",appName, JOptionPane.WARNING_MESSAGE);
            if(username==null) System.exit(0);
        }

        String name;
        do{
            name = JOptionPane.showInputDialog("Please enter your full name (in format firstname surname): ");
            if(name==null) System.exit(0);
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

        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your gender: ",appName, JOptionPane.QUESTION_MESSAGE,null,Gender.values(),Gender.OTHER);
        if(gender==null) System.exit(0);
        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your star sign: ",appName, JOptionPane.QUESTION_MESSAGE,null,StarSign.values(),StarSign.CAPRICORN);
        if(starSign==null) System.exit(0);
        Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your religion: ",appName, JOptionPane.QUESTION_MESSAGE,null,Religion.values(),Religion.UNAFFILIATED);
        if(religion==null) System.exit(0);

        String phoneNumber;
        do{
            phoneNumber = JOptionPane.showInputDialog("Please enter your phone number (10-digit number in the format 0412345678): ");
            if(phoneNumber==null) System.exit(0);}
        while(!isValidPhoneNumber(phoneNumber));

        String email;
        do {
            email = JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE);
            if (email == null) System.exit(0);
        }while(!isValidEmail(email));

        String statement = JOptionPane.showInputDialog(null,"Please tell us why you think you're a geek: ",appName, JOptionPane.QUESTION_MESSAGE);
        if(statement==null) System.exit(0);

        String userInput = JOptionPane.showInputDialog(null,"Please enter your favourite computer game or enter s to skip: ",appName, JOptionPane.QUESTION_MESSAGE);
        if(userInput==null) System.exit(0);
        Set<String> faveGames = new HashSet<>();
        while(!userInput.equals("s") && !userInput.equals("d")){
            faveGames.add(userInput.toLowerCase());
            userInput = JOptionPane.showInputDialog(null,"Please enter your next favourite computer game or enter d to finish: ",appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null) System.exit(0);
        }

        //EDIT 5: request that the user input their preferred tv shows when signing up
        userInput = JOptionPane.showInputDialog(null,"Please enter your favourite tv show or enter s to skip: ",appName, JOptionPane.QUESTION_MESSAGE);
        if(userInput==null) System.exit(0);
        Set<String> favouriteTVShows = new HashSet<>();
        while(!userInput.equals("s") && !userInput.equals("d")){
            favouriteTVShows.add(userInput.toLowerCase());
            userInput = JOptionPane.showInputDialog(null,"Please enter your next favourite tv show or enter d to finish: ",appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null) System.exit(0);
        }

        //EDIT 5: pass the favourite games into the DreamGeek instantiation.
        DreamGeek dreamGeek = new DreamGeek(0,0,gender,starSign,religion, faveGames,favouriteTVShows);
        return new Geek(username,name,age,statement,null,phoneNumber,email,dreamGeek);
    }

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
     * a regex matcher that ensures that the user's entry complies with RFC 5322
     * source: <a href="https://www.baeldung.com/java-email-validation-regex">...</a>
     * @param email the candidate email entered by the user
     * @return true if email matches regex/false if not
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
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
        fileContents.add(newGeek.getDatabaseEntry());

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
        String username = JOptionPane.showInputDialog(null,"Please enter your username:",appName, JOptionPane.QUESTION_MESSAGE);
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {
            int minAge = 0, maxAge = 0;
            while(minAge==0) {
                try {
                    minAge = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the min age you desire? ",appName, JOptionPane.QUESTION_MESSAGE));
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
                }
            }
            while(maxAge<minAge) {
                try {
                    maxAge = Integer.parseInt(JOptionPane.showInputDialog(null,"What is the max age you desire? ",appName, JOptionPane.QUESTION_MESSAGE));
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Max age must be greater than min age. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
                }
            }

            StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your preferred star sign: ",appName, JOptionPane.QUESTION_MESSAGE,null,StarSign.values(),StarSign.CAPRICORN);
            if(starSign==null) System.exit(0);

            Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",appName, JOptionPane.QUESTION_MESSAGE,null,Gender.values(),Gender.OTHER);
            if(gender==null) System.exit(0);

            Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",appName, JOptionPane.QUESTION_MESSAGE,null,Religion.values(),Religion.UNAFFILIATED);
            if(religion==null) System.exit(0);

            Set<String> favouriteGames = new HashSet<>();
            String userInput = JOptionPane.showInputDialog(null,"Would you like your dream geek to be into games? (yes/no)",appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null) System.exit(0);
            if (userInput.equalsIgnoreCase("yes")) {
                userInput = JOptionPane.showInputDialog(null,"Which game would you like your dream geek to be into? (enter done to finish)",appName, JOptionPane.QUESTION_MESSAGE);
                while (!userInput.equalsIgnoreCase("done")) {
                    favouriteGames.add(userInput.toLowerCase());
                    userInput = JOptionPane.showInputDialog(null,"If there is another game you'd like to add, enter it or enter done to finish",appName, JOptionPane.QUESTION_MESSAGE);
                    if(userInput==null) System.exit(0);
                }
            } else favouriteGames = null;

            //EDIT 6: request that the user input which tv shows they want their match to be into
            //see the strong similarity between the code to get games and the code to get tv shows?
            //can you think of a way of reducing this code duplication?
            Set<String> favouriteTVShows = new HashSet<>();
            userInput = JOptionPane.showInputDialog(null,"Would you like your dream geek to be into binging tv shows? (yes/no)",appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null) System.exit(0);
            if (userInput.equalsIgnoreCase("yes")) {
                userInput = JOptionPane.showInputDialog(null,"Which tv show would you like your dream geek to be into? (enter done to finish)",appName, JOptionPane.QUESTION_MESSAGE);
                while (!userInput.equalsIgnoreCase("done")) {
                    favouriteTVShows.add(userInput.toLowerCase());
                    userInput = JOptionPane.showInputDialog(null,"If there is another tv show you'd like to add, enter it or enter done to finish",appName, JOptionPane.QUESTION_MESSAGE);
                    if(userInput==null) System.exit(0);
                }
            } else favouriteTVShows = null;

            //EDIT 6: pass the tv shows into the DreamGeek instantiation
            DreamGeek dreamGeek = new DreamGeek(minAge,maxAge,gender,starSign,religion, favouriteGames, favouriteTVShows);

            List<Geek> potentialMatches = allGeeks.findDreamGeek(dreamGeek, username);
            if (potentialMatches.size()==0) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.",appName, JOptionPane.INFORMATION_MESSAGE);
            else {
                String[] options = new String[potentialMatches.size()];
                StringBuilder infoToShow= new StringBuilder("You have the following potential matches: \n");
                for(int i=0;i<potentialMatches.size();i++){
                    infoToShow.append(potentialMatches.get(i).getGeekDescription());
                    options[i]=potentialMatches.get(i).getName();
                }
                String decision = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",appName, JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if(decision==null) {
                    JOptionPane.showMessageDialog(null, "No one will not be informed or contacted about your search.",appName, JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
                else{
                    for (Geek match : potentialMatches) {
                        if (decision.equals(match.getName()))
                            JOptionPane.showMessageDialog(null, match.getContactDetailsDescription(),appName, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
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
                fileContents.set(i, user.getDatabaseEntry());
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
