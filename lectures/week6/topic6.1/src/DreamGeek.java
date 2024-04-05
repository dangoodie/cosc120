/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class DreamGeek {
    //fields that characterise a generic 'dream geek'
    private final Gender gender;
    private final StarSign starSign;
    private final int minAge;
    private final int maxAge;
    //EDIT: remove fields - put them in Friend
//    private final Religion religion;
//    private Set<String> favouriteGames = new HashSet<>();
//    private Set<String> favouriteTVShows = new HashSet<>();

    /**
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     */
    //EDIT: remove fields from constructor accordingly
    public DreamGeek(int minAge, int maxAge, Gender gender, StarSign starSign){
                     //Religion religion, Set<String> favouriteGames, Set<String> favouriteTVShows){
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.gender=gender;
        this.starSign=starSign;
//        this.religion = religion;
//        if(favouriteGames!=null) this.favouriteGames=new HashSet<>(favouriteGames);
//        if(favouriteTVShows!=null) this.favouriteTVShows=new HashSet<>(favouriteTVShows);
    }

    //getters
    /**
     * used to get min age of a user's dream geek
     * @return the min age
     */
    public int getMinAge() {
        return minAge;
    }
    /**
     * used to get max age of a user's dream geek
     * @return the max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * gender: male, female or other
     * @return the Geek's gender
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     * one of 12 western astrological star signs
     * @return the Geek's star sign
     */
    public StarSign getStarSign(){
        return this.starSign;
    }

    //EDIT: remove religion, games and tv show getters to Friend
//    /**
//     * @return an enum constant representing the dream geek's religion
//     */
//    public Religion getReligion() {
//        return religion;
//    }
//
//    /**
//     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
//     * @return a Set (HashSet) of all this geek's favourite computer games
//     */
//    public Set<String> getFavouriteGames(){
//        return new HashSet<>(favouriteGames);
//    }
//
//    /**
//     * contains the String names/titles of the Geek's preferred tv shows (duplicates not allowed)
//     * @return a Set (HashSet) of all this geek's favourite tv shows
//     */
//    public Set<String> getFavouriteTVShows(){
//        return new HashSet<>(favouriteTVShows);
//    }

    //other methods
    /**
     * @return a String description of the DreamGeek - religion, star sign, gender and games
     */
    public String getDescription(){
        //EDIT: reduce description to super class fields only
        return //this.getReligion()+", "+ //remove Friend references -> go to Friend for Friend specific getDescription method
                this.getStarSign()+" "+ this.getGender();//+". \nFavourite games: "+this.getFavouriteGames().toString()+
                //"\nFavourite tv shows: "+this.getFavouriteTVShows();
    }

    /**
     * compares a DreamGeek against a real Geek's DreamGeek features
     * @param realGeek a Geek object representing a real, registered user
     * @return true if the real Geek's 'dream' features match this DreamGeek's features
     */
    public boolean matches(DreamGeek realGeek) {
        if (!this.getGender().equals(realGeek.getGender())) return false;
        if (!this.getStarSign().equals(StarSign.NA)){
            return this.getStarSign().equals(realGeek.getStarSign());
        }
        //EDIT: these Friend specific comparisons are now in the Friend matches method
//        if (!this.getReligion().equals(Religion.NA)){
//            if (!this.getReligion().equals(realGeek.getReligion())) return false;
//        }
//        Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
//        if (this.getFavouriteGames().size() > 0) {
//            gamesInCommon.retainAll(realGeek.getFavouriteGames());
//            if(gamesInCommon.size()==0) return false;
//        }
//        Set<String> tvShowsInCommon = new HashSet<>(this.getFavouriteTVShows());
//        if (this.getFavouriteTVShows().size()>0){
//            tvShowsInCommon.retainAll(realGeek.getFavouriteTVShows());
//            return tvShowsInCommon.size() != 0;
//        }
        return true;
    }
}
