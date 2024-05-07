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

public class SeekAGeek {
    private static final String filePath = "./allGeeks.txt";
    private static final String premiumUsersFilePath = "./premiumGeeks.txt";
    private static AllGeeks allGeeks;
    private static final String appName = SubscriptionFeatures.appName;//"Seek A Geek 8D";
//    private final static String iconPath = "./icon.png";
    private static final ImageIcon icon = SubscriptionFeatures.icon;//new ImageIcon(iconPath);

    //EDIT 24
    private static SubscriptionFeatures user;

    /**
     * main method used to interact with user - program simplified to only searching for a geek
     * checks if user is on file (valid username), if so, allows user to search for and get matching geek contact details
     * @param args none required
     */
    public static void main(String[] args) {
        //EDIT 23: call the loadPremiumUsers method to get the usernames of premium users
        Set<String> premiumSubscribers = loadPremiumUsers(premiumUsersFilePath);
        allGeeks = loadGeeks();
        String username = (String) JOptionPane.showInputDialog(null,"Welcome to Seek A Geek! \n\nPlease enter your username:",
                appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {

            //EDIT 24: if the username entered is valid, check if it is premium. If so, create a
            //Premium object, otherwise, create a Basic object (code to an interface!)
            if(premiumSubscribers.contains(username)) user = new Premium();
            else user = new Basic(1);

//            TypeOfDreamGeek type = (TypeOfDreamGeek) JOptionPane.showInputDialog(null,"What kind of geek companion are you looking for? ",
//                    appName,JOptionPane.QUESTION_MESSAGE,icon,TypeOfDreamGeek.values(),TypeOfDreamGeek.FRIEND);

            //EDIT 25: Use the getUserInput method to generate the user’s DreamGeek object.
            DreamGeek dreamGeek = user.getUserInput();

            //DreamGeek dreamGeek = searchForGeek(type);
            processSearchResults(dreamGeek, username);
        }
        System.exit(0);
    }

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
            RomanticActivities romanticActivity = RomanticActivities.BINGE_TV_SHOWS;
            try{
                romanticActivity = RomanticActivities.valueOf(geekInfo[8].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Romantic activity could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            ValentinesGifts valentinesGift = ValentinesGifts.CHESS_SET;
            try{
                valentinesGift = ValentinesGifts.valueOf(geekInfo[9].toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Error in file. Valentine's Day gift could not be parsed for geek on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                System.exit(0);
            }

            String institution = geekInfo[10].toLowerCase();
            String course = geekInfo[11].toLowerCase();
            String subjectArea = geekInfo[12].toUpperCase();
            String school = geekInfo[13].toLowerCase();
            String graduationYear = geekInfo[14].toUpperCase(); //add validation here

            Set<String> typesOfRelationships = loadCollectionData(elements[1]);
            Set<TypeOfDreamGeek> typeOfDreamGeek = new HashSet<>();
            for(String type: typesOfRelationships) typeOfDreamGeek.add(TypeOfDreamGeek.valueOf(type.toUpperCase().replace(" ","_")));

            Set<String> hobbies = loadCollectionData(elements[2]);
            Set<String> faveCompGames = loadCollectionData(elements[3]);
            Set<String> favouriteTVShows = loadCollectionData(elements[4]);
            String statement = elements[5].replace("],","");
            Set<String> blockedGeeks = loadCollectionData(elements[6]);

            Map<Criteria,Object> criteriaMap = new LinkedHashMap<>();
            criteriaMap.put(Criteria.GENDER,gender);
            criteriaMap.put(Criteria.STAR_SIGN,starSign);
            criteriaMap.put(Criteria.RELIGION,religion);
            criteriaMap.put(Criteria.ROMANTIC_ACTIVITY,romanticActivity);
            criteriaMap.put(Criteria.VALENTINES_GIFT,valentinesGift);
            criteriaMap.put(Criteria.INSTITUTION,institution);
            criteriaMap.put(Criteria.COURSE,course);
            criteriaMap.put(Criteria.SUBJECT_AREA,subjectArea);
            criteriaMap.put(Criteria.FAVOURITE_COMPUTER_GAMES,faveCompGames);
            criteriaMap.put(Criteria.FAVOURITE_TV_SHOWS,favouriteTVShows);
            criteriaMap.put(Criteria.HOBBIES,hobbies);
            criteriaMap.put(Criteria.SCHOOL,school);
            criteriaMap.put(Criteria.GRADUATION_YEAR,graduationYear);
            criteriaMap.put(Criteria.TYPE_OF_RELATIONSHIP,typeOfDreamGeek);

            DreamGeek dreamGeek = new DreamGeek(criteriaMap);

            Geek geek = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeek);
            allGeeks.addGeek(geek);
        }
        return allGeeks;
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

    //EDIT 22: write this method to read the new premiumGeeks.txt data file
    /**
     * a method to read the premium users file (only usernames)
     * @param filePath a String representing the path to the file containing premium users
     * @return a Set of usernames of premium users
     */
    private static Set<String> loadPremiumUsers(String filePath) {
        Set<String> premiumUsers = new HashSet<>();
        List<String> premiumUsersRaw = null;
        try {
            premiumUsersRaw = Files.readAllLines(Path.of(filePath));
        }catch (IOException io) {
            System.out.println("The file could not be loaded. Check file path is correct. Terminating.\nError message: " + io.getMessage());
            System.exit(0);
        }
        for(String geek: premiumUsersRaw) premiumUsers.add(geek.split(",")[0]);
        return premiumUsers;
    }

    /**
     * use to parse/read collection type data from the file
     * @param rawData a String straight from the file in the format item1, item2 etc. containing unwanted characters
     * @return a Set of Strings, each representing one 'cleaned up' item in a collection
     */
    private static Set<String> loadCollectionData(String rawData){
        String[] splitData = rawData.replace("]","").replace("\r","").split(",");
        //EDIT 33: to display the lists in alphabetical order,
        //simply change the type of Set from a HashSet to a TreeSet.
        Set<String> processedData = new TreeSet<>();
        for(String item: splitData) {
            processedData.add(item.toLowerCase().strip());
        }
        return processedData;
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

    //EDIT 26: comment this method out, as user input is handled by Premium and Basic
//    private static DreamGeek searchForGeek(TypeOfDreamGeek type){
//        int minAge = -1, maxAge = -1;
//        while(minAge<18) {
//            String userInput = (String) JOptionPane.showInputDialog(null,"What is the min age you desire? ",
//                    appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
//            if(userInput==null) System.exit(0);
//            try {
//                minAge = Integer.parseInt(userInput);
//            }
//            catch (NumberFormatException e){
//                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
//            }
//            if(minAge<18) JOptionPane.showMessageDialog(null,"Age must be >= 18. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
//        }
//        while(maxAge<minAge) {
//            String userInput = (String) JOptionPane.showInputDialog(null,"What is the max age you desire? ",
//                    appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
//            if(userInput==null) System.exit(0);
//            try {
//                maxAge = Integer.parseInt(userInput);
//            }
//            catch (NumberFormatException e){
//                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
//            }
//            if(maxAge<minAge) JOptionPane.showMessageDialog(null,"Max age must be greater than "+minAge+". Please try again.",appName, JOptionPane.ERROR_MESSAGE);
//        }

//        Map<Criteria,Object> criteriaMap = new HashMap<>();
//        criteriaMap.put(Criteria.TYPE_OF_RELATIONSHIP,type);

//        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your preferred star sign: ",
//                appName, JOptionPane.QUESTION_MESSAGE,icon,StarSign.values(),StarSign.CAPRICORN);
//        if(starSign==null) System.exit(0);
//        if(!starSign.equals(StarSign.NA)) criteriaMap.put(Criteria.STAR_SIGN,starSign);

//        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",appName, JOptionPane.QUESTION_MESSAGE,icon,Gender.values(),Gender.OTHER);
//        if(gender==null) System.exit(0);
//        if(!gender.equals(Gender.NA)) criteriaMap.put(Criteria.GENDER,gender);

//        if(type==TypeOfDreamGeek.STUDY_BUDDY) {
//            String[] options = {"Institution and course", "Subject area"};
//            String selectedOption = (String) JOptionPane.showInputDialog(null, "How would you like to search for a study buddy?",
//                    appName, JOptionPane.QUESTION_MESSAGE, icon, options, "");
//            if (selectedOption.equals(options[0])) {
//                String institution = (String) JOptionPane.showInputDialog(null, "Please enter the institution you're interested in: ",
//                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
//                if (institution == null) System.exit(0);
//                institution = institution.toLowerCase();
//                String course = (String) JOptionPane.showInputDialog(null, "Please enter the course you're interested in: ",
//                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
//                if (course == null) System.exit(0);
//                course = course.toLowerCase();
//                criteriaMap.put(Criteria.INSTITUTION, institution);
//                criteriaMap.put(Criteria.COURSE, course);
//            } else {
//                String subjectArea = (String) JOptionPane.showInputDialog(null, "Please enter your subject area: ",
//                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
//                if (subjectArea == null) System.exit(0);
//                subjectArea = subjectArea.toUpperCase();
//                criteriaMap.put(Criteria.SUBJECT_AREA, subjectArea);
//            }
//        }
//        else if(type==TypeOfDreamGeek.OLD_SCHOOL_FRIEND){
//            String school = (String) JOptionPane.showInputDialog(null, "At what school did your old friend attend? ",
//                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
//            if(school==null) System.exit(0);
//            criteriaMap.put(Criteria.SCHOOL,school);
//            String graduationYear = (String) JOptionPane.showInputDialog(null, "During which year did your old friend graduate? ",
//                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
//            if(graduationYear==null) System.exit(0);
//            criteriaMap.put(Criteria.GRADUATION_YEAR,graduationYear);
//        }
//        else{
//            Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",
//                    appName, JOptionPane.QUESTION_MESSAGE,icon,Religion.values(),Religion.UNAFFILIATED);
//            if(religion==null) System.exit(0);
//            if(!religion.equals(Religion.NA)) criteriaMap.put(Criteria.RELIGION,religion);

//            Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
//            if(favouriteGames.size()!=0) criteriaMap.put(Criteria.FAVOURITE_COMPUTER_GAMES,favouriteGames);
//
//            Set<String> favouriteTVShows = getUserFavouriteCollection("binging tv shows","tv show");
//            if(favouriteTVShows.size()!=0) criteriaMap.put(Criteria.FAVOURITE_TV_SHOWS,favouriteTVShows);
//
//            Set<String> hobbies = getUserFavouriteCollection("hobbies","hobby");
//            if(hobbies.size()!=0) criteriaMap.put(Criteria.HOBBIES,hobbies);

//            if(type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)){
//                ValentinesGifts valentinesGift = (ValentinesGifts) JOptionPane.showInputDialog(null, "The kind of Valentine's gift a Geek loves " +
//                                "says a lot about their personality. From the list, which would you be most likely to buy for your dream geek on Valentine's Day?",
//                        appName, JOptionPane.QUESTION_MESSAGE, icon, ValentinesGifts.values(), ValentinesGifts.CHESS_SET);
//                if(valentinesGift==null) System.exit(0);
//                criteriaMap.put(Criteria.VALENTINES_GIFT,valentinesGift);

//                RomanticActivities favouriteRomanticActivity = (RomanticActivities) JOptionPane.showInputDialog(null, "Select your favourite " +
//                                "romantic activity from the list.", appName, JOptionPane.QUESTION_MESSAGE, icon, RomanticActivities.values(), RomanticActivities.BINGE_TV_SHOWS);
//                if(favouriteRomanticActivity==null) System.exit(0);
//                criteriaMap.put(Criteria.ROMANTIC_ACTIVITY,favouriteRomanticActivity);
//            }
//        }
//        return new DreamGeek(minAge,maxAge,criteriaMap);
//    }

//    /**
//     * a method that uses JOptionPane to get the user to enter their dream geek's favourite activities
//     * @param activity a geeky activity, e.g., binging tv shows or playing computer games
//     * @param item a singular geek item, e.g., game or tv show
//     * @return a Set of Strings, where each String is one geek item
//     */
//    public static Set<String> getUserFavouriteCollection(String activity, String item){
//        Set<String> items = new HashSet<>();
//        String userInput = (String) JOptionPane.showInputDialog(null,"Would you like your dream geek to be into "+activity+"? (yes/no)",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
//        if(userInput==null) System.exit(0);
//        if (userInput.equalsIgnoreCase("yes")) {
//            userInput = (String) JOptionPane.showInputDialog(null,"Which "+item+" would you like your dream geek to be into? (enter done to finish)",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
//            while (!userInput.equalsIgnoreCase("done")) {
//                items.add(userInput.toLowerCase());
//                userInput = (String) JOptionPane.showInputDialog(null,"If there is another "+item+" you'd like to add, enter it or enter done to finish",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
//                if(userInput==null) System.exit(0);
//            }
//        }
//        return items;
//    }

    /**
     * a method to display the user's matches (if there are any)
     * @param dreamGeek a DreamGeek object representing the user's 'dream' geek
     * @param username the user's unique, registered username
     */
    public static void processSearchResults(DreamGeek dreamGeek, String username){
        List<Geek> potentialMatches = allGeeks.findDreamGeek(dreamGeek, username);
        if (potentialMatches.size()==0) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.",appName, JOptionPane.INFORMATION_MESSAGE);
        else{
            //EDIT 27:
            String decision = user.displayResults(potentialMatches);
            //moved to Premium/Basic displayResults method
//            String[] options = new String[potentialMatches.size()];
//            StringBuilder infoToShow= new StringBuilder("You have the following potential matches: \n");
//            for(int i=0;i<potentialMatches.size();i++){
//                infoToShow.append(potentialMatches.get(i).getGeekDescription());
//                options[i]=potentialMatches.get(i).getName();
//            }
//            String decision = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",
//                    appName, JOptionPane.QUESTION_MESSAGE,icon,options,options[0]);
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
