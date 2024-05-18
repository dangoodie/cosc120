/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;

public class EnterDetails {

    public static void main(String[] args){

        //create a load main window JFrame
        JFrame mainWindow = new JFrame("Collection Geek Info");
        //when the user clicks 'close', the program will exit
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this positions the window in the centre of the screen
        //comment it out, and the window will appear in the top left corner of your screen
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setMinimumSize(new Dimension(400,200));
        mainWindow.setIconImage(new ImageIcon("./icon.png").getImage());
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainWindow.setLayout(new FlowLayout());

        //let's add instructions for the user
        JLabel instructionEnterName = new JLabel("Please enter your name:");
        //now let's add a text field in which they can enter their name
        //a bigger columns value increases the length of the text field
        JTextField nameEntry = new JTextField(12);
        //now let's add another instruction...
        JLabel instructionEnterGeekStatement = new JLabel("Please explain why you're a geek!");
        //now let's give them a text area in which they can answer the question
        JTextArea geekStatement = new JTextArea(4,12);

        //now let's get them to choose their preferred colour
        String[] colours = {"Blue", "Red", "Pink", "Yellow"};
        JLabel instructionColor = new JLabel("Please select your favourite color!");
        //the array above populates the dropdown list (similar to the JOptionPanes we used throughout this course)
        JComboBox<String> colorSelection = new JComboBox<>(colours);

        //now let's ask them a yes/no question...
        JLabel loveCOSC120 = new JLabel("Do you love COSC120? (SAY YESSS!!!)");
        JCheckBox selectCOSC120 = new JCheckBox("Yes");

        //now let's get them to send us all their info
        JButton submitInfo = new JButton("Submit");

        //now add everything to the JFrame. These are added according to the layout manager (FlowLayout)
        mainWindow.add(instructionEnterName);
        mainWindow.add(nameEntry);
        mainWindow.add(instructionEnterGeekStatement);
        mainWindow.add(geekStatement);
        mainWindow.add(instructionColor);
        mainWindow.add(colorSelection);
        mainWindow.add(loveCOSC120);
        mainWindow.add(selectCOSC120);
        mainWindow.add(submitInfo);

        //make the frame visible
        mainWindow.setVisible(true);
    }
}
