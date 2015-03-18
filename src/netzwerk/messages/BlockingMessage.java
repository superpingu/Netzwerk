
package netzwerk.messages;

import netzwerk.Connector;

/**
 * A BlockingMessage is sent by a client which is blocked until it receives an answer from the server
 * The answer has to be the same class
 */
public abstract class BlockingMessage extends Message {
    @Override
    public synchronized void onClientReceive(Connector client) {
        client.notify();
    }
}
