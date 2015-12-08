package callofcactus.multiplayer;

import callofcactus.GameTexture;
import callofcactus.MultiPlayerGame;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.math.Vector2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by woute on 7-12-2015.
 */
public class SerializerTest {

    NotMovingEntity notMovingEntity1;
    NotMovingEntity notMovingEntity2;

    String test;

    @Before
    public void setUp(){
        notMovingEntity1 = new NotMovingEntity(new MultiPlayerGame(), new Vector2(0, 0), true, 10, false, GameTexture.texturesEnum.wallTexture, 10, 10);
    }

    @Test
    public void testSerialeDesiredObjects64() throws Exception {
        test = new Serializer2().toString(notMovingEntity1);

        notMovingEntity2 = (NotMovingEntity) new Serializer2().fromString(test);

        System.out.println("expected :" + notMovingEntity1.getLocation());
        System.out.println("reality :" + notMovingEntity2.getLocation());
        Assert.assertEquals(notMovingEntity1.isSolid(), notMovingEntity2.isSolid());
        Assert.assertEquals(notMovingEntity1.getHealth(), notMovingEntity2.getHealth());
        Assert.assertEquals(notMovingEntity1.getLocation(), notMovingEntity2.getLocation());
        Assert.assertEquals(notMovingEntity1.getLastLocation(), notMovingEntity2.getLastLocation());
        Assert.assertEquals(notMovingEntity1.getSpriteWidth(), notMovingEntity2.getSpriteWidth());
        Assert.assertEquals(notMovingEntity1.getSpriteHeight(), notMovingEntity2.getSpriteHeight());
        Assert.assertEquals(notMovingEntity1.getDamage(), notMovingEntity2.getDamage());
        Assert.assertEquals(notMovingEntity1.getSpriteTexture(), notMovingEntity2.getSpriteTexture());
    }

}