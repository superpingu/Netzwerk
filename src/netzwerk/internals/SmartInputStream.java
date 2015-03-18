
package netzwerk.internals;

import netzwerk.listeners.MessageListener;
import java.io.*;

/*
 *  An ObjectInputStream that calls a listener when a message is received
 */
public class SmartInputStream extends ObjectInputStream {
    IncomingDataCaller caller;
    
    public SmartInputStream(InputStream in) throws IOException {
        super(in);
        caller = new IncomingDataCaller(this);
        caller.start();
    }
    public void addMessageListener(MessageListener listener) {
        caller.addMessageListener(listener);
    }
    public void addCloseListener(Runnable listener) {
        caller.addCloseListener(listener); 
    }
    @Override
    public void close() {
        caller.close();
        try {
            super.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
