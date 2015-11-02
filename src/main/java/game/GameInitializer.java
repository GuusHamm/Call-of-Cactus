package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.menu.EndScreen;
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

    @Override
    public void create() {
        // TODO Game creation

        int width = Gdx.graphics.getDesktopDisplayMode().width;
        int height = Gdx.graphics.getDesktopDisplayMode().height;
        Gdx.graphics.setDisplayMode(width, height, true);
        createNewGame();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        batch = new SpriteBatch();

        this.setScreen(new MainMenu(this));
    }

    public void createNewGame() {
        this.game = new game.Game(1, 1, false, 10000);
    }

    @Override
    public void dispose()    {
        super.dispose();
        Gdx.app.exit();
    }

    public OrthographicCamera getCamera()
    {
        return camera;
    }

    public Screen getGameScreen()
    {
        return gameScreen;
    }

    public SpriteBatch getBatch()
    {
        return batch;
    }

    public game.Game getGame()
    {
        return game;
    }
}
