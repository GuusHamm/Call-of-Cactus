package testClasses;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Teun on 19-10-2015.
 */
public class TestGame extends Game
{
    private game.Game game;

    private Texture bulletTexture;
    private Texture playerTexture;
    private Texture wallTexture;

    public TestGame()
    {
        super();
    }

    @Override
    public void create()
    {
        game = new game.Game();
        bulletTexture = new Texture(Gdx.files.internal("spike.png"));
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        wallTexture = new Texture(Gdx.files.internal("wall.png"));

        game.notify();
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    public game.Game getGame()
    {
        return game;
    }

    public Texture getBulletTexture()
    {
        return bulletTexture;
    }

    public Texture getPlayerTexture()
    {
        return playerTexture;
    }

    public Texture getWallTexture()
    {
        return wallTexture;
    }
}
