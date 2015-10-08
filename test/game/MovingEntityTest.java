package game;

import game.role.Tank;
import junit.framework.TestCase;

import javafx.geometry.Point2D;


/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class MovingEntityTest extends TestCase {

    Bullet bullet;

    public void setUp() throws Exception {
        super.setUp();
        bullet= new Bullet(new HumanCharacter(new Game(1,1,false,100), new Point2D(1, 1), "testplayer", new Tank() ,new Point2D(1,1)) ,new Point2D(1,1));

    }

    public void tearDown() throws Exception {

    }

    public void testGetBaseSpeed() throws Exception {
        
    }

    public void testSetBaseSpeed() throws Exception {

    }

    public void testUpdate() throws Exception {

    }

    public void testMove() throws Exception {

    }
}