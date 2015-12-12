package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.*;
import callofcactus.io.DatabaseManager;
import callofcactus.menu.GameScreen;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.Command;
import callofcactus.role.Sniper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class Administration {
    private static Administration instance = null;
    private GameTexture gameTextures;
    private GameSounds gameSounds;
    private GameScreen gameScreen;
    private Account localAccount;
    private HumanCharacter localPlayer;
    private DatabaseManager databaseManager = new DatabaseManager();
    private boolean muted = true;
    private boolean godmode = false;
    private List<NotMovingEntity> notMovingEntities;
    private List<MovingEntity> movingEntities;
    private List<HumanCharacter> players;
    private Vector2 mousePosition;
    private int steps = 1;

    private ClientS client = ClientS.getInstance();

    //  MP_Score
    private HashMap<String, Integer> scoreBoard;

    private Administration(Account localAccount) {

        this.notMovingEntities = new ArrayList<>();
        this.movingEntities = new ArrayList<>();
        this.players = new ArrayList<>();
        this.localAccount = localAccount;
        this.gameTextures = new GameTexture();
        this.gameSounds = new GameSounds(this);

        this.scoreBoard = new HashMap<>();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName(), h.getScore());
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateEntities();
            }
        }, 10);
    }

    public static Administration getInstance() {
        if (instance == null) {
            instance = new Administration(new Account(Utils.getRandomName(6)));
            instance.setClientS();
        }
        return instance;
    }
    public void setClientS(){
        if(instance == null){
            getInstance();
        }
        client = ClientS.getInstance();
    }
    public long secondsToMillis(int seconds) {
        return seconds * 1000;
    }

    public void removeEntity(Entity entity) {

        if (entity instanceof MovingEntity) {
            movingEntities.remove(entity);
            if (entity instanceof HumanCharacter)
                System.out.println("remove human");
            //  TODO change end callofcactus condition for iteration 2 of the callofcactus

        } else if (entity instanceof NotMovingEntity) {
            notMovingEntities.remove(entity);
        }
    }

    public int angle(Vector2 beginVector, Vector2 endVector) {
        return (360 - (int) Math.toDegrees(Math.atan2(endVector.y - beginVector.y, endVector.x - beginVector.x))) % 360;
    }

    public Vector2 getMouse() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();

        return new Vector2(x, y);
    }
    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(int x, int y) {
        this.mousePosition = new Vector2(x,y);
    }
    public GameTexture getGameTextures() {
        return gameTextures;
    }

    public ClientS getClient() {
        return client;
    }

    public GameSounds getGameSounds() {
        return gameSounds;
    }

    public boolean getMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean getGodmode() {
        return godmode;
    }

    public void setGodmode(boolean godmode) {
        this.godmode = godmode;
    }

    public HumanCharacter getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(HumanCharacter localPlayer) {
        this.localPlayer = localPlayer;
    }

    public Account getLocalAccount() {
        return localAccount;
    }

    public void setLocalAccount(Account localAccount) {
        this.localAccount = localAccount;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public List<NotMovingEntity> getNotMovingEntities() {
        return notMovingEntities;
    }

    public List<MovingEntity> getMovingEntities() {
        return movingEntities;
    }

    public List<HumanCharacter> getPlayers() {

        List<MovingEntity> searchables = movingEntities;
        List<HumanCharacter> returnValues = searchables.stream().filter(e -> e instanceof HumanCharacter).map(e -> (HumanCharacter) e).collect(Collectors.toList());
        return returnValues;
    }

    public List<Entity> getAllEntities() {
        List<Entity> entities = new ArrayList<Entity>();
        entities.addAll(notMovingEntities);
        entities.addAll(movingEntities);
        return entities;
    }

    public void updateScoreBoard()
    {
        Collections.sort(this.players);

        this.scoreBoard.clear();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName(), h.getScore());
        }
    }

    public HashMap<String, Integer> getScoreBoard() {
        Collections.sort(this.players);

        this.scoreBoard.clear();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName(), h.getScore());
        }

        return this.scoreBoard;
    }

    public void updateEntities() {
//        players           = client.getLatestUpdatesPlayers(players);
//        movingEntities    = client.getLatestUpdatesMovingEntities(movingEntities);
//        notMovingEntities = client.getLatestUpdatesNotMovingEntities(notMovingEntities);

        updateScoreBoard();
    }

    public void setEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof HumanCharacter) {
                players.add((HumanCharacter) entity);
            } else if (entity instanceof MovingEntity) {
                movingEntities.add((MovingEntity) entity);
            } else if (entity instanceof NotMovingEntity) {
                notMovingEntities.add((NotMovingEntity) entity);
            }
        }
    }

    public void addEntity(Entity e){
        if( e instanceof MovingEntity){movingEntities.add((MovingEntity) e);};
        if( e instanceof NotMovingEntity){notMovingEntities.add((NotMovingEntity) e);};
        if( e instanceof HumanCharacter){players.add((HumanCharacter) e);};
    }


    /**
     * Calculates the new position between the currentPosition to the Endposition.
     *
     * @param currentPosition : The current position of the object
     * @param EndPosition     : The position of the end point
     * @param speed           : The speed that the object can move with
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition, double speed) {

        float x = currentPosition.x;
        float y = currentPosition.y;

        //gets the difference of the two x coordinates
        double differenceX = EndPosition.x - x;
        //gets the difference of the two y coordinates
        double differenceY = EndPosition.y - y;

        //pythagoras formula
        double c = Math.sqrt(Math.pow(Math.abs(differenceX), 2) + Math.pow(Math.abs(differenceY), 2));

        if (c <= (steps * speed)) {
            return EndPosition;
        }

        double ratio = c / (steps * speed);

        x += (differenceX / ratio);
        y += (differenceY / ratio);

        return new Vector2(x, y);
    }

    /**
     * Calculates the new position from a beginposition and a angle..
     *
     * @param currentPosition : The current position of the object
     * @param speed           : The speed that the object can move with
     * @param angle           : The angle of where the object should be heading
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle) {

        double newAngle = angle + 90f;

        double x = currentPosition.x;
        double y = currentPosition.y;

        //uses sin and cos to calculate the EndPosition
        x = x + (Math.sin(Math.toRadians(newAngle)) * (steps * speed));
        y = y + (Math.cos(Math.toRadians(newAngle)) * (steps * speed));

        float xF = Float.parseFloat(Double.toString(x));
        float yF = Float.parseFloat(Double.toString(y));
        return new Vector2(xF, yF);
    }

    public HumanCharacter searchPlayer(int id) {

        for (HumanCharacter p : players) {
            if (p.getID() == id) {
                HumanCharacter player = p;
                return player;
            }
        }

        return null;
    }

    public void addSinglePlayerHumanCharacter() {
        Player p = new HumanCharacter(null, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), "CaptainCactus", new Sniper(), GameTexture.texturesEnum.playerTexture, 64, 26);
        players.add((HumanCharacter) p);
        localPlayer = (HumanCharacter) p;
        client.sendMessageAndReturn(new Command(Command.methods.POST, new Entity[]{p}, Command.objectEnum.HumanCharacter));
    }
}
