package dbit.zmq.test;

import org.zeromq.*;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class RequestServer 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
    	ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket server = context.socket(ZMQ.REQ);
        String url="ipc://abc";
        server.bind(url);
        server.setReceiveTimeOut(10000);
        
        ZMQ.Socket client = context.socket(ZMQ.REP);
        client.connect(url);
        client.setReceiveTimeOut(10000);
        
        server.send("hello");
//        server.send("hello2"); //未响应前不能再次发送
        
        System.out.print("clientReceive: ");
        System.out.println(new String(client.recv(0)));
        client.send("world");
        
        System.out.print("serverReceive: ");
        System.out.println(new String(server.recv(0)));
        
    }
}
