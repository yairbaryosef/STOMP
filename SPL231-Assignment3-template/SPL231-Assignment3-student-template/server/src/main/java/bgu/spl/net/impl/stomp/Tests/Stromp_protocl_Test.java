package bgu.spl.net.impl.stomp.Tests;
import bgu.spl.net.impl.stomp.Implements.Frame;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.constants;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.Game_Event;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol;

import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.Event;
public class Stromp_protocl_Test{
     public static void main(String[] args) {
       test_frame();
     }

     public static void test_frame(){
        Stomp_Protocol<String> stom=new Stomp_Protocol<String>();
        Frame frame=new Frame();
        frame.command=constants.send;
        frame.headers.put("a", "b");
        frame.headers.put("b", "c");
        frame.body="abc\nbac\n";
        System.out.println(frame.toString());
        String[] message=frame.toString().split("\n");
        System.out.println(new Frame(message).toString());
        String recipt=frame.reciept(0);
         message=recipt.split("\n");
        System.out.println(new Frame(message).toString());

     }
}