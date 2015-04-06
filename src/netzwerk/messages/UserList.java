
package netzwerk.messages;

import netzwerk.*;

/*
 *
 */
public class UserList extends BlockingMessage {
    private String[] users;
    
    public static String[] get(Connector server) {
        return ((UserList)server.sendBlockingMessage(new UserList())).getUsers();
    }
    @Override
    public void onServerReceive(ServerClient client) {
        users = ServerClient.getUserList();
        client.sendMessage(this);
    }

    public String[] getUsers() {
        return users;
    }
}
