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
    private Texture humanTexture;

    public TestGame()
    {
        super();
    }

    @Override
    public void create()
    {
        game = new game.Game(1, 1, false, 100);
        bulletTexture = new Texture(Gdx.files.internal("spike.png"));
        humanTexture = new Texture(Gdx.files.internal("player.png"));
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
        return humanTexture;
    }
}
