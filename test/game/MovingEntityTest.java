package game;

import game.role.Tank;
import junit.framework.TestCase;

import javafx.geometry.Point2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;


/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class MovingEntityTest extends TestCase {

    Bullet bullet;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Game game = new Game(1, 1, false, 100);
        Point2D location = new Point2D(1, 1);
        String name = "testplayer";
        Tank rol = new Tank();

        HumanCharacter human = new HumanCharacter(game, location, name, rol);

        bullet= new Bullet(human.getGame(), new Point2D(1,1), human);

        bullet.setBaseSpeed(1);

    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetBaseSpeed() throws Exception {
        //Create a variable with the speed you want to set
        double speed = 1.1;
        //Set the base speed to the valie of above variable
        bullet.setBaseSpeed(speed);
        //Test if getBaseSpeed returns previous setted speed
        assertEquals("De snelheid van de kogel is niet gelijk aan wat er verwacht was", bullet.getBaseSpeed(), 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBaseSpeed() throws Exception {
        //Setting the speed to a negative value. Exception expected
        bullet.setBaseSpeed(-1.1);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testMove() throws Exception {
        Point2D endLocation = new Point2D(2 ,2);
        Point2D beginLocation = bullet.getLocation();

        //This is the root of 2
        bullet.setBaseSpeed(1.41421356237);
        bullet.move(new Point2D(2, 2));

        assertEquals("In case you get this error the move method did not return the correct value", bullet.location, endLocation);
    }
}