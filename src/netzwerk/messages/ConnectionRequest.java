
package netzwerk.messages;

import netzwerk.Connection;
import netzwerk.ServerClient;
import netzwerk.Connector;


/*
 *   Message de requête de connexion 
 */
public class ConnectionRequest extends Message {
    private String targetUID;
    private String sourceUID;
    
    public ConnectionRequest(String sourceUID, String targetUID) {
        this.targetUID = targetUID;
        this.sourceUID = sourceUID;
    }
    @Override
    public void onServerReceive(ServerClient client) {
        // essaie de trouver le destinataire
        if(ServerClient.getClient(targetUID)!=null) {          // si il est bien connecté, on crée une nouvelle connection (que des connections à deux pour l'instant)
            Connection connection = new Connection();
            connection.addMember(client);
            connection.addMember(ServerClient.getClient(targetUID));
            ServerClient.getClient(targetUID).sendMessage(this);
        } else {                                         // sinon on répond que le destinataire spécifié n'a pas été trouvé
            client.sendMessage(new ConnectionAnswer(ConnectionAnswer.TARGETNOTFOUND));
        }
    }

    @Override
    public void onClientReceive(Connector client) {
        if(client.getConnectionListener()!=null)
            client.getConnectionListener().connectionRequest(sourceUID);
    }
    
}
