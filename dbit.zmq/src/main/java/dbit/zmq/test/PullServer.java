package dbit.zmq.test;

import org.zeromq.*;
import org.zeromq.ZMQ.Event;
import org.zeromq.ZMQ.Socket;

/**
 * Hello world!
 *
 */
public class PullServer 
{
    public static void main( String[] args )
    {
//      System.out.println( "Hello World!" );
    	Event event;
    	ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket server = context.socket(ZMQ.PULL);
        
        //监控Server事件
        String serverMonitorUrl="inproc://monitor000";
        server.monitor(serverMonitorUrl, ZMQ.EVENT_ALL);
        Socket serverMonitor = context.socket(ZMQ.PAIR);
        serverMonitor.connect(serverMonitorUrl);
        serverMonitor.setReceiveTimeOut(10000);
        
        String url="ipc://abc";
        server.bind(url);
        server.setReceiveTimeOut(10000);
        event=Event.recv(serverMonitor);
        System.out.println("serverMonitorReceive: "+event.getEvent());
        
        ZMQ.Socket client = context.socket(ZMQ.PUSH);
        
        //监控Client事件
        String clientMonitorUrl="inproc://monitor001";
        client.monitor(clientMonitorUrl, ZMQ.EVENT_ALL);
        Socket clientMonitor = context.socket(ZMQ.PAIR);
        clientMonitor.connect(clientMonitorUrl);
        clientMonitor.setReceiveTimeOut(10000);
        
        client.connect(url);
        event=Event.recv(clientMonitor);
        System.out.println("clientMonitorReceive: "+event.getEvent());
        
        client.send("hello");
        client.send("hello2");
        
        System.out.print("serverReceive: ");
        for (int i=0;i<2;i++) {
        	System.out.println(new String(server.recv(0)));
        }
        
        client.close();
        event=Event.recv(clientMonitor);
        System.out.println("clientMonitorReceive: "+event.getEvent());
        
        server.close();
        event=Event.recv(serverMonitor);
        System.out.println("serverMonitorReceive: "+event.getEvent());
    }
}
