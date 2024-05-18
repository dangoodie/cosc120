/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;

public class UserInputAgeRange {

    private int minAge=-1;
    private int maxAge=-1;
    private final int minimumLegalAge=18;

    private JTextField min;
    private JTextField max;

    /**
     * method that allows user to choose an age range. Includes event handling.
     * sets the class fields to user-chosen values.
     * experiment by delegating the actions performed when the action listener is trigger
     * to 1 or 2 methods
     * @return a JPanel containing the text fields used to enter age range
     */
    public JPanel getUserInputMinMaxAge(){
        JLabel minLabel = new JLabel("Min. age");
        JLabel maxLabel = new JLabel("Max. age");
        min = new JTextField(3);
        max = new JTextField(3);
        JLabel feedback = new JLabel("Must be 18 or above");

        //this is how you change the font and size of text
        feedback.setFont(new Font("", Font. ITALIC, 12));

        //complex action listener
        min.addActionListener(e -> {
            try{
                //attempt to parse the user's entry as an integer
                this.minAge = Integer.parseInt(min.getText());
                if(minAge < minimumLegalAge) {
                    //if the entry is below 18, update the feedback
                    feedback.setText("Min age must be greater than "+minimumLegalAge);
                    //set the feedback to red 'incorrect'
                    feedback.setForeground(Color.RED);
                    //select the user's entry, so when they type, it replaces the previous entry
                    min.selectAll();
                    //keep their cursor in min
                    min.requestFocus();
                }else {
                    //no need to give feedback for correct entry
                    feedback.setText("");
                    //once the user has correctly entered a min value, send their cursor to the max field
                    max.requestFocus();
                }
            }catch (NumberFormatException n){
                //if they enter anything other than an integer, update the feedback
                feedback.setText("Please enter a valid integer");
                feedback.setForeground(Color.RED);
                min.selectAll();
                min.requestFocus();
            }
        });
        max.addActionListener(e -> {
            try{
                this.maxAge = Integer.parseInt(max.getText());
                if(maxAge < minAge) {
                    //if the entry is less than in age, update feedback
                    feedback.setText("Max age must be greater than min age");
                    feedback.setForeground(Color.RED);
                    max.selectAll();
                    max.requestFocus();
                }else {
                    feedback.setText("");
                }
            }catch (NumberFormatException n){
                feedback.setText("Please enter a valid integer");
                feedback.setForeground(Color.RED);
                max.selectAll();
                max.requestFocus();
            }
        });

        //used to contain the 2 input fields and their labels
        JPanel inputPanel = new JPanel();
        inputPanel.add(minLabel);
        inputPanel.add(min);
        inputPanel.add(maxLabel);
        inputPanel.add(max);

        //used to contain the inputPanel, and feedback label (as well as padding)
        JPanel finalPanel = new JPanel();
        //EDIT 1: add border with title around the components
        finalPanel.setBorder(BorderFactory.createTitledBorder("Age range"));
        finalPanel.setLayout(new BorderLayout());
        finalPanel.add(inputPanel,BorderLayout.NORTH);

        //EDIT 2: add padding between the text fields and feedback label
        finalPanel.add(Box.createRigidArea(new Dimension(0,10)));
        finalPanel.add(feedback,BorderLayout.SOUTH);

        return finalPanel;
    }

    //getters - used to access values user has entered
    public int getMinAge() {
        return Integer.parseInt(min.getText());
    }
    public int getMaxAge() {
        return Integer.parseInt(max.getText());
    }
}
