package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.events.Event;;

public class Stomp_Protocol implements StompMessagingProtocol<String>,MessagingProtocol<String>{
  public Connections_imp<String> connections;
  public int connectionId=-1;
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
        unSubscribe(frame);
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
        body="";
        
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
    connections.send(connectionId, create_reciept(connectionId));

    //TODO: UPDATE MAPS

    connections.disconnect(id);
    return null;
}
//unSubscribbe
public String unSubscribe(Frame frame) {

    // GET SUBSCRIPTION ID 
     int subscriptionId=Integer.valueOf(frame.headers.get("id"));

     //CHECK IF THE SUBSCRIPTION ID EXIST
     boolean isExist=false;
     if(connections.get_All_CLIENT_TOPICS.get(connectionId)==null){
        String error="UNSUBSCRIBE TO UNSIGNED TOPIC";
        connections.send(connectionId, create_Error(frame,error));
          return null;
     }
     
     for(String topic:connections.get_All_CLIENT_TOPICS.get(connectionId).keySet()){
                if(connections.get_All_CLIENT_TOPICS.get(connectionId).get(topic)==subscriptionId){
                 //remove client from topic
                  connections.games_channel_to_ConnectionId.get(topic).remove(connectionId);
                  
                  //remove subscriptionID
                  connections.get_All_CLIENT_TOPICS.get(connectionId).remove(topic);

                  isExist=true;
                }
     }
     if(isExist){
        //send receipt
        connections.send(connectionId, create_reciept(connectionId));
     }
     else{
        //send error
        String error="UNSUBSCRIBE TO UNSIGNED TOPIC";
        connections.send(connectionId, create_Error(frame,error));
     }
    return null;
}
//send
public String Send(Frame frame) {
    //get game name
   String topic= frame.headers.get("destination");
   //send event to all clients
   for(Integer connectioId:connections.games_channel_to_ConnectionId.get(topic)){
      connections.send(connectionId, create_Message(frame,connectionId));
      System.out.println(create_Message(frame,connectionId));
   }
   return null;
}

//Meesage frame
public String create_Message(Frame client_frame,int connection){
    Frame frame=new Frame();

    //command
    frame.command="MESSAGE";

    //headers
    int subscription=connections.get_All_CLIENT_TOPICS.get(connection).get(client_frame.headers.get("destination"));
    frame.headers.put("subscription", String.valueOf(subscription));
    frame.headers.put("message-id", String.valueOf(connections.count_Messages));
    connections.count_Messages++;
    frame.headers.put("destination", client_frame.headers.get("destination"));

    frame.body=client_frame.body;
    
    return frame.toString();
}
//connect
public String Connect(Frame msg){
    Frame frame=new Frame();
    frame.command=constants.connected;
    frame.headers.put(constants.version, "1.2");
    connections.send(connectionId, frame.toString());
    System.out.println(frame.toString());
    return frame.toString();
   
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
    HashMap<String,Integer> topics;
    
    topics=connections.get_All_CLIENT_TOPICS.get(connectionId);
    
    if(topics==null){
        topics=new HashMap<>();
    }
    
    if(is_legal_Subscribe_Frame(frame)){
    String game_name=frame.headers.get("destination");
     if(!connections.games_channel_to_ConnectionId.containsKey(game_name)){//if game DOESN'T EXIST
           connections.games_channel_to_ConnectionId.put(game_name,new ArrayList<>());//insert game
           
    }
   
   //TODO: add receipt to client frame 
    int subscriptionid=Integer.valueOf(frame.headers.get("id"));
    //create new topic to the client
   
    topics.put( game_name,subscriptionid);
    connections.get_All_CLIENT_TOPICS.put(connectionId, topics);
    connections.games_channel_to_ConnectionId.get(game_name).add(connectionId);
    connections.send(connectionId, create_reciept(connectionId));//send to client receipt
    System.out.println(connections.games_channel_to_ConnectionId);
}
else{
    //return error frame
     String errorMessage="client already subscribed to this topic";
    connections.send(connectionId, create_Error(frame,errorMessage));
    System.out.println(create_Error(frame,errorMessage));
    
}System.out.println(connections.get_All_CLIENT_TOPICS);
System.out.println(connections.games_channel_to_ConnectionId);

    return null;


}
//return error string
public String create_Error(Frame frame,String errorMessage){
    /*
     * RECEIPT
     * receipt-id:id
     * 
     * ^@
     */
   Frame error=new Frame();
   //command 
   error.command="ERROR";
   //headers
   error.headers.put("receipt-id", String.valueOf(connectionId));
   error.headers.put("message", "malformed frame received");
   //body
   error.body="The message :\n-----\n"+frame.toString()+"\n-----\n"+errorMessage+"\n";
   return error.toString();


}
//check if the frame is valid
private boolean is_legal_Subscribe_Frame(Frame frame) {
   
    if((connections.get_All_CLIENT_TOPICS.get(connectionId)!=null)&&connections.get_All_CLIENT_TOPICS.get(connectionId).containsKey(frame.headers.get("destination"))){
            return false;
    }
    return true;
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

  public Frame gFrame(){
    return new Frame();
  } 
  public Frame string_array_frame(String[] array){
    return new Frame(array);
  }
}