/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//EDIT 8: create the Premium class, implementing our new interface
public class Premium implements SubscriptionFeatures{

    //EDIT 9: create a field of type Map<Criteria,Object> to contain the user’s selections.
    private final Map<Criteria, Object> criteria = new HashMap<>();
    private TypeOfDreamGeek type;//EDIT 30

    //EDIT 14: Implement this method using the 4 methods coded in EDITS 10-13,
    //as well as the SubscriptionFeatures default methods.
    /**
     * a method to get the user to input data based on their level of subscription
     * the data should populate a Map</Criteria,Object> and select an age range to instantiate
     * a DreamGeek object
     * @return a DreamGeek object representing the user's conceptual 'dream' geek
     */
    @Override
    public DreamGeek getUserInput() {
        type = getRelationshipType(); //EDIT 30
        criteria.put(Criteria.TYPE_OF_RELATIONSHIP,type);
        int[] ageRange = getUserInputMinMaxAge();
        if (type.equals(TypeOfDreamGeek.FRIEND)) getFriendInput();
        else if(type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) getMoreThanAFriendInput();
        else if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)) getStudyBuddyInput();
        else if (type.equals(TypeOfDreamGeek.OLD_SCHOOL_FRIEND)) getOldSchoolFriendInput();
        return new DreamGeek(ageRange[0],ageRange[1],criteria);
    }

    //EDIT 15: Use the code from SeekAGeek processSearchResults to implement the displayResults method.
    /**
     * a method to show the user details about the Geek/s that meet their criteria
     * @param potentialMatches a List of Geeks that meet the user's criteria
     * @return a String value representing the user's selection of which Geek they prefer (or None)
     */
    @Override
    public String displayResults(List<Geek> potentialMatches) {
        String[] options = new String[potentialMatches.size()];
        StringBuilder infoToShow= new StringBuilder(new StringBuilder("You have the following potential matches: \n"));
        for(int i=0;i<potentialMatches.size();i++){
            //infoToShow.append(potentialMatches.get(i).getGeekDescription());
            //EDIT 30: check the relationship type sought by the user. Based on this, pass the
            //appropriate Set field created in EDIT 29 into the getGeekDescription method.
            if (type.equals(TypeOfDreamGeek.FRIEND)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.friendFeatures));
            if (type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.moreThanAFriendFeatures));
            if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.studyBuddyFeatures));
            if (type.equals(TypeOfDreamGeek.OLD_SCHOOL_FRIEND)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.oldSchoolFriendFeatures));
            options[i]=potentialMatches.get(i).getName();
        }
        return (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",
                appName, JOptionPane.QUESTION_MESSAGE,icon,options,options[0]);
    }

    //EDIT 10: use the default methods in SubscriptionFeatures to collect all user input for friend filtering
    /**
     * method to get user to input criteria to filter possible friend matches
     */
    public void getFriendInput(){
        Religion religion = getReligionInput();
        if (!religion.equals(Religion.NA)) criteria.put(Criteria.RELIGION,religion);
        Gender gender = getGenderInput();
        if (!gender.equals(Gender.NA)) criteria.put(Criteria.GENDER, gender);
        StarSign starSign = getStarSignInput();
        if (!starSign.equals(StarSign.NA)) criteria.put(Criteria.STAR_SIGN, starSign);
        Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
        if(!favouriteGames.isEmpty()) criteria.put(Criteria.FAVOURITE_COMPUTER_GAMES,favouriteGames);
        Set<String> favouriteTVShows = getUserFavouriteCollection("binging tv shows","tv show");
        if(!favouriteTVShows.isEmpty()) criteria.put(Criteria.FAVOURITE_TV_SHOWS,favouriteTVShows);
        Set<String> hobbies = getUserFavouriteCollection("hobbies","hobby");
        if(!hobbies.isEmpty()) criteria.put(Criteria.HOBBIES,hobbies);
    }

    //EDIT 11
    /**
     * method to get study buddy input
     */
    public void getStudyBuddyInput() {
        //repetitious code - how could we minimise this?
        Religion religion = getReligionInput();
        if (!religion.equals(Religion.NA)) criteria.put(Criteria.RELIGION,religion);
        Gender gender = getGenderInput();
        if (!gender.equals(Gender.NA)) criteria.put(Criteria.GENDER, gender);
        StarSign starSign = getStarSignInput();
        if (!starSign.equals(StarSign.NA)) criteria.put(Criteria.STAR_SIGN, starSign);
        //end repetitious code
        String[] options = {"Institution and course", "Subject area"};
        String selectedOption = (String) JOptionPane.showInputDialog(null, "How would you like to filter potential study buddies?", SubscriptionFeatures.appName, JOptionPane.QUESTION_MESSAGE, null, options, "");
        if (selectedOption.equals(options[0])) {
            criteria.put(Criteria.INSTITUTION, getInstitutionInput());
            criteria.put(Criteria.COURSE, getCourseInput());
        } else {
            criteria.put(Criteria.SUBJECT_AREA, getSubjectAreaInput());
        }
    }

    //EDIT 12
    /**
     * method to get user to input criteria to filter more than a friend input - premium subscribers only
     */
    public void getMoreThanAFriendInput() {
        getFriendInput(); //all 'more than a friend' geeks should also be 'friend' compatible
        criteria.put(Criteria.VALENTINES_GIFT, getValentinesGiftInput());
        criteria.put(Criteria.ROMANTIC_ACTIVITY, getRomanticActivityInput());
    }

    //EDIT 13
    /**
     * method to get user to input criteria to filter old school friends - premium subscribers only
     */
    public void getOldSchoolFriendInput() {
        criteria.put(Criteria.SCHOOL, getHighSchoolInput());
        criteria.put(Criteria.GRADUATION_YEAR, getGraduationYearInput());
    }
}
