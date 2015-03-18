package netzwerk;

import netzwerk.listeners.*;
import netzwerk.messages.*;
import netzwerk.internals.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Connector extends Client {
    private int timeOutDelay = 1000;   // how long we wait for an answer to a blocking message (in ms)

    private ConnectionListener connectionListener;
    private Message lastMessage; //last received message
    
    // le constructeur doit avoir la forme public Connector(String UID); 
    public Connector(String localUID){
        this.UID=localUID;
    }

    /* connecter la socket TCP au serveur distant.*/
    public void connect(byte[] serverIp, int port) {
        // open socket connected to server
        try {
            socket = new Socket(InetAddress.getByAddress(serverIp), port);
            System.out.println("RemotePen : connecting to the server" ); 
        } catch (UnknownHostException e) {
            System.err.println("RemotePen : Cannot find specified host : " + serverIp);
            System.err.println(e);
        } catch (IOException e) {
            System.err.println("RemotePen : problem with I/O");
            System.err.println(e);
        }
        connect(socket); // connecter les flux d'entrée/sortie
        
        // handshake : on envoie notre UID au serveur pour que les autres clients puissent nous identifier
        sendMessage(new UID(UID));
    }    
    
    // send a message and wait for the answer (returned message has to be the same class)
    public synchronized BlockingMessage sendBlockingMessage(BlockingMessage message) {
        sendMessage(message);
        lastMessage = null;
        try {
            this.wait(1000);
        } catch (InterruptedException ex) {}

        if(lastMessage!=null && message.getClass().isInstance(lastMessage))
            return (BlockingMessage) lastMessage;
        else
            System.err.println("RemotePen : reception timeout ! No packet received");

        return null;
    }
    /* appelee quand on a recu un message*/
    @Override
    protected synchronized void onReceive(Message message) {
        lastMessage = message;
        message.onClientReceive(this);
    }
    
    /*envoyer une requête de connection à l'utilisateur spécifié*/
    public void connectToUser(String targetUID) {
        sendMessage(new ConnectionRequest(UID,targetUID));
    }
    /*envoie un paquet connnectionClosure a l'utilisateur distant contenant 
    l'UID local pour se deconnecter*/
    public void disconnectFromUser(){
        sendMessage(new ConnectionClosure(UID));
    }
    /* accepter ou non la requete de connection recu*/
    public void acceptConnection(boolean isAccepted) {
        if(isAccepted) {
            sendMessage(new ConnectionAnswer(ConnectionAnswer.ACCEPT));
        }
        else {
            sendMessage(new ConnectionAnswer(ConnectionAnswer.DECLINE));
        }
    }
    
    // appelée si le flux d'entrée est fermé (symptome que le serveur s'est déconnecté)
    @Override
    protected void onClose() {
        System.out.println("Remote Pen: connection lost !");
        close();
    }
    
    // getters and setters :
    public void setConnectionListener(ConnectionListener listener) {
        connectionListener= listener;
    }
    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }
    
    public int getTimeOutDelay() {
        return timeOutDelay;
    }
    public void setTimeOutDelay(int timeOutDelay) {
        this.timeOutDelay = timeOutDelay;
    }

    public Message getLastMessage() {
        return lastMessage;
    }   
}