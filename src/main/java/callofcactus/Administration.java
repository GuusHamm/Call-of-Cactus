package callofcactus;

import callofcactus.entities.HumanCharacter;

/**
 * Created by woute on 23-11-2015.
 */
public class Administration {

    HumanCharacter localPlayer;
    GameTexture gameTextures;
    GameSounds gameSounds;
    boolean muted=true;

    public Administration(HumanCharacter localPlayer, GameTexture gameTextures, GameSounds gameSounds)
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

    boolean godmode=false;


    public HumanCharacter getLocalPlayer() {
        return localPlayer;
    }



}
