package dbit.zmq.test;

import org.zeromq.*;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class PushServer 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
    	ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket server = context.socket(ZMQ.PUSH);
        String url="ipc://abc";
        server.bind(url);
        
        ZMQ.Socket client = context.socket(ZMQ.PULL);
        client.connect(url);
        client.setReceiveTimeOut(0);
        
        server.send("hello");
        server.send("hello2");
        
        System.out.print("clientReceive: ");
        for (int i=0;i<2;i++) {
        	System.out.println(new String(client.recv(0)));
        }
    }
}
