public class DreamCat extends DreamPet{
    private final Hair hair;

    public DreamCat(int minAge, int maxAge, String breed, Sex sex, DeSexed deSexed, Purebred purebred, Hair hair) {
        super(minAge, maxAge, breed, sex, deSexed, purebred);
        this.hair = hair;
    }

    // getters
    public Hair getHair() {
        return hair;
    }

    //
    @Override
    public String getDreamPetDescription() {
        return super.getDreamPetDescription() +
                "> Hair: " + hair.toString() + ".\n";
    }

    @Override
    public boolean compareDreamPets(DreamPet otherDreamPet) {
        if (otherDreamPet instanceof DreamCat otherDreamCat) {
            return super.compareDreamPets(otherDreamCat) &&
                    this.hair == otherDreamCat.getHair();
        }
        return false;
    }
}
