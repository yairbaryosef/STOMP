package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.Frame;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

public class STOMP_encoder_decoder implements MessageEncoderDecoder<Serializable>{

    @Override
    public String decodeNextByte(byte nextByte) {
        // TODO Auto-generated method stub

        //return byte's value 

        return null;
    }

    @Override
    public byte[] encode(Serializable message) {
        // TODO Auto-generated method stub
        Stomp_Protocol.Frame frame= new Frame(message.split("\n"));
        return serializeObject(frame);
        return null;
    }
    
}
