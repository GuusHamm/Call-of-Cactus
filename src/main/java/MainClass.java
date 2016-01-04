import callofcactus.GameInitializer;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainClass {
    public static final String NAME = "Call of Cactus: Desert Warfare";

    public static void main(String[] args) throws UnknownHostException {

        System.out.println(InetAddress.getLocalHost().getHostAddress());

        System.out.println("Starting Call Of Cactus...");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = MainClass.NAME;
        config.fullscreen = false;

        new LwjglApplication(new GameInitializer(), config);

    }
}
