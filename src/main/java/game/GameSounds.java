package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Wouter Vanmulken on 5-11-2015.
 */
public class GameSounds {

    private Sound[] hitSounds ;
    private Sound[] bulletSound ;
    private Game game;

    public GameSounds(Game g)
    {
        this.game = g;
        hitSounds = new Sound[]{
                Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab2.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab3.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab4.mp3"))
        };
        bulletSound = new Sound[] {
                Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3"))
        };
    }

    /**
     * Plays a random sound of 'HIT'
     */
    public void playRandomHitSound() {
        if(game.getGodmode() || game.isMuted())
            return;
        // TODO Unit Test
        ((Sound) Utils.getRandomObjectFromArray(hitSounds)).play(.4f);
    }

    /**
     * Plays a random sound of 'FIRE'
     */
    public void playBulletFireSound()
    {
        if(game.getGodmode() || game.isMuted())
            return;
        ((Sound) Utils.getRandomObjectFromArray(bulletSound)).play(.3f);
    }
}
