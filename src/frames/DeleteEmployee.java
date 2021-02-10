package frames;

import database.EmployeeData;

import javax.swing.*;
import java.awt.*;

public class DeleteEmployee extends JFrame {
    JButton delete,cancel,search;
    JLabel nameLabel,emailLabel,idLabel,ageLabel,phoneLabel,salaryLabel,imageView,status;
    JTextField name,email,id,age,phone,salary;
    JPanel imagePanel;
    DeleteEmployee(){
        delete = new JButton("Delete");
        cancel = new JButton("Cancel");
        imagePanel = new JPanel();
        status = new JLabel();
        search = new JButton("Search");

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

        search.setBounds(170,10,100,30);

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
        delete.setBounds(180,320,100,40);

        delete.addActionListener(e->{
            int option = JOptionPane.showConfirmDialog(this,"Sure you want update ID "+id.getText()+" ?","Update Employee",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.NO_OPTION)
                return;
            try {
                EmployeeData.init();
                boolean isDeleted=EmployeeData.deleteEmployee(id.getText());
                if(isDeleted){
                    setVisible(false);
                    Facilities.statusString = "Employee Deleted successfully !!!";
                    new Facilities();
                } else {
                    status.setText("Employee not exist of ID "+id.getText());
                    repaint();
                }
                EmployeeData.close();
            }catch (Exception ea){
                status.setText("Please enter proper data");
                repaint();
            }
        });

        search.addActionListener(e -> {
            try {
                String[] data = EmployeeData.getData(id.getText());
                if(data == null){
                    status.setText("Employee not exist of ID "+id.getText());
                    repaint();
                }
                else{
                    name.setText(data[1]);
                    email.setText(data[2]);
                    phone.setText(data[3]);
                    age.setText(data[4]);
                    salary.setText(data[5]);
                }
            }catch (Exception ei){
               ei.fillInStackTrace();
            }
        });


        cancel.addActionListener(e->{
            Facilities.statusString = "";
            setVisible(false);
            new Facilities();
        });
        //imageView.setIcon(new ImageIcon(ImageFilter.getImage()));
        imagePanel.add(imageView);

        add(search);
        add(status);
        add(imagePanel);
        add(delete);
        add(cancel);

        new SetAttributes().set(this);
    }

    public void addTextField(JTextField textField,int x,int y,int width,int height){
        textField.setBounds(x,y,width,height);
        if(textField!=id){
            textField.setEditable(false);
        }
        add(textField);
    }

    public void addLabels(JLabel label,int x,int y){
        label.setForeground(Color.white);
        label.setBounds(x,y,100,30);
        add(label);
    }
}
