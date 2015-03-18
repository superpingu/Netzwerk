
package netzwerk;

import java.io.IOException;
import java.net.*;
import netzwerk.internals.*;

// A server allowing clients and server to exchange Messages objects with connection mechanism for auto-redirection

public class Server {
    private int port;
    private ConnectionMaker maker;
    private ServerSocket connectionSocket;
    
    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            connectionSocket = new ServerSocket(port);
            connectionSocket.setPerformancePreferences(7, 2, 1);
            maker = new ConnectionMaker(connectionSocket);
            maker.start();
        } catch (IOException ex) {
            System.err.println("Server : failed in creating connection socket");
            System.err.println(ex);
        }
        
    }
    private String IPtoString(InetAddress inet) {
        byte ip[] = inet.getAddress();
        return (ip[0]&0xFF) + "." + (ip[1]&0xFF) + "." + (ip[2]&0xFF) + "." + (ip[3]&0xFF);
    }
    // get the local IP in the network the server is running on
    public String getLocalIP() {
        try {
            return IPtoString(InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            System.err.println("Server : couldn't get the local IP");
            System.err.println(ex);
        }
        return null;
    }
    public int getPort() {
        return port;
    }
    
    // disconnect the server
    public void close() {
        try {
            ServerClient.closeAll();
            maker.close();
            connectionSocket.close();
        } catch (IOException ex) {
            System.err.println("Server : Connection closed with errors");
            System.err.println(ex);
        }
    }
    
}
