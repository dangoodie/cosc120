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

/**
 * This class instantiates the AllGeek and Geek classes, allowing a user to
 * 1) add their info to the Geek database/sign up
 * 2) search for a dream geek
 * 3) block an undesirable geek from contacting them or appearing in searches
 */
public class SeekAGeek {
    //fields
    private static final String filePath = "./allGeeks.txt"; //String representing path to file
    private static AllGeeks allGeeks;

    /**
     * main method used to interact with user - optionally calls addGeekToFile, searchForGeek or blockAGeek
     * @param args not required
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
        //create an instance of the AllGeeks class. This will act as a dataset with specialised methods
        AllGeeks allGeeks = new AllGeeks();
        Path path = Path.of(filePath);//load the file
        //load the file data as a List of Strings (each String is one geek's info) ensuring appropriate exception handling
        List<String> eachGeek = null;
        try{
            eachGeek = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("The file could not be loaded. Check file path is correct. Terminating.\nError message: "+io.getMessage());
            System.exit(0);
        }

        //loop through the file data, skipping the first line (look at the file, do you see why?)
        for (int i=1;i<eachGeek.size();i++) {
            //split each String in the list by [ and , to separate the geek info, statement, favourite games and blocked list
            String[] elements = eachGeek.get(i).split("\\[");

            //separate the geek info by splitting by comma. This will separate the following; username,name,gender,age,phone number,star sign, into individual items
            String[] geekInfo = elements[0].split(",");
            //assign all the geek info to appropriate variables, removing unwanted characters
            String username = geekInfo[0].replaceAll("\n", "");
            String name = geekInfo[1];
            String gender = geekInfo[2].toLowerCase();
            String starSign = geekInfo[5];

            int age = -1;
            try{
                age = Integer.parseInt(geekInfo[3]);
            }catch (NumberFormatException n){ //always print a useful, meaningful message
                System.out.println("Error in file. Age could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            long phoneNumber = 0;
            try{
                phoneNumber = Long.parseLong(geekInfo[4]);
            }catch (NumberFormatException n){ //always print a useful, meaningful message
                System.out.println("Error in file. Phone number could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            //Next, clean up the geek statement by removing the trailing square bracket
            String statement = elements[1].replace("],","");

            //separate the list of games into individual games removing the trailing square bracket  and return carriage
            String[] favouriteGames = elements[2].replace("]","").replace("\r","").split(",");
            //add all the games to a Set (this will automatically remove duplicates)
            Set<String> faveCompGames = new HashSet<>();
            Collections.addAll(faveCompGames, favouriteGames);

            //clean up the blocked geeks list by removing the trailing square bracket and any whitespaces
            String blocked = elements[3].replace("]","").replace("\r","");
            //if there are any blocked geeks, add them to a HashSet
            Set<String> blockedGeeks = null;
            if(blocked.length()>0){
                blockedGeeks=new HashSet<>();
                String[] allBlocked = blocked.split(",");
                Collections.addAll(blockedGeeks, allBlocked);
            }

            //create a new Geek object from the information above, and add it to the AllGeeks object
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
        if(username==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        while(allGeeks.getGeek(username)!=null || username.length()==0) { //if the username entered by the user is already in the dataset, request another username
            username = JOptionPane.showInputDialog("This username is invalid or is already taken. Please try again. ");
            if(username==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        }
        //otherwise, request that they enter their name
        String name;
        do{
            name = JOptionPane.showInputDialog("Please enter your full name (in format firstname surname): ");
            if(name==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        } while(!name.contains(" ")); //their first name and surname must be separated by a whitespace

        //once they've entered a valid name, request that they enter their age
        int age = 0;
        do{
            String ageInput = JOptionPane.showInputDialog("Please enter your age (18+ only): ");
            if(ageInput==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            try {
                age = Integer.parseInt(ageInput);
            }catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Invalid entry. You must enter a valid integer.");
            }
        }
        while (age<18); //continue requesting the user's age input for as long as it is not valid

        //once they've entered a valid age, request that they enter their gender
        String gender;
        do{
            gender = JOptionPane.showInputDialog("Please enter your gender (male/female/other): ");
            if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        } while (gender.length()==0);
        if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) gender="other"; //if the user enters anything other than male and female, assign 'other' to gender

        String starSign;
        do { //this is a lot of typing! Can you think of a better way to achieve this?
            starSign = JOptionPane.showInputDialog("Please enter your star sign (Capricorn, Aquarius, Pisces, Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius): ");
            if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        }while (starSign.length()==0);

        long phoneNumber = 0;
        String phoneNumberInput;
        do{
            phoneNumberInput = JOptionPane.showInputDialog("Please enter your phone number (10 digit number in the format 0412345678): ");
            if(phoneNumberInput==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            try {
                phoneNumber = Long.parseLong(phoneNumberInput);
            }catch (NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Invalid entry. You must enter a 10 digit number in the format 0412345678.");
            }
        }while (phoneNumber==0 && phoneNumberInput.length()!=10); //continue requesting input until they've entered valid input

        String statement = JOptionPane.showInputDialog("Please tell us why you think you're a geek: ");
        if(statement==null) System.exit(0); //if the user closes/cancels the dialog, exit normally

        //ask users to enter all their favourite games, storing them in a Set
        String faveGame = JOptionPane.showInputDialog("Please enter your favourite computer game or enter s to skip: ");
        if(faveGame==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        Set<String> faveGames = new HashSet<>();
        while(!faveGame.equals("s") && !faveGame.equals("d")){
            faveGames.add(faveGame);
            faveGame = JOptionPane.showInputDialog("Please enter your next favourite computer game or enter d to finish: ");
            if(faveGame==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        }
        //create and return a new Geek object using the info collected above
        return new Geek(username,name,age,gender,starSign,statement,faveGames,null,phoneNumber);
    }

    /**
     * method to add a new geek to the file containing all the geek data
     * @param newGeek a Geek object representing the current Geek user of the program
     */
    private static void addGeekToFile(Geek newGeek) {
        //load the file
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try{
            fileContents = Files.readAllLines(path);
        }catch (IOException ioRead){
            System.out.println("File could not be read. \nError message: "+ioRead.getMessage());
            System.exit(0);
        }
        //reformat the gender data to write to the file
        String gender = newGeek.getGender().toUpperCase();
        //create a String to add to the file
        String geekLineToAdd = newGeek.getUsername()+","+newGeek.getName()+","+gender+","+newGeek.getAge()+",0"+
                newGeek.getPhoneNumber()+","+newGeek.getStarSign()+",["+newGeek.getStatement()+
                "],"+newGeek.getFavouriteGames()+","+newGeek.getBlockedGeeks();
        //add the String to the List of Strings
        fileContents.add(geekLineToAdd);

        //write the List of Strings to the file
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
            return false; //if the username entered is not on the database return an appropriate message
        }
        return true;
    }

    /**
     * obtains user input for the criteria they desire in a companion geek including age, star sign, gender and favourite games
     * uses this information to search the Geek dataset for compatible geeks. If there is a compatible Geek, the user is given the option
     * to contact the compatible geek. If there isn't, an appropriate message is provided to the user.
     */
    private static void searchForGeek(){
        //ask the user for their username, so that you can verify that they've signed up and exclude any blocked geeks from their potential matches list
        String username = JOptionPane.showInputDialog("Please enter your username:");
        if(username==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        if(checkUsernameExists(username)) {
            //ask for dream geek preferences
            int age = 0;
            do {
                String preferredAge = JOptionPane.showInputDialog("Please enter your preferred age (must be >= 18): ");
                if (preferredAge == null) System.exit(0); //if the user closes/cancels the dialog, exit normally
                try {
                    age = Integer.parseInt(preferredAge);
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Invalid entry.");
                }
            }while(age < 18);

            String starSign;
            do { //can you think of a better way to achieve this?
                starSign = JOptionPane.showInputDialog("Please enter your preferred star sign (Capricorn, Aquarius, Pisces, Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius): ");
                if(starSign==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            }while (starSign.length()==0);

            String gender;
            do{
                gender = JOptionPane.showInputDialog("Please enter your preferred gender (male/female/other): ");
                if(gender==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            } while (gender.length()==0);
            if(!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) gender="other"; //if the user enters anything other than male and female, assign 'other' to gender


            Set<String> favouriteGames = new HashSet<>();
            String userInput = JOptionPane.showInputDialog("Would you like your dream geek to be into games? (yes/no)");
            if(userInput==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
            if (userInput.equalsIgnoreCase("yes")) {
                userInput = JOptionPane.showInputDialog("Which game would you like your dream geek to be into? (enter done to finish)");
                while (!userInput.equalsIgnoreCase("done")) {
                    favouriteGames.add(userInput);
                    userInput = JOptionPane.showInputDialog("If there is another game you'd like to add, enter it or enter done to finish");
                    if(userInput==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
                }
            } else favouriteGames = null;

            //create a new Geek object to represent the dream geek - empty arguments should indicate to you that the Geek class is being misused - more on that next week!
            Geek dreamGeek = new Geek("", "", age, gender, starSign, "", favouriteGames, null,0);
            //search the database for compatible geeks
            Geek potentialMatch = allGeeks.findDreamGeek(dreamGeek, username);
            //if there are no potential matches, let the user know
            if (potentialMatch == null) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.");
            //if there is a potential match, ask the user whether they'd like to contact their match
            else {
                String decision = JOptionPane.showInputDialog("Potential match! " + potentialMatch.getName() + " is an " + potentialMatch.getAge() + " year old " + potentialMatch.getStarSign() + " " +
                        potentialMatch.getGender() + " who loves the following games: " + potentialMatch.getFavouriteGames().toString() + ".\nAbout " + potentialMatch.getName() + ": " + potentialMatch.getStatement() +
                        "\nWould you like to contact them? (yes/no)");
                //if the user chooses to connect, provide them with the potential match's phone number
                if (decision.equalsIgnoreCase("yes"))
                    JOptionPane.showMessageDialog(null, potentialMatch.getName() + "'s phone number is: 0" + potentialMatch.getPhoneNumber());
                else
                    JOptionPane.showMessageDialog(null, potentialMatch.getName() + " will not be informed or contacted about your search.");
            }
        }
    }

    /**
     * obtains user input 1) user's username and 2) the username of the Geek to block
     * if username exists, call the addGeekToBlockedListOnFile method to write the username to the file
     * otherwise, let the user know that no such username exists
     */
    private static void blockAGeek(){
        //they must enter their username to use block another geek
        String username = JOptionPane.showInputDialog("Please enter your username: ");
        if(username==null) System.exit(0); //if the user closes/cancels the dialog, exit normally
        if(checkUsernameExists(username)) {
            //if the username is in the database, ask them who they want to block
            String geekToBlock = JOptionPane.showInputDialog("Please enter the username of the Geek you'd like to block: ");
            //if the geek they want to block doesn't exist, let the user know
            if (allGeeks.getGeek(geekToBlock) == null)
                JOptionPane.showMessageDialog(null, "A geek with username " + geekToBlock + " does not exist.");
            else if(username.equals(geekToBlock))
                JOptionPane.showMessageDialog(null, "You can't block yourself!");
            else {
                //if the geek does exist, add them to the user's blocked list
                Geek user = allGeeks.getGeek(username);
                user.blockGeek(allGeeks.getGeek(geekToBlock));
                //now that the data has changed, write those changes to the file using the addGeekToBlockedListOnFile method in this class
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
        //load the file
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try{
            fileContents = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("File could not be loaded. \nError message: "+io.getMessage());
            System.exit(0);
        }
        //iterate through the List of Strings (skip the first)
        for (int i = 1; i < fileContents.size(); i++) {
            //split each String by , and check if the first item is equal to the user parameter's username property
            String[] splitData = fileContents.get(i).split(",");
            if (splitData[0].equals(user.getUsername())) {
                //once the username has been correctly located, generate a new String containing the new blocked geek info
                String gender = splitData[2];
                String newLine = user.getUsername() + "," + user.getName() + "," + gender + "," + user.getAge() + ",0" +
                        user.getPhoneNumber() + "," + user.getStarSign() + ",[" + user.getStatement() +
                        "]," + user.getFavouriteGames() + "," + user.getBlockedGeeks();
                //replace the relevant String in the List with the new line
                fileContents.set(i, newLine);
                //write the altered List to the file
                try{
                    Files.write(path,fileContents);
                }catch (IOException ioWrite){
                    System.out.println("File could not be written. \nError message: "+ioWrite.getMessage());
                    System.exit(0);
                }
                //terminate here - there's no point continuing to loop through the list once the job has been done.
                break;
            }
        }
    }

}
