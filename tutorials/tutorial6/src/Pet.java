public record Pet(String name, long microchipNumber, int age, Double adoptionFee, DreamPet genericFeatures) {

    public Pet {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (microchipNumber < 0) {
            throw new IllegalArgumentException("Microchip number cannot be negative");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (adoptionFee < 0) {
            throw new IllegalArgumentException("Adoption fee cannot be negative");
        }
    }

    public String getName() {
        return name;
    }

    public long getMicrochipNumber() {
        return microchipNumber;
    }

    public int getAge() {
        return age;
    }

    public Double getAdoptionFee() {
        return adoptionFee;
    }

    public DreamPet getGenericFeatures() {
        return genericFeatures;
    }

    public String getPetDescription() {
        return "Name: " + name + "\n" +
                "Microchip Number: " + microchipNumber + "\n" +
                "Age: " + age + "\n" +
                "Adoption Fee: " + adoptionFee + "\n";
    }

    public boolean comparePets(Pet otherPet) {
        return name.equals(otherPet.getName()) &&
                microchipNumber == otherPet.getMicrochipNumber() &&
                age == otherPet.getAge() &&
                adoptionFee.equals(otherPet.getAdoptionFee()) &&
                genericFeatures.compareDreamPets(otherPet.genericFeatures); // <- this is better I think
    }
}
