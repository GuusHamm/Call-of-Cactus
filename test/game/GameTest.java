package game;


import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;
import testClasses.GameMockup;

/**
 * @author Wouter
 */
public class GameTest extends TestCase {

	game.Game game;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		game = new GameMockup();
	}

	@Override
	public void tearDown() throws Exception {

	}

//    public void testGetAccountsInGame() throws Exception {
//    //not-implemented testClasses
////        Account a =  new Account("","","","");
////        a.joinGame(game);
////        assertEquals("Account was not properly added to the game.", 1, game.getAccountsInGame().size());
//    }
//
//    public void testCollisionDetect() throws Exception {
//        //non working testClasses
////        Rectangle a = new Rectangle(1,1,10,10);
////        Rectangle b = new Rectangle(2,2,10,10);
////        assertTrue("",game.collisionDetect(a,b));
//    }
//
//    public void testGenerateSpawn() throws Exception {
//        //needs non-implemented classes
//        //TODO testgeneratespawn
//    }

	public void testAngle() throws Exception {
		Vector2 a = new Vector2(10, 10);
		Vector2 b = new Vector2(20, 10);
		assertEquals(0, game.angle(a, b));
	}

	public void testCalculateNewPosition() throws Exception {

		Vector2 begin = new Vector2(10, 10);
		Vector2 end = new Vector2(20, 20);

		int speed = 1;

		double resultX = (begin.x + (Math.sin(Math.toRadians(45)) * speed));
		double resultY = (begin.y + (Math.cos(Math.toRadians(45)) * speed));

		float floatX = Float.parseFloat(String.valueOf(resultX));
		float floatY = Float.parseFloat(String.valueOf(resultY));


		assertEquals("The calculated position is not correct", new Vector2(floatX, floatY), game.calculateNewPosition(begin, end, speed));

	}

	public void testCalculateNewPosition1() throws Exception {
		//calculates with the angle
		Vector2 a = new Vector2(10, 10);
		assertEquals("The calculated position is not correct", new Vector2(10f, 11f), game.calculateNewPosition(a, 1, 270));
	}

	public void testAddEntityToGame() throws Exception {

	}

	public void testRemoveEntityFromGame() throws Exception {

	}

	public void testCreate() throws Exception {

	}
}