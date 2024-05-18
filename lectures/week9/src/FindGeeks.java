/**
 * @author Dr Andreas Shepley (asheple2@une.edu.au | andreashepley01@gmail.com)
 * created for COSC120 (Trimester 1 2022)
 * last revised: Trimester 1 2024
 */

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class FindGeeks {

    public static void main(String[] args){
        //load the geek data
        Map<String,Integer> geekNamesToAges = loadGeeks("./allGeeks.txt");

        //create the main frame
        JFrame mainWindow = new JFrame("Seek A Geek");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIconImage(new ImageIcon("./icon.png").getImage());
        mainWindow.setMinimumSize(new Dimension(300,100));

        //create an instance of UserInputAgeRange
        UserInputAgeRange ageRange = new UserInputAgeRange();
        //add the panel returned by getUserInputMinMaxAge to the main frame
        mainWindow.add(ageRange.getUserInputMinMaxAge(),BorderLayout.CENTER);

        //EDIT 3: add the geek gif to the top of the frame
        JLabel geekGIF = new JLabel(new ImageIcon("day-dreaming-nerd.gif"));
        mainWindow.add(geekGIF,BorderLayout.NORTH);

        //Create a submit button and add it to the main frame
        JButton submitAgeRange = new JButton("Submit");
        submitAgeRange.addActionListener(e -> {
            //EDIT 4: load and play the audio_file when showing users compatible geeks
            try{
                Clip clip = AudioSystem.getClip();
                //clip.open(AudioSystem.getAudioInputStream(new File("./WilhelmScream.wav")));
                clip.open(AudioSystem.getAudioInputStream(new File("./R2D2-hey-you.wav")));
                clip.start();
            }catch (Exception ex){
                System.out.println("Couldn't load sound file.");
            }

            //loop through the Map of names/ages, and determine which geeks have compatible age
            //ranges. Concatenate this info in a String and show it in a JOptionPane.
            StringBuilder output = new StringBuilder();
            for(String name: geekNamesToAges.keySet()) {
                if(geekNamesToAges.get(name)>=ageRange.getMinAge() &&
                        geekNamesToAges.get(name)<= ageRange.getMaxAge()){
                    output.append(name).append(": ").append(geekNamesToAges.get(name)).append(" years old").append("\n");
                }
            }
            //show the JOptionPane
            JOptionPane.showMessageDialog(null, output.toString(),"Matches found!",JOptionPane.INFORMATION_MESSAGE);
        });
        mainWindow.add(submitAgeRange, BorderLayout.SOUTH);

        //automatically set the size of the window and set it to visible
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    //create a method that reads the allGeeks.txt file, saving the name to age mapping in a Map.
    /**
     * a method to load and extract info from allGeeks.txt
     * @param filePath the path to allGeeks.txt
     * @return a map of geek names to age
     */
    public static Map<String,Integer> loadGeeks(String filePath){
        Map<String,Integer> nameToAge = new HashMap<>();
        List<String> eachGeek = null;
        Path path = Path.of(filePath);
        try{
            eachGeek = Files.readAllLines(path);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Could not load file! Username cannot be verified!");
            System.exit(0);
        }
        for (int i=1;i<eachGeek.size();i++) {
            String[] elements = eachGeek.get(i).split("\\[");
            String[] geekInfo = elements[0].split(",");
            String name = geekInfo[1];
            int age = Integer.parseInt(geekInfo[2]);
            nameToAge.put(name,age);
        }
        return nameToAge;
    }
}
