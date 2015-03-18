
package netzwerk.messages;

import netzwerk.Connector;
import netzwerk.ServerClient;

/*
 */
public class UID extends Message {
    private String UID;
    
    public UID(String UID) {
        this.UID = UID;
    }
    @Override
    public void onServerReceive(ServerClient client) {
        client.setUID(UID);
    }

    @Override
    public void onClientReceive(Connector client) {

    }
}
