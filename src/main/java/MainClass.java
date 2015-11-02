import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import game.GameInitializer;

public class MainClass
{
    public static final String NAME = "Call of Cactus: Desert Warfare";

    public static void main(String[] args)
    {
        System.out.println("Starting Call Of Cactus...");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = MainClass.NAME;
        new LwjglApplication(new GameInitializer(), config);
    }
}
