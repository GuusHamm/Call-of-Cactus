package callofcactus;


import com.badlogic.gdx.math.Vector2;

/**
 * @author Wouter
 */
public class GameTest extends BaseTest {

	private callofcactus.IGame game;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		game = new SinglePlayerGame();
	}

//    public void testGetAccountsInGame() throws Exception {
//    //not-implemented testClasses
////        Account a =  new Account("","","","");
////        a.joinGame(callofcactus);
////        assertEquals("Account was not properly added to the callofcactus.", 1, callofcactus.getAccountsInGame().size());
//    }
//
//    public void testCollisionDetect() throws Exception {
//        //non working testClasses
////        Rectangle a = new Rectangle(1,1,10,10);
////        Rectangle b = new Rectangle(2,2,10,10);
////        assertTrue("",callofcactus.collisionDetect(a,b));
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
}