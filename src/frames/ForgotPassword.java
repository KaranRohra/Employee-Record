package frames;

import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ForgotPassword extends JFrame implements ActionListener {
    JTextField email,otpText;
    JPasswordField password;
    JLabel emailLabel,passwordLabel,duplicateEmailAlert,otpLabel;
    JButton cancel,change,sendOTP;
    JCheckBox showPassword;
    int otp;

    ForgotPassword() {
        otp = generateOTP();
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        email = new JTextField();
        password = new JPasswordField();
        cancel = new JButton("Cancel");
        change = new JButton("Register");
        showPassword = new JCheckBox("Show Password");
        duplicateEmailAlert =new JLabel();
        sendOTP= new JButton("Send Otp");
        otpLabel=new JLabel("Enter OTP:");
        otpText = new JTextField();

        duplicateEmailAlert.setForeground(Color.GREEN);

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

        otpLabel.setForeground(Color.WHITE);
        otpLabel.setBounds(20,170,100,50);
        otpText.setBounds(90,180,200,30);

        sendOTP.setBounds(100,250,100,40);
        sendOTP.addActionListener(this);

        change.addActionListener(this);
        change.setBounds(180,320,100,40);
        change.setBackground(Color.GREEN); // To change background of button
        change.setForeground(Color.BLUE); // To change text color on button
        change.setOpaque(true);

        cancel.addActionListener(this);
        cancel.setBounds(20,320,100,40);
        cancel.setBackground(Color.GREEN); // To change background of button
        cancel.setForeground(Color.BLUE); // To change text color on button
        cancel.setOpaque(true);

        add(otpLabel);
        add(otpText);
        add(sendOTP);
        add(duplicateEmailAlert);
        add(cancel);
        add(change);
        add(showPassword);
        add(email);
        add(password);
        add(passwordLabel);
        add(emailLabel);

        setTitle("Forgot Password");
        new SetAttributes().set(this);
    }

    private int generateOTP() {
        StringBuilder otp=new StringBuilder();
        for(int i=0;i<6;i++){
            otp.append(new Random().nextInt(10));
        }
        return Integer.parseInt(new String(otp));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cancel){
            setVisible(false);
            new LogInForm();
        } else if(e.getSource()==change){
            int enteredOtp;
            try {
                enteredOtp = Integer.parseInt(otpText.getText());
            }catch (Exception ee){
                displayDuplicateAlert("Enter valid OTP");
                return;
            }

            if(otp != enteredOtp){
                displayDuplicateAlert("Incorrect OTP");
                return;
            }

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

            boolean isChanged = AdminData.updateAdmin(email.toLowerCase(), HashPassword.getSHA256(password));
            if(!isChanged) {
                displayDuplicateAlert("This Email is Not Exist !!!");
            }
            else {
                setVisible(false);
                System.out.println("Updated !!!");

                new LogInForm();
            }
        }
        else if(e.getSource()==sendOTP){
            database.ForgotPassword.sendMail(email.getText(),otp);
        }
    }

    private void displayDuplicateAlert(String alert){
        duplicateEmailAlert.setText(alert);
        duplicateEmailAlert.setBounds(90,45,200,30);
        add(duplicateEmailAlert);
        duplicateEmailAlert.setForeground(Color.RED);
        repaint();
    }
}
