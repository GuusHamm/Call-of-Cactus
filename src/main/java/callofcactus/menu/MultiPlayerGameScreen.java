package callofcactus.menu;


import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.GameTexture;
import callofcactus.account.Account;
import callofcactus.entities.*;
import callofcactus.entities.pickups.Pickup;
import callofcactus.map.CallOfCactusMap;
import callofcactus.map.DefaultMap;
import callofcactus.map.MapFiles;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.ServerS;
import callofcactus.multiplayer.ServerVariables;
import callofcactus.role.Boss;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Wouter Vanmulken
 */
public class MultiPlayerGameScreen implements Screen {
    boolean testingShow = false;
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
    private boolean tabDown = false;
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
    //  TiledMap with OrthographicCamera
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    //  Camera lock
    private MapProperties properties;
    private int levelWidthPx;
    private int levelHeightPx;
    //  ScoreBoard
    private GlyphLayout glyphLayout;
    private SpriteBatch scoreBoardBatch;
    private BitmapFont scoreBoardFont;
    private Account account;
    // Connection lost feedback

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
                case Input.Keys.BACKSPACE:
                    if (ServerS.getExists()) {
                        ServerVariables.setShouldServerStop(true);
                    } else {
                        ClientS.getInstance().sendStop();
                        Administration.getInstance().dump();
                    }
                    gameInitializer.setScreen(new MainMenu(gameInitializer, account));
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
                case Input.Keys.TAB:
                    tabDown = true;
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
                case Input.Keys.TAB:
                    tabDown = false;
                    break;
                default:
                    return false;
            }
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            for (Entity e : administration.getPlayers()) {

                System.out.println("PlayerID: " + e.getID());
            }
            System.out.println("localPlayerID: " + administration.getLocalPlayer().getID());
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
    public MultiPlayerGameScreen(GameInitializer gameInitializer, Account account) {

        this.gameInitializer = gameInitializer;
        this.administration = Administration.getInstance();

        administration.addSinglePlayerHumanCharacter();

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

        //  Tiled Map implementation
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

//        tiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load(MapFiles.getFileName(MapFiles.MAPS.COMPLICATEDMAP));
        tiledMap = administration.getTiledMap();
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

        this.account = account;

        //  ScoreBoard implementation, don't you f*cking dare overwrite this
        sr = new ShapeRenderer();
        scoreBoardFont = new BitmapFont();
        scoreBoardFont.setColor(Color.WHITE);
        scoreBoardBatch = new SpriteBatch();

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                for (Entity e : Administration.getInstance().getMovingEntities()) {
                    if (e instanceof Bullet) {
                        ((Bullet) e).move();
                    }
                }
            }
        }, 100, 15);
    }

    /**
     * Is executed when callofcactus window is shown from being hidden
     */
    @Override
    public void show() {
        System.out.println();
        if (testingShow) {
            for (int i = 0; i < administration.getAllEntities().size(); i++) {
                Entity e = administration.getAllEntities().get(i);
                if (e.destroy()) {
                    administration.removeEntity(e);
                    i--;
                }
            }
        }
        testingShow = true;

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
        //If the game is over match ID will be set, starting at 1.
        if (administration.getMatchID() != 0 || ServerVariables.getShouldServerStop()) {
            goToEndScreen();
        }

        if (administration.isMapChanged()) {
            tiledMap = administration.getTiledMap();
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        }

        //Check whether W,A,S or D are pressed or not
        procesMovementInput();
//        administration.compareHit();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  Enables the use of transparent tiles in tiled maps
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        player = administration.getLocalPlayer();

        //  Keep camera centered on player or within map bounds
        camera.position.x = Math.min(Math.max(player.getLocation().x, screenWidth / 2), levelWidthPx - (screenWidth / 2));
        camera.position.y = Math.min(Math.max(player.getLocation().y, screenHeight / 2), levelHeightPx - (screenHeight / 2));
        camera.update();

        backgroundRenderer.render(backgroundBatch);

        drawPlayer();

        for (Entity e : administration.getMovingEntities()) {
            if (!(e instanceof HumanCharacter)) {
                drawEntity(e);
            }
        }


        drawMap();

        drawHud();

        if (tabDown) {
            drawScoreBoard();
        }

        //Will only play if the player is moving
        //playWalkSound(v);
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
//            if (player == null) {
//                System.out.println("Player is Null; MultiplayerGameScreen drawHUD");
////                return false;
//                player = new HumanCharacter(null, new Vector2(100, 100), "TestingPlayer", new Sniper(), GameTexture.texturesEnum.playerTexture, 128, 32, false);
//            }
            player = Administration.getInstance().getLocalPlayer();

            hudBatch.begin();

            // Display connection lost message
            if (administration.isConnectionLost()) {
                font.draw(hudBatch, String.format("Connection lost. Reconnecting..."), screenWidth / 2, screenHeight / 2);
            }

            font.draw(hudBatch, String.format("Health: %s", player.getHealth()), 10, screenHeight - 30);
            font.draw(hudBatch, String.format("Ammo: %s", player.getRole().getAmmo()), 10, screenHeight - 60);
            font.draw(hudBatch, String.format("Fps: %d", Gdx.graphics.getFramesPerSecond()), 10, screenHeight - 120);
            font.draw(hudBatch, String.format("Score: %d", player.getScore()), screenWidth - 100, screenHeight - 30);
            //font.draw(hudBatch, String.format("Wave: %d", administration.getWaveNumber()), screenWidth / 2, screenHeight - 30);
            //For kills
            font.draw(hudBatch, String.format("Kills: %d", player.getAccount().getKillCount()), screenWidth / 2, screenHeight - 50);

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

//        System.out.println("total players" + administration.getPlayers().size());
//        System.out.println("total MovingEntities" + administration.getMovingEntities().size());

        for (MovingEntity m : administration.getInstance().getMovingEntities()) {
            if (m instanceof HumanCharacter) {

                HumanCharacter p = (HumanCharacter) m;
                try {
                    Sprite playerSprite;
                    if (p.getRole() instanceof Boss) {
                        playerSprite = new Sprite(administration.getGameTextures().getTexture(GameTexture.texturesEnum.bossTexture));
                    } else {
                        playerSprite = new Sprite(administration.getGameTextures().getTexture(GameTexture.texturesEnum.playerTexture));
                    }

                    Vector2 location = p.getLocation();
                    playerSprite.setPosition(location.x, location.y);

                    float width = p.getSpriteWidth();
                    float height = p.getSpriteHeight();

                    playerSprite.setSize(width, height);
                    playerSprite.setCenter(p.getLocation().x, p.getLocation().y);

                    playerSprite.setSize(width, height);
                    playerSprite.setOriginCenter();

                    int screenX = (int) (p.getLocation().x - (camera.viewportWidth / 2));
                    int screenY = (int) (p.getLocation().y - (camera.viewportHeight / 2));

                    int angle = 0;
                    if (p.getID() == administration.getLocalPlayer().getID()) {

                        float mouseX = (administration.getMouse().x) + (camera.position.x - (camera.viewportWidth / 2));
                        float mouseY = (screenHeight - (administration.getMouse().y) + (camera.position.y - (camera.viewportHeight / 2)));
                        Vector2 newMousePosition = new Vector2(mouseX, mouseY);

                        //System.out.println(newMousePosition.toString()+":::"+p.getLocation() + ":::"+(camera.position.x- (camera.viewportWidth/2))+" ; "+(camera.position.y- (camera.viewportHeight/2)));

                        angle = 360 - administration.angle(p.getLocation(), newMousePosition);


                        administration.getLocalPlayer().setAngle(angle, true);
                    }
//                        else{
//                            p.setAngle(angle, false);
//                        }
//                    for(MovingEntity movingEntity : Administration.getInstance().getMovingEntities()){
//                        if (movingEntity instanceof HumanCharacter){
//                            if (movingEntity.getID() == Administration.getInstance().getLocalPlayer().getID()){
//                                ( movingEntity).setAngle(angle, false);
//                            }
//                        }
//                    }

                    playerSprite.rotate((float) p.getAngle() - 90);


                    SpriteBatch sb = new SpriteBatch();
                    sb.begin();
                    sb.setProjectionMatrix(camera.combined);
                    playerSprite.draw(sb);
                    font.draw(sb, p.getName(), p.getLocation().x + 25, p.getLocation().y + 25);
                    //font.draw(sb, String.valueOf(p.getAngle()), p.getLocation().x + 25, p.getLocation().y - 25);
                    sb.end();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param entity : The entity that has to be drawn
     * @return true when succeeded and false when an Exception is thrown
     */
    private boolean drawEntity(Entity entity) {
        try {
//            if (entity instanceof Bullet) {
//                ((Bullet) entity).move();
//            }
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
            characterBatch.setProjectionMatrix(camera.combined);
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
        player = Administration.getInstance().getLocalPlayer();
        if (player == null) {
            System.out.println("Player is Null; MultiplayerGameScreen processMovementInput");
            //                return false;
//            player = new HumanCharacter(null, new Vector2(100, 100), "TestingPlayer", new Sniper(), GameTexture.texturesEnum.playerTexture, 128, 32, false);
        }

        if (wDown || aDown || sDown || dDown) {

//            player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));

            if (wDown) {
                Vector2 testingVector = new Vector2(player.getLocation().x,player.getLocation().y);
                if (checkCanMoveHere(player.calculateNewPosition(testingVector.add(0, steps * (float) player.getSpeed())))) {
                    player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));
                    player.move(player.getLocation().add(0, steps * (float) player.getSpeed()), true);
                }
            }
            if (aDown) {
                Vector2 testingVector = new Vector2(player.getLocation().x,player.getLocation().y);
                if (checkCanMoveHere(player.calculateNewPosition(testingVector.add(-1 * steps * (float) player.getSpeed(), 0)))) {
                    player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));
                    player.move(player.getLocation().add(-1 * steps * (float) player.getSpeed(), 0), true);
                }
            }
            if (sDown) {
                Vector2 testingVector = new Vector2(player.getLocation().x,player.getLocation().y);
                if (checkCanMoveHere(player.calculateNewPosition(testingVector.add(0, -1 * steps * (float) player.getSpeed())))) {
                    player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));
                    player.move(player.getLocation().add(0, -1 * steps * (float) player.getSpeed()), true);
                }
            }
            if (dDown) {
                Vector2 testingVector = new Vector2(player.getLocation().x,player.getLocation().y);
                if (checkCanMoveHere(player.calculateNewPosition(testingVector.add(steps * (float) player.getSpeed(), 0)))) {
                    player.setLastLocation(new Vector2(player.getLocation().x, player.getLocation().y));
                    player.move(player.getLocation().add(steps * (float) player.getSpeed(), 0), true);
                }
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
        try {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
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
        gameInitializer.setScreen(new MultiPlayerEndScreen(gameInitializer));

        // TODO implement LibGDX Dialog, advance to Main Menu after pressing "OK"
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
                administration.getGameSounds().playRandomWalkSound();
                walkTime = 0;
            }
        } else {
            walkTime = 0;
        }

    }

    private boolean drawScoreBoard() {
        HashMap<String, Integer> scoreBoard = administration.getScoreBoard();

        try {
            glyphLayout = new GlyphLayout();

            int scoreBoardWidth = 0;
            int scoreBoardHeight = 25;

            //  Get width for the scoreboard based on longest string
            for (HashMap.Entry entry : scoreBoard.entrySet()) {
                glyphLayout.setText(scoreBoardFont, entry.getKey() + " " + entry.getValue());
                if (glyphLayout.width > scoreBoardWidth) {
                    scoreBoardWidth = (int) glyphLayout.width;
                }
                scoreBoardHeight += glyphLayout.height + 25;
            }

            if (scoreBoardWidth < screenWidth / 3) {
                scoreBoardWidth = (int) screenWidth / 3;
            }

            if (scoreBoardHeight < screenHeight / 3) {
                scoreBoardHeight = (int) screenHeight / 3;
            }

            Rectangle scoreBoardLoc = new Rectangle((screenWidth / 2) - (scoreBoardWidth / 2), (screenHeight / 2) - (scoreBoardHeight / 2), scoreBoardWidth + 30, scoreBoardHeight + 30);

            sr.begin(ShapeRenderer.ShapeType.Filled);
            //  Enables transparency of the ScoreBoard rectangle
            Gdx.gl.glEnable(GL20.GL_BLEND);
            sr.setColor(new Color(0f, 0f, 0f, 0.8f));
            sr.rect(scoreBoardLoc.x, scoreBoardLoc.y, scoreBoardLoc.width, scoreBoardLoc.height);
            sr.end();

            scoreBoardBatch.begin();
            scoreBoardFont.draw(scoreBoardBatch, "Scoreboard", scoreBoardLoc.x + 15, scoreBoardLoc.y + scoreBoardLoc.height - 15);

            int count = 0;
            int startPosition = (int) (scoreBoardLoc.y + scoreBoardLoc.height - 40);
            for (HashMap.Entry entry : scoreBoard.entrySet()) {
                String s = entry.getKey().toString() + " " + entry.getValue().toString();
                scoreBoardFont.draw(scoreBoardBatch, s, scoreBoardLoc.x + 15, startPosition - (count * 25));
                count++;
            }

            scoreBoardBatch.end();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean checkCanMoveHere(Rectangle newHitbox) {

        Rectangle entityHitbox = newHitbox;

        entityHitbox = new Rectangle(entityHitbox.x-(entityHitbox.width/2), entityHitbox.y-(entityHitbox.height/2), entityHitbox.width, entityHitbox.height);
        Rectangle wallHitbox;

        for (MapObject collisionObject : administration.getCollisionObjects()) {
            if (collisionObject instanceof RectangleMapObject) {
                wallHitbox = ((RectangleMapObject) collisionObject).getRectangle();

                if (entityHitbox.overlaps(wallHitbox) || wallHitbox.overlaps(entityHitbox)) {
                    return false;
                }
            }
        }
        return true;
    }
}
