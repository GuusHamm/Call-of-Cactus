package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.Entity;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.MovingEntity;
import callofcactus.entities.NotMovingEntity;
import callofcactus.io.DatabaseManager;
import callofcactus.menu.GameScreen;
import callofcactus.multiplayer.ClientS;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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

    private ClientS client;

    public Administration(Account localAccount) {
        this.localAccount = localAccount;
        this.gameTextures = new GameTexture();
        this.gameSounds = new GameSounds(this);
//        ClientS s = new ClientS();
//        s.sendMessageAndReturn(new Command(Command.methods.GET,null));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateEntities();
            }
        }, 10);

    }

    public static Administration getInstance() {
        if (instance == null) {
            instance = new Administration(new Account("Captain Cactus"));
        }
        return instance;
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

    public void updateEntities() {
//        players           = client.getLatestUpdatesPlayers(players);
//        movingEntities    = client.getLatestUpdatesMovingEntities(movingEntities);
//        notMovingEntities = client.getLatestUpdatesNotMovingEntities(notMovingEntities);

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

    public void sendChanges() {

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

}
