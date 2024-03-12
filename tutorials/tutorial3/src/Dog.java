/**
 * This is a Dog class
 */

public class Dog {
    private final String name;
    private final int microchipNumber;
    private final String breed;
    private final String sex;
    private boolean isDesexed;
    private int age;

    /**
     * Constructor for the Dog class
     * @param name a String representing the dog's name
     * @param microchipNumber an int representing the dog's microchip number
     * @param breed a String representing the dog's breed
     * @param sex a String representing the dog's sex
     * @param isDesexed a boolean representing whether the dog is desexed
     * @param age an int representing the dog's age
     */

    public Dog(String name, int microchipNumber, String breed, String sex, boolean isDesexed, int age) {
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.breed = breed;
        this.sex = sex;
        this.isDesexed = isDesexed;
        this.age = age;
    }

    /**
     * Method to provide access to the dog's name
     * @return a String representing the dog's name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to provide access to the dog's microchip number
     * @return an int representing the dog's microchip number
     */
    public int getMicrochipNumber() {
        return microchipNumber;
    }

    /**
     * Method to provide access to the dog's breed
     * @return a String representing the dog's breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Method to provide access to the dog's sex
     * @return a String representing the dog's sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Method to provide access to the dog's desexed status
     * @return a boolean representing whether the dog is desexed
     */
    public boolean getDesexedStatus() {
        return isDesexed;
    }

    /**
     * Method to provide access to the dog's age
     * @return an int representing the dog's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Method to set the dog's desexed status
     * @param isDesexed a boolean representing whether the dog is desexed
     */
    public void setDesexedStatus(boolean isDesexed) {
        this.isDesexed = isDesexed;
    }

    /**
     * Method to compare this Dog's breed to another Dog's breed
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs are the same breed
     */
    public boolean isSameBreed(Dog otherDog) {
        return this.breed.equals(otherDog.getBreed());
    }

    /**
     * Method to compare this Dog's sex to another Dog's sex
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs are the same breed
     */
    public boolean isSameSex(Dog otherDog) {
        return this.sex.equals(otherDog.getSex());
    }

    /**
     * Method to test whether this Dog's age falls within a specified age range
     * @param min an int representing the minimum age
     * @param max an int representing the maximum age
     * @return a boolean representing whether the dog's age falls within the specified range
     */
    public boolean isAgeInRange(int min, int max) {
        return this.age >= min && this.age <= max;
    }

    /**
     * Method to compare whether this Dog's desexed status to another Dog's desexed status
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs have the same desexed status
     */
    public boolean isSameDesexedStatus(Dog otherDog) {
        return this.isDesexed == otherDog.getDesexedStatus();
    }
}
