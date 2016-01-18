package callofcactus.account;

import callofcactus.entities.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Teun on 18-1-2016.
 */
public class PlayerAvatarTest {

    @Test
    public void testLoadImage() throws Exception {

    }

    @Test
    public void testSaveImage() throws Exception {
        PlayerAvatar.saveImage("C:\\Projects\\Java\\Call-of-Cactus\\testResources\\avatars\\1.png", 1);
    }
}