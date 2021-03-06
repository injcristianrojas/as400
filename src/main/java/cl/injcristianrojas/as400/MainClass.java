package cl.injcristianrojas.as400;

import com.ibm.as400.access.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MainClass {

    private static ConnectionData connData;
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        connData = new ConnectionData();
        testJDBC("hmaturana", "12345");
    }

    private static void testJDBC(String username, String password) {
        if (!connectionTest()) {
            logger.error("Connection error. Shutting down...");
            return;
        }
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:as400://pub400.com/" + connData.getMainLibrary(),
                    connData.getUsername(),
                    connData.getPassword()
            );
            String sql = "SELECT * FROM users WHERE username = '" + username + "' and password = '" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int rows = 0;
            while (rs.next()) {
                rows++;
            }
            rs.close();
            conn.close();
            if (rows > 0)
                logger.info("Login successful");
            else
                logger.error("Username/password incorrect");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e);
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
