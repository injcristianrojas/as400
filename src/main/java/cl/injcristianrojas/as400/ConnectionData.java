package cl.injcristianrojas.as400;

import java.io.FileReader;
import java.util.Properties;

public class ConnectionData {
    private String host;
    private String username;
    private String password;
    private String mainLibrary;

    public ConnectionData() {
        try {
            FileReader reader = new FileReader("as400.properties");
            Properties p = new Properties();
            p.load(reader);
            this.host = p.getProperty("hostname");
            this.username = p.getProperty("username");
            this.password = p.getProperty("password");
            this.mainLibrary = p.getProperty("mainLibrary");
        } catch (Exception e) {
            e.printStackTrace();
            this.host = null;
            this.username = null;
            this.password = null;
            this.mainLibrary = null;
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getMainLibrary() { return this.mainLibrary; }
}
