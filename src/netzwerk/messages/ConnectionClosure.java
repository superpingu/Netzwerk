
package netzwerk.messages;

import netzwerk.ServerClient;
import netzwerk.Connector;

/*
 * Message de fermeture de connexion
 */
public class ConnectionClosure extends Message {
    private String source;         // l'UID du client qui ferme la connexion
    
    // creer un message de fermeture de connexion 
    public ConnectionClosure(String source) {
        this.source = source;
    }
    @Override
    public void onServerReceive(ServerClient client) {
        if(client.getConnection()!=null)
            client.getConnection().close(client);
    }

    @Override
    public void onClientReceive(Connector client) {
        if(client.getConnectionListener()!=null)
            client.getConnectionListener().connectionClosed(source);
    }
}
