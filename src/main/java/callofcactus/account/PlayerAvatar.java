package callofcactus.account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.io.Serializable;

/**
 * Created by Teun on 18-1-2016.
 */
public class PlayerAvatar implements Serializable {

    public static final int MAX = 3;
    public static final int MIN = 1;

    public static final String DEFAULT_PATH = "avatars/";
    public static final String DEFAULT_EXTENSION = ".png";

    private int id;
    private Texture[] textures;

    public PlayerAvatar(int id) {
        this.id = id;
        textures = new Texture[MAX - MIN];
        Gdx.app.postRunnable(this::load);
    }

    private void load() {
        for (int i = MIN; i < MAX; i++) {
            textures[i - MIN] = new Texture(getFileHandle(i));
        }
    }

    public String getFilePath(int i) {
        return DEFAULT_PATH + i + DEFAULT_EXTENSION;
    }

    public FileHandle getFileHandle(int i) {
        return Gdx.files.internal(getFilePath(i));
    }

    public Sprite getSprite(int i) {
        return new Sprite(new Texture(getFileHandle(i)));
    }

    public SpriteDrawable getSpriteDrawable() {
        return new SpriteDrawable(getSprite(id));
    }

    public PlayerAvatar next() {
        id = (id > MAX) ? MIN : id + 1;
        System.out.println("New player icon set to " + id);
        return this;
    }

    public int getId() {
        return id;
    }
}
