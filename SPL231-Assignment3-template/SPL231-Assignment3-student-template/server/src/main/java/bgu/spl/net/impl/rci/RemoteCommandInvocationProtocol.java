package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.stomp.Implements.Connections_imp;

import java.io.Serializable;

public class RemoteCommandInvocationProtocol<T> implements MessagingProtocol<Serializable> {

    private T arg;

    public RemoteCommandInvocationProtocol(T arg) {
        this.arg = arg;
    }

    @Override
    public void process(Serializable msg) {
   //   return "((Command) msg).execute(arg)";
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
    @Override
    public void setConnections(Connections_imp<Serializable> connections) {
        // TODO Auto-generated method stub
        
    }

}
