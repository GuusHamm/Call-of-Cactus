package game;


import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;
import testClasses.TestGameInitializer;

import java.awt.*;

/**
 * Created by woute on 14-10-2015.
 */
public class GameTest extends TestCase
{

    game.Game game;

    public void setUp() throws Exception {
        super.setUp();

        TestGameInitializer gameInitializer = new TestGameInitializer();

        game = gameInitializer.getGame().getGame();
    }

    public void tearDown() throws Exception {

    }

    public void testGetAccountsInGame() throws Exception {
    //not-implemented testClasses
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
        float xF = Float.parseFloat(String.valueOf(x));
        float yF = Float.parseFloat(String.valueOf(y));
        assertEquals("Mousepositions did not match",new Vector2(xF,yF), game.getMouse());
    }

    public void testCollisionDetect() throws Exception {
        //non working testClasses
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
        double aD = (10+Math.cos(Math.toRadians(315)));
        double bD = (10+Math.cos(Math.toRadians(315)));
        float aF = Float.parseFloat(String.valueOf(aD));
        float bF = Float.parseFloat(String.valueOf(bD));
        assertEquals("The calculated position is not correct", new Vector2(aF, bF), game.calculateNewPosition(a, b, 1));
    }

    public void testCalculateNewPosition1() throws Exception {
        //calculates with the angle
        Vector2 a = new Vector2(10,10);
        assertEquals("The calculated position is not correct", new Vector2(10f,11f), game.calculateNewPosition(a, 1, 270));
    }

    public void testAddEntityToGame() throws Exception {

    }

    public void testRemoveEntityFromGame() throws Exception {

    }

    public void testCreate() throws Exception {

    }
}