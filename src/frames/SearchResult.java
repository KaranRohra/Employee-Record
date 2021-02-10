package frames;

import database.EmployeeData;

import javax.swing.*;
import java.awt.*;

public class SearchResult extends JFrame {
    JButton cancel,selectImage;
    JLabel nameLabel,emailLabel,idLabel,ageLabel,phoneLabel,salaryLabel,imageView,status;
    JTextField name,email,id,age,phone,salary;
    JPanel imagePanel;
    public SearchResult(String resultEmail){
        String[] data = EmployeeData.searchByEmail(resultEmail);
        cancel = new JButton("Back");
        selectImage = new JButton("Select");
        imagePanel = new JPanel();
        status = new JLabel();

        nameLabel = new JLabel("Name: ");
        emailLabel = new JLabel("Email: ");
        idLabel = new JLabel("ID: ");
        ageLabel = new JLabel("Age: ");
        phoneLabel = new JLabel("Phone: ");
        salaryLabel = new JLabel("Salary: ");

        name = new JTextField(data[2]);
        email = new JTextField(data[1]);
        id = new JTextField(data[0]);
        age = new JTextField(data[5]);
        phone = new JTextField(data[3]);
        salary = new JTextField(data[4]);

        imageView = new JLabel(new ImageIcon(data[6]));

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


        cancel.addActionListener(e->{
            setVisible(false);
            new Search();
        });
        //imageView.setIcon(new ImageIcon(ImageFilter.getImage()));
        imagePanel.add(imageView);
        selectImage.setEnabled(false);

        add(status);
        add(selectImage);
        add(imagePanel);
        add(cancel);

        new SetAttributes().set(this);
    }

    public void addTextField(JTextField textField,int x,int y,int width,int height){
        textField.setBounds(x,y,width,height);
        textField.setEditable(false);
        add(textField);
    }

    public void addLabels(JLabel label,int x,int y){
        label.setForeground(Color.white);
        label.setBounds(x,y,100,30);
        add(label);
    }
}
