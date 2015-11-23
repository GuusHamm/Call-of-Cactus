package callofcactus;

import callofcactus.entities.HumanCharacter;

/**
 * Created by woute on 23-11-2015.
 */
public class Administration {

    private HumanCharacter localPlayer;
    private GameTexture gameTextures;
    private GameSounds gameSounds;

    private boolean muted=true;
    private boolean godmode=false;

    private static Administration instance = null;

    protected Administration() {
        // Exists only to defeat instantiation.

    }

    public static Administration getInstance() {
        if(instance == null) {
            instance = new Administration(null);
        }
        return instance;
    }

    public Administration(HumanCharacter localPlayer)
    {
        this.localPlayer = localPlayer;
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



}
