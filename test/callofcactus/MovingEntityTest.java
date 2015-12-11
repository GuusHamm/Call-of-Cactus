package callofcactus;

import callofcactus.entities.Bullet;
import callofcactus.entities.HumanCharacter;
import callofcactus.map.MapFiles;
import callofcactus.role.Boss;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class MovingEntityTest extends BaseTest {

	private Bullet bullet;
	private HumanCharacter humanCharacter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		IGame game = new SinglePlayerGame(MapFiles.MAPS.COMPLICATEDMAP);
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();

		humanCharacter = new HumanCharacter(game, location, name, rol, null, 64, 64);

		bullet = new Bullet(humanCharacter.getGame(), new Vector2(1, 1), humanCharacter, 100, 1, null, 0, 10, 10);
		bullet.setSpeed(1);

	}

	@Test
	public void testGetDamage() throws Exception {
		assertEquals(humanCharacter.getDamage(), humanCharacter.getGame().getJSON().get("playerBaseDamage"));
	}

	@Test
	public void testGetBaseSpeed() throws Exception {
		//Create a variable with the speed you want to set
		int speed = 1;
		//Set the base speed to the valie of above variable
		bullet.setSpeed(speed);

		//Test if getBaseSpeed returns previous setted speed
		System.out.println("Speed: " + speed + ", bullet speed: " + bullet.getSpeed());
		assertEquals("The speed of the bullet did not equal what was excpected", bullet.getSpeed(), 1.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBaseSpeed() throws Exception {
		//Setting the speed to a negative value. Exception expected
		bullet.setSpeed(-1);
	}

	@Test
	public void testMove() throws Exception {
		//This is the root of 2
		bullet.setSpeed((int) Math.sqrt(2));
		bullet.move(new Vector2(2, 2));
		assertEquals(bullet.getSpeed(), Math.sqrt(2));

		// TODO Fix this error
//		assertEquals("In case you get this error the move method did not return the correct value", bullet.location, endLocation);
	}
}