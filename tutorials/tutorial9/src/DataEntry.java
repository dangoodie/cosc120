/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataEntry {

    //Part 1.2: fields at the top of the class to contain the user's choices.
    private static JTextField nameEntry;
    private static JComboBox<String> breedSelection;
    private static JCheckBox deSexed;
    private static ButtonGroup sex;

    /**
     * this is the main method - it creates the main frame, adds the user selection
     * panel and button to the main frame and shows the main frame.
     * It also contains an action listener that writes the user's input to a file
     * or prints it to standard output (delegate this to a method)
     * @param args NA
     */
    public static void main(String[] args){
        //Part 1.7: create your JFrame, giving it an appropriate title.
        JFrame mainWindow = new JFrame("Dog Adoption Search Criteria");
        //Part 1.8: Ensure that the program exits when the user selects close.
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Part 1.9: Ensure the frame is positioned in the center of your screen.
        mainWindow.setLocationRelativeTo(null);
        //Part 1.10: Replace the default icon with icon.png
        mainWindow.setIconImage(new ImageIcon("./icon.jpg").getImage());
        //Part 1.11: Call the method you created above, adding the returned panel to the main frame.
        mainWindow.add(userSelections(), BorderLayout.CENTER);

        //Part 1.12: Add a button onto the main frame, positioning it appropriately.
        JButton submitInfo = new JButton("Submit");
        //Part 1.13: Add an event handler (action listener) onto the button...
        submitInfo.addActionListener(e -> {
            String isDeSexed = "";
            if(deSexed.isSelected()) isDeSexed="de-sexed";
            /* Part 1.13.2: print to standard output
            System.out.println("Name: "+nameEntry.getText());
            System.out.println("Breed: "+breedSelection.getSelectedItem());
            System.out.println("Sex: "+isDeSexed+" "+sex.getSelection().getActionCommand());
             */
            //Part 1.13.1: write the user's selections to a simple text file named dog_adoption_search_filters.txt
            String filePath ="dog_adoption_search_filters.txt";
            Path path = Path.of(filePath);
            String lineToWrite = nameEntry.getText()+" wishes to adopt a "+isDeSexed+" "+sex.getSelection().getActionCommand()+" "
                    +breedSelection.getSelectedItem()+" dog.";
            try {
                Files.writeString(path, lineToWrite);
                JOptionPane.showMessageDialog(null,"Adoption file written.... close this dialog to terminate."
                        ,null, JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }catch (IOException io){
                JOptionPane.showMessageDialog(null,"Error: Adoption request could not be submitted. Please try again!"
                        ,null, JOptionPane.ERROR_MESSAGE);
            }
        });
        mainWindow.add(submitInfo,BorderLayout.SOUTH);

        mainWindow.pack();
        //Part 1.14: Ensure your main frame is visible, and appropriately sized.
        mainWindow.setVisible(true);
    }

    //Part 1.3: Outside the main method, create another method with return type JPanel.
    /**
     * method allow user to enter/select preferences
     * @return JPanel of 4 sub-JPanels, each containing a user entry/selection
     */
    public static JPanel userSelections(){
        //Part 1.4.1: Create a label that indicates to the user what they are expected to enter/select.
        JLabel instructionEnterName = new JLabel("Please enter your name:");
        //Part 1.4.2: Initialise the corresponding field (user input, e.g. name entry), entering
        //appropriate parameters into the object declaration, e.g., size of text field.
        nameEntry = new JTextField(12);
        //Part 1.4.3: Create a panel to which you add the appropriate label and field.
        JPanel namePanel = new JPanel();
        namePanel.add(instructionEnterName);
        namePanel.add(nameEntry);
        //Part 1.4.4: Choose an appropriate background color for the panel.
        namePanel.setBackground(Color.CYAN);

        String[] breeds = {"poodle", "jack russell", "bulldog", "chihuahua", "rottweiler", "dalmatian", "doberman", "pit bull"};
        //Part 1.4.1
        JLabel instructionBreed = new JLabel("Please select or enter your preferred breed");
        //Part 1.4.2
        breedSelection = new JComboBox<>(breeds);
        breedSelection.setSelectedIndex(3);
        breedSelection.setEditable(true);
        //Part 1.4.3
        JPanel breedSelectionPanel = new JPanel();
        breedSelectionPanel.add(instructionBreed);
        breedSelectionPanel.add(breedSelection);
        //Part 1.4.4
        breedSelectionPanel.setBackground(Color.ORANGE);

        //Part 1.4.1
        JLabel instructionDeSexed = new JLabel("Do you want your dog to be de-sexed?");
        //Part 1.4.2
        deSexed = new JCheckBox("Yes");
        deSexed.setSelected(true);
        //Part 1.4.3
        JPanel deSexedPanel = new JPanel();
        deSexedPanel.add(instructionDeSexed);
        deSexedPanel.add(deSexed);
        //Part 1.4.4
        deSexedPanel.setBackground(Color.YELLOW);

        //Part 1.4.1
        JLabel instructionSex = new JLabel("Please select a sex");
        //create one button for each sex, and
        JRadioButton female = new JRadioButton("Female");
        female.setActionCommand("female");
        JRadioButton male = new JRadioButton("Male");
        male.setActionCommand("male");
        //Part 1.4.2
        sex = new ButtonGroup();
        sex.add(female);
        sex.add(male);
        //Part 1.4.3
        JPanel sexPanel = new JPanel();
        sexPanel.add(instructionSex);
        sexPanel.add(female);
        sexPanel.add(male);
        //Part 1.4.4
        sexPanel.setBackground(Color.PINK);

        //Part 1.5: Next, create a 'master' panel that will contain all the other panels.
        //Choose an appropriate layout manager for this panel, and add all the data entry panels to it.
        JPanel allEntries = new JPanel();
        allEntries.setLayout(new BoxLayout(allEntries,BoxLayout.Y_AXIS));
        allEntries.add(namePanel);
        allEntries.add(breedSelectionPanel);
        allEntries.add(deSexedPanel);
        allEntries.add(sexPanel);
        //Part 1.6: Return the master panel.
        return allEntries;
    }

}
