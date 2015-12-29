package callofcactus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Wouter Vanmulken on 5-11-2015.
 */
public class GameSounds {

    private Sound[] hitSounds;
    private Sound[] bulletSound;
    private Sound[] walkSound;
    private Administration administration;

    public GameSounds(Administration a) {
        administration = a;
        try {
            hitSounds = new Sound[]{
                    Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab2.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab3.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab4.mp3"))
            };
            bulletSound = new Sound[]{
                    Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3"))
            };
            walkSound = new Sound[]{
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot1.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot2.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot3.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot4.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot5.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot6.mp3")),
                    Gdx.audio.newSound(Gdx.files.internal("sounds/walking/coc_boot7.mp3"))
            };
        } catch (Exception e) {
            hitSounds = new Sound[]{};
            bulletSound = new Sound[]{};
        }
    }

    public void playRandomHitSound() {
        if (administration.getGodmode() || administration.getMuted())
            return;
        Utils.getRandomEntry(hitSounds).play(.4f);
    }

    public void playBulletFireSound() {
        if (administration.getGodmode() || administration.getMuted())
            return;
        Utils.getRandomEntry(bulletSound).play(.3f);
    }
    public void playRandomWalkSound() {
        if (administration.getGodmode() || administration.getMuted())
            return;
        Utils.getRandomEntry(walkSound).play(.2f);
    }
}
