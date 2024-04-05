import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeekAGeek {
    //fields
    private static final String filePath = "./allGeeks.txt";
    private static AllGeeks allGeeks;
    private static final String appName = "Seek A Geek 8D";
    private final static String iconPath = "./icon.png";
    private static final ImageIcon icon = new ImageIcon(iconPath);

    /**
     * main method used to interact with user - program simplified to only searching for a geek
     * checks if user is on file (valid username), if so, allows user to search for and get matching geek contact details
     * @param args none required
     */
    public static void main(String[] args) {
        allGeeks = loadGeeks();
        String username = (String) JOptionPane.showInputDialog(null,"Welcome to Seek A Geek! \n\nPlease enter your username:",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
        if(username==null) System.exit(0);
        if(checkUsernameExists(username)) {
            //EDIT: A new enum - Greek Geek wants us to cater for 3 types of user searches
            TypeOfDreamGeek type = (TypeOfDreamGeek) JOptionPane.showInputDialog(null,"What kind of geek companion you looking for? ",appName,JOptionPane.QUESTION_MESSAGE,icon,TypeOfDreamGeek.values(),TypeOfDreamGeek.FRIEND);
            //EDIT: pass the type of geek into the search method, as this will determine what kind of search data is requested
            DreamGeek dreamGeek = searchForGeek(type);
            processSearchResults(dreamGeek, username);
        }
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
        /*
        EDIT: new file format:username,name,age,phone number,email,gender,star sign,religion,romantic activity,valentine's gift,
        university,course,subject area,favourite computer games,favourite tv shows,statement,blockedGeeks
        */
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

            //EDIT: read the new data from the file
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
            //end edit

            String[] favouriteGames = elements[1].replace("]","").replace("\r","").split(",");
            Set<String> faveCompGames = new HashSet<>();
            for(String game: favouriteGames) faveCompGames.add(game.toLowerCase().strip());

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

            //EDIT: Create different types of DreamGeek objects for the geeks based on their data
            if((!institution.equals("na") && !course.equals("na")) || !subjectArea.equals("NA")){
                //if the geek has study buddy data, create a StudyBuddy object, and add it to allGeeks
                //notice the dummy 0, 0 values for minAge and maxAge - this is another poor design warning
                DreamGeek dreamGeekStudyBuddy = new StudyBuddy(0,0,gender,starSign,institution,course,subjectArea);
                Geek geekStudyBuddy = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeekStudyBuddy);
                allGeeks.addGeek(geekStudyBuddy);
            }
            if(!valentinesGift.name().equals("NA") && !romanticActivity.name().equals("NA")) {
                //if romantic activity and valentine's gift data have been provided, create a MoreThanAFriend object and add it to allGeeks
                DreamGeek dreamGeekMoreThanAFriend = new MoreThanAFriend(0,0,gender, starSign, religion, faveCompGames, favouriteTVShows,romanticActivity,valentinesGift);
                Geek geekMoreThanAFriend = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeekMoreThanAFriend);
                allGeeks.addGeek(geekMoreThanAFriend);
            }
            //the minimum requirement to use Seek A Geek is to provide Friend data, so let's create a Friend object for each Geek
            DreamGeek dreamGeekFriend = new Friend(0,0,gender, starSign, religion, faveCompGames, favouriteTVShows);
            Geek geekFriend = new Geek(username,name,age,statement,blockedGeeks,phoneNumber,emailAddress,dreamGeekFriend);
            allGeeks.addGeek(geekFriend);
            //END EDIT
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
    private static DreamGeek searchForGeek(TypeOfDreamGeek type){ //EDIT: add parameter type
        int minAge = -1, maxAge = -1;
        while(minAge<18) {
            String userInput = (String) JOptionPane.showInputDialog(null,"What is the min age you desire? ",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
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
            String userInput = (String) JOptionPane.showInputDialog(null,"What is the max age you desire? ",appName, JOptionPane.QUESTION_MESSAGE, icon,null,null);
            if(userInput==null) System.exit(0);
            try {
                maxAge = Integer.parseInt(userInput);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.",appName, JOptionPane.ERROR_MESSAGE);
            }
            if(maxAge<minAge) JOptionPane.showMessageDialog(null,"Max age must be greater than "+minAge+". Please try again.",appName, JOptionPane.ERROR_MESSAGE);
        }
        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null,"Please select your preferred star sign: ",appName, JOptionPane.QUESTION_MESSAGE,icon,StarSign.values(),StarSign.CAPRICORN);
        if(starSign==null) System.exit(0);
        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",appName, JOptionPane.QUESTION_MESSAGE,icon,Gender.values(),Gender.OTHER);
        if(gender==null) System.exit(0);

        //EDIT: the type of DreamGeek that is instantiated will vary depending on the user's type of geek selection, so leave it uninitialised
        DreamGeek dreamGeek;
        //EDIT: if the user picked study buddy
        if(type==TypeOfDreamGeek.STUDY_BUDDY) {
            //give the user the option to search by institution and course OR subject area
            String[] options = {"Institution and course", "Subject area"};
            String selectedOption = (String) JOptionPane.showInputDialog(null, "How would you like to search for a study buddy?", appName, JOptionPane.QUESTION_MESSAGE, icon, options, "");
            if (selectedOption.equals(options[0])) {
                //if the user picks institution and course, get them to enter their preferences (maybe a dropdown list here would be a better option?)
                String institution = (String) JOptionPane.showInputDialog(null, "Please enter the institution you're interested in: ", appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(institution==null) System.exit(0);
                institution=institution.toLowerCase();
                String course = (String) JOptionPane.showInputDialog(null, "Please enter the course you're interested in: ", appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(course==null) System.exit(0);
                course=course.toLowerCase();
                //this is polymorphism in action - the DreamGeek variable has taken on the form of StudyBuddy!
                //You should be noticing a design flaw here ... hard coding values is always a warning
                dreamGeek = new StudyBuddy(minAge, maxAge, gender, starSign, institution, course, "NA");
            } else {
                //if the user picks subject area, get them to enter their preference
                String subjectArea = (String) JOptionPane.showInputDialog(null, "Please enter your subject area: ", appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
                if(subjectArea==null) System.exit(0);
                subjectArea=subjectArea.toUpperCase();
                //more poor design!!!
                dreamGeek = new StudyBuddy(minAge, maxAge, gender, starSign, "NA", "NA", subjectArea);
            }
        }
        //if the user picked friend
        else if(type==TypeOfDreamGeek.FRIEND){
            Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",appName, JOptionPane.QUESTION_MESSAGE,icon,Religion.values(),Religion.UNAFFILIATED);
            if(religion==null) System.exit(0);
            Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
            Set<String> favouriteTVShows = getUserFavouriteCollection("binging tv shows","tv show");
            //this is polymorphism in action - the DreamGeek variable has taken on the form of Friend!
            dreamGeek = new Friend(minAge,maxAge,gender,starSign,religion,favouriteGames,favouriteTVShows);
        }
        else{
            //the only other option is MoreThanAFriend... which is very similar to Friend, so we're going to have to duplicate the above code,
            // and then add the romantic activity and valentine's gift data. This duplication is a BIG poor design warning
            Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",appName, JOptionPane.QUESTION_MESSAGE,icon,Religion.values(),Religion.UNAFFILIATED);
            if(religion==null) System.exit(0);
            Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
            Set<String> favouriteTVShows = getUserFavouriteCollection("binging tv shows","tv show");
            //now deal with the romantic activity and valentine's data
            ValentinesGifts valentinesGift = (ValentinesGifts) JOptionPane.showInputDialog(null, "The kind of Valentine's gift a Geek loves " +
                                "says a lot about their personality. From the list, which would you be most likely to buy for your dream geek on Valentine's Day?",
                        appName, JOptionPane.QUESTION_MESSAGE, icon, ValentinesGifts.values(), ValentinesGifts.CHESS_SET);
            if(valentinesGift==null) System.exit(0);
            RomanticActivities favouriteRomanticActivity = (RomanticActivities) JOptionPane.showInputDialog(null, "Select your favourite " +
                                "romantic activity from the list.", appName, JOptionPane.QUESTION_MESSAGE, icon, RomanticActivities.values(), RomanticActivities.BINGE_TV_SHOWS);
            if(favouriteRomanticActivity==null) System.exit(0);
            //this is polymorphism in action - the DreamGeek variable has taken on the form of MoreThanAFriend!
            dreamGeek = new MoreThanAFriend(minAge,maxAge,gender,starSign,religion,favouriteGames,favouriteTVShows,favouriteRomanticActivity,valentinesGift);
        }
        //END EDIT
        //return the user's 'dream' geek
        return dreamGeek;
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
        } else items = null;
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
            String decision = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",appName, JOptionPane.QUESTION_MESSAGE,icon,options,options[0]);
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
