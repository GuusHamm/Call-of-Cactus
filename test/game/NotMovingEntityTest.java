package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testClasses.TestGameInitializer;

/**
 * Created by Wouter Vanmulken  on 8-10-2015.
 */
public class NotMovingEntityTest extends TestCase {

    NotMovingEntity entity;
    @Before
    public void setUp() throws Exception {
        super.setUp();

        TestGameInitializer gameInitializer = new TestGameInitializer();

        Texture wallTexture = gameInitializer.getGame().getWallTexture();
        entity = new NotMovingEntity(new Game(1,2,false,1),new Vector2(1,1),false,1, false,wallTexture);
    }
    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void testIsSolid() throws Exception {
        assertEquals(false,entity.isSolid());
    }
    @Test
    public void testGetHealth() throws Exception {
        assertEquals(1,entity.getHealth());
    }

    @Test
    public void testGetLocation() throws Exception {
        assertEquals(1.0, entity.getLocation().x);
        assertEquals(1.0, entity.getLocation().y);
    }
//not-implemented yet //TODO implement notmovingtest
//    @Test
//    public void testDestroy() throws Exception {
//        entity.destroy();
//        assertNull(entity);
//    }

}