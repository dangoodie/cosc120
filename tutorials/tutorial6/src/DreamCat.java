public class DreamCat extends DreamPet{
    private final Hair hair;

    public DreamCat(int minAge, int max, String breed, Sex sex, DeSexed deSexed, Purebred purebred, Hair hair) {
        super(minAge, max, breed, sex, deSexed, purebred);
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
        if (otherDreamPet instanceof DreamCat) {
            DreamCat otherDreamCat = (DreamCat) otherDreamPet;
            return super.compareDreamPets(otherDreamCat) &&
                    this.hair == otherDreamCat.getHair();
        }
        return false;
    }
}
