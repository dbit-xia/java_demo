package dbit.nng;

import nanomsg.reqrep.RepSocket;

public class EchoServer {
    public static void main(String[] args) {
        RepSocket sock = new RepSocket();
        sock.bind("ipc:///tmp/sock");

        while (true) {
//            byte[] receivedData = sock.recvBytes();
//            sock.send(receivedData);
        }

//        sock.close();
    }
}