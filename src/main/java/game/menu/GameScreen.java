package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Teun
 */
public class GameScreen implements Screen
{
    HumanCharacter player;
    int count=0;
    //Movement variables
    private float walkTime;
    private boolean playerIsMoving = false;
    private boolean wDown = false;
    private boolean aDown = false;
    private boolean sDown = false;
    private boolean dDown = false;
    private boolean mouseClick = false;
    private long lastShot = 0;
    private Vector2 size;
    private Game game;
    private GameInitializer gameInitializer;
    private int steps=1;
    // HUD variables
    private float screenWidth;
    private float screenHeight;
    private SpriteBatch hudBatch;
    private BitmapFont font;
    private CharSequence healthText;
    private CharSequence scoreText;
    //Character variables
    private SpriteBatch characterBatch;
    //AI variables
    private SpriteBatch AIBatch;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;
    //  MAP variables
    private Map map;
    private SpriteBatch mapBatch;
    //Sound
    private Music bgm;

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
                    playerIsMoving = true;

                    break;
                case Input.Keys.A:
                    aDown = true;
                    playerIsMoving = true;
                    break;
                case Input.Keys.S:
                    sDown = true;
                    playerIsMoving = true;
                    break;
				case Input.Keys.D:
					dDown = true;
                    playerIsMoving = true;
					break;
				case Input.Keys.SPACE:
					mouseClick = true;
					break;
                case Input.Keys.ESCAPE:
                    Gdx.app.exit();
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
                    if(!aDown && !sDown && !dDown){
                        playerIsMoving = false;
                    }

					break;
				case Input.Keys.A:
					aDown = false;
                    if(!wDown && !sDown && !dDown){
                        playerIsMoving = false;
                    }
					break;
				case Input.Keys.S:
					sDown = false;
                    if(!aDown && !wDown && !dDown){
                        playerIsMoving = false;
                    }
					break;
				case Input.Keys.D:
					dDown = false;
                    if(!aDown && !sDown && !wDown){
                        playerIsMoving = false;
                    }
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
            game.setMousePositions(i,i1);
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1)
        {
            game.setMousePositions(i,i1);
            return false;
        }

        @Override
        public boolean scrolled(int i)
        {
            return false;
        }
    };

    /**
     * Starts the game in a new screen, give gameInitializer object because spriteBatch is used from that object
     * @param gameInitializer : This has a spriteBatch and a camera for use in game
     */
    public GameScreen(GameInitializer gameInitializer) {
        // TODO Create game shizzle over here
        System.out.println("GameScreen constructor called");
        this.gameInitializer = gameInitializer;

        this.game = gameInitializer.getGame();

        this.map = new Map(this.game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // HUD initialization
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();
        this.hudBatch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        this.healthText = "Health: ";
        this.scoreText = "Score: ";

        this.characterBatch = new SpriteBatch();
        this.AIBatch = new SpriteBatch();


        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer();
        this.mapBatch = new SpriteBatch();

        // Input Processor remains in this class to have access to objects
        Gdx.input.setInputProcessor(inputProcessor);

        // Playing audio
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/coc_battle.mp3"));
        bgm.setVolume(0.15f);
        bgm.setLooping(true);
        bgm.play();
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

//        FileHandle fileHandle2 = Gdx.files.internal("wall.png");
//        Texture t2 = new Texture(fileHandle2);
//
//        game.addEntityToGame(new NotMovingEntity(game,new Vector2(10,10),true,10,false,t2, 50,50));

        this.map = new Map(this.game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FileHandle fileHandle2 = Gdx.files.internal("wall.png");
        Texture t2 = new Texture(fileHandle2);

        game.addEntityToGame(new NotMovingEntity(game,new Vector2(10,10),true,10,false,t2, 50,50));

    }

    /**
     * Render the game here
     * @param v Deltatime in seconds
     */
    @Override
    public void render(float v)
    {
        //Check whether W,A,S or D are pressed or not
        procesMovementInput();
        compareHit();

        SpriteBatch batch = gameInitializer.getBatch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        player = game.getPlayer();

        backgroundRenderer.render(backgroundBatch);
        for(Entity e : game.getNotMovingEntities()){drawRectangle(e);}

        drawAI();
        drawPlayer();
        ArrayList<Bullet> bullets = new ArrayList<>();


//         for(Entity e : game.getAllEntities()){drawRectangle(e);}
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
        game.getMovingEntities().removeAll(bullets);


        drawHud();

        drawMap();


        //Will only play if the player is movingaaaa
        playWalkSound(v);
        drawHud();

        batch.end();

        // TODO Hud drawn twice?
//        drawHud();

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
            String mousePosition = String.format("Mouse: %s}", game.getMouse());
            String playerPosition = String.format("Player: %s}", game.getPlayer().getLocation());
            String angleText = "Angle : " + player.getAngle();
            scoreText = "Score: " + player.getScore();
            hudBatch.begin();
            font.draw(hudBatch, (healthText), 10, screenHeight - 30);
            //font.draw(hudBatch, (playerPosition), 10, 425);
            //font.draw(hudBatch, (mousePosition), 10, 400);
            //font.draw(hudBatch, (angleText), 10, 375);

            font.draw(hudBatch,scoreText,screenWidth - 100,screenHeight - 30);
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
            int angle = game.angle(new Vector2(player.getLocation().x, (size.y - player.getLocation().y)), game.getMouse());
            playerSprite.rotate(angle - 90);
			player.setAngle(angle);

            characterBatch.begin();
            playerSprite.draw(characterBatch);
            font.draw(characterBatch, player.getName(), player.getLocation().x + 25, player.getLocation().y + 25);
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
            entitySprite.setPosition(location.x, location.y);

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

    /**
     * Testing method for drawing hitboxes
     * @param entity Entity to draw rectangle around
     * @return True if draw successfully, false if otherwise
     */
    ShapeRenderer sr;
    private boolean drawRectangle(Entity entity){
        try{

            if(sr==null) {
                sr = new ShapeRenderer();
                sr.setColor(Color.CLEAR);
            }
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(entity.getLocation().x-(entity.getSpriteWidth()/2),entity.getLocation().y-(entity.getSpriteHeight()/2),entity.getSpriteWidth(),entity.getSpriteHeight() );
            sr.end();

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private void procesMovementInput(){

        if(wDown || aDown || sDown || dDown) {

            player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));

            if (wDown) {
                player.move(player.getLocation().add(0, steps * (float) player.getSpeed()));
            }
            if (aDown) {
                player.move(player.getLocation().add(-1 * steps * (float) player.getSpeed(), 0));
            }
            if (sDown) {
                player.move(player.getLocation().add(0, -1 * steps * (float) player.getSpeed()));
            }
            if (dDown) {
                player.move(player.getLocation().add(steps * (float) player.getSpeed(), 0));
            }
        }
		if (mouseClick){
            if (TimeUtils.millis() - lastShot > game.secondsToMillis(player.getFireRate()) / 10) {

                player.fireBullet(new Texture("spike.png"));
                lastShot = TimeUtils.millis();
            }
        }
        }

    private boolean drawAI() {
        game.spawnAI();

        try {
            AIBatch.begin();
            for (MovingEntity a : game.getMovingEntities()) {
                if (a instanceof AICharacter) {
                    int size = 10;
                    a.move(player.getLocation());
                    Sprite s = new Sprite(a.getSpriteTexture());
                    s.setSize(size, size);
                    s.setPosition((a.getLocation().x - (size / 2)), (a.getLocation().y - (size / 2)));
                    s.draw(AIBatch);
                }
            }
            AIBatch.end();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean drawMap()
    {
        //TODO code 'spawnlocations' of the walls / objects on the map.
        try {
            mapBatch.begin();
            List<NotMovingEntity> nME = game.getNotMovingEntities();
            for (NotMovingEntity e : nME) {
                Sprite s = new Sprite(e.getSpriteTexture());
                s.setSize(e.getSpriteWidth(), e.getSpriteHeight());
                s.setPosition(e.getLocation().x, e.getLocation().y );
                s.draw(mapBatch);
            }
            mapBatch.end();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private void compareHit()
    {

        List<Entity> entities = game.getAllEntities();
        List<Entity> toRemoveEntities = new ArrayList<>();

        if(!entities.contains(game.getPlayer()))
        {
            game.addEntityToGame(game.getPlayer());
            System.out.println("Player created");
            System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        }

        //starts a loop of entities that than creates a loop to compare the entity[i] to entity[n]
        //n = i+1 to prevent double checking of entities.
        //Example:
        // entity[1] == entity[2] will be checked
        // entity[2] == entity[1] will not be checked
        for (int i = 0; i < entities.size(); i++)
        {
            //gets the first entity to compare to
            Entity a = entities.get(i);

            for(int n = i+1; n < entities.size(); n++)
            {
                //gets the second entity to compare to
                Entity b = entities.get(n);

                //Checks if the hitbox of entity a overlaps with the hitbox of entity b, for the hitboxes we chose to use rectangles
                if(a.getHitBox().overlaps(b.getHitBox()))
                {

                    if(a instanceof Bullet)
                    {
                        count++;

                        if(b instanceof NotMovingEntity)
                        {
                            System.out.println("duck");
                        }
                        //makes it so your own bullets wont destroy eachother
                        if (b instanceof Bullet) {
                            if (((Bullet) a).getShooter().equals(((Bullet) b).getShooter())) {
                                continue;
                            }
                        }
                        //if b is the shooter of both bullets a and b then continue to the next check.
                        if(b instanceof HumanCharacter && ((Bullet) a).getShooter()==b){
                            continue;
                        }

                        //if the bullet hit something the bullet will disapear by taking damage and the other entity will take
                        //the damage of the bullet.
                        a.takeDamage(1);
                        b.takeDamage(a.getDamage());

                        //Add 1 point to the shooter of the bullet for hitting.

                        if(a instanceof MovingEntity) {

                            System.out.println("1-"+count + "-" + i+ "-" + n);
                            //((HumanCharacter) ((Bullet) a).getShooter()).addScore(1);
                        }

                        //Play hit sound
//                        Sound sound = getRandomHitSound();
//                        sound.play(.3F);

                    }
                    // this does exactly the same as the previous if but with a and b turned around
                    else if(b instanceof Bullet){
                        count++;
                        if(a instanceof AICharacter)
                        {
                            System.out.println("duck");
                        }
                        if (a instanceof Bullet) {
                            if (((Bullet) b).getShooter().equals(((Bullet) a).getShooter())) {
                                continue;
                            }
                        }

                        //Incase the shooter of the bullet is the one the collision is with break.
                        if(a instanceof HumanCharacter && ((Bullet) b).getShooter()==a){
                            continue;}

                        b.takeDamage(1);
                        a.takeDamage(b.getDamage());

                        if(a instanceof MovingEntity) {
                            System.out.println("2-" + count + "-" + i+ "-" + n);
                            //((HumanCharacter) ((Bullet) b).getShooter()).addScore(1);
                        }

                        //Play hit sound
                        Sound sound = getRandomHitSound();
                        sound.play(.3F);

                    }



                    //Check collision between AI and player
                    if(a instanceof HumanCharacter && b instanceof AICharacter)
                    {
                        if (!game.getGodmode()) {
                            a.takeDamage(b.getDamage());
                        }
                        a.takeDamage(b.getDamage());
                        toRemoveEntities.add(b);

                        //Play hit sound
                        Sound ouch = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_playerHit.mp3"));
                        ouch.play(.4F);
                        Sound sound = getRandomHitSound();
                        sound.play(.3F);
                    }
                    //Checks the as the previous if but with a and b turned around
                    else if(b instanceof HumanCharacter && a instanceof AICharacter)
                    {
                        if (!game.getGodmode()) {
                            b.takeDamage(a.getDamage());
                        }
                        b.takeDamage(a.getDamage());
                        toRemoveEntities.add(a);
                    }
                    //checks if a MovingEntity has collided with a NotMovingEntity
                    //if so, the current location will be set to the previous location
                    if(a instanceof NotMovingEntity && ((NotMovingEntity) a).isSolid() && b instanceof MovingEntity)
                    {
                        b.setLocation(b.getLastLocation());
                    }
                    else if(b instanceof NotMovingEntity && ((NotMovingEntity) b).isSolid() && a instanceof MovingEntity)
                    {
                        a.setLocation(a.getLastLocation());
                    }


                    //  Checks if all the "HumanCharacter"s are dead (= End-Game condition for the first iteration of
                    //  the game)
                    //  TODO change end-game condition for iteration(s) 2 (and 3)
                    if (a instanceof HumanCharacter && ((HumanCharacter) a).getHealth() <= 0)
                    {
                        goToEndScreen();
                    }
                    else if (b instanceof HumanCharacter && ((HumanCharacter) b).getHealth() <= 0)
                    {
                        goToEndScreen();
                    }

                    //checks if a MovingEntity has collided with a NotMovingEntity
                    //if so, the current location will be set to the previous location
                    if(a instanceof NotMovingEntity && ((NotMovingEntity) a).isSolid() && b instanceof MovingEntity)
                    {
                        b.setLocation(b.getLastLocation());
                    }
                    else if(b instanceof NotMovingEntity && ((NotMovingEntity) b).isSolid() && a instanceof MovingEntity)
                    {
                        a.setLocation(a.getLastLocation());

                    }
                }
            }
        }
        //This will destroy all the entities that will need to be destroyed for the previous checks.
        //this needs to be outside of the loop because you can't delete objects in a list while you're
        //working with the list
        for(Entity e : toRemoveEntities)
        {
            e.destroy();
        }

    }

    private void goToEndScreen() {

        this.dispose();
        gameInitializer.setScreen(new EndScreen(gameInitializer, game));

        // TODO Implement when to go to endscreen
        // TODO implement LibGDX Dialog, advance to Main Menu after pressing "OK"
//        Dialog endGame = new Dialog("Game over", );
        this.dispose();
        gameInitializer.setScreen(new EndScreen(gameInitializer, game));
        bgm.stop();
    }



    /**
     *
      * @return 1 out of 4 hit sounds
     */
    private Sound getRandomHitSound(){
        // TODO Unit Test
        Sound sound = null;
        int random = new Random().nextInt(4) + 1;
        switch(random){
            case 1:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3"));
                break;
            case 2:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab2.mp3"));
                break;
            case 3:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab3.mp3"));
                break;
            case 4:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab4.mp3"));
                break;
        }
        return sound;
    }

    private void playWalkSound(float deltaTime){

        if(playerIsMoving){
            walkTime += deltaTime;

            if(walkTime >= .3f){
                Sound sound = null;
                int random = new Random().nextInt(7) + 1;

                switch(random){
                    case 1:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot1.mp3"));
                        break;
                    case 2:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot2.mp3"));
                        break;
                    case 3:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot3.mp3"));
                        break;
                    case 4:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot4.mp3"));
                        break;
                    case 5:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot5.mp3"));
                        break;
                    case 6:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot6.mp3"));
                        break;
                    case 7:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot7.mp3"));
                        break;
                }
                sound.play(.2f);
                walkTime = 0;
            }
        }
        else{
            walkTime = 0;
        }

    }

}
