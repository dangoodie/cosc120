/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class DreamGeek {

    private final Gender gender;
    private final StarSign starSign;
    private int minAge;
    private int maxAge;

    /**
     * @param minAge the lowest age a user desires
     * @param maxAge the highest age a user desires
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     */
    public DreamGeek(int minAge, int maxAge, Gender gender, StarSign starSign){
        this.minAge=minAge;
        this.maxAge=maxAge;
        this.gender=gender;
        this.starSign=starSign;
    }

    /**
     * @param gender an enum value representing the Geek's gender (male, female or other)
     * @param starSign an enum representing the geek's star sign
     */
    public DreamGeek(Gender gender, StarSign starSign){
        this.gender=gender;
        this.starSign=starSign;
    }

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

    /**
     * @return a String description of the DreamGeek - religion, star sign, gender and games
     */
    public String getDescription(){
        return this.getStarSign()+" "+ this.getGender();
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
        return true;
    }
}
