/**
 * @author Daniel Gooden (dgooden@myune.edu.au | dan.gooden.dev@gmail.com)
 * created for COSC120 Assignment 1
 */

/**
 * A record that represents a geek object.
 * @param name
 * @param phoneNumber
 */
public record Geek(String name, String phoneNumber) {

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
}
