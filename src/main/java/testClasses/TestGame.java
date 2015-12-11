package testClasses;


import callofcactus.SinglePlayerGame;
import callofcactus.map.MapFiles;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Teun on 19-10-2015.
 */
public class TestGame extends Game {
    private callofcactus.IGame game;

    private Texture bulletTexture;
    private Texture playerTexture;
    private Texture wallTexture;

    public TestGame() {
        super();
    }

    @Override
    public void create() {

        game = new SinglePlayerGame(MapFiles.MAPS.COMPLICATEDMAP);
        bulletTexture = new Texture(Gdx.files.internal("spike.png"));
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        wallTexture = new Texture(Gdx.files.internal("wall.png"));

        game.notify();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public callofcactus.IGame getGame() {
        return game;
    }

    public Texture getBulletTexture() {
        return bulletTexture;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Texture getWallTexture() {
        return wallTexture;
    }
}
