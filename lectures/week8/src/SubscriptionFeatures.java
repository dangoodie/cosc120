/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.util.*;

public interface SubscriptionFeatures {

    //EDIT 3: name of the app and app icon
    String appName = "Seek A Geek 8D";
    ImageIcon icon = new ImageIcon("./icon.png");

    //EDIT 7: premium user's monthly fee
    double premiumSubscriptionFee = 19.99;

    //EDIT 29: create 4 new fields; Sets to contain the Criteria to display for each type of Geek.
    //Because ‘fields’ in interfaces are public static and final by default, they can be used anywhere
    //in the program, without an object having to be created. This shows how interfaces can be useful
    //in ‘bunching together’ data and methods that relate to each other.

    //use a LinkedHashSet to preserve the order of the criteria
    //These fields give you explicit control over what data is displayed in the search results
    Set<Criteria> friendFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.GENDER,Criteria.STAR_SIGN,Criteria.RELIGION,
            Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES,Criteria.SUBJECT_AREA,
            Criteria.INSTITUTION,Criteria.COURSE));

    Set<Criteria> moreThanAFriendFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.GENDER,Criteria.STAR_SIGN,Criteria.RELIGION,
            Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES,Criteria.VALENTINES_GIFT,
            Criteria.ROMANTIC_ACTIVITY,Criteria.SUBJECT_AREA,Criteria.INSTITUTION,Criteria.COURSE));

    Set<Criteria> studyBuddyFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.GENDER,Criteria.STAR_SIGN,
            Criteria.RELIGION,Criteria.SUBJECT_AREA,Criteria.INSTITUTION,Criteria.COURSE,
            Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES));

    Set<Criteria> oldSchoolFriendFeatures = new LinkedHashSet<>(Arrays.asList(Criteria.SCHOOL,Criteria.GRADUATION_YEAR, Criteria.GENDER,
            Criteria.STAR_SIGN,Criteria.RELIGION,Criteria.FAVOURITE_COMPUTER_GAMES,Criteria.FAVOURITE_TV_SHOWS,Criteria.HOBBIES,Criteria.SUBJECT_AREA,
            Criteria.INSTITUTION,Criteria.COURSE));


    //EDIT 1 - : create two method headers/signatures.
    //This will mandate that all implementing classes (at this stage
    //Premium and Basic) implement these two methods.
    /**
     * a method to get the user to input data based on their level of subscription
     * the data should populate a Map</Criteria,Object> and select an age range to instantiate
     * a DreamGeek object
     * @return a DreamGeek object representing the user's conceptual 'dream' geek
     */
    DreamGeek getUserInput();

    /**
     * a method to show the user details about the Geek/s that meet their criteria
     * @param potentialMatches a List of Geeks that meet the user's criteria
     * @return a String value representing the user's selection of which Geek they prefer (or None)
     */
    String displayResults(List<Geek> potentialMatches);
    //END EDIT 1

    //EDIT 6:
    /**
     * a method to ask the user to choose what kind of relationship they're after
     * @return  the type/s chosen (only one possible choice at this stage)
     */
    default TypeOfDreamGeek getRelationshipType(){
        return (TypeOfDreamGeek) JOptionPane.showInputDialog(null,"What kind of geek companion you looking for? ",
                appName,JOptionPane.QUESTION_MESSAGE,icon,TypeOfDreamGeek.values(),TypeOfDreamGeek.FRIEND);
    }

    //EDIT 4: – Create a method to get the user to choose an age range (min and max ages).
    /**
     * method to get user to choose age range
     * @return array of 2 int values where index position 0 is minAge and 1 is maxAge
     */
    default int[] getUserInputMinMaxAge(){
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
        return new int[]{minAge,maxAge};
    }

    //EDIT 5: this method used to be in SeekAGeek.
    /**
     * a method that uses JOptionPane to get the user to enter their dream geek's favourite activities
     * @param activity a geeky activity, e.g., binging tv shows or playing computer games
     * @param item a singular geek item, e.g., game or tv show
     * @return a Set of Strings, where each String is one geek item
     */
    default Set<String> getUserFavouriteCollection(String activity, String item){
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

    //EDIT 2: create ONE default method per filter for user input
    /**
     * method to get user to choose their preferred gender from a dropdown list
     * @return the Gender (enum value) selected by the user
     */
    default Gender getGenderInput() {
        Gender gender = (Gender) JOptionPane.showInputDialog(null,"Please select your preferred gender: ",appName, JOptionPane.QUESTION_MESSAGE,icon,Gender.values(),Gender.OTHER);
        if(gender==null) System.exit(0);
        return gender;
    }

    /**
     * method to get user to select their preferred religion from a dropdown list
     * @return the Religion (enum value) selected by the user
     */
    default Religion getReligionInput(){
        Religion religion = (Religion) JOptionPane.showInputDialog(null,"Please select your preferred religion:",
                appName, JOptionPane.QUESTION_MESSAGE,icon,Religion.values(),Religion.UNAFFILIATED);
        if(religion==null) System.exit(0);
        return religion;
    }

    /**
     * method to get user to select their preferred star sign (or NA) from a dropdown list
     * @return the StarSign (enum value) selected by the user
     */
    default StarSign getStarSignInput() {
        StarSign starSign = (StarSign) JOptionPane.showInputDialog(null, "Please select your preferred star sign: ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, StarSign.values(), StarSign.CAPRICORN);
        if (starSign == null) System.exit(0);
        return starSign;
    }

    /*
    Methods specific to study buddy
     */

    /**
     * method to get user to input their preferred subject area - for study buddy
     * @return the String value entered by the user
     */
    default String getSubjectAreaInput(){
        String subjectArea = (String) JOptionPane.showInputDialog(null, "Please enter your subject area: ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if (subjectArea == null) System.exit(0);
        subjectArea = subjectArea.toUpperCase();
        return subjectArea;
    }

    /**
     * method to get user to input the institution at which they study - for study buddy
     * @return the String value entered by the user
     */
    default String getInstitutionInput() {
        String institution = (String) JOptionPane.showInputDialog(null, "Please enter the institution you're interested in: ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if (institution == null) System.exit(0);
        institution = institution.toLowerCase();
        return institution;
    }

    /**
     * method to get user to input the course they are studying - for study buddy
     * @return the String value entered by the user
     */
    default String getCourseInput(){
        String course = (String) JOptionPane.showInputDialog(null, "Please enter the course you're interested in: ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if (course == null) System.exit(0);
        course = course.toLowerCase();
        return course;
    }

    /*
    Methods specific to more than a friend
     */
    /**
     * method to get user to select their preferred valentine's gift from a dropdown list
     * @return user's selection of their dream Valentine's Day gift (type = ValentinesGifts enum)
     */
    default ValentinesGifts getValentinesGiftInput() {
        ValentinesGifts valentinesGift = (ValentinesGifts) JOptionPane.showInputDialog(null, "The kind of Valentine's gift a Geek loves " +
                        "says a lot about their personality. From the list, which would you be most likely to buy for your dream geek on Valentine's Day?",
                appName, JOptionPane.QUESTION_MESSAGE, icon, ValentinesGifts.values(), ValentinesGifts.CHESS_SET);
        if(valentinesGift==null) System.exit(0);
        return valentinesGift;
    }

    /**
     * method to get user to select their preferred romantic activity from a dropdown list
     * @return user's selection of their favourite romantic activity (type = RomanticActivities enum)
     */
    default RomanticActivities getRomanticActivityInput(){
        RomanticActivities favouriteRomanticActivity = (RomanticActivities) JOptionPane.showInputDialog(null, "Select your favourite " +
                "romantic activity from the list.", appName, JOptionPane.QUESTION_MESSAGE, icon, RomanticActivities.values(), RomanticActivities.BINGE_TV_SHOWS);
        if(favouriteRomanticActivity==null) System.exit(0);
        return favouriteRomanticActivity;
    }

    /*
    Methods specific to old school friend
     */

    /**
     * method to get user to input the school from which they graduated
     * @return the String value entered by the user
     */
    default String getHighSchoolInput(){
        String school = (String) JOptionPane.showInputDialog(null, "At what school did your old friend attend? ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if(school==null) System.exit(0);
        return school;
    }

    /**
     * method to get user to input the year in which they graduated
     * @return the String value entered by the user
     */
    default String getGraduationYearInput(){
        String graduationYear = (String) JOptionPane.showInputDialog(null, "During which year did your old friend graduate? ",
                appName, JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if(graduationYear==null) System.exit(0);
        return graduationYear;
    }
    //END EDIT 2
}
