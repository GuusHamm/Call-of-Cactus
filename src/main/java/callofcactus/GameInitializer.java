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
    public static GameInitializer getInstance() {
        if(instance==null){
            instance = new GameInitializer();
        }
        return instance;
    }

    private static GameInitializer instance;

    @Override
    public void create() {
        int width = 1920;
        int height = 1080;
        int width2 = 1366;
        int height2 = 768;
//        int width = 1000;
//        int height = 1000;

        Gdx.graphics.setDisplayMode(width2, height2, false);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width2, height2);

        batch = new SpriteBatch();

        this.setScreen(new MainMenu(this,null));
    }

    @Override
    public void dispose() {
        screen.dispose();
        super.dispose();
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