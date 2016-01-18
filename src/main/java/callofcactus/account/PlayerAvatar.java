package callofcactus.account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Created by Teun on 18-1-2016.
 */
public class PlayerAvatar {

    public static final int MAX = 3;
    public static final int MIN = 1;

    public static final String DEFAULT_PATH = "avatars/";
    public static final String DEFAULT_EXTENSION = ".png";

    private int id;

    public PlayerAvatar(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return DEFAULT_PATH + id + DEFAULT_EXTENSION;
    }

    public FileHandle getFileHandle() {
        return Gdx.files.internal(getFilePath());
    }

    public Image getImage() {
        return new Image(new Texture(getFileHandle()));
    }

    public Sprite getSprite() {
        return new Sprite(new Texture(getFileHandle()));
    }

    public SpriteDrawable getSpriteDrawable() {
        return new SpriteDrawable(getSprite());
    }

    public PlayerAvatar next() {
        id = (id >= MAX) ? MIN : id + 1;
        return this;
    }
}
