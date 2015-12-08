package callofcactus.multiplayer;

import callofcactus.GameTexture;
import callofcactus.MultiPlayerGame;
import callofcactus.entities.Entity;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.math.Vector2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by woute on 7-12-2015.
 */
public class SerializerTest {

    Entity entities;
    Entity entities2;

    ServerS serverS;
    ClientS clientS;


    @Before
    public void setUp(){


        entities = new NotMovingEntity(new MultiPlayerGame(),new Vector2(0,0),true,10,false, GameTexture.texturesEnum.wallTexture,10,10);
//        entities = new Entity[]{new NotMovingEntity(new MultiPlayerGame(),new Vector2(0,0),true,10,false, GameTexture.texturesEnum.wallTexture,10,10)};

    }

    String test;

    @Test
    public void testSerialeDesiredObjects64() throws Exception {
        test = new Serializer2().toString(entities);
        System.out.println(test);

        entities2= new Serializer2().fromString(test);

//        System.out.println("length 1 :" + entities.length + "; length 2 : " +entities2.length);
        System.out.println(entities.toString());
        System.out.println(entities2.toString());
        System.out.println("expected :" + entities.getLocation());
        System.out.println("reality :" + entities2.getLocation());
        Assert.assertSame(entities, entities2);
    }

}