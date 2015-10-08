package game;

import javafx.geometry.Point2D;
import junit.framework.TestCase;

/**
 * Created by Wouter Vanmulken  on 8-10-2015.
 */
public class NotMovingEntityTest extends TestCase {

    NotMovingEntity entity;
    public void setUp() throws Exception {
        super.setUp();
        entity = new NotMovingEntity(new Game(1,2,false,1),new Point2D(1,1),false,1, false);
    }

    public void tearDown() throws Exception {

    }

    public void testIsSolid() throws Exception {
        assertEquals(false,entity.isSolid());
    }

    public void testGetHealth() throws Exception {
        assertEquals(1,entity.getHealth());
    }

    public void testGetLocation() throws Exception {
        assertEquals(1, entity.getLocation().getX());
        assertEquals(1, entity.getLocation().getY());
    }

    public void testDestroy() throws Exception {
        entity.destroy();
        assertNull(entity);
    }

}