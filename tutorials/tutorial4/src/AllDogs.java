import java.util.ArrayList;

public class AllDogs {
    ArrayList<Dog> dogs = new ArrayList<Dog>();

    public void addDog(Dog dog) {
        dogs.add(dog);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
    }

    public Dog searchDog(Dog dog) {
        for (Dog d : dogs) {
            if (d.isSameBreed(dog) && d.isSameSex(dog) && d.isSameDesexedStatus(dog) && d.isSameAge(dog)) {
                return d;
            }
        }
        return null;
    }
}
