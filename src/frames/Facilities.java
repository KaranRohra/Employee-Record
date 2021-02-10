package frames;

import database.EmployeeData;

import javax.swing.*;
import java.awt.*;

//rohrakaran38@gmail.com
//kak
public class Facilities extends JFrame {
    JButton addEmployee,deleteEmployee,updateEmployee,editProfile,search,logout,deleteAll;
    JLabel status;
    static String statusString="";
    Facilities(){
        addEmployee = new JButton("Add Employee");
        deleteEmployee = new JButton("Delete Employee");
        updateEmployee = new JButton("Update Employee");
        editProfile = new JButton("Edit Profile");
        search = new JButton("Search Employee");
        logout = new JButton("Logout");
        status = new JLabel(statusString);
        deleteAll = new JButton("Delete All");

        status.setBounds(50,5,300,30);
        status.setForeground(Color.GREEN);

        logout.setBounds(200,330,70,30);
        logout.addActionListener(e->{
            LogInOrRegister.stringStatus = "";
            setVisible(false);
            new LogInOrRegister();
        });

        addEmployee.addActionListener(e->{
            setVisible(false);
            new AddEmployee();
        });

        deleteEmployee.addActionListener(e->{
            setVisible(false);
            new DeleteEmployee();
        });

        updateEmployee.addActionListener(e->{
            setVisible(false);
            new UpdateEmployee();
        });

        deleteAll.addActionListener(e->{
               //JOptionPane.showConfirmDialog(this,"Sure you want delete all Employee Data ?","Update Employee",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(JOptionPane.showConfirmDialog(this,"Sure you want delete all Employee Data ?","Delete all",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                EmployeeData.deleteAll();
        });

        search.addActionListener(e->{
            new Search();
            setVisible(false);
        });

        editProfile.addActionListener(e->{
            new ForgotPassword();
            setVisible(false);
        });

        add(status);
        add(logout);

        addColor(editProfile,30);
        addColor(addEmployee,80);
        addColor(updateEmployee,130);
        addColor(search,180);
        addColor(deleteEmployee,230);
        addColor(deleteAll,280);

        setTitle("Employee Data");
        new SetAttributes().set(this);
    }

    private void addColor(JButton button,int y){
        button.setForeground(Color.BLUE);
        button.setBackground(Color.GREEN);
        button.setBounds(50,y,200,40);
        button.setOpaque(true);
        add(button);
    }
}
