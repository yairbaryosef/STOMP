package bgu.spl.net.api;

import bgu.spl.net.impl.stomp.Implements.Connections_imp;;

public interface MessagingProtocol<T> {
 
    /**
     * process the given message 
     * @param msg the received message
     * @return the response to send or null if no response is expected by the client
     */
    void process(T msg);
 
    /**
     * @return true if the connection should be terminated
     */
    boolean shouldTerminate();

    public void setConnections(Connections_imp<T> connections);
 
}