package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.events.Event;;

public class Stomp_Protocol<T> implements StompMessagingProtocol<String>,MessagingProtocol<String>{
  private Connections_imp<String> connections;
@Override
public void start(int connectionId, Connections<String> connections) {
    // TODO Auto-generated method stub

}
@Override
public void process(String message) {
    // TODO Auto-generated method stub

    String[] split=message.split("\n");
    
    Frame frame=new Frame(split);

    if(frame.command.equals(constants.connect)){
      Connect(frame);
    }
    else if(frame.command.equals(constants.subscribe)){
       Subscribe(frame);
    }
    else if(frame.command.equals(constants.send)){
        Send(frame);
     }
     else if(frame.command.equals(constants.unSubscribe)){
        unSubscribe();
     }
     else if(frame.command.equals(constants.disconnect)){
       Disconnect(frame);
     }

     


}
@Override
public boolean shouldTerminate() {
    // TODO Auto-generated method stub
    
    return false;
}

public class Frame{
    public String command;

    public HashMap<String,String> headers;
    
 
    public  String body;
    public Frame(){
        headers=new HashMap<>();
        
    }
    public Frame(String[] message ){
        //init command
        command=message[0];
        headers=new HashMap<>();
        
        //init headers
        int i=1;
      
        while(!message[i].equals("")){

        
            String[] line=message[i].split(":");
            headers.put(line[0], line[1]);
            i++;
           
           
        
       }
        //skip this line
        i++;
        //init body
        body="";
        while(!message[i].equals("^@")){
            System.out.println(message[i]);
            body=body+message[i]+" \n";
            i++;
        }

        
    }

   @Override
   public String toString() {
       // TODO Auto-generated method stub
       String head="";
       for(String key:headers.keySet()){
      head=head+key+":"+headers.get(key)+"\n";
       }
       
       head=head+"";
       String b="";
       if(body!=null){
        b=body;
       }
       return command+"\n"+head+"\n"+b+"^@";
   }
   //recipt
   public String reciept(int connectionId){
    Frame frame=new Frame();
    frame.command=constants.recipt;
 
    frame.headers.put("receipt - id",String.valueOf(connectionId));
    return frame.toString();
 
 
 }
    
    
}

public class constants{
    public static final String connect="CONNECT";
    public static final String connected="CONNECTED";
    public static final String subscribe="SUBSCRIBE";
    public static final String disconnect="DISCONNECT";
    public static final String unSubscribe="UNSUBSCRIBE";
    public static final String send="SEND";
    public static final String version="version";
    public static final String recipt="RECEIPT";
    public static final String receipt_id="receipt-id";

}
   
public Connections_imp<String> getConnections() {
        return connections;
    }
@Override
public void setConnections(Connections_imp<String> connections) {
    // TODO Auto-generated method stub
    this.connections=connections;
}

//disconnect
public String Disconnect(Frame frame) {
    /*
     * DISCONNECT
     * receipt-id:id
     * 
     * ^@
     */
    //get connectionid by the recipt
    int id=Integer.valueOf(frame.headers.get(constants.receipt_id));
    connections.send(id, create_reciept(id));
    connections.disconnect(id);
    return null;
}
//unSubscribbe
public String unSubscribe() {
    return null;
}
//send
public String Send(Frame frame) {
   String game= frame.headers.get("destination");
   
 
   connections.send(game, Message());
   return null;
}

//Meesage frame
public String Message(){
    Frame frame=new Frame();
    return frame.toString();
}
//connect
public String Connect(Frame msg){
    Frame frame=new Frame();
    frame.command=constants.connected;
    frame.headers.put(constants.version, "1.2");
    connections.send(connections.maping_connectionId_to_socket.size(), frame.toString());
    return null;
   
}
//subscribe
public String Subscribe(Frame frame){
     /*
     * SUBSCRIBE
     * destination:topic
     * receipt:?-optinal
     * id:
     * 
     * ^@
     */
   
     if(!connections.games_channel_to_subscriptionsId.containsKey(frame.headers.get("destination"))){//if game DOESN'T EXIST
        //init new game
          
           String game_name=frame.headers.get("destination");

           connections.games_channel_to_subscriptionsId.put(game_name,new ArrayList<>());//insert game
           
         //maping between subscription to connectionid

           int connectionid=Integer.valueOf(frame.headers.get("receipt"));//TODO: add receipt to client frame 
           int subscriptionid=Integer.valueOf(frame.headers.get("id"));
           connections.maping_subscriptionsId_to_connectionId.put(subscriptionid, connectionid);
           connections.games_channel_to_subscriptionsId.get(game_name).add(subscriptionid);
           connections.subscriptionid++;
            

          
       

          connections.send(connectionid, create_reciept(connectionid));//send to client receipt
    }
    else if(connections.games_channel_to_subscriptionsId.get(frame.headers.get("destination")).contains(frame.headers.get("id"))){//if game  EXIST
        //get exist game
        String game_name=frame.headers.get("destination");

           
           
         //maping between subscription to connectionid

           int connectionid=Integer.valueOf(frame.headers.get("receipt"));//TODO: add receipt to client frame 
           connections.maping_subscriptionsId_to_connectionId.put(connections.subscriptionid, connectionid);
           connections.games_channel_to_subscriptionsId.get(game_name).add(connections.subscriptionid);
           connections.subscriptionid++;
            

          
       

          connections.send(connectionid, create_reciept(connectionid));//send to client receipt
    }
    else{

    }
    return null;

}

//recipt messsage
public String create_reciept(int connectionId){
    /*
     * RECEIPT
     * receipt-id:id
     * 
     * ^@
     */
   Frame frame=new Frame();
   frame.command=constants.recipt;

   frame.headers.put("receipt-id",String.valueOf(connectionId));
   return frame.toString();


}

   
}