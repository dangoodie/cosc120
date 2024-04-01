/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class Dog {
    //fields
    private final String name;
    private final long microchipNumber;
    private final int age;

    private DreamDog dreamDog;

    /**
     * constructor to create a Dog object
     *
     * @param name            the dog's name
     * @param microchipNumber the dog's microchip number - unique 9-digit number
     * @param age             the dog's age in years
     * @param breed           the dog's breed
     * @param sex             the dog's sex (male or female)
     * @param deSexed         the dog's de-sexed status - true if de-sexed, false if not
     */
    public Dog(String name, long microchipNumber, int age, String breed, Sex sex, DeSexed deSexed) {
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.age = age;
        this.dreamDog = new DreamDog(0,0, breed, sex, deSexed);
    }

    //getters

    /**
     * @return the dog's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the dog's microchip number - unique 9-digit number
     */
    public long getMicrochipNumber() {
        return microchipNumber;
    }

    /**
     * @return the dog's age in years
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the dreamDog object
     */
    public DreamDog getDreamDog() {
        return dreamDog;
    }

}