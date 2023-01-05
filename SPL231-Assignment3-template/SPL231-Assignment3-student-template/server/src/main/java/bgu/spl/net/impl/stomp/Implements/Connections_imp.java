package bgu.spl.net.impl.stomp.Implements;


import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;



import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

public class Connections_imp<T> implements Connections<T>{
     //map between user id to connection id
    public HashMap<Integer,HashMap<String,Integer>> get_All_CLIENT_TOPICS;

     //map between connection id to connection handler
    public HashMap<Integer,ConnectionHandler> maping_connectionId_to_socket;
     
     //map between subid to tpic
     public HashMap<Integer,String> maping_between_subsciptionid_to_topic;

     


     //counters for connectionid and subscriptionid
     public int connectionId=0;
     public int subscriptionid=0;
     public int count_Messages=0;

     //Game events
     public HashMap<String,ArrayList<Integer>> games_channel_to_ConnectionId;
   public Connections_imp(){
    maping_connectionId_to_socket=new HashMap<>();
    get_All_CLIENT_TOPICS=new HashMap<>();
    games_channel_to_ConnectionId=new HashMap<>();
    maping_between_subsciptionid_to_topic=new HashMap<>();
   
}
   
    @Override
    public boolean send(int connectionId, T msg) {
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
    public void send(String channel,T msg) {
        // TODO Auto-generated method stub

        //get the clients that subscirbe to the channel and send them the msg
        for(Integer connectionId:games_channel_to_ConnectionId.get(channel)){
          send(connectionId,msg);
        }
       
    }

    @Override
    public void disconnect(int connectionId) {
        // TODO Auto-generated method stub
        maping_connectionId_to_socket.remove(connectionId);
        
        //remove client access to the server
        
    }

    
    
}