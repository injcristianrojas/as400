package cl.injcristianrojas.as400;

import com.ibm.as400.access.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MainClass {

    private static ConnectionData connData;

    public static void main(String[] args) {
        connData = new ConnectionData();
        testJDBC("hmaturana", "12345");
    }

    private static void testJDBC(String username, String password) {
        if (!connectionTest()) {
            System.out.println("Connection error. Shutting down...");
            return;
        }
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            Connection conn = DriverManager.getConnection("jdbc:as400://pub400.com/" + connData.getMainLibrary(), connData.getUsername(), connData.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username +"' and password = '" + password + "'");
            int rows = 0;
            while ( rs.next() ) {
                //System.out.printf("User selected: %s %s%n", rs.getString("name"), rs.getString("surname"));
                rows++;
            }
            rs.close();
            conn.close();
            System.out.println(rows > 0 ? "Login successful" : "Username/password incorrect");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean connectionTest() {
        boolean successful;

        AS400 as400 = new AS400(connData.getHost(), connData.getUsername(), connData.getPassword());
        try {
            successful = as400.validateSignon();
        } catch (AS400SecurityException | IOException e) {
            successful = false;
        }

        as400.disconnectAllServices();

        return successful;
    }

}
