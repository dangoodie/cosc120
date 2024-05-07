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

public class Basic implements SubscriptionFeatures{

    //EDIT 16: create a field of type Map<Criteria,Object> to contain the user’s selections, and an
    //int numberOfSearchResultsToShow to restrict the number of results the basic user can see.
    private final Map<Criteria, Object> criteria = new HashMap<>();
    private final int numberOfSearchResultsToShow;
    private TypeOfDreamGeek type; //EDIT 31

    //EDIT 16: Also create a constructor in Basic to initialise numberOfSearchResultsToShow.
    /**
     * Creates instance of Basic class, used to represent user on basic plan
     * @param numberOfSearchResultsToShow number of search results to display to user on basic plan
     */
    public Basic(int numberOfSearchResultsToShow){
        this.numberOfSearchResultsToShow=numberOfSearchResultsToShow;
    }

    //EDIT 20: use the 3 methods coded in EDITs 17-19, as well as the SubscriptionFeatures getRelationshipType
    //default method to implement the getUserInput method.
    //Note: OldSchoolFriend is only available to premium users.
    /**
     * a method to get the user to input data based on their level of subscription
     * the data should populate a Map</Criteria,Object> and select an age range to instantiate
     * a DreamGeek object
     * @return a DreamGeek object representing the user's conceptual 'dream' geek
     */
    @Override
    public DreamGeek getUserInput() {
        type = getRelationshipType();//EDIT 31
        criteria.put(Criteria.TYPE_OF_RELATIONSHIP,type);
        while (type.equals(TypeOfDreamGeek.OLD_SCHOOL_FRIEND)){
            JOptionPane.showMessageDialog(null,"This feature is limited to premium subscribers only! Subscribe today for only $"+SubscriptionFeatures.premiumSubscriptionFee,SubscriptionFeatures.appName,JOptionPane.INFORMATION_MESSAGE);
            type = getRelationshipType();
        }
        int[] ageRange = getUserInputMinMaxAge();
        if (type.equals(TypeOfDreamGeek.FRIEND)) getFriendInput();
        else if(type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) getMoreThanAFriendInput();
        else if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)) getStudyBuddyInput();

        return new DreamGeek(ageRange[0],ageRange[1],criteria);
    }

    //EDIT 21: Use the code from SeekAGeek to implement this method. Note: use the numberOfSearchResultsToShow
    //variable to limit how many results the user can see and let them know that to access the remainder of the
    //potential matches, they’ll have to subscribe!
    /**
     * a method to show the user details about the Geek/s that meet their criteria
     * @param potentialMatches a List of Geeks that meet the user's criteria
     * @return a String value representing the user's selection of which Geek they prefer (or None)
     */
    @Override
    public String displayResults(List<Geek> potentialMatches) {
        String[] options = new String[numberOfSearchResultsToShow];
        StringBuilder infoToShow= new StringBuilder("You have "+potentialMatches.size()+" potential matches: \n (Only "+numberOfSearchResultsToShow+" are shown. Subscribe to see all potential matches today!)\n");
        int limit = Math.min(numberOfSearchResultsToShow,potentialMatches.size());
        for (int i = 0; i < limit; i++) {
            //EDIT 31: check the relationship type sought by the user. Based on this, pass the
            //appropriate Set field created in EDIT 29 into the getGeekDescription method.
            if (type.equals(TypeOfDreamGeek.FRIEND)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.friendFeatures));
            if (type.equals(TypeOfDreamGeek.MORE_THAN_A_FRIEND)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.moreThanAFriendFeatures));
            if (type.equals(TypeOfDreamGeek.STUDY_BUDDY)) infoToShow.append(potentialMatches.get(i).getGeekDescription(SubscriptionFeatures.studyBuddyFeatures));
            //infoToShow.append(potentialMatches.get(i).getGeekDescription());
            options[i]=potentialMatches.get(i).getName();
        }
        return (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which (if any) geek you'd like to contact:",SubscriptionFeatures.appName,JOptionPane.QUESTION_MESSAGE,null,options,"");
    }

    //EDIT 17: Use the default methods in SubscriptionFeatures to get user input for friend, according to the Greek Geek’s instructions,
    //i.e., only allow input for gender and favourite games.
    /**
     * method to get user to input criteria to filter possible friend matches - religion and tv shows for premium subscribers only
     */
    public void getFriendInput(){
        Gender gender = getGenderInput();
        if (!gender.equals(Gender.NA)) criteria.put(Criteria.GENDER, gender);
        Set<String> favouriteGames = getUserFavouriteCollection("playing computer games","game");
        if(!favouriteGames.isEmpty()) criteria.put(Criteria.FAVOURITE_COMPUTER_GAMES,favouriteGames);
        JOptionPane.showMessageDialog(null,"Premium users can filter based on religion, favourite TV shows and hobbies too! " +
                "Subscribe today for only $"+SubscriptionFeatures.premiumSubscriptionFee,SubscriptionFeatures.appName,JOptionPane.INFORMATION_MESSAGE);

    }

    //EDIT 18: create a similar method to get study buddy input, remembering to advertise the institution
    //and course option as a premium feature, but allow them to filter based on subject area.
    /**
     * method to get study buddy input
     */
    public void getStudyBuddyInput() {
        Gender gender = getGenderInput();
        if (!gender.equals(Gender.NA)) criteria.put(Criteria.GENDER, gender);
        String[] options = {"Institution and course", "Subject area"};
        String selectedOption = (String) JOptionPane.showInputDialog(null, "How would you like to filter potential study buddies?", SubscriptionFeatures.appName, JOptionPane.QUESTION_MESSAGE, null, options, "");
        while (selectedOption.equals(options[0])) {
            JOptionPane.showMessageDialog(null,"This feature is limited to premium subscribers only! Subscribe today for only "+SubscriptionFeatures.premiumSubscriptionFee,SubscriptionFeatures.appName,JOptionPane.INFORMATION_MESSAGE);
            selectedOption = (String) JOptionPane.showInputDialog(null, "Please subscribe or choose another option!", SubscriptionFeatures.appName, JOptionPane.QUESTION_MESSAGE, null, options, "");

        }
        criteria.put(Criteria.SUBJECT_AREA, getSubjectAreaInput());
    }

    //EDIT 19: create a similar method to get more than a friend input, remembering to advertise the romantic activity as a premium feature, but allow the
    //basic user to filter based on Valentine’s Day gift.
    /**
     * method to get user to input criteria to filter more than a friend input
     */
    public void getMoreThanAFriendInput() {
        getFriendInput();
        criteria.put(Criteria.VALENTINES_GIFT, getValentinesGiftInput());
        JOptionPane.showMessageDialog(null,"Premium users can filter based on favourite romantic activity too! " +
                "Subscribe today for only $"+SubscriptionFeatures.premiumSubscriptionFee,SubscriptionFeatures.appName,JOptionPane.INFORMATION_MESSAGE);
    }

}
