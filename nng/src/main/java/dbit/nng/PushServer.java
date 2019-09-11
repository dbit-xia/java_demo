package dbit.nng;

import nanomsg.exceptions.IOException;
import nanomsg.pipeline.PullSocket;
import nanomsg.pipeline.PushSocket;

public class PushServer {
    private static String url = "tcp://127.0.0.1:7789";

    public static void main(String[] args) {
        final PushSocket socket = new PushSocket();
        socket.bind(url);
        socket.send("hello");
    }
}
