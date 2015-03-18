
package netzwerk.listeners;

// un listener contenant les methodes appelés pour la gestion de connection/deconnection avec un SmartPen distant
public abstract class ConnectionListener {
    // appelée a la reception d'une requete de connection par le client d'UID distantUID
    public void connectionRequest(String distantUID){}
    // appelée si le client distant ferme la connection
    public void connectionClosed(String distantUID){}
    // appellée à la reception de la reponse du client 
    // accepted : true/false si le client a ete trouve et a répondu, null si le client n'a pas été trouvé
    public void connectionAnswer(short answer){}
}
