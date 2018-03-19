package cloudr.daemon.Model;

public class Packet {
    private String type;
    private String name;
    private byte[] data;

    public Packet(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Packet(String type, String name, byte[] data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }

    public String getType() { return this.type; }

    public String getName() { return this.name; }

    public byte[] getData() { return this.data; }
}
