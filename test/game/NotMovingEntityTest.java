package game;

import javafx.geometry.Point2D;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Wouter Vanmulken  on 8-10-2015.
 */
public class NotMovingEntityTest extends TestCase {

    NotMovingEntity entity;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        entity = new NotMovingEntity(new Game(1,2,false,1),new Point2D(1,1),false,1, false);
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
        assertEquals(1, entity.getLocation().getX());
        assertEquals(1, entity.getLocation().getY());
    }

    @Test
    public void testDestroy() throws Exception {
        entity.destroy();
        assertNull(entity);
    }

}