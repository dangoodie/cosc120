public class Person {
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String fName;
    private final String lName;

    public Person(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        String[] names = name.split(" ");
        this.fName = names[0];
        this.lName = names[names.length-1];
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
