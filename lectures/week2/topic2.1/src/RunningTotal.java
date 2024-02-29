/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

public class RunningTotal {

    /**
     * uses a for loop running total to print the first 10 triangular numbers
     * @param args none required
     */
    public static void main(String[] args){

        int sum=0;
        //The variable i is the control variable. It is initialised to 1
        System.out.println("running total to generate triangular numbers");
        for(int i = 1; i <= 10; i++){ //++ increments the control variable by 1
            sum+=i;
            System.out.println("adding "+i+" ....new sum: "+sum);
        }
    }
}
