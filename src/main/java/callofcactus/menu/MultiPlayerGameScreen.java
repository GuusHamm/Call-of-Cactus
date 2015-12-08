package callofcactus.menu;


import callofcactus.*;
import callofcactus.entities.*;
import callofcactus.entities.pickups.Pickup;
import callofcactus.map.CallOfCactusMap;
import callofcactus.map.DefaultMap;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Wouter Vanmulken
 */
public class MultiPlayerGameScreen implements Screen {
    private HumanCharacter player;

    private ShapeRenderer sr;
    //Movement variables
    private float walkTime;
    private boolean playerIsMoving = false;

    private boolean wDown = false;
    private boolean aDown = false;
    private boolean sDown = false;
    private boolean dDown = false;
    private boolean mouseClick = false;
    private boolean spaceDown = false;

    private long lastShot = 0;
    private Vector2 size;
    private GameInitializer gameInitializer;
    private int steps = 1;
    // HUD variables
    private float screenWidth;
    private float screenHeight;
    private SpriteBatch hudBatch;
    private BitmapFont font;
    //Character variables
    private SpriteBatch characterBatch;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;
    private SpriteBatch AIBatch;
    private SpriteBatch mapBatch;
    //Sound
    private Music bgm;
    private Administration administration = Administration.getInstance();
    /**
     * InputProcessor for input in this window
     */
    private InputProcessor inputProcessor = new InputProcessor() {
        @Override
        public boolean keyDown(int i) {
            switch (i) {
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
                    spaceDown = true;
                    break;
                case Input.Keys.ESCAPE:
                    Gdx.app.exit();
                    break;
                case Input.Keys.SHIFT_RIGHT:
                    administration.setGodmode(!administration.getGodmode());
                    break;
                case Input.Keys.ALT_RIGHT:
                    administration.setMuted(!administration.getMuted());
                    if (administration.getMuted()) {
                        bgm.setVolume(0f);
                    } else {
                        bgm.setVolume(0.2f);
                    }
                    break;
                default:
                    return false;
            }

            return false;
        }

        @Override
        public boolean keyUp(int i) {
            switch (i) {
                case Input.Keys.W:
                    wDown = false;
                    if (!aDown && !sDown && !dDown) {
                        playerIsMoving = false;
                    }

                    break;
                case Input.Keys.A:
                    aDown = false;
                    if (!wDown && !sDown && !dDown) {
                        playerIsMoving = false;
                    }
                    break;
                case Input.Keys.S:
                    sDown = false;
                    if (!aDown && !wDown && !dDown) {
                        playerIsMoving = false;
                    }
                    break;
                case Input.Keys.D:
                    dDown = false;
                    if (!aDown && !sDown && !wDown) {
                        playerIsMoving = false;
                    }
                    break;
                case Input.Keys.SPACE:
                    spaceDown = false;
                    break;
                default:
                    return false;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            mouseClick = true;
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            mouseClick = false;
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            administration.setMousePosition(i, i1);
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            administration.setMousePosition(i, i1);
            return false;
        }

        @Override
        public boolean scrolled(int i) {
            return false;
        }
    };

    /**
     * Starts the callofcactus in a new screen, give administrationInitializer object because spriteBatch is used from that object
     *
     * @param gameInitializer : This has a spriteBatch and a camera for use in callofcactus
     */
    public MultiPlayerGameScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.administration = Administration.getInstance();

        // HUD initialization
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();
        this.hudBatch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);

        this.characterBatch = new SpriteBatch();
        this.AIBatch = new SpriteBatch();

        this.mapBatch = new SpriteBatch();

        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer();

        //Set the cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("smallcrosshair32.png"));
        int xHotSpot = pm.getWidth() / 2;
        int yHotSpot = pm.getHeight() / 2;
        Cursor customCursor = Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot);
        Gdx.graphics.setCursor(customCursor);

        // Input Processor remains in this class to have access to objects
        Gdx.input.setInputProcessor(inputProcessor);

        // Playing audio
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/coc_battle.mp3"));
        bgm.setVolume(0.2f);
        bgm.setLooping(true);
        bgm.play();

        this.player = Administration.getInstance().getLocalPlayer();
    }

    /**
     * Is executed when callofcactus window is shown from being hidden
     */
    @Override
    public void show() {
        for (int i = 0; i < administration.getAllEntities().size(); i++) {
            Entity e = administration.getAllEntities().get(i);
            if (e.destroy()) {
                administration.removeEntity(e);
                i--;
            }
        }

        CallOfCactusMap defaultMap = new DefaultMap(null, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
//		this.defaultMap = new CallOfCactusTiledMap(administration, MapFiles.MAPS.COMPLICATEDMAP);
        defaultMap.init();

    }

    /**
     * Render the callofcactus here
     *
     * @param v Deltatime in seconds
     */
    @Override
    public void render(float v) {
        //Check whether W,A,S or D are pressed or not
        SpriteBatch batch = gameInitializer.getBatch();
        procesMovementInput();
//        administration.compareHit();

        administration.getMovingEntities().stream().filter(e -> e instanceof HumanCharacter && ((HumanCharacter) e).getHealth() <= 0).forEach(e -> goToEndScreen());

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        player = administration.getLocalPlayer();

        backgroundRenderer.render(backgroundBatch);
        for (Entity e : administration.getNotMovingEntities()) {
            drawRectangle(e);
        }

        drawPlayer();
        ArrayList<Bullet> bullets = new ArrayList<>();


//         for(Entity e : callofcactus.getAllEntities()){drawRectangle(e);}
        for (Entity e : administration.getMovingEntities()) {
            if (!(e instanceof HumanCharacter)) {
                drawEntity(e);
            }
            Rectangle r = new Rectangle(e.getLocation().x, e.getLocation().y, e.getSpriteWidth(), e.getSpriteHeight());

            boolean contain = new Rectangle(-20, -20, Gdx.graphics.getWidth() + 40, Gdx.graphics.getHeight() + 40).contains(r);

            if (e instanceof Bullet && (!contain)) {
                bullets.add((Bullet) e);
            }
        }
        administration.getMovingEntities().removeAll(bullets);


        drawHud();

        drawMap();


        //Will only play if the player is moving
        playWalkSound(v);
        drawHud();

        batch.end();
    }

    /**
     * Executed on resize
     *
     * @param i  X when resized
     * @param i1 Y when resized
     */
    @Override
    public void resize(int i, int i1) {
        size = new Vector2(i, i1);
    }

    @Override
    public void pause() {
        return;
    }

    @Override
    public void resume() {
        return;
    }

    @Override
    public void hide() {
        return;
    }

    @Override
    public void dispose() {
        return;
    }

    /**
     * Drawing the Heads up display
     *
     * @return true when succeeded and false when an Exception is thrown
     */
    private boolean drawHud() {
        try {
            HumanCharacter player = administration.getLocalPlayer();

            hudBatch.begin();
            font.draw(hudBatch, String.format("Health: %s", player.getHealth()), 10, screenHeight - 30);
            font.draw(hudBatch, String.format("Ammo: %s", player.getRole().getAmmo()), 10, screenHeight - 60);
            font.draw(hudBatch, String.format("Fps: %d", Gdx.graphics.getFramesPerSecond()), 10, screenHeight - 120);
            font.draw(hudBatch, String.format("Score: %d", player.getScore()), screenWidth - 100, screenHeight - 30);
            //font.draw(hudBatch, String.format("Wave: %d", administration.getWaveNumber()), screenWidth / 2, screenHeight - 30);
            //For kills
            font.draw(hudBatch, String.format("Kills: %d", administration.getLocalPlayer().getKillCount()), screenWidth / 2, screenHeight - 50);

            if (administration.getGodmode()) {
                font.draw(hudBatch, String.format("Health: %s", player.getHealth()), 10, screenHeight - screenHeight + 210);
                font.draw(hudBatch, String.format("Speed: %s", player.getSpeed()), 10, screenHeight - screenHeight + 180);
                font.draw(hudBatch, String.format("Damage: %s", player.getDamage()), 10, screenHeight - screenHeight + 150);
                font.draw(hudBatch, String.format("Fire Rate: %s", player.getFireRate()), 10, screenHeight - screenHeight + 120);
                font.draw(hudBatch, String.format("Ammo: %s", player.getRole().getAmmo()), 10, screenHeight - screenHeight + 90);
                font.draw(hudBatch, String.format("Entities in the administration: %s", administration.getMovingEntities().size()), 10, screenHeight - screenHeight + 60);
                font.draw(hudBatch, "How does it feel being a god?", 10, screenHeight - screenHeight + 30);
            }
            hudBatch.end();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Drawing the player
     *
     * @return true when succeeded and false when an Exception is thrown
     */
    private boolean drawPlayer() {
        try {
            player = administration.getLocalPlayer();

            Sprite playerSprite = new Sprite(administration.getGameTextures().getTexture(GameTexture.texturesEnum.playerTexture));
            Vector2 location = player.getLocation();
            playerSprite.setPosition(location.x, location.y);

            float width = player.getSpriteWidth();
            float height = player.getSpriteHeight();

            playerSprite.setSize(width, height);
            playerSprite.setCenter(player.getLocation().x, player.getLocation().y);

            playerSprite.setSize(width, height);
            playerSprite.setOriginCenter();

            int angle = administration.angle(new Vector2(player.getLocation().x, (size.y - player.getLocation().y)), administration.getMouse());
            playerSprite.rotate(angle - 90);
            player.setAngle(angle);

            characterBatch.begin();
            playerSprite.draw(characterBatch);
            font.draw(characterBatch, player.getName(), player.getLocation().x + 25, player.getLocation().y + 25);
            characterBatch.end();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param entity : The entity that has to be drawn
     * @return true when succeeded and false when an Exception is thrown
     */
    private boolean drawEntity(Entity entity) {
        try {
            if (entity instanceof Bullet) {
                ((Bullet) entity).move();
            }
            Sprite entitySprite = new Sprite(entity.getSpriteTexture());
            Vector2 location = entity.getLocation();
            entitySprite.setPosition(location.x, location.y);

            float width = entity.getSpriteWidth();
            float height = entity.getSpriteHeight();

            entitySprite.setSize(width, height);
            entitySprite.setCenter(location.x, location.y);

            entitySprite.setOriginCenter();
            if (entity instanceof Bullet) {
                entitySprite.rotate((float) ((Bullet) entity).getAngle() - 90);
            }
            characterBatch.begin();
            entitySprite.draw(characterBatch);
            characterBatch.end();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param entity : The entity that has to be drawn
     * @return true when succeeded and false when an Exception is thrown
     */
    private boolean drawRectangle(Entity entity) {
        try {
            if (entity instanceof Pickup) {
                return false;
            }

            if (sr == null) {
                sr = new ShapeRenderer();
                sr.setColor(Color.CLEAR);
            }
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.rect(entity.getLocation().x - (entity.getSpriteWidth() / 2), entity.getLocation().y - (entity.getSpriteHeight() / 2), entity.getSpriteWidth(), entity.getSpriteHeight());
            sr.end();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Move the player according to WASD input. Also fire bullets when the left-mousebutton is clicked.
     */
    private void procesMovementInput() {

        if (wDown || aDown || sDown || dDown) {

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
        if (mouseClick && TimeUtils.millis() - lastShot > administration.secondsToMillis(player.getFireRate()) / 50) {
            player.fireBullet(GameTexture.texturesEnum.bulletTexture);
            lastShot = TimeUtils.millis();
        }
        if (spaceDown && TimeUtils.millis() - lastShot > administration.secondsToMillis(player.getFireRate()) / 50) {
            player.fireBulletShotgun(GameTexture.texturesEnum.bulletTexture);
            lastShot = TimeUtils.millis();
        }
    }

    /**
     * Draw all AI that are currently in-callofcactus.
     *
     * @return true if all AI were drawn, false if an error occured.
     */
//    private boolean drawAI() {
//        ((SinglePlayerGame) game).spawnAI();
//
//        try {
//            AIBatch.begin();
//            for (MovingEntity a : administration.getMovingEntities()) {
//                if (a instanceof AICharacter) {
//                    AICharacter ai = (AICharacter) a;
//                    int size = 10;
//                    a.move(ai.getPlayerToFollow().getLocation());
//                    Sprite s = new Sprite(a.getSpriteTexture());
//                    s.setSize(size, size);
//                    s.setPosition((a.getLocation().x - (size / 2)), (a.getLocation().y - (size / 2)));
//                    s.draw(AIBatch);
//                }
//            }
//            AIBatch.end();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    /**
     * Draws the DefaultMap
     *
     * @return true is succeeded and false when an Exception is thrown
     */
    private boolean drawMap() {
        //TODO code 'spawnlocations' of the walls / objects on the defaultMap.
        try {
            mapBatch.begin();
            List<NotMovingEntity> nME = administration.getNotMovingEntities();
            for (NotMovingEntity e : nME) {
                Sprite s = new Sprite(e.getSpriteTexture());
                s.setSize(e.getSpriteWidth(), e.getSpriteHeight());
                s.setPosition(e.getLocation().x, e.getLocation().y);
                s.draw(mapBatch);
            }
            mapBatch.end();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * When called, this screen will be disposed and the end screen(callofcactus over) will be displayed.
     */
    private void goToEndScreen() {

        this.dispose();
        gameInitializer.setScreen(new EndScreen(gameInitializer, administration));

        // TODO Implement when to go to endscreen
        // TODO implement LibGDX Dialog, advance to Main Menu after pressing "OK"
//        Dialog endadministration = new Dialog("administration over", );
        this.dispose();
        gameInitializer.setScreen(new EndScreen(gameInitializer, administration));
        bgm.stop();
    }


    /**
     * If the player is moving, play one out of 7 (hardcoded) movement sounds every .3 seconds.
     *
     * @param deltaTime : The last time this method was used.
     */
    private void playWalkSound(float deltaTime) {

        if (administration.getMuted()) {
            return;
        }

        if (playerIsMoving) {
            walkTime += deltaTime;

            if (walkTime >= .3f) {
                Sound sound;
                int random = new Random().nextInt(6) + 1;

                switch (random) {
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
                    default:
                        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot7.mp3"));
                        break;
                }
                sound.play(.2f);
                walkTime = 0;
            }
        } else {
            walkTime = 0;
        }

    }

}
