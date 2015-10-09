import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.Game;

/**
 * @author Teun
 */
public class GameInitializer implements ApplicationListener {

    private game.Game game;
    private Screen gameScreen;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture testImage;

    public void create() {
        // TODO Check constructor plz
        game = new Game(1, 1, false, 100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        testImage = new Texture(Gdx.files.internal("droplet.png"));
    }

    public void resize(int i, int i1) {

    }

    public void render() {
        // TODO Render game itself
    }

    public void pause() {
        // TODO Stop game ticks
    }

    public void resume() {
        // TODO Resume game ticks
    }

    public void dispose() {
        // TODO Exit game code
    }
}
