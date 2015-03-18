
package netzwerk;

import netzwerk.messages.*;
import java.util.ArrayList;

/*
 *  Cette classe permet le passage automatique d'information d'un client aux autres.
 *  Pour l'instant, on ne peut que faire des connections à deux même si l'architecture
 *  est prévue pour pouvoir plus facilement étendre à plusieurs
 */
public class Connection {
    private ArrayList<ServerClient> members = new ArrayList<>();
    
    // envoyer un message a tous les membres connectés (excepté l'envoyeur)
    public void sendMessage(Message message, ServerClient sender) {
        for(ServerClient target : members) {
            if(!target.equals(sender))
                target.sendMessage(message);
        }
    }
    public void addMember(ServerClient newMember) {
        members.add(newMember);
        newMember.setConnection(this);
    }
    public void close(ServerClient sender) {
        sendMessage(new ConnectionClosure(sender.getUID()), sender);
        disconnectAll();
    }
    // enlever toutes les références vers cette connection (qui est alors détruite si le garbage collector fait son boulot)
    public void disconnectAll() {
        for(ServerClient member : members)
            member.setConnection(null);
    }
}
