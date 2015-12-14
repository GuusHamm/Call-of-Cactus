package callofcactus.menu;


import callofcactus.*;
import callofcactus.entities.*;
import callofcactus.entities.ai.AICharacter;
import callofcactus.entities.pickups.Pickup;
import callofcactus.map.CallOfCactusMap;
import callofcactus.map.CallOfCactusTiledMap;
import callofcactus.map.DefaultMap;
import callofcactus.map.MapFiles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teun
 */
public class GameScreen implements Screen {
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
    private IGame game;
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
    //  TiledMap with OrthographicCamera
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    //  Camera lock
    private MapProperties properties;
    private int levelWidthPx;
    private int levelHeightPx;

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
                    game.setGodMode(!game.getGodMode());
                    break;
                case Input.Keys.ALT_RIGHT:
                    game.setMuted(!game.getMuted());
                    if (game.getMuted()) {
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
            game.setMousePositions(i, i1);
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            game.setMousePositions(i, i1);
            return false;
        }

        @Override
        public boolean scrolled(int i) {
            return false;
        }
    };

    /**
     * Starts the callofcactus in a new screen, give gameInitializer object because spriteBatch is used from that object
     *
     * @param gameInitializer : This has a spriteBatch and a camera for use in callofcactus
     */
    public GameScreen(GameInitializer gameInitializer) {
        this.gameInitializer = gameInitializer;
        this.game = gameInitializer.getGame();

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

        this.player = game.getPlayer();

        //  Tiled Map implementation
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        tiledMap = ((SinglePlayerGame)game).getTiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //  Setting screenHeight and screenWidth
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        //  Map properties used for keeping camera within mapbounds
        properties = tiledMap.getProperties();
        int levelWidthTiles = properties.get("width", Integer.class);
        int levelHeightTiles = properties.get("height", Integer.class);
        int tileWidth = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);
        levelWidthPx = levelWidthTiles * tileWidth;
        levelHeightPx = levelHeightTiles * tileHeight;
    }

    /**
     * Is executed when callofcactus window is shown from being hidden
     */
    @Override
    public void show() {
        for (int i = 0; i < game.getAllEntities().size(); i++) {
            Entity e = game.getAllEntities().get(i);
            if (e.destroy()) {
                game.removeEntityFromGame(e);
                i--;
            }
        }
    }

    /**
     * Render the callofcactus here
     *
     * @param v Deltatime in seconds
     */
    @Override
    public void render(float v) {
        //Check whether W,A,S or D are pressed or not
        procesMovementInput();
        game.compareHit();

        game.getMovingEntities().stream().filter(e -> e instanceof HumanCharacter && ((HumanCharacter) e).getHealth() <= 0).forEach(e -> goToEndScreen());

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  Enables the use of transparent tiles in tiled maps
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        player = game.getPlayer();

        //  Keep camera centered on player or within map bounds
        camera.position.x = Math.min(Math.max(player.getLocation().x, screenWidth / 2), levelWidthPx - (screenWidth / 2));
        camera.position.y = Math.min(Math.max(player.getLocation().y, screenHeight / 2), levelHeightPx - (screenHeight / 2));
        camera.update();

        backgroundRenderer.render(backgroundBatch);

        if (game instanceof SinglePlayerGame) {
            drawAI();
        }

        drawPlayer();

        for (Entity e : game.getMovingEntities()) {
            if (!(e instanceof HumanCharacter)) {
                drawEntity(e);
            }
        }

        drawMap();

        drawHud();

        //Will only play if the player is moving
        playWalkSound(v);
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
        screenWidth = size.x;
        screenHeight = size.y;
        camera.setToOrtho(false, size.x, size.y);
        camera.update();
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
            HumanCharacter player = game.getPlayers().get(0);

            hudBatch.begin();
            font.draw(hudBatch, String.format("Health: %s", player.getHealth()), 10, screenHeight - 30);
            font.draw(hudBatch, String.format("Ammo: %s", player.getRole().getAmmo()), 10, screenHeight - 60);
            font.draw(hudBatch, String.format("Fps: %d", Gdx.graphics.getFramesPerSecond()), 10, screenHeight - 120);
            font.draw(hudBatch, String.format("Score: %d", player.getScore()), screenWidth - 100, screenHeight - 30);
            font.draw(hudBatch, String.format("Wave: %d", game.getWaveNumber()), screenWidth / 2, screenHeight - 30);
            //For kills
            font.draw(hudBatch, String.format("Kills: %d", game.getPlayer().getKillCount()), screenWidth / 2, screenHeight - 50);

            if (game.getGodMode()) {
                font.draw(hudBatch, String.format("Health: %s", player.getHealth()), 10, screenHeight - screenHeight + 210);
                font.draw(hudBatch, String.format("Speed: %s", player.getSpeed()), 10, screenHeight - screenHeight + 180);
                font.draw(hudBatch, String.format("Damage: %s", player.getDamage()), 10, screenHeight - screenHeight + 150);
                font.draw(hudBatch, String.format("Fire Rate: %s", player.getFireRate()), 10, screenHeight - screenHeight + 120);
                font.draw(hudBatch, String.format("Ammo: %s", player.getRole().getAmmo()), 10, screenHeight - screenHeight + 90);
                font.draw(hudBatch, String.format("Entities in the game: %s", game.getMovingEntities().size()), 10, screenHeight - screenHeight + 60);
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
            Sprite playerSprite = new Sprite(game.getTextures().getTexture(GameTexture.texturesEnum.playerTexture));
            Vector2 location = player.getLocation();
            playerSprite.setPosition(location.x, location.y);

            float width = player.getSpriteWidth();
            float height = player.getSpriteHeight();

            playerSprite.setSize(width, height);
            playerSprite.setCenter(player.getLocation().x, player.getLocation().y);

            playerSprite.setSize(width, height);
            playerSprite.setOriginCenter();

            int screenX = (int) (player.getLocation().x - (camera.viewportWidth / 2));
            int screenY = (int) (player.getLocation().y - (camera.viewportHeight / 2));

            float mouseX = game.getMouse().x;
            float mouseY = game.getMouse().y;

            System.out.println("ScreenX: " + screenX);
            System.out.println("ScreenY: " + screenY);
            System.out.println("old-MouseX: " + mouseX);
            System.out.println("Old-MouseY: " + mouseY);


            if (screenX > 0) {
                mouseX += screenX;
            }
            else {
                //at the edge
            }
            if (screenY > 0) {
                mouseY += screenY;
            }
            else {
                mouseY -= screenY * 2;
            }

            System.out.println("Position x: " + camera.position.x);
            System.out.println("Position y: " + camera.position.y);

            System.out.println("MouseX: " + mouseX);
            System.out.println("MouseY: " + mouseY);
            System.out.println("Player X: " + player.getLocation().x);
            System.out.println("Player Y: " + player.getLocation().y);
            System.out.println("");

            Vector2 newMousePosition = new Vector2(mouseX, mouseY);

            int angle = game.angle(new Vector2(player.getLocation().x, (player.getLocation().y)), newMousePosition);
            //int angle = game.angle(new Vector2(player.getLocation().x, (size.y - player.getLocation().y)), newMousePosition);
            playerSprite.rotate(angle - 90);

            player.setAngle(angle,true);

            characterBatch.begin();
            characterBatch.setProjectionMatrix(camera.combined);
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
        if (mouseClick && TimeUtils.millis() - lastShot > game.secondsToMillis(player.getFireRate()) / 50) {
            player.fireBullet(GameTexture.texturesEnum.bulletTexture);
            lastShot = TimeUtils.millis();
        }
        if (spaceDown && TimeUtils.millis() - lastShot > game.secondsToMillis(player.getFireRate()) / 50) {
            player.fireBulletShotgun(GameTexture.texturesEnum.bulletTexture);
            lastShot = TimeUtils.millis();
        }
    }

    /**
     * Draw all AI that are currently in-callofcactus.
     *
     * @return true if all AI were drawn, false if an error occured.
     */
    private boolean drawAI() {
        ((SinglePlayerGame) game).spawnAI();

        try {
            AIBatch.begin();
            AIBatch.setProjectionMatrix(camera.combined);
            for (MovingEntity a : game.getMovingEntities()) {
                if (a instanceof AICharacter) {
                    AICharacter ai = (AICharacter) a;
                    int size = 10;
                    a.move(ai.getPlayerToFollow().getLocation());
                    Sprite s = new Sprite(a.getSpriteTexture());
                    s.setSize(size, size);
                    s.setPosition((a.getLocation().x - (size / 2)), (a.getLocation().y - (size / 2)));
                    s.draw(AIBatch);
                }
            }
            AIBatch.end();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Draws the DefaultMap
     *
     * @return true is succeeded and false when an Exception is thrown
     */
    private boolean drawMap() {
        try {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    /**
     * When called, this screen will be disposed and the end screen(callofcactus over) will be displayed.
     */
    private void goToEndScreen() {
        if (game instanceof SinglePlayerGame) {
            ((SinglePlayerGame) game).deleteCheckpoint();
        }
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
     * If the player is moving, play one out of 7 (hardcoded) movement sounds every .3 seconds.
     *
     * @param deltaTime : The last time this method was used.
     */
    private void playWalkSound(float deltaTime) {

        if (game.getMuted()) {
            return;
        }

        if (playerIsMoving) {
            walkTime += deltaTime;

            if (walkTime >= .3f) {
                ((SinglePlayerGame)game).getGameSounds().playRandomWalkSound();
                walkTime = 0;
            }
        } else {
            walkTime = 0;
        }

    }

}
