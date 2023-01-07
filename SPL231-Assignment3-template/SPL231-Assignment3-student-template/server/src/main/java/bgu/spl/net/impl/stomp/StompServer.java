package bgu.spl.net.impl.stomp;

import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;

import org.w3c.dom.events.Event;

import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.impl.stomp.Implements.STOMP_encoder_decoder;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol;

public class StompServer {

    public static void main(String[] args) {
        // TODO: implement this
        if(args[1].equals("tpc")){
            Server.threadPerClient(
                7777, //port
                () -> new Stomp_Protocol(), //protocol factory
                STOMP_encoder_decoder::new //message encoder decoder factory
        ).serve();
        }
        else{
            Reactor<String> reactor=new Reactor<String>(0,
                7777, //port
                () -> new Stomp_Protocol(), //protocol factory
                STOMP_encoder_decoder::new //message encoder decoder factory
          );
          reactor.serve();
        }
    }
}
