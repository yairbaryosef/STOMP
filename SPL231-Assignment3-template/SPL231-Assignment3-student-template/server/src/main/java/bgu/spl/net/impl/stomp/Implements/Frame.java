package bgu.spl.net.impl.stomp.Implements;
import java.util.HashMap;
import bgu.spl.net.impl.stomp.Implements.Event;

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
