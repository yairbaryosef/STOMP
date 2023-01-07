package bgu.spl.net.impl.stomp.Implements;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.stomp.Implements.Stomp_Protocol.Frame;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class STOMP_encoder_decoder implements MessageEncoderDecoder<String>{
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    @Override
    public String decodeNextByte(byte nextByte) {
        // TODO Auto-generated method stub
        if ((nextByte == '@')&&(bytes[len-1]=='^')) {
            pushByte(nextByte);
            return popString();
        }

        pushByte(nextByte);
        return null; //not a line yet
        //return byte's value 

    }

    @Override
    public byte[] encode(String message) {
        // TODO Auto-generated method stub
        return message.getBytes();
        
    }
    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }
    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }
}
