/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class SeekAGeekV3 {
    /**
     * demonstrates the concept of encapsulation in action
     * demonstrates usage of getters and setters to protect sensitive or corruptible fields
     * demonstrates effective data protection when using mutable objects
     * @param args none required
     */
    public static void main(String[] args){

        //create two geek objects
        GeekV3 geek1 = new GeekV3();
        GeekV3 geek2 = new GeekV3();

        //let's add in some favourite tv shows for geek1
        System.out.println("Geek 1......");
        geek1.addFavouriteTVShows();

        //Now let's add in some favourite tv shows for geek2
        System.out.println("Geek 2......");
        geek2.addFavouriteTVShows();

        //visualise each geeks' tv shows
        System.out.println("geek 1's tv shows: "+geek1.getFavouriteTVShows());
        System.out.println("geek 2's tv shows: "+geek2.getFavouriteTVShows());

        //like before, find out if the geeks' favorite tv shows are the same or different
        //retainAll will return true if they're different, false if they are not
        System.out.println("\nAre the geek's favorite tv shows different? "+
                geek1.getFavouriteTVShows().retainAll(geek2.getFavouriteTVShows()));

        /*
        Print geek1's favourite tv shows... notice the values remain unchanged.
        This is because the getFavouriteTVShows method returns a copy of the favorite tv shows
        This protected the field from modification when retainAll was used
         */
        System.out.println("\ngeek 1's tv shows after access: "+geek1.getFavouriteTVShows());

        //note: the password and username fields are protected. They are not accessible from this class.
        //although the username and password fields are used by signIn, the implementation of signIn is hidden
        geek1.signIn("joey_gani","jo#42#34");

        //the password field can be changed, however the implementation of the change is hidden, and the field
        //is protected from unauthorised access (as signIn is required to change the password)
        geek1.changePassword();
    }
}
