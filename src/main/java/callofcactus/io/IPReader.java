package callofcactus.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Teun on 8-12-2015.
 */
public class IPReader {

    private String ip;

    public IPReader readIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            this.ip = in.readLine();
        } catch (IOException e) {
            Logger.getInstance().logEvent(e.getMessage(), Logger.TypeEnum.Info, getClass());
            e.printStackTrace();
        }
        return this;
    }

    public String getIp() {
        return ip;
    }

}
