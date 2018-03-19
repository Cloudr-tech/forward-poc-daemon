package cloudr.daemon;

import java.net.InetAddress;

public class APIObject {
    private String hostname;
    private String ip;
    private long storageLeft;

    public APIObject(String hostname, String ip, long storageLeft) {
        this.hostname = hostname;
        this.ip = ip;
        this.storageLeft = storageLeft;
    }
}
