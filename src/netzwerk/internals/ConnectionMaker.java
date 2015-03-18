
package netzwerk.internals;

import netzwerk.ServerClient;
import java.io.IOException;
import java.net.*;

/*
 *  ConnectionMaker creates a new client whenever a client tries to connect to the server
 */
public class ConnectionMaker extends Thread {
    private ServerSocket connectionSocket;
    private boolean running;
    
    public ConnectionMaker(ServerSocket socket) {
        connectionSocket = socket;
    }
    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                Socket newSocket = connectionSocket.accept();
                ServerClient.add(newSocket);
                System.out.println("Server : new client connected !");
            } catch (IOException ex) {
                if(connectionSocket.isClosed()) {
                    System.out.println("Server : stop connection making");
                } else {
                    System.err.println("Server : failed in creating a connection");
                }
            } 
        }
    }
    public void close() {
        running = false;
    }
}
