package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.GameInitializer;

/**
 * Created by Teun on 12-10-2015.
 */
public class GameScreen implements Screen
{

    private GameInitializer gameInitializer;

    public GameScreen(GameInitializer gameInitializer) {
        // TODO Create game shizzle over here
        this.gameInitializer = gameInitializer;

        // Input Processor remains in this class to have access to objects
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float v)
    {
        SpriteBatch batch = gameInitializer.getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
            // TODO Render game
        batch.end();
    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

    private InputProcessor inputProcessor = new InputProcessor()
    {
        @Override
        public boolean keyDown(int i)
        {
            return false;
        }

        @Override
        public boolean keyUp(int i)
        {
            return false;
        }

        @Override
        public boolean keyTyped(char c)
        {
            return false;
        }

        @Override
        public boolean touchDown(int i, int i1, int i2, int i3)
        {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3)
        {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2)
        {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1)
        {
            return false;
        }

        @Override
        public boolean scrolled(int i)
        {
            return false;
        }
    };
}
