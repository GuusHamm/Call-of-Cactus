package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.Game;
import game.GameInitializer;

/**
 * @author Teun
 */
public class GameScreen implements Screen
{

    private Game game;

    private GameInitializer gameInitializer;

    // HUD variables
    private SpriteBatch hudBatch;
    private BitmapFont font;
    private CharSequence healthText;
    private CharSequence scoreText;

    /**
     * Starts the game in a new screen, give gameInitializer object because spriteBatch is used from that object
     * @param gameInitializer
     */
    public GameScreen(GameInitializer gameInitializer) {
        // TODO Create game shizzle over here
        System.out.println("GameScreen constructor called");
        this.gameInitializer = gameInitializer;

        // HUD initialization
        this.hudBatch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        this.healthText = "Health: ";
        this.scoreText = "Score: ";



        // Input Processor remains in this class to have access to objects
        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Is executed when game window is shown from being hidden
     */
    @Override
    public void show()
    {

    }

    /**
     * Render the game here
     * @param v Deltatime in seconds
     */
    @Override
    public void render(float v)
    {
        SpriteBatch batch = gameInitializer.getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawHud();


        batch.begin();
            // TODO Render game
            game.draw(batch);
        batch.end();

        game.update(v);
    }

    /**
     * Executed on resize
     * @param i X when resized
     * @param i1 Y when resized
     */
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

    /**
     * InputProcessor for input in this window
     */
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

    private boolean drawHud(){
        try{
            hudBatch.begin();
            font.draw(hudBatch, healthText, 10, 475);
            font.draw(hudBatch,scoreText,700,475);
            hudBatch.end();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
