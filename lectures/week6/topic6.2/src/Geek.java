/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

/*
 * Remains unchanged
 */
import java.util.HashSet;
import java.util.Set;

/**
 * class from which Geek objects can be created
 */
public class Geek {
    private final String username;
    private final String name;
    private final int age;
    private final String statement;
    private Set<String> blockedGeeks = new HashSet<>();
    private final String phoneNumber;
    private final String emailAddress;
    private final DreamGeek genericFeatures;

    /**
     * Constructor used to create a Geek object
     * @param username a unique String value used to identify an individual geek
     * @param name a String value representing the Geek's first, last and any middle names
     * @param age an int value representing the Geek's age
     * @param statement a String representing a statement about what makes this person a geek
     * @param blockedGeeks a Set of Strings representing the usernames of blocked Geeks
     * @param phoneNumber a String value representing the geek's 10 digit phone number
     * @param emailAddress a String representing the user's email address. Must contain @
     */
    public Geek(String username, String name, int age, String statement, Set<String> blockedGeeks,
                String phoneNumber, String emailAddress, DreamGeek dreamGeek){
        this.username=username;
        this.name=name;
        this.age=age;
        this.emailAddress = emailAddress;
        this.statement=statement;
        if(blockedGeeks!=null) this.blockedGeeks=new HashSet<>(blockedGeeks);
        this.phoneNumber=phoneNumber;
        this.genericFeatures=dreamGeek;
    }

    /**
     * username is unique to the Geek object
     * @return the Geek's username
     */
    public String getUsername(){
        return username;
    }

    /**
     * full name, including first name, surname and any middle names
     * @return the Geek's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * int representing the Geek's age in years
     * @return the Geek's age
     */
    public int getAge(){
        return this.age;
    }

    /**
     * @return the real geek's generic features inc. gender, star sign and favourite games
     */
    public DreamGeek getGenericFeatures(){
        return genericFeatures;
    }

    /**
     * @return the Geek's 10-digit phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @return the geek's email address (contains @)
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * A String of phrases/sentences set by the user when they sign up. May contain any available character/s.
     * @return the Geek's statement of why they consider themselves to be a geek
     */
    public String getStatement(){
        return this.statement;
    }

    /**
     * when a Geek blocks another Geek, the blocked Geek's username is added to this Set
     * @return a Set (HashSet) of all the usernames of geeks blocked by 'this' geek
     */
    public Set<String> getBlockedGeeks(){
        return new HashSet<>(blockedGeeks);
    }

    /**
     * includes phone and email address
     * @return a String description of this Geek's contact details
     */
    public String getContactDetailsDescription(){
        return this.getName() + "'s phone number is: 0" + this.getPhoneNumber()+
                ".\nTheir email address is: "+this.getEmailAddress();
    }

    /**
     * @return a String describing a matching geek to the user
     */
    public String getGeekDescription(){
        return this.getName()+" is a/an "+this.getAge()+" year old "+this.genericFeatures.getDescription()+
                ".\nAbout "+this.getName()+": "+this.getStatement()+"\n\n";
    }
}