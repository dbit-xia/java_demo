package dbit.nng;

import nanomsg.pipeline.PullSocket;

public class Greeter {
    public static void main(String[] args) {
        PullSocket sock = new PullSocket();
        sock.connect("tcp://localhost:6789");

        for (int i=0; i<3; i++) {
            System.out.println("Hello " + sock.recvString());
        }

        sock.close();
    }
}