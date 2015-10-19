package testClasses;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Teun on 19-10-2015.
 */
public class TestGameInitializer
{

    private TestGame game;

    public TestGameInitializer() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "TestGameInitializer";
        config.width = 800;
        config.height = 480;
        game = new TestGame();
        new LwjglApplication(game, config);
    }

    public TestGame getGame()
    {
        return game;
    }
}
