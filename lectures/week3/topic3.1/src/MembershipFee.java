/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class MembershipFee {

    /**
     * this program demonstrates the effect of passing by value
     * demonstrates the relationship between parameters and arguments when using primitive values
     * Re-enforces knowledge of formatting
     * @param args none required
     */
    public static void main(String[] args){
        //local variables
        float membershipFee = 59.99f;
        float discount = 0.25f;

        System.out.println("The old price is: "+membershipFee);
        /*
        this doesn't work.... the above variable values were simply copied into the method.
        the method changed the value of its parameter, not the value of the above local variable
         */
        discountedMembershipFeeV1(membershipFee, discount);
        System.out.println("DOESN'T WORK!! The new price is: "+membershipFee);

        /*
        this works - the method returned a new double value representing the discounted price
        the method changed the value of its parameter, then returned the new value, which has been
        assigned to the membershipFee variable - the original 59.99 was never changed - instead, the
        44.99 was assigned to the membershipFee variable.
         */
        System.out.print("WORKS!! The new price is: ");
        membershipFee = discountedMembershipFeeV2(membershipFee,discount);
        System.out.printf("%.2f",membershipFee);
    }
    /**
     * calculates the new discounted membership fee
     * useless method, as the calculation value is not returned
     * @param membershipFee the original membership fee
     * @param discount the decimal representation of the percentage discount
     */
    private static void discountedMembershipFeeV1(float membershipFee, float discount){
        membershipFee=(1-discount)*membershipFee;
    }

    /**
     * calculates the new discounted membership fee
     * @param membershipFee the original membership fee
     * @param discount the decimal representation of the percentage discount
     * @return the new discounted membership fee
     */
    private static float discountedMembershipFeeV2(float membershipFee, float discount){
        membershipFee=(1-discount)*membershipFee;
        return membershipFee;
    }

}
