/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.swing.*;
import java.awt.*;

public class BasicWindow {

    public static void main(String[] args){
        //main window, with title of window (this can be done without a title too)
        JFrame mainWindow = new JFrame("Seek A Geek");
        //Always handle the window closing event, by choosing what the program is
        //to do when the user closes it (by default, it just hides it!)
        //the line below means that when the user clicks 'close', the program will exit
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this positions the window in the middle of your screen
        mainWindow.setLocationRelativeTo(null);

        //this will set the minimum size of the window to width = 300 and height = 100
        mainWindow.setMinimumSize(new Dimension(300,100));
        //alternatively, use pack to automatically size it
        //mainWindow.pack(); //comment line 17, then uncomment this line to use pack

        //choose an icon for the JFrame
        mainWindow.setIconImage(new ImageIcon("./icon.png").getImage());

        //change the window look and feel
        JFrame.setDefaultLookAndFeelDecorated(true);

        //load a new layout manager to centre the label (the default is left-aligned - comment out to visualise)
        mainWindow.setLayout(new FlowLayout(FlowLayout.CENTER));

        //let's add a message to the window
        JLabel message = new JLabel("Welcome to Seek a Geek!");
        //add the message to the window
        mainWindow.add(message);

        //A JFrame is by default invisible, so the setVisible(true) method must be called to make it visible.
        mainWindow.setVisible(true);
    }
}
