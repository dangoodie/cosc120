/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;

public class EnterDetailsV2 {

    public static void main(String[] args){
        //same as before....
        JFrame mainWindow = new JFrame("Collection Geek Info");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.png").getImage());

        //instruction and related text field...
        JLabel instructionEnterName = new JLabel("Please enter your name:");
        JTextField nameEntry = new JTextField(12);

        //create a panel for the name components (lines 14-15), i.e. instruction and data entry
        JPanel namePanel = new JPanel();
        namePanel.add(instructionEnterName); //add the label
        namePanel.add(nameEntry); //add the text field
        //set the panel background to a different color, to visualise where the panel is on the main frame
        namePanel.setBackground(Color.CYAN);

        //instruction and related text field...
        JLabel instructionEnterGeekStatement = new JLabel("Please explain why you're a geek!");
        JTextArea geekStatement = new JTextArea(4,12);
        //create a panel for the geek statement components
        JPanel geekStatementPanel = new JPanel();
        geekStatementPanel.add(instructionEnterGeekStatement);
        geekStatementPanel.add(geekStatement);
        geekStatementPanel.setBackground(Color.PINK); //set its background to pink

        String[] colours = {"Blue", "Red", "Pink", "Yellow"};
        JLabel instructionColor = new JLabel("Please select your favourite color!");
        JComboBox<String> colorSelection = new JComboBox<>(colours);
        //create a panel for the color components
        JPanel colorSelectionPanel = new JPanel();
        colorSelectionPanel.add(instructionColor);
        colorSelectionPanel.add(colorSelection);
        colorSelectionPanel.setBackground(Color.ORANGE);

        //create a panel for the COSC120 components
        JLabel loveCOSC120 = new JLabel("Do you love COSC120? (SAY YESSS!!!)");
        JCheckBox selectCOSC120 = new JCheckBox("Yes");
        JPanel loveCOSC120Panel = new JPanel();
        loveCOSC120Panel.add(loveCOSC120);
        loveCOSC120Panel.add(selectCOSC120);
        loveCOSC120Panel.setBackground(Color.YELLOW);

        //create a panel to store the 4 new panels created above
        JPanel allEntries = new JPanel();
        //add the 4 panels in a 2 by 2 grid using the GridLayout manager
        allEntries.setLayout(new GridLayout(2,2));
        //alternatively, stack them on top of each other using the BoxLayout manager (y-axis aligned stacks them vertically)
        //allEntries.setLayout(new BoxLayout(allEntries,BoxLayout.Y_AXIS));

        //add all the panels to the large panel (its like adding small containers into a big one)
        allEntries.add(namePanel);
        allEntries.add(geekStatementPanel);
        allEntries.add(colorSelectionPanel);
        allEntries.add(loveCOSC120Panel);

        //add the JPanel containing the 4 other JPanels to the centre of the main window
        mainWindow.add(allEntries,BorderLayout.CENTER);

        //add the submit button at the bottom of the main window
        JButton submitInfo = new JButton("Submit");
        mainWindow.add(submitInfo,BorderLayout.SOUTH);

        //automatically set the size of the window
        mainWindow.pack();
        //ensure the window is visible!
        mainWindow.setVisible(true);
    }
}
