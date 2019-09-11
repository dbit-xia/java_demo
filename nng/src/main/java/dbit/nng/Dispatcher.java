package dbit.nng;

import nanomsg.pipeline.PushSocket;
import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    public static void main(String[] args) {
        PushSocket sock = new PushSocket();
        sock.bind("tcp://*:6789");

        List<String> people = new ArrayList<String>();
        people.add("Foo");
        people.add("Bar");
        people.add("Baz");

        for(int i=0; i<people.size(); ++i) {
            sock.send(people.get(i));
        }

        sock.close();
    }
}