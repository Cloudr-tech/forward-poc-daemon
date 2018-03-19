package cloudr.daemon;

import cloudr.daemon.Model.Packet;

import java.util.LinkedList;
import java.util.Queue;

public class InputQueue {
    private static Queue<Packet> queue = new LinkedList<>();

    private static InputQueue ourInstance = new InputQueue();

    public static InputQueue getInstance() {
        return ourInstance;
    }

    private InputQueue() {
    }

    public void add(Packet msg) { queue.add(msg); }

    public Packet poll(String packet) { return queue.poll(); }
}
