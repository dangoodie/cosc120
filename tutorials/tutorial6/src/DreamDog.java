import java.util.Objects;

public class DreamDog {
    private int minAge;
    private int maxAge;
    private final String breed;
    private final Sex sex;
    private DeSexed deSexed;
    private final Purebred purebred;

    public DreamDog(int minAge, int maxAge, String breed, Sex sex, DeSexed deSexed, Purebred purebred) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.breed = breed;
        this.sex = sex;
        this.deSexed = deSexed;
        this.purebred = purebred;
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

    public Purebred getPurebred() {
        return purebred;
    }

    // methods
    /**
     * @return a string describing the dream dog
     */
    public String getDreamDogDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(sex.toString()).append(" ").append(breed).append(".\n");
        sb.append("> Purebred: ").append(purebred.toString()).append(".\n");
        sb.append("> De-sexed: ").append(deSexed.toString()).append(".\n");
        return sb.toString();
    }

    public boolean compareDreamDogs(DreamDog otherDreamDog) {
        // if the user doesn't care about purebred
        if (otherDreamDog.getPurebred() == Purebred.NA){
            return this.sex == otherDreamDog.getSex() &&
                    this.deSexed == otherDreamDog.isDeSexed() &&
                    this.breed.equals(otherDreamDog.getBreed());
        }

        // if the user does care about purebred
        else{
            return this.breed.equals(otherDreamDog.getBreed()) &&
                    this.sex == otherDreamDog.getSex() &&
                    this.deSexed == otherDreamDog.isDeSexed() &&
                    this.purebred == otherDreamDog.getPurebred();
        }
    }
}