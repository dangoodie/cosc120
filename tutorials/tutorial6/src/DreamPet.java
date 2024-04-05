public class DreamPet {
private final int minAge;
    private final int maxAge;
    private final String breed;
    private final Sex sex;
    private DeSexed deSexed;
    private final Purebred purebred;

    public DreamPet(int minAge, int maxAge, String breed,Sex sex, DeSexed deSexed, Purebred purebred) {
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

    // setters
    public void setDeSexed(DeSexed deSexed) {
        this.deSexed = deSexed;
    }

    // methods

    public String getDreamPetDescription() {
        return sex.toString() + " " + breed + ".\n" +
                "> Purebred: " + purebred.toString() + ".\n" +
                "> De-sexed: " + deSexed.toString() + ".\n";
    }

    public boolean compareDreamPets(DreamPet otherDreamPet) {
        // if the user doesn't care about purebred
        if (otherDreamPet.getPurebred() == Purebred.NA) {
            return this.sex == otherDreamPet.getSex() &&
                    this.deSexed == otherDreamPet.isDeSexed() &&
                    this.minAge <= otherDreamPet.getMinAge() &&
                    this.maxAge >= otherDreamPet.getMaxAge() &&
                    this.breed.equals(otherDreamPet.getBreed());
        } else {
            return this.breed.equals(otherDreamPet.getBreed()) &&
                    this.sex == otherDreamPet.getSex() &&
                    this.deSexed == otherDreamPet.isDeSexed() &&
                    this.purebred == otherDreamPet.getPurebred() &&
                    this.minAge <= otherDreamPet.getMinAge() &&
                    this.maxAge >= otherDreamPet.getMaxAge();
        }
    }
}
