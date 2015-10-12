package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.menu.MainMenu;

/**
 * @author Teun
 */
public class GameInitializer extends Game
{

    private game.Game game;
    private Screen gameScreen;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public GameInitializer() {
        super();
    }

    public void create() {
        // TODO Game creation

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        this.setScreen(new MainMenu(this));
    }


}
