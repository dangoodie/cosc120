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
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(getMicrochipNumber()).append(") is a ").append(age).append(" year old ").append(genericFeatures.getSex()).append(" ");
        if (getGenericFeatures().getPurebred().equals(Purebred.YES)) {
            sb.append("purebred ");
        }
        sb.append(getGenericFeatures().getBreed()).append(".\n");
        sb.append(getGenericFeatures().getDreamPetDescription());
        sb.append("> Adoption fee: $").append(adoptionFee).append(".\n\n");
        return sb.toString();
    }
}
