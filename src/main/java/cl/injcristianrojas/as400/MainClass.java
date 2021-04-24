package cl.injcristianrojas.as400;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.CommandCall;

public class MainClass {

    public static void main(String[] args) {
        ConnectionData connData = new ConnectionData();

        AS400 server = new AS400(connData.getHost(), connData.getUsername(), connData.getPassword());
        CommandCall cmd = new CommandCall(server);

        try {
            cmd.run("DSPLIB PUB400SYS");
            AS400Message[] messageList = cmd.getMessageList();

            for (AS400Message msg: messageList) {
                System.out.println(msg.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.disconnectService(AS400.COMMAND);
    }

}
