package database;

import frames.SearchResult;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeData {
    private static String TABLE_NAME;
    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String SALARY = "salary";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String PATH = "PATH";
    private static final String VERSION = "VERSION";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void init(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_record", "root", "MySQL2001*");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setNewTableName(String email){
        char[] emailAsTable = email.toCharArray();
        StringBuilder newTableName=new StringBuilder();
        for (char x : emailAsTable) {
            if ((x >= '0' && x <= '9') || (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')) {
                newTableName.append(x);
            } else {
                newTableName.append((int) (x));
            }
        }
        TABLE_NAME = new String(newTableName);
        EmployeeData.updateTable();
    }

    public static boolean addEmployee(String id,String name,String email,String phoneNUmber,int age,long salary,String imagePath){
        String query = "INSERT INTO " + TABLE_NAME +
                " VALUES('" + id +"','" + name + "','" + email + "','" + phoneNUmber + "'," + age + "," + salary + ",'" +imagePath+"');";
        try {
            resultSet = statement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+ ID +" = '" + id +"';");
            if(resultSet.next()){
                return false;
            }
            statement.executeUpdate(query);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static boolean deleteEmployee(String id){
        try {
            resultSet = statement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+ ID +" = '"+ id +"';");
            if(resultSet.next()){
                statement.executeUpdate( "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id +"';");
                return true;
            }

        }catch (Exception e){
           e.fillInStackTrace();
        }
        return false;
    }
    public static void close(){
        try {
            connection.close();
            statement.close();
            resultSet.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String[] getData(String id){
        String[] res;
        init();
        try {
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "';");
            if(resultSet.next()) {
                res = new String[7];
                for(int i=1;i<=7;i++){
                    res[i-1] = String.valueOf(resultSet.getString(i));
                }
                return res;
            }
        } catch (Exception e){
            e.fillInStackTrace();
        } finally {
            close();
        }
        return null;
    }

    public static boolean updateEmployee(String id,String name,String email,String phoneNUmber,int age,long salary,String imagePath){
        init();
        String query = "UPDATE " + TABLE_NAME +
                " SET " + ID + " = '" + id + "'," +
                NAME + " = '" + name + "'," +
                EMAIL + " = '" + email + "'," +
                PHONE_NUMBER + " = '" + phoneNUmber + "'," +
                AGE + " = " + age + "," +
                SALARY + " = " + salary + ","+
                PATH + " = '" + imagePath +
                "' WHERE id = '" + id + "';";
        try {
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = "+id);
            if(resultSet.next()){
                statement.executeUpdate(query);
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            close();
        }
        return false;
    }

    public static void deleteAll(){
        init();
        try {
            resultSet = statement.executeQuery("SELECT * FROM "+TABLE_NAME);
            if(resultSet.next()) {
                statement.executeUpdate("DELETE FROM "+TABLE_NAME+" LIMIT "+resultSet.getInt(1)+1);
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            close();
        }
    }

    public static void searchAndSetData(ArrayList<JButton> resultEmployee, String toSearch) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL + " LIKE '" + toSearch + "%'";
        init();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                JButton button = new JButton(resultSet.getString(EMAIL));
                button.setBackground(Color.WHITE);
                button.addActionListener(e -> new SearchResult(button.getText()));

                resultEmployee.add(button);
            }
        } catch (Exception e){
            e.fillInStackTrace();
        } finally {
            close();
        }
    }

    public static String[] searchByEmail(String email){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+EMAIL+" = '"+email+"';";
        init();
        String[] res=new String[7];
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            res[0] = resultSet.getString(ID);
            res[1] = resultSet.getString(EMAIL);
            res[2] = resultSet.getString(NAME);
            res[3] = resultSet.getString(PHONE_NUMBER);
            res[4] = resultSet.getString(SALARY);
            res[5] = resultSet.getString(AGE);
            res[6] = resultSet.getString(PATH);

        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            close();
        }
        return res;
    }

    public static void updateTable(){
        String query1="ALTER TABLE "+TABLE_NAME+" ADD "+PATH+" VARCHAR(500);";
        String query2="ALTER TABLE "+TABLE_NAME+" ADD "+VERSION+" INTEGER DEFAULT 1;";
        String countNumberOfColumnQuery = "SELECT COUNT(*) AS NUMBER_OF_COLUMN FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = '"+TABLE_NAME+"';";
        init();
        try {
            resultSet = statement.executeQuery(countNumberOfColumnQuery);
            resultSet.next();
            if(resultSet.getInt("NUMBER_OF_COLUMN")==6) {
                statement.executeUpdate(query1);
                statement.executeUpdate(query2);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            close();
        }
    }
}





