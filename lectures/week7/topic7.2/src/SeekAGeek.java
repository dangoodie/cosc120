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
    private static final String filePath = "allGeeks.txt";
    private static AllGeeks allGeeks;
    private static final String appName = "Seek A Geek 8D";
    private final static String iconPath = "icon.png";
    private static final ImageIcon icon = new ImageIcon(iconPath);

    /**
     * main method used to interact with user - program simplified to only searching for a geek
     * checks if user is on file (valid username), if so, allows user to search for and get matching geek contact details
     * @param args none required
     */
    public static void main(String[] args) {
        allGeeks = loadGeeks();
        String username = (String) JOptionPane.showInputDialog(null,"Welcome to Seek A Geek! \n\nPlease enter your username:",
                appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {
            TypeOfDreamGeek type = (TypeOfDreamGeek) JOptionPane.showInputDialog(null,"What kind of geek companion you looking for? ",
                    appName,JOptionPane.QUESTION_MESSAGE,icon,TypeOfDreamGeek.values(),TypeOfDreamGeek.FRIEND);
            DreamGeek dreamGeek = searchForGeek(type);
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

            //EDIT 10: parse the school and graduation data from the file and add it to the criteria Map.
            String school = geekInfo[13].toLowerCase();
            String graduationYear = geekInfo[14].toUpperCase(); //add validation here

            /*EDIT 5: use the new loadCollectionsData method to automate this and minimise duplicate code
            String[] favouriteGames = elements[1].replace("]","").replace("\r","").split(",");
            Set<String> faveCompGames = new HashSet<>();
            for(String game: favouriteGames) faveCompGames.add(game.toLowerCase().strip());

            String[] faveTVShows = elements[2].replace("]","").replace("\r","").split(",");
            Set<String> favouriteTVShows = new HashSet<>();
            for(String tvShow: faveTVShows) favouriteTVShows.add(tvShow.toLowerCase().strip());

            String blocked = elements[4].replace("]","").replace("\r","");
            Set<String> blockedGeeks = null;
            if(blocked.length()>0){
                blockedGeeks=new HashSet<>();
                String[] allBlocked = blocked.split(",");
                Collections.addAll(blockedGeeks, allBlocked);
            }*/

            //EDIT 12: read the types of relationships data from the file
            Set<String> typesOfRelationships = loadCollectionData(elements[1]);
            Set<TypeOfDreamGeek> typeOfDreamGeek = new HashSet<>();
            for(String type: typesOfRelationships) typeOfDreamGeek.add(TypeOfDreamGeek.valueOf(type.toUpperCase().replace(" ","_")));

            //EDIT 4: read the list of hobbies data from the file...
            Set<String> hobbies = loadCollectionData(elements[2]);
            //EDIT 5: use the new loadCollectionsData method to read the collection type data
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
            //EDIT 4: add the list of hobbies data to the Map
            criteriaMap.put(Criteria.HOBBIES,hobbies);
            //EDIT 10: add school and graduation data to the criteria Map.
            criteriaMap.put(Criteria.SCHOOL,school);
            criteriaMap.put(Criteria.GRADUATION_YEAR,graduationYear);
            //EDIT 12: add the types of relationships data to the map
            criteriaMap.put(Criteria.TYPE_OF_RELATIONSHIP,typeOfDreamGeek);

            DreamGeek dreamGeek = new DreamGeek(criteriaMap);

            Geek geek = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeek);
            allGeeks.addGeek(geek);
        }
        return allGeeks;
    }

    //EDIT 5: create a method called loadCollectionData and use it to process the data for tv shows,
    //games and hobbies. We can also use the same method for the blocked geeks too!
    /**
     * use to parse/read collection type data from the file
     * @param rawData a String straight from the file in the format item1, item2 etc. containing unwanted characters
     * @return a Set of Strings, each representing one 'cleaned up' item in a collection
     */
    private static Set<String> loadCollectionData(String rawData){
        String[] splitData = rawData.replace("]","").replace("\r","").split(",");
        Set<String> processedData = new HashSet<>();
        for(String item: splitData) processedData.add(item.toLowerCase().strip());
        return processedData;
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
     * @return a DreamGeek object representing a user's 'perfect' match
     */
    private static DreamGeek searchForGeek(TypeOfDreamGeek type){
        int minAge = -1, maxAge = -1;
        while(minAge<18) {
            String userInput = (String) JOptionPane.showInputDialog(null,"What is the min age you desire? ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
            if(userInput==null) System.exit(0);
            try {
                minAge = Integer.parseInt(userInput);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
            }
            if(minAge<18) JOptionPane.showMessageDialog(null,"Age must be >= 18. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
        }
        while(maxAge<minAge) {
            String userInput = (String) JOptionPane.showInputDialog(null,"What is the max age you desire? ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
            if(userInput==null) System.exit(0);
            try {
                maxAge = Integer.parseInt(userInput);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
            }
            if(maxAge<minAge) JOptionPane.showMessageDialog(null,"Max age must be greater than "+minAge+". Please try again.",appName, JOptionPane.ERROR_MESSAGE);
        }

        Map<Criteria,Object> criteriaMap = new HashMap<>();
        //EDIT 13: add the user's preferred type of relationship to the map
        criteriaMap.put(Criteria.TYPE_OF_RELATIONSHIP,type);

        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your preferred star sign: ",
                appName, JOptionPane.QUESTION_MESSAGE,icon,StarSign.values(),StarSign.CAPRICORN);
        if(starSign==null) System.exit(0);
        if(!starSign.equals(StarSign.NA)) criteriaMap.put(Criteria.STAR_SIGN,starSign);

        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",appName, JOptionPane.QUESTION_MESSAGE,icon,Gender.values(),Gender.OTHER);
        if(gender==null) System.exit(0);
        //EDIT 2: only add the user’s Gender selection to the criteria Map if it is not NA.
        if(!gender.equals(Gender.NA)) criteriaMap.put(Criteria.GENDER,gender);

        if(type==TypeOfDreamGeek.STUDY_BUDDY) {
            String[] options = {"Institution and course", "Subject area"};
            String selectedOption = (String) JOptionPane.showInputDialog(null, "How would you like to search for a study buddy?",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, options, "");
            if (selectedOption.equals(options[0])) {
                String institution = (String) JOptionPane.showInputDialog(null, "Please enter the institution you're interested in: ",
                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(institution==null) System.exit(0);
                institution=institution.toLowerCase();
                String course = (String) JOptionPane.showInputDialog(null, "Please enter the course you're interested in: ",
                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(course==null) System.exit(0);
                course=course.toLowerCase();
                criteriaMap.put(Criteria.INSTITUTION,institution);
                criteriaMap.put(Criteria.COURSE,course);
            } else {
                String subjectArea = (String) JOptionPane.showInputDialog(null, "Please enter your subject area: ",
                        appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(subjectArea==null) System.exit(0);
                subjectArea=subjectArea.toUpperCase();
                criteriaMap.put(Criteria.SUBJECT_AREA,subjectArea);
            }
        }
        //EDIT 9: add an else if(OldSchoolFriend statement), and request user input for
        // school and graduation year. Add these to the Map.
        else if(type==TypeOfDreamGeek.OLD_SCHOOL_FRIEND){
            String school = (String) JOptionPane.showInputDialog(null, "At what school did your old friend attend? ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if(school==null) System.exit(0);
            criteriaMap.put(Criteria.SCHOOL,school);
            //add error catching and input validation, e.g. year must start with 19 or 20 and be 4 digits long.
            String graduationYear = (String) JOptionPane.showInputDialog(null, "During which year did your old friend graduate? ",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
            if(graduationYear==null) System.exit(0);
            criteriaMap.put(Criteria.GRADUATION_YEAR,graduationYear);
        }
        else{
            Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",
                    appName, JOptionPane.QUESTION_MESSAGE,icon,Religion.values(),Religion.UNAFFILIATED);
            if(religion==null) System.exit(0);
            if(!religion.equals(Religion.NA)) criteriaMap.put(Criteria.RELIGION,religion);

            Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
            if(favouriteGames.size()!=0) criteriaMap.put(Criteria.FAVOURITE_COMPUTER_GAMES,favouriteGames);

            Set<String> favouriteTVShows = getUserFavouriteCollection("binging tv shows","tv show");
            if(favouriteTVShows.size()!=0) criteriaMap.put(Criteria.FAVOURITE_TV_SHOWS,favouriteTVShows);

            //EDIT 6: use our good design to easily get user hobby data
            Set<String> hobbies = getUserFavouriteCollection("hobbies","hobby");
            if(hobbies.size()!=0) criteriaMap.put(Criteria.HOBBIES,hobbies);

            if(type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)){
                ValentinesGifts valentinesGift = (ValentinesGifts) JOptionPane.showInputDialog(null, "The kind of Valentine's gift a Geek loves " +
                                "says a lot about their personality. From the list, which would you be most likely to buy for your dream geek on Valentine's Day?",
                        appName, JOptionPane.QUESTION_MESSAGE, icon, ValentinesGifts.values(), ValentinesGifts.CHESS_SET);
                if(valentinesGift==null) System.exit(0);
                criteriaMap.put(Criteria.VALENTINES_GIFT,valentinesGift);

                RomanticActivities favouriteRomanticActivity = (RomanticActivities) JOptionPane.showInputDialog(null, "Select your favourite " +
                                "romantic activity from the list.", appName, JOptionPane.QUESTION_MESSAGE, icon, RomanticActivities.values(), RomanticActivities.BINGE_TV_SHOWS);
                if(favouriteRomanticActivity==null) System.exit(0);
                criteriaMap.put(Criteria.ROMANTIC_ACTIVITY,favouriteRomanticActivity);
            }
        }
        return new DreamGeek(minAge,maxAge,criteriaMap);
    }

    /**
     * a method that uses JOptionPane to get the user to enter their dream geek's favourite activities
     * @param activity a geeky activity, e.g., binging tv shows or playing computer games
     * @param item a singular geek item, e.g., game or tv show
     * @return a Set of Strings, where each String is one geek item
     */
    public static Set<String> getUserFavouriteCollection(String activity, String item){
        Set<String> items = new HashSet<>();
        String userInput = (String) JOptionPane.showInputDialog(null,"Would you like your dream geek to be into "+activity+"? (yes/no)",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
        if(userInput==null) System.exit(0);
        if (userInput.equalsIgnoreCase("yes")) {
            userInput = (String) JOptionPane.showInputDialog(null,"Which "+item+" would you like your dream geek to be into? (enter done to finish)",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
            while (!userInput.equalsIgnoreCase("done")) {
                items.add(userInput.toLowerCase());
                userInput = (String) JOptionPane.showInputDialog(null,"If there is another "+item+" you'd like to add, enter it or enter done to finish",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
                if(userInput==null) System.exit(0);
            }
        }
        return items;
    }

    /**
     * a method to display the user's matches (if there are any)
     * @param dreamGeek a DreamGeek object representing the user's 'dream' geek
     * @param username the user's unique, registered username
     */
    public static void processSearchResults(DreamGeek dreamGeek, String username){
        List<Geek> potentialMatches = allGeeks.findDreamGeek(dreamGeek, username);
        if (potentialMatches.size()==0) JOptionPane.showMessageDialog(null, "Sadly, no match meets your criteria.",appName, JOptionPane.INFORMATION_MESSAGE);
        else {
            String[] options = new String[potentialMatches.size()];
            StringBuilder infoToShow= new StringBuilder("You have the following potential matches: \n");
            for(int i=0;i<potentialMatches.size();i++){
                infoToShow.append(potentialMatches.get(i).getGeekDescription());
                options[i]=potentialMatches.get(i).getName();
            }
            String decision = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",
                    appName, JOptionPane.QUESTION_MESSAGE,icon,options,options[0]);
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
