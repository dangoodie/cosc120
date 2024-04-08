/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import java.util.Set;

public class MoreThanAFriend extends Friend{
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
        super(minAge, maxAge, gender, starSign, religion, favouriteGames,favouriteTVShows);
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
    public MoreThanAFriend(Gender gender, StarSign starSign, Religion religion, Set<String> favouriteGames,
                           Set<String> favouriteTVShows, RomanticActivities mostRomanticActivity, ValentinesGifts favouriteValentinesGift) {
        super(gender, starSign, religion, favouriteGames,favouriteTVShows);
        this.mostRomanticActivity=mostRomanticActivity;
        this.favouriteValentinesGift=favouriteValentinesGift;
    }

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
        return super.getDescription() + ". Their dream valentine's gift would be "+this.getFavouriteValentinesGift().toString()+
                ", and their favourite romantic activity is "+this.getMostRomanticActivity();
    }

    @Override
    public boolean matches(DreamGeek realGeek) {
        if(realGeek instanceof MoreThanAFriend geekDate) {
            if (!super.matches(realGeek)) return false;
            if (!this.getMostRomanticActivity().equals(geekDate.getMostRomanticActivity())) return false;
            return this.getFavouriteValentinesGift().equals(geekDate.getFavouriteValentinesGift());
        }
        return false;
    }
}
