package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;
import bgu.spl.net.srv.Connections;
import java.util.HashMap;;

public class Stomp_Protocol<T> implements StompMessagingProtocol<String>,MessagingProtocol<String>{
   //map between user id to connection id
    HashMap<Integer,Integer> maping_userId_to_connectionId;
  //map between connection id to connection handler
  HashMap<Integer,ConnectionHandler> maping_connectionId_to_socket;
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
        frame.Connect();
    }
    else if(frame.command.equals(constants.subscribe)){
       frame.Subscribe();
    }
    else if(frame.command.equals(constants.send)){
        frame.Send();
     }
     else if(frame.command.equals(constants.unSubscribe)){
        frame.unSubscribe();
     }
     else if(frame.command.equals(constants.disconnect)){
        frame.Disconnect();
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
    //disconnect
    public void Disconnect() {
    }
    //unSubscribbe
    public void unSubscribe() {
    }
    //send
    public void Send() {
    }
    //connect
    public void Connect(){
       
    }
    //subscribe
    public void Subscribe(){

    }
    
}
public class constants{
    public static final String connect="Connect";
    public static final String subscribe="Subscribe";
    public static final String disconnect="Disconnect";
    public static final String unSubscribe="UnSubscribe";
    public static final String send="Send";
}
    

    
    
}