package database;

import java.sql.*;

public class AdminData {
    private final static String TABLE_NAME = "admin_record";
    private final static String EMAIL_COLUMN = "admin_email";
    private final static String PASSWORD_COLUMN = "admin_password";

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

    public static boolean addAdmin(String email,String password){
        String query = "INSERT INTO " + TABLE_NAME + " VALUES('" + email + "','" + password + "');";

        try {
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+EMAIL_COLUMN +" = '"+email+"';");
            if(resultSet.next()){
                // This condition is to check duplicate email in database
                return false;
            }
            statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return true;
    }

    public static String searchAdmin(String email, String password) {
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE "+EMAIL_COLUMN +" = '"+email+"';";
        try {
            resultSet = statement.executeQuery(query);
            if(!resultSet.next()){
                // This condition is to check email is exist in data or not
                return "false Account does not exist !!!";
            }
            else if(password.equals(resultSet.getString(PASSWORD_COLUMN))) {
                return "true";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "false Wrong password !!!";
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

    public static void createEmployeeTable(String EMPLOYEE_TABLE_NAME) {
        String query = "CREATE TABLE IF NOT EXISTS " + EMPLOYEE_TABLE_NAME +
                "( id VARCHAR(20) PRIMARY KEY, name VARCHAR(50), email VARCHAR(50), phoneNumber VARCHAR(11), age INTEGER, salary LONG," +
                "path VARCHAR(500),VERSION INTEGER);";
        try {
            statement.executeUpdate(query);
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }

    public static boolean updateAdmin(String email, String password) {
        String query = "UPDATE "+TABLE_NAME+
                " SET "+EMAIL_COLUMN +" = '"+email+"',"+
                PASSWORD_COLUMN + " = '"+password+"' "+
                "WHERE "+EMAIL_COLUMN+" = '"+email+"';";
        init();
        try {
            statement.executeUpdate(query);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            close();
        }
        return false;
    }
}
