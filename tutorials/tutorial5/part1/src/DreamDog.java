public class DreamDog {
    private int minAge;
    private int maxAge;
    private String breed;
    private Sex sex;
    private DeSexed deSexed;

    public DreamDog(int minAge, int maxAge, String breed, Sex sex, DeSexed deSexed) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.breed = breed;
        this.sex = sex;
        this.deSexed = deSexed;
    }

    // getters
    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getBreed() {
        return breed;
    }

    public Sex getSex() {
        return sex;
    }

    public DeSexed isDeSexed() {
        return deSexed;
    }
}
