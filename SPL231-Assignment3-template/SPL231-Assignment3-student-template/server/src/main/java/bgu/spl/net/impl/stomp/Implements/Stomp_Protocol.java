package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import java.util.HashMap;;

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
       Subscribe();
    }
    else if(frame.command.equals(constants.send)){
        Send();
     }
     else if(frame.command.equals(constants.unSubscribe)){
        unSubscribe();
     }
     else if(frame.command.equals(constants.disconnect)){
       Disconnect();
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
        //init headers
        int i=1;
        while(!message[i].equals("\n")){
            String[] line=message[i].split(":");
            headers.put(line[0], line[1]);
        }
        //skip this line
        i++;
        //init body
        body="";
        while(!message[i].equals("^@")){
            body=body+message[i];
        }

        
    }

   @Override
   public String toString() {
       // TODO Auto-generated method stub
       String head="";
       for(String key:headers.keySet()){
        head=head+key+":"+headers.get(key)+"\n";
       }
       head=head+"\n\n";
       return command+"\n"+head+body+"\n"+"^@";
   }
    
    
}
public class constants{
    public static final String connect="Connect";
    public static final String connected="Connected";
    public static final String subscribe="Subscribe";
    public static final String disconnect="Disconnect";
    public static final String unSubscribe="UnSubscribe";
    public static final String send="Send";
    public static final String version="version";
    public static final String recipt="RECEIPT";

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
public String Disconnect() {
    return null;
}
//unSubscribbe
public String unSubscribe() {
    return null;
}
//send
public String Send() {
    return null;
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
public String Subscribe(){
    return null;

}

//recipt messsage
public String reciept(int connectionId){
   Frame frame=new Frame();
   frame.command=constants.recipt;

   frame.headers.put("receipt - id",String.valueOf(connectionId));
   return frame.toString();


}
    
    
}