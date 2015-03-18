
package netzwerk.internals;

import netzwerk.messages.Message;
import netzwerk.listeners.MessageListener;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author arnaud
 */
public class IncomingDataCaller extends Thread {
    boolean running;
    ObjectInputStream in;
    MessageListener listener;
    Runnable closeListener;
    
    public IncomingDataCaller(ObjectInputStream in) {
        this.in = in;
    }
    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                Message message = (Message) in.readObject();
                if(listener!=null)
                    listener.messageReceived(message);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("SmartInputStream : closed input stream");
                if((closeListener != null)&&running)
                    closeListener.run();
                running = false;
            }
        }
    }
    public void close() {
        running = false;
    }
    public void addCloseListener(Runnable listener) {
        closeListener = listener;
    }
    public void addMessageListener(MessageListener listener) {
        this.listener = listener;
    }
}
