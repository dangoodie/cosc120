/**
 * This is a Dog class
 */

public class Dog {
    private final String name;
    private final int microchipNumber;
    private final String breed;
    private final Gender gender;
    private Desexed isDesexed;
    private final int age;
    private int minAge;
    private int maxAge;

    /**
     * Constructor for the Dog class
     * @param name a String representing the dog's name
     * @param microchipNumber an int representing the dog's microchip number
     * @param breed a String representing the dog's breed
     * @param gender a String representing the dog's sex
     * @param isDesexed a boolean representing whether the dog is desexed
     * @param age an int representing the dog's age
     */

    public Dog(String name, int microchipNumber, String breed, Gender gender, Desexed isDesexed, int age) {
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.breed = breed;
        this.gender = gender;
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
    public Gender getGender() {
        return gender;
    }

    /**
     * Method to provide access to the dog's desexed status
     * @return a boolean representing whether the dog is desexed
     */
    public Desexed getDesexedStatus() {
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
    public void setDesexedStatus(Desexed isDesexed) {
        this.isDesexed = isDesexed;
    }

    /**
     * Method to compare this Dog's breed to another Dog's breed
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs are the same breed
     */
    public boolean isSameBreed(Dog otherDog) {
        return this.breed.equalsIgnoreCase(otherDog.getBreed());
    }

    /**
     * Method to compare this Dog's sex to another Dog's sex
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs are the same breed
     */
    public boolean isSameSex(Dog otherDog) {
        return this.gender.toString().equalsIgnoreCase(otherDog.getGender().toString());
    }

    /**
     * Method to compare this Dog's age to another Dog's age
     * @param otherDog a Dog object representing the other dog
     * @return a boolean representing whether the dogs are the same age
     */
    public boolean isSameAge(Dog otherDog) {
        return this.age == otherDog.getAge();
    }

    /**
     * Method to get the minimum age of a user's dream dog
     * @return the minimum age
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Method to get the maximum age of a user's dream dog
     * @return the maximum age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Method to set the minimum age of a user's dream dog
     * @param minAge an int representing the minimum age
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * Method to set the maximum age of a user's dream dog
     * @param maxAge an int representing the maximum age
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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
