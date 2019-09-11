package dbit.zmq.test;

import org.zeromq.*;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class ResponseServer 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
    	ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket server = context.socket(ZMQ.REP);
        String url="ipc://abc";
        server.bind(url);
        server.setReceiveTimeOut(10000);
        
        ZMQ.Socket client = context.socket(ZMQ.REQ);
        client.connect(url);
        client.setReceiveTimeOut(10000);
        
        client.send("hello");
//      client.send("hello2"); //未响应前不能再次发送
        
        System.out.print("serverReceive: ");
        System.out.println(new String(server.recv(0)));
        server.send("world");
        
        System.out.print("clientReceive: ");
        System.out.println(new String(client.recv(0)));
        
    }
}
