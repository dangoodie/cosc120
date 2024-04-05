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
    private final Double adoptionFee;

    /**
     * constructor to create a Dog object
     *
     * @param name            the dog's name
     * @param microchipNumber the dog's microchip number - unique 9-digit number
     * @param age             the dog's age in years
     * @param adoptionFee     the dog's adoption fee
     * @param dreamDog        the dreamDog object
     */
    public Dog(String name, long microchipNumber, int age,  Double adoptionFee, DreamDog dreamDog) {
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.age = age;
        this.adoptionFee = adoptionFee;
        this.dreamDog = dreamDog;
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

    /**
     * @return the dog's adoption fee
     */
    public Double getAdoptionFee() {
        return adoptionFee;
    }

    //methods
    /**
     * @return a string describing the dog
     */
    public String getDogDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(getMicrochipNumber()).append(") is a ").append(age).append(" year old ").append(dreamDog.getDreamDogDescription());
        sb.append("> Adoption fee: $").append(adoptionFee).append(".\n\n");
        return sb.toString();
    }
}