package frames;

import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationForm extends JFrame implements ActionListener {
    JTextField email;
    JPasswordField password;
    JLabel emailLabel,passwordLabel,duplicateEmailAlert;
    JButton cancel,register;
    JCheckBox showPassword;
    boolean isInserted;

    RegistrationForm() {
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        email = new JTextField();
        password = new JPasswordField();
        cancel = new JButton("Cancel");
        register = new JButton("Register");
        showPassword = new JCheckBox("Show Password");
        duplicateEmailAlert =new JLabel("");
        isInserted = true;

        duplicateEmailAlert.setForeground(Color.RED);

        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(20,60,100,50);
        email.setBounds(90,70,200,30);
        email.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(!isInserted){
                    email.setText("");
                    remove(duplicateEmailAlert);
                    repaint();
                    isInserted = true;
                }
            }
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) { }
        });

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(20, 100,100,50);
        password.setBounds(90,110,200,30);

        showPassword.setBounds(90,140,200,30);
        showPassword.setForeground(Color.WHITE);
        showPassword.addActionListener(e -> {
            JCheckBox c = (JCheckBox) e.getSource();
            password.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });

        register.addActionListener(this);
        register.setBounds(180,320,100,40);
        register.setBackground(Color.GREEN); // To change background of button
        register.setForeground(Color.BLUE); // To change text color on button
        register.setOpaque(true);

        cancel.addActionListener(this);
        cancel.setBounds(20,320,100,40);
        cancel.setBackground(Color.GREEN); // To change background of button
        cancel.setForeground(Color.BLUE); // To change text color on button
        cancel.setOpaque(true);

        add(duplicateEmailAlert);
        add(cancel);
        add(register);
        add(showPassword);
        add(email);
        add(password);
        add(passwordLabel);
        add(emailLabel);

        setTitle("Registration Form");
        new SetAttributes().set(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cancel){
            setVisible(false);
            new LogInOrRegister();
        } else if(e.getSource()==register){
            String email = this.email.getText();
            String password = new String(this.password.getPassword());

            if(email.isBlank()){
                displayDuplicateAlert("Email field cannot be empty");
                return;
            }
            else if(password.isEmpty() || password.isBlank()){
                displayDuplicateAlert("Password field cannot be empty");
                return;
            }

            AdminData.init();
            isInserted = AdminData.addAdmin(email.toLowerCase(), HashPassword.getSHA256(password));
            if(!isInserted) {
                displayDuplicateAlert("This Email is already Exist !!!");
            }
            else {
                setVisible(false);
                LogInOrRegister.stringStatus="Registration Successful !!!";

                EmployeeData.setNewTableName(email);
                AdminData.createEmployeeTable(EmployeeData.getTableName());

                new LogInOrRegister();
            }
            AdminData.close();
        }
    }

    private void displayDuplicateAlert(String alert){
        duplicateEmailAlert.setText(alert);
        duplicateEmailAlert.setBounds(90,45,200,30);
        add(duplicateEmailAlert);
        isInserted = false;
        repaint();
    }

}
