package frames;

import database.EmployeeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Search extends JFrame {
    JTextField searchBar;
    JPanel dataPanel;
    JButton back;
    ArrayList<JButton> resultEmployee;
    Search(){
        Facilities.statusString = "";

        searchBar = new JTextField();
        dataPanel = new JPanel();
        back = new JButton("Back");

        searchBar.setBounds(0,0,300,30);
        dataPanel.setBounds(0,40,300,270);
        back.setBounds(100,320,100,50);
        resultEmployee = new ArrayList<>();

        dataPanel.setBackground(Color.WHITE);
        back.setForeground(Color.BLUE);
        back.setBackground(Color.GREEN);
        back.setOpaque(true);

        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                resultEmployee.clear();
                dataPanel.removeAll();

                EmployeeData.searchAndSetData(resultEmployee,searchBar.getText());

                displaySearchResult();
                repaint();
            }
        });

        back.addActionListener(e->{
            Facilities.statusString = "";
            setVisible(false);
            new Facilities();
        });

        add(back);
        add(searchBar);
        add(dataPanel);

        new SetAttributes().set(this);
    }

    private void displaySearchResult(){
        int y=0;
        for(JButton label:resultEmployee){
            label.setBounds(0,y,300,20);
            dataPanel.add(label);
            y+=20;
        }
    }
}
