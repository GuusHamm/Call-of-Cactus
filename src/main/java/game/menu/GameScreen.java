package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import game.*;
import game.role.Soldier;

import java.util.ArrayList;

/**
 * @author Teun
 */
public class GameScreen implements Screen
{
    HumanCharacter player;
    //Movement variables
    boolean wDown = false;
    boolean aDown = false;
    boolean sDown = false;
    boolean dDown = false;
    boolean mouseClick = false;
    private Vector2 size;
    private Game game;
    //private CharSequence healthValue;
    //private CharSequence scoreValue;
    private GameInitializer gameInitializer;
    private int steps=1;
    // HUD variables
    private SpriteBatch hudBatch;
    private BitmapFont font;
    private CharSequence healthText;
    private CharSequence scoreText;
    //Character variables
    private SpriteBatch characterBatch;
    /**
     * InputProcessor for input in this window
     */
    private InputProcessor inputProcessor = new InputProcessor()
    {
        @Override
        public boolean keyDown(int i)
        {
            switch(i){
                case Input.Keys.W:
                    wDown = true;
                    break;
                case Input.Keys.A:
                    aDown = true;
                    break;
                case Input.Keys.S:
                    sDown = true;
                    break;
				case Input.Keys.D:
					dDown = true;
					break;
				case Input.Keys.SPACE:
					mouseClick = true;
					break;
            }
            return false;
        }

        @Override
        public boolean keyUp(int i)
        {
            switch(i){
				case Input.Keys.W:
					wDown = false;
					break;
				case Input.Keys.A:
					aDown = false;
					break;
				case Input.Keys.S:
					sDown = false;
					break;
				case Input.Keys.D:
					dDown = false;
					break;
				case Input.Keys.SPACE:
					mouseClick = false;
					break;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char c)
        {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button)
        {
            mouseClick = true;
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button)
        {
            mouseClick = false;
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

    //AI variables
    private SpriteBatch AIBatch;
    private long lastSpawnTime = 0;
    private int AInumber = 0;
    private int AIAmount = 3;
    private int maxAI = 20;
    private ArrayList<AICharacter> aiCharacters = new ArrayList<>();

    /**
     * Starts the game in a new screen, give gameInitializer object because spriteBatch is used from that object
     * @param gameInitializer : This has a spriteBatch and a camera for use in game
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
        //this.healthValue = "Nothing";
        //this.scoreValue = "Nothing";

        this.game = gameInitializer.getGame();
        this.characterBatch = new SpriteBatch();
        this.AIBatch = new SpriteBatch();
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
        for (int i = 0; i < game.getAllEntities().size(); i++) {
            Entity e = game.getAllEntities().get(i);
            if (e.destroy()) {
                game.removeEntityFromGame(e);
                i--;
            }
        }
    }

    /**
     * Render the game here
     * @param v Deltatime in seconds
     */
    @Override
    public void render(float v)
    {
        //Check whether W,A,S or D are pressed or not
        checkMovementInput();

        SpriteBatch batch = gameInitializer.getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        player = game.getPlayer();

        drawAI();
        drawPlayer();
        ArrayList<Bullet> bullets = new ArrayList<>();
        for(Entity e :game.getMovingEntities())
        {
            if(!( e instanceof HumanCharacter)) {
                drawEntity(e);
            }
            Rectangle r = new Rectangle(e.getLocation().x,e.getLocation().y,e.getSpriteWidth(),e.getSpriteHeight());

            boolean contain = new Rectangle(-20,-20,Gdx.graphics.getWidth()+40,Gdx.graphics.getHeight()+40).contains(r);

            if(e instanceof Bullet &&(!contain))
            {
                bullets.add((Bullet) e);
            }
        }
        System.out.println(bullets.size());
        game.getMovingEntities().removeAll(bullets);
        for(Entity e :game.getNotMovingEntities())
        {
            drawEntity(e);
        }

        batch.begin();
            // TODO Render game
        batch.end();

        drawHud();
        //game.update(v);

        System.out.println("this many object :" +game.getMovingEntities().size());
    }

    /**
     * Executed on resize
     * @param i X when resized
     * @param i1 Y when resized
     */
    @Override
    public void resize(int i, int i1)
    {
        size = new Vector2(i,i1);
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

    private boolean drawHud(){
        try{
            HumanCharacter player = game.getPlayer();
            healthText = "Health: " + player.getHealth();
            scoreText = "Score: " + player.getScore();
            hudBatch.begin();
            font.draw(hudBatch, (healthText), 10, 475);
            font.draw(hudBatch,scoreText,700,475);
            hudBatch.end();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    private boolean drawPlayer(){
        try{
            HumanCharacter player = game.getPlayer();
            Sprite playerSprite = new Sprite(player.getSpriteTexture());
            Vector2 location = player.getLocation();
            playerSprite.setPosition(location.x,location.y);

            float width  = player.getSpriteWidth();
            float height = player.getSpriteHeight();

            playerSprite.setSize(width,height);
            playerSprite.setCenter(player.getLocation().x, player.getLocation().y);

            playerSprite.setSize(width, height);

            playerSprite.setOriginCenter();
            int angle = game.angle(
                    new Vector2(
                            player.getLocation().x  ,
                            (size.y -player.getLocation().y) )

                    , game.getMouse()
            )-90;

            playerSprite.rotate(angle);
			player.setDirection(angle);
            characterBatch.begin();
            playerSprite.draw(characterBatch);
            characterBatch.end();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    private boolean drawEntity(Entity entity){
        try{
            if(entity instanceof Bullet)
            {
                ((Bullet)entity).move();
            }
            Sprite entitySprite = new Sprite(entity.getSpriteTexture());
            Vector2 location = entity.getLocation();
            entitySprite.setPosition(location.x,location.y);

            float width  = entity.getSpriteWidth();
            float height = entity.getSpriteHeight();

            entitySprite.setSize(width, height);
            entitySprite.setCenter(location.x, location.y);

            entitySprite.setOriginCenter();
            if(entity instanceof Bullet) {
                entitySprite.rotate((float)((Bullet)entity).getAngle()-90);
            }
            characterBatch.begin();
            entitySprite.draw(characterBatch);
            characterBatch.end();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void checkMovementInput(){

        if(wDown){
            player.getLocation().add(0, steps * player.getSpeed());
        }
        if(aDown){
            player.getLocation().add(-1 * steps * player.getSpeed() ,0);
        }
        if(sDown){
            player.getLocation().add(0,-1 * steps *player.getSpeed());
        }
        if(dDown){
            player.getLocation().add(steps * player.getSpeed(),0);
        }
		if (mouseClick){
 			player.fireBullet();
		}
    }

    private boolean drawAI() {
        spawnAI();

        try {
            AIBatch.begin();
            for (AICharacter a : aiCharacters) {
                int size = 10;
                for (AICharacter ai : aiCharacters) {
                    if (ai.getLocation().equals(player.getLocation())) {
                        if (a.getLocation().equals(player.getLocation())) {
                             if (size < 25) size++;
                        }
                    }
                }
                a.move();
                Sprite s = new Sprite(a.getSpriteTexture());
                s.setSize(size, size);
                s.setPosition((a.getLocation().x - (size / 2)), (a.getLocation().y - (size / 2)));
                s.draw(AIBatch);
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
        if(TimeUtils.millis() - lastSpawnTime < secondsToMillis(10)) {
            return;
        }

        //TODO Set the name of the texture for AI's instead of "spike.png"
        Texture aiTexture = new Texture(Gdx.files.internal("spike.png"));
        for (int i=0; i < AIAmount; i++) {

            //Create the AI
            AICharacter a = new AICharacter(game, new Vector2((int)(Math.random() * 750), (int)(Math.random() * 400)), ("AI" + AInumber++), new Soldier(), game.getPlayer(), aiTexture, 10,10);

            //Add the AI to the AI-list
            aiCharacters.add(a);
        }
        //The amount of AI's that will spawn next round will increase with 1 if it's not max already
        if (AIAmount < maxAI) {
            AIAmount++;
        }

        //Set the time to lastSpawnTime so you know when you should spawn next time
        lastSpawnTime = TimeUtils.millis();
    }

    private long secondsToMillis(int seconds) {
        return seconds * 1000;
    }

}
