package cl.injcristianrojas.as400;

import com.ibm.as400.access.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MainClass {

    static ConnectionData connData;

    public static void main(String[] args) {
        connData = new ConnectionData();
        connectionTest();
        testJDBC("hmaturana' or '1'='1");
    }

    private static void testJDBC(String username) {
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            Connection conn = DriverManager.getConnection("jdbc:as400://pub400.com/" + connData.getMainLibrary(), connData.getUsername(), connData.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username +"'");
            while ( rs.next() )
                System.out.printf("User selected: %s %s%n", rs.getString("name"), rs.getString("surname"));
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connectionTest() {
        boolean successful;

        AS400 as400 = new AS400(connData.getHost(), connData.getUsername(), connData.getPassword());
        try {
            successful = as400.validateSignon();
        } catch (AS400SecurityException | IOException e) {
            successful = false;
        }
        System.out.println(successful ? "Connection OK" : "Connection error");

        as400.disconnectAllServices();
    }

}
