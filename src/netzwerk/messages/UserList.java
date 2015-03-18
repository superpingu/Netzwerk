
package netzwerk.messages;

import netzwerk.ServerClient;

/*
 *
 */
public class UserList extends BlockingMessage {
    private String[] users;
    
    @Override
    public void onServerReceive(ServerClient client) {
        users = ServerClient.getUserList();
        client.sendMessage(this);
    }

    public String[] getUsers() {
        return users;
    }
}
