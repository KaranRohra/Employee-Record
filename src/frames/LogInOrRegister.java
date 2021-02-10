package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogInOrRegister extends JFrame implements ActionListener {
    JButton logIn,registration;
    JLabel status;
    static String stringStatus;
    LogInOrRegister(){
        logIn=new JButton("Log in");
        registration=new JButton("Register");
        status=new JLabel(stringStatus);

        logIn.setBounds(50,120,200,50);
        registration.setBounds(50,180,200,50);
        status.setBounds(80,20,200,50);

        status.setForeground(Color.GREEN);

        logIn.setBackground(Color.GREEN); // To change background of button
        logIn.setForeground(Color.BLUE); // To change text color on button
        logIn.setOpaque(true);

        registration.setBackground(Color.GREEN); // To change background of button
        registration.setForeground(Color.BLUE); // To change text color on button
        registration.setOpaque(true);

        logIn.addActionListener(this);
        registration.addActionListener(this);

        add(status);
        add(logIn);
        add(registration);
        add(status);
        setTitle("Employee Record");
        new SetAttributes().set(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logIn){
            new LogInForm();
        }
        else if(e.getSource()==registration){
            new RegistrationForm();
        }
        setVisible(false);
    }
}
