package bgu.spl.net.impl.stomp.Implements;


import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

public class Connections_imp<T> implements Connections{
     //map between user id to connection id
     HashMap<Integer,Integer> maping_userId_to_connectionId;
     //map between connection id to connection handler
     HashMap<Integer,ConnectionHandler> maping_connectionId_to_socket;
     //map between topic to all subscriptions
     HashMap<String,ArrayList<Integer>> maping_between_topics_to_subscrip;
     HashMap<Integer,String> maping_between_subsciptionid_to_topic;
   public Connections_imp(){
    maping_connectionId_to_socket=new HashMap<>();
    maping_userId_to_connectionId=new HashMap<>();
    maping_between_topics_to_subscrip=new HashMap<>();
    maping_between_subsciptionid_to_topic=new HashMap<>();
}

    @Override
    public boolean send(int connectionId, Object msg) {
        // TODO Auto-generated method stub
         try{
            maping_connectionId_to_socket.get(connectionId).send(msg);
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
        maping_connectionId_to_socket.remove(connectionId);
        
        //remove client access to the server
        
    }

    
    
}