/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class SeekAGeekV2 {
    /**
     * this program demonstrates the problems associated with making class fields publicly accessible
     * demonstrates how mutable class fields can be unintentionally corrrupted or changed if they are publicly accessible
     * demonstrates how sensitive object information can be accessed and changed from anywhere in the program if class fields are public
     * @param args none required
     */
    public static void main(String[] args){

        //create two geek objects
        GeekV2 geek1 = new GeekV2();
        GeekV2 geek2 = new GeekV2();

        //let's add in some favourite tv shows for geek1
        System.out.println("Geek 1......");
        geek1.setFavouriteTVShows();

        //Now let's add in some favourite tv shows for geek2
        System.out.println("Geek 2......");
        geek2.setFavouriteTVShows();

        //visualise each geeks' tv shows
        System.out.println("geek 1's tv shows: "+geek1.favoriteTVShows);
        System.out.println("geek 2's tv shows: "+geek2.favoriteTVShows);

        //find out if the geeks' favorite tv shows are the same or different
        //retainAll will return true if they're different, false if they are not
        System.out.println("\nAre the geek's favorite tv shows different? "+geek1.favoriteTVShows.retainAll(geek2.favoriteTVShows));

        //print geek1's favourite tv shows...
        System.out.println("\ngeek 1's tv shows after access: "+geek1.favoriteTVShows);
        /*
        calling retainAll on geek1's tv shows has removed those that they don't share with geek2.
        This unintentional usage has corrupted geek1's favoriteTVShows field
        This is why geek1's favouriteTVShows should be private, and only able to be changed via a set method
         */

        /*now let's look at another example of unintended usage...
        geek1's private data is accessible from anywhere in the program, meaning anyone can view it and change it...
        it should only be able to be viewed and changed after the geek's identity has been authenticated...
        in a big program with multiple users/developers this can result in problems such as privacy breaches
         */
        System.out.println("\nName: "+geek1.name + "\nUsername: "+geek1.userName+ "Password: "+geek1.password);
        geek1.password = "gotcha101"; //change the password!!
        System.out.println("Password changed to: "+geek1.password);
    }

}
