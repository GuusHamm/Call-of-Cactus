package callofcactus.account;

/**
 * Created by Teun on 18-1-2016.
 */
public class PlayerAvatar {

    public static final String DEFAULT_PATH = "avatars/";
    public static final String DEFAULT_EXTENSION = ".png";

    public static String getFilePath(int id) {
        return DEFAULT_PATH + id + DEFAULT_EXTENSION;
    }

}
