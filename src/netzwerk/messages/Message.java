/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netzwerk.messages;

import java.io.Serializable;
import netzwerk.ServerClient;
import netzwerk.Connector;

/**
 *
 * @author arnaud
 */
public abstract class Message implements Serializable {
    // appelé quand le serveur a recu un message
    public abstract void onServerReceive(ServerClient client);
    // appelé quand le client a recu un message
    public abstract void onClientReceive(Connector client);
}
