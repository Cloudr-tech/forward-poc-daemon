package cloudr.daemon;

import cloudr.daemon.Model.Packet;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class File extends Thread {
    private Packet packet;
    public void run() {
        try {
            packet = InputQueue.getInstance().poll();
            if (packet.getType().equals("upload")) {

            }
        } catch () {

        }
    }

    public void write(String fileName, byte[] buffer) {
        try {
            FileOutputStream file = new FileOutputStream("./storage/" + fileName, true);
            file.write(buffer);
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] read(String fileName) {
        try {
            java.io.File file = new java.io.File(fileName);
            long fileSize = 0;
            if(file.exists()) {
                fileSize = file.length();
            }
            FileReader reader = new FileReader(fileName);
            char[] buffer = new char[(int)fileSize];
            reader.read(buffer);
            reader.close();
            return Charset.defaultCharset().encode(CharBuffer.wrap(buffer)).array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
