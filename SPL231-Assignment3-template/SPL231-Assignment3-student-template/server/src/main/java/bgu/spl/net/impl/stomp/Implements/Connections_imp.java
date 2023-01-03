package bgu.spl.net.impl.stomp.Implements;


import java.util.List;
import java.util.HashMap;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

public class Connections_imp<T> implements Connections{
    HashMap<Integer,ConnectionHandler> connectionHandlers;
    
Connections_imp(){
    connectionHandlers=new HashMap<>();
}

    @Override
    public boolean send(int connectionId, Object msg) {
        // TODO Auto-generated method stub
         try{
            connectionHandlers.get(connectionId).send(msg);
            return true;
         }
         catch(Exception e)
         {
            
         }
        //get the connection handler by the connection id and send him the msg

        return false;
    }

    @Override
    public void send(String channel, Object msg) {
        // TODO Auto-generated method stub

        //get the clients that subscirbe to the channel and send them the msg
        
    }

    @Override
    public void disconnect(int connectionId) {
        // TODO Auto-generated method stub

        //remove client access to the server
        
    }

    
    
}