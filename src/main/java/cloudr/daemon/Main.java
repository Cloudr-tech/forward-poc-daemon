package cloudr.daemon;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        if (!start()) {
            System.out.println("Exit now");
            return ;
        }
        new Server(4242).run();
    }

    private static boolean start() {

        InetAddress localhost = null;
        APIObject object;
        APIResponse resp;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (localhost != null) {
            resp = APIService.put((object = new APIObject(localhost.getHostName(), localhost.getHostAddress(), 30000000000)));
        } else {
            System.out.println("Can't find your hostname");
            return false;
        }
        if (resp.getStatus() == true) {
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run() {
                    resp = Retrofit().patch(object);
                    if (resp.getStatus() == false) {
                        System.out.println("An Error occured");
                        return ;
                    }
                }
            },0,10000);
        } else {
            System.out.println("Daemon already exists");
            return false;
        }
        return true;
    }
}
