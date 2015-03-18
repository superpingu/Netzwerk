
package netzwerk.internals;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import netzwerk.listeners.MessageListener;
import netzwerk.messages.Message;

/*
 * Cette classe contient un système capable de se connecter et d'echager des Messages via le réseau
 */
public abstract class Client {
    
    protected Socket socket;
    protected ObjectOutputStream out;
    protected SmartInputStream in;          // an objectInputStream with built-in listener support (calls onReceive when a message is received)
    protected String UID;
    
    protected void connect(Socket socket) {
        this.socket = socket;
        // creation des flux d'entree/sortie
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println("Client : unable to create client output stream");
            System.err.println(ex);
        }
        try {
            in = new SmartInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println("Client : unable to create client input stream");
            System.err.println(ex);
        }
        
        in.addMessageListener(new MessageListener() { // appelé quand in recoit un message
            @Override
            public void messageReceived(Message message) {
                onReceive(message);
            }
        });
        in.addCloseListener(new Runnable() { // appelé quand in a été fermé
            @Override
            public void run() {
                onClose();
            }          
        });
    }
    // a la reception d'un message complet ( objet Message )
    protected abstract void onReceive(Message message);
    
    // envoie d'un message au client représenté par cet objet
    public void sendMessage(Message message) {
        if(socket!=null && socket.isConnected()) {      // check to socket is ready to send data
            System.out.println("Client : send message " + 
                    message.getClass().getSimpleName() + " to " + UID);
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException ex) {
                System.err.println("Client : problem while sending a message");
                System.err.println(ex);
            }
        }
    }
    // si le flux d'entrée a été fermé (signifie le client a fermé la socket)
    protected abstract void onClose();
    
    public void close() {
        try {
            out.flush();
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            System.err.println("Client : problem closing the socket");
            System.err.println(ex);
        }
    }
        
    // ######### Getters and Setters #########
    
    // renvoie l'identifiant unique du client
    public String getUID() {
        return UID;
    }
    public void setUID(String UID) {
        this.UID = UID;
    }
}
