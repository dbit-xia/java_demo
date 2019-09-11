package dbit.nng;

import nanomsg.exceptions.IOException;
import nanomsg.pipeline.PullSocket;
import nanomsg.pipeline.PushSocket;

public class Pipeline {
    private static String url = "tcp://127.0.0.1:7789";

    public static void main(String[] args) {
        node1();
        node0();
    }

    private static void node1() {
        final PullSocket socket = new PullSocket();
        socket.bind(url);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        System.out.println(socket.recvString());  // 阻塞socket，直到超时或者有响应
                        Thread.sleep(1000);
                    } catch (IOException e) {       // 忽略超时
                        // e.printStackTrace();
                    } catch (InterruptedException e) {
                        // e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static void node0() {
        final PushSocket socket = new PushSocket();
        socket.connect(url);
        socket.send("hello");
    }
}
