package frames;

import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LogInForm extends JFrame implements ActionListener{
    JTextField email;
    JPasswordField password;
    JLabel emailLabel,passwordLabel,duplicateEmailAlert;
    JButton cancel,log,forgotPassword;
    JCheckBox showPassword;

    LogInForm(){
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        email = new JTextField();
        password = new JPasswordField();
        cancel = new JButton("Cancel");
        log = new JButton("Login");
        showPassword = new JCheckBox("Show Password");
        duplicateEmailAlert =new JLabel();
        forgotPassword = new JButton("forgot password ?");

        duplicateEmailAlert.setForeground(Color.RED);
        //forgotPassword.setForeground(Color.WHITE);

        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(20,60,100,50);
        email.setBounds(90,70,200,30);

        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(20, 100,100,50);
        password.setBounds(90,110,200,30);

        showPassword.setBounds(90,140,200,30);
        showPassword.setForeground(Color.WHITE);
        showPassword.addActionListener(e -> {
            JCheckBox c = (JCheckBox) e.getSource();
            password.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });

        forgotPassword.setBounds(90,170,200,30);
        forgotPassword.addActionListener(this);

        log.addActionListener(this);
        log.setBounds(180,320,100,40);
        log.setBackground(Color.GREEN); // To change background of button
        log.setForeground(Color.BLUE); // To change text color on button
        log.setOpaque(true);

        cancel.addActionListener(this);
        cancel.setBounds(20,320,100,40);
        cancel.setBackground(Color.GREEN); // To change background of button
        cancel.setForeground(Color.BLUE); // To change text color on button
        cancel.setOpaque(true);

        add(forgotPassword);
        add(duplicateEmailAlert);
        add(cancel);
        add(log);
        add(showPassword);
        add(email);
        add(password);
        add(passwordLabel);
        add(emailLabel);

        setTitle("Login Form");
        new SetAttributes().set(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cancel){
            LogInOrRegister.stringStatus = "";
            setVisible(false);
            new LogInOrRegister();
        } else if(e.getSource()==log){
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
            String status = AdminData.searchAdmin(email.toLowerCase(), HashPassword.getSHA256(password));
            boolean isLogin = Boolean.parseBoolean(status.substring(0,4));
            if(!isLogin) {
                displayDuplicateAlert(status.substring(5));
            }
            else {
                EmployeeData.setNewTableName(email);
                //System.out.println(EmployeeData.getTableName());
                setVisible(false);
                new Facilities();
            }
            AdminData.close();
        } else if(e.getSource() == forgotPassword){
            setVisible(false);
            new ForgotPassword();
        }
    }

    private void displayDuplicateAlert(String alert){
        duplicateEmailAlert.setText(alert);
        duplicateEmailAlert.setBounds(90,45,200,30);
        add(duplicateEmailAlert);
        repaint();
    }
}
