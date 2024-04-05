/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

//import java.util.HashSet; //no longer needed
import java.util.Set;

//EDIT 1: public class MoreThanAFriend extends DreamGeek {
public class MoreThanAFriend extends Friend{

    //EDIT 2: delete all the redundant code
//    private final Religion religion;
//    private final Set<String> favouriteGames;
//    private final Set<String> favouriteTVShows;
    private final RomanticActivities mostRomanticActivity;
    private final ValentinesGifts favouriteValentinesGift;

    /**
     * @param minAge   the lowest age a user desires
     * @param maxAge   the highest age a user desires
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param religion         an enum constant representing the geek's religion
     * @param favouriteGames   a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     * @param mostRomanticActivity      an Enum representing the geek's favourite romantic activity (from a list)
     * @param favouriteValentinesGift   an Enum representing the geek's favourite Valentine's Day gift (from a list)
     */
    public MoreThanAFriend(int minAge, int maxAge, Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames,
                           Set<String> favouriteTVShows, RomanticActivities mostRomanticActivity, ValentinesGifts favouriteValentinesGift) {
        //EDIT 2: pass the relevant arguments into super - to initialise the Friend superclass fields
        super(minAge, maxAge, gender, starSign, religion, favouriteGames,favouriteTVShows);
        //EDIT 2: delete all the redundant code
//        this.religion=religion;
//        this.favouriteGames=new HashSet<>(favouriteGames);
//        this.favouriteTVShows=new HashSet<>(favouriteTVShows);
        this.mostRomanticActivity=mostRomanticActivity;
        this.favouriteValentinesGift=favouriteValentinesGift;
    }

    /**
     * @param gender   an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     * @param religion         an enum constant representing the geek's religion
     * @param favouriteGames   a Set of Strings, representing the geek's favourite computer games
     * @param favouriteTVShows a Set of Strings, representing the geek's favourite tv shows
     * @param mostRomanticActivity      an Enum representing the geek's favourite romantic activity (from a list)
     * @param favouriteValentinesGift   an Enum representing the geek's favourite Valentine's Day gift (from a list)
     */
    //EDIT 12: create a constructor for objects created to represent real geeks - no age range
    public MoreThanAFriend(Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames,
                           Set<String> favouriteTVShows, RomanticActivities mostRomanticActivity, ValentinesGifts favouriteValentinesGift) {
        super(gender, starSign, religion, favouriteGames,favouriteTVShows);
        this.mostRomanticActivity=mostRomanticActivity;
        this.favouriteValentinesGift=favouriteValentinesGift;
    }

//EDIT 2: delete all the redundant code
//    /**
//     * @return an enum constant representing the dream geek's religion
//     */
//    public Religion getReligion() {
//        return religion;
//    }
//    /**
//     * contains the String names/titles of the Geek's preferred games (duplicates not allowed)
//     * @return a Set (HashSet) of all this geek's favourite computer games
//     */
//    public Set<String> getFavouriteGames(){
//        return new HashSet<>(favouriteGames);
//    }
//    /**
//     * contains the String names/titles of the Geek's preferred tv shows (duplicates not allowed)
//     * @return a Set (HashSet) of all this geek's favourite tv shows
//     */
//    public Set<String> getFavouriteTVShows(){
//        return new HashSet<>(favouriteTVShows);
//    }

    /**
     * @return an enum constant representing the geek's preferred romantic activity
     */
    public RomanticActivities getMostRomanticActivity() {
        return mostRomanticActivity;
    }
    /**
     * @return an enum constant representing the geek's preferred valentine's gift
     */
    public ValentinesGifts getFavouriteValentinesGift() {
        return favouriteValentinesGift;
    }

    @Override
    public String getDescription() {
        //EDIT 3: simplify the getDescription method
        //return this.getReligion()+", "+ super.getDescription() + " who loves the following games: "+this.getFavouriteGames().toString()+
        return super.getDescription() + ". Their dream valentine's gift would be "+this.getFavouriteValentinesGift().toString()+
                ", and their favourite romantic activity is "+this.getMostRomanticActivity();
    }

    @Override
    public boolean matches(DreamGeek realGeek) {
        if(realGeek instanceof MoreThanAFriend geekDate) {
            if (!super.matches(realGeek)) return false;
            if (!this.getMostRomanticActivity().equals(geekDate.getMostRomanticActivity())) return false;
            return this.getFavouriteValentinesGift().equals(geekDate.getFavouriteValentinesGift());
            //EDIT 3: simplify the matches method
//            if (!this.getReligion().equals(Religion.NA)) {
//                if (!this.getReligion().equals(geekDate.getReligion())) return false;
//            }
//            Set<String> gamesInCommon = new HashSet<>(this.getFavouriteGames());
//            if (this.getFavouriteGames().size() > 0) {
//                gamesInCommon.retainAll(geekDate.getFavouriteGames());
//                if (gamesInCommon.size() == 0) return false;
//            }
//            Set<String> tvShowsInCommon = new HashSet<>(this.getFavouriteTVShows());
//            if (this.getFavouriteTVShows().size() > 0) {
//                tvShowsInCommon.retainAll(geekDate.getFavouriteTVShows());
//                return tvShowsInCommon.size() != 0;
//            }
        }
        return false;
    }
}
