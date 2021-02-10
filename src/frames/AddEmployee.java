package frames;

import database.EmployeeData;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddEmployee extends JFrame {
    JButton add,cancel,selectImage;
    JLabel nameLabel,emailLabel,idLabel,ageLabel,phoneLabel,salaryLabel,imageView,status;
    JTextField name,email,id,age,phone,salary;
    JPanel imagePanel;
    String imagePath;
    AddEmployee(){
        Facilities.statusString = "";
        add = new JButton("Add");
        cancel = new JButton("Cancel");
        selectImage = new JButton("Select");
        imagePanel = new JPanel();
        status = new JLabel();

        nameLabel = new JLabel("Name: ");
        emailLabel = new JLabel("Email: ");
        idLabel = new JLabel("ID: ");
        ageLabel = new JLabel("Age: ");
        phoneLabel = new JLabel("Phone: ");
        salaryLabel = new JLabel("Salary: ");

        name = new JTextField();
        email = new JTextField();
        id = new JTextField();
        age = new JTextField();
        phone = new JTextField();
        salary = new JTextField();

        imageView = new JLabel();

        imagePanel.setBounds(20,30,100,100);
        selectImage.setBounds(20,140,100,30);

        addLabels(idLabel,130,30);
        addTextField(id,130,50,150,30);

        addLabels(nameLabel,130,80);
        addTextField(name,130,100,150,30);

        addLabels(emailLabel,20,170);
        addTextField(email,70,170,200,30);

        addLabels(phoneLabel,20,210);
        addTextField(phone,70,210,200,30);

        addLabels(ageLabel,20,250);
        addTextField(age,70,250,50,30);

        addLabels(salaryLabel,130,250);
        addTextField(salary,170,250,100,30);


        status.setForeground(Color.RED);
        status.setBounds(80,280,200,30);
        cancel.setBounds(20,320,100,40);
        add.setBounds(180,320,100,40);

        add.addActionListener(e->{
            try {
                EmployeeData.init();
                boolean isInserted=EmployeeData.addEmployee(id.getText(),name.getText(),email.getText(),phone.getText(),Integer.parseInt(age.getText()),Long.parseLong(salary.getText()),imagePath);
                if(isInserted){
                    setVisible(false);
                    Facilities.statusString = "Employee Added successfully !!!";
                    new Facilities();
                } else {
                    status.setText("Employee already present !!!");
                    repaint();
                }
                EmployeeData.close();
            } catch (Exception ea){
                status.setText("Please enter proper data");
                repaint();
            }
        });

        selectImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);

            int option = fileChooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                imagePath = file.getPath();
                imageView.setIcon(new ImageIcon(imagePath));
                repaint();
            }
        });

        cancel.addActionListener(e->{
            Facilities.statusString = "";
            setVisible(false);
            new Facilities();
        });
        //imageView.setIcon(new ImageIcon(ImageFilter.getImage()));
        imagePanel.add(imageView);

        add(status);
        add(selectImage);
        add(imagePanel);
        add(add);
        add(cancel);

        new SetAttributes().set(this);
    }

    public void addTextField(JTextField textField,int x,int y,int width,int height){
        textField.setBounds(x,y,width,height);
        add(textField);
    }

    public void addLabels(JLabel label,int x,int y){
        label.setForeground(Color.white);
        label.setBounds(x,y,100,30);
        add(label);
    }
}
