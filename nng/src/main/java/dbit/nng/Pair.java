package dbit.nng;

import nanomsg.exceptions.IOException;
import nanomsg.pair.PairSocket;

public class Pair {
    private static String url = "tcp://127.0.0.1:7789";

    public static void main(String[] args) {
        node0();
        node1();
    }

    private static void node0() {
        PairSocket socket = new PairSocket();
        socket.connect(url);
        send(socket);
        recv(socket, "node0");
    }

    private static void node1() {
        PairSocket socket = new PairSocket();
        socket.bind(url);
        send(socket);
        recv(socket, "node1");
    }

    private static void recv(final PairSocket socket, final String nodeName) {
        socket.setRecvTimeout(2000);   // 设置执行recv的超时时间
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        System.out.println(nodeName + ":" + socket.recvString());  // 阻塞socket，直到超时或者有响应
                        Thread.sleep(1000);
                    } catch (IOException e) {       // 忽略超时
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static void send(final PairSocket socket) {
        socket.setSendTimeout(1100);        // 设置执行send的超时时间
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        socket.send("hello");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}