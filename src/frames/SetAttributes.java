package frames;

import javax.swing.*;
import java.awt.*;

public class SetAttributes {
    public void set(JFrame frame){
        frame.getContentPane().setBackground(Color.BLUE); // To change background of JFrame
        frame.setLocation(550,150);
        frame.setSize(300,400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
