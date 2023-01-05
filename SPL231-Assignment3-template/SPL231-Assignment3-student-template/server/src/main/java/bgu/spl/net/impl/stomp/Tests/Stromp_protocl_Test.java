package bgu.spl.net.impl.stomp.Tests;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.Frame;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.constants;

import java.util.HashMap;

import bgu.spl.net.impl.stomp.Implements.Connections_imp;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol;


public class Stromp_protocl_Test{
  static Stomp_Protocol stom;
  static Stomp_Protocol.Frame frame;
  static Connections_imp<String> connections;

     public static void main(String[] args) {
      connections=new Connections_imp();
      stom=new Stomp_Protocol();
      stom.connections=connections;
      frame=stom.gFrame();
      
      // test_frame();

      // test_Connect();

       //test_Error();

       //tets_Subscribe();
       tets_Send();
     }

     public static void test_frame(){
       
        frame.command=constants.send;
        frame.headers.put("a", "b");
        frame.headers.put("b", "c");
        frame.body="abc\nbac\n";
        System.out.println(frame.toString());
        String[] message=frame.toString().split("\n");
        System.out.println();
        System.out.println(stom.string_array_frame(message).toString());
        String recipt=stom.create_reciept(0);
         message=recipt.split("\n");
         System.out.println();
        System.out.println(stom.string_array_frame(message).toString());

     }
    
     public static void test_Connect(){
      frame.command=constants.connect;
     
        stom.process(frame.toString());
     }
     public static void test_Error(){
      System.out.println(stom.create_Error(frame,"error"));
     }
     
     public static void tets_Subscribe(){
      frame.command=constants.subscribe;
       frame.headers.put("id", "0");
       frame.headers.put("destination","/a_b");
        stom.process(frame.toString());
        stom.process(frame.toString());
     }
      
     public static void tets_Send(){
        tets_Subscribe();
         
        frame=stom.gFrame();
        frame.command=constants.send;
      
        frame.headers.put("destination", "/a_b");

        stom.process(frame.toString());
        stom.process(frame.toString());

     }
    }