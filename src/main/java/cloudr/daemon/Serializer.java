package cloudr.daemon;

import cloudr.daemon.Model.Packet;
import com.google.gson.Gson;

public class Serializer {
    private static Gson serializer = new Gson();

    private static Serializer ourInstance = new Serializer();

    public static Serializer getInstance() {
        return ourInstance;
    }

    private Serializer() {
    }

    public String serialize(Packet msg) {
        return serializer.toJson(msg);
    }

    public Packet deserialize(String packet) {
        return serializer.fromJson(packet, Packet.class);
    }
}
