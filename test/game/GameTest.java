package game;

import com.badlogic.gdx.math.Vector2;
import javafx.geometry.Point2D;
import junit.framework.TestCase;

import java.awt.*;

/**
 * Created by woute on 14-10-2015.
 */
public class GameTest extends TestCase {

    Game game;

    public void setUp() throws Exception {
        super.setUp();
        game = new Game(10,1,false,100);
    }

    public void tearDown() throws Exception {

    }

    public void testGetAccountsInGame() throws Exception {
    //not-implemented test
//        Account a =  new Account("","","","");
//        a.joinGame(game);
//        assertEquals("Account was not properly added to the game.", 1, game.getAccountsInGame().size());
    }

    public void testGetGameLevel() throws Exception {
        assertEquals("gamelevel was noet properly set",10,game.getGameLevel());
    }

    public void testGetMaxScore() throws Exception {
        assertEquals("Gamelevel was noet properly set",100,game.getMaxScore());
    }

    public void testGetMaxNumberOfPlayers() throws Exception {
        assertEquals("Max. number of players was not properly set",1,game.getMaxNumberOfPlayers());
    }

    public void testGetMouse() throws Exception {
        double x = MouseInfo.getPointerInfo().getLocation().getX();
        double y = MouseInfo.getPointerInfo().getLocation().getY();
        assertEquals("Mousepositions did not match",new Point2D(x,y), game.getMouse());
    }

    public void testCollisionDetect() throws Exception {
        //non working test
//        Rectangle a = new Rectangle(1,1,10,10);
//        Rectangle b = new Rectangle(2,2,10,10);
//        assertTrue("",game.collisionDetect(a,b));
    }

    public void testGenerateSpawn() throws Exception {
        //needs non-implemented classes
        //TODO testgeneratespawn
    }

    public void testAngle() throws Exception {
        Vector2 a = new Vector2(10,10);
        Vector2 b = new Vector2(20,10);
        assertEquals(0, game.angle(a, b));
    }

    public void testCalculateNewPosition() throws Exception {
        //calculates with two points
        Vector2 a = new Vector2(10,10);
        Vector2 b = new Vector2(20,10);
        assertEquals("The calculated position is not correct", new Point2D(
                (10+Math.cos(Math.toRadians(315))),
                (10+Math.cos(Math.toRadians(315)))),
                                game.calculateNewPosition(a, b, 1));
    }

    public void testCalculateNewPosition1() throws Exception {
        //calculates with the angle
        Vector2 a = new Vector2(10,10);
        assertEquals("The calculated position is not correct", new Point2D(10,11), game.calculateNewPosition(a, 1, 270));
    }

    public void testAddEntityToGame() throws Exception {

    }

    public void testRemoveEntityFromGame() throws Exception {

    }

    public void testCreate() throws Exception {

    }
}