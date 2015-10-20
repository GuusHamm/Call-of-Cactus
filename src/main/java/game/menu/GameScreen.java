package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import game.AICharacter;
import game.Game;
import game.GameInitializer;
import game.role.Role;
import game.role.Soldier;

import java.util.ArrayList;

/**
 * @author Teun
 */
public class GameScreen implements Screen
{

    private Game game;

    private GameInitializer gameInitializer;

    // HUD variables
    private SpriteBatch hudBatch;
    private SpriteBatch AIBatch;
    private BitmapFont font;
    private CharSequence healthText;
    private CharSequence scoreText;
    //AI variables
    private long lastSpawnTime = 0;
    private int AInumber = 0;
    private int AIAmount = 3;
    private int maxAI = 20;
    private boolean spawning = false;
    private ArrayList<AICharacter> aiCharacters = new ArrayList<AICharacter>();

    //Voor testen:
    private int locationValue = 0;

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

        this.AIBatch = new SpriteBatch();

        this.game = gameInitializer.getGame();
//        this.testTexture = new Texture(Gdx.files.internal("player.png"));

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

        drawAI();

        batch.begin();
            // TODO Render game
        //For Each AICharacter in aiCharacters draw the AI
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

    private boolean drawAI() {
        spawnAI();

        try {
            AIBatch.begin();
            Texture t = new Texture(Gdx.files.internal("small_ball2.png"));
            for (AICharacter a : aiCharacters) {
                AIBatch.draw(t, a.getLocation().x, a.getLocation().y);
            }
            AIBatch.end();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    private void spawnAI() {

        //Check if the last time you called this method was long enough to call it again.
        //You can change the rate at which the waves spawn by altering the parameter in secondsToMillis
        if(TimeUtils.millis() - lastSpawnTime < secondsToMillis(1)) {
            return;
        }
        if (spawning) {
            return;
        }
        spawning = true;
        //public AICharacter(Game game, Vector2 spawnLocation, String name, Role role, HumanCharacter player,Texture spriteTexture)
        Role soldier = new Soldier();
        //TODO Set the name of the texture for AI's instead of "spike.png"
        Texture aiTexture = new Texture(Gdx.files.internal("small_ball2.png"));
        for (int i=0; i < AIAmount; i++) {
            AICharacter a = new AICharacter(game, new Vector2((int)(Math.random() * 750), (int)(Math.random() * 400)), ("AI" + AInumber++), soldier, game.getPlayer(), aiTexture);
            //Add the AI to the AI-list
            aiCharacters.add(a);
            locationValue = locationValue + 20;
        }
        //The amount of AI's that will spawn next round will increase with 1 if it's not max already
        if (AIAmount < maxAI) {
            AIAmount++;
        }

        //Set the time to lastSpawnTime so you know when you should spawn next time
        lastSpawnTime = TimeUtils.millis();
        spawning = false;
    }

    private long secondsToMillis(int seconds) {
        return seconds * 1000;

    }
}
