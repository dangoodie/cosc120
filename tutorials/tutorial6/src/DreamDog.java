import java.util.Objects;

public class DreamDog extends DreamPet {
    private final Training training;
    private final int dailyExercise;

    public DreamDog(int minAge, int maxAge, String breed,Sex sex, DeSexed deSexed, Purebred purebred, Training training, int dailyExercise) {
        super(minAge, maxAge, breed, sex, deSexed, purebred);
        this.training = training;
        this.dailyExercise = dailyExercise;
    }

    // getters
    public Training getTraining() {
        return training;
    }

    public int getDailyExercise() {
        return dailyExercise;
    }

    // methods

    @Override
    public String getDreamPetDescription() {
        return super.getDreamPetDescription() +
                "> Training: " + training.toString() + ".\n" +
                "> Daily exercise: " + dailyExercise + " minutes.\n";
    }

    @Override
    public boolean compareDreamPets(DreamPet otherDreamPet) {
        if (otherDreamPet instanceof DreamDog) {
            DreamDog otherDreamDog = (DreamDog) otherDreamPet;
            return super.compareDreamPets(otherDreamDog) &&
                    this.training == otherDreamDog.getTraining() &&
                    this.dailyExercise == otherDreamDog.getDailyExercise();
        }
        return false;
    }
}