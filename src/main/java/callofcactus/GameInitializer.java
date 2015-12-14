package callofcactus;


import callofcactus.map.MapFiles;
import callofcactus.menu.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Teun
 */
public class GameInitializer extends com.badlogic.gdx.Game {

    private callofcactus.IGame game;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void create() {
        int width = Gdx.graphics.getDesktopDisplayMode().width;
        int height = Gdx.graphics.getDesktopDisplayMode().height;
//        int width = 1000;
//        int height = 1000;

        Gdx.graphics.setDisplayMode(width,height, false);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        batch = new SpriteBatch();

        this.setScreen(new MainMenu(this,null));
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.exit();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void createSinglePlayerGame() {
        game = new SinglePlayerGame(MapFiles.MAPS.COMPLICATEDMAP);
    }

    public MultiPlayerGame createNewMultiplayerGame() {
        game = new MultiPlayerGame();
        return (MultiPlayerGame) game;
    }


    public IGame getGame() {
        return game;
    }

}