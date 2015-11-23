package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.HumanCharacter;
import callofcactus.io.DatabaseManager;
import callofcactus.menu.GameScreen;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class Administration {

    private GameTexture gameTextures;
    private GameSounds gameSounds;

    private GameScreen gameScreen;

    private Account localAccount;
    private HumanCharacter localPlayer;

    private DatabaseManager databaseManager = new DatabaseManager();

    private boolean muted=true;
    private boolean godmode=false;

    private static Administration instance = null;

    public static Administration getInstance() {
        if(instance == null) {
            instance = new Administration(new Account("Captain Cactus"));
        }
        return instance;
    }

    public Administration(Account localAccount)
    {
        this.localAccount = localAccount;
        this.gameTextures = new GameTexture();
        this.gameSounds = new GameSounds(this);
    }

    public GameTexture getGameTextures() {
        return gameTextures;
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



}
