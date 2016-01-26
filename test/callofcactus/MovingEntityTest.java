package callofcactus;

import callofcactus.entities.Bullet;
import callofcactus.entities.HumanCharacter;
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

		IGame game = new SinglePlayerGame();
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();

		humanCharacter = new HumanCharacter(game, location, name, rol, GameTexture.texturesEnum.playerTexture, 64, 64, true);

		bullet = new Bullet(humanCharacter.getGame(), new Vector2(1, 1), humanCharacter, 100, 1, GameTexture.texturesEnum.bulletTexture, 0, 10, 10, false);
		bullet.setSpeed(1, false);

	}

	@Test
	public void testGetDamage() throws Exception {
		humanCharacter.setDamage(10,false);
		assertEquals(10, humanCharacter.getDamage());
	}

	@Test
	public void testGetBaseSpeed() throws Exception {
		//Create a variable with the speed you want to set
		int speed = 1;
		//Set the base speed to the valie of above variable
		bullet.setSpeed(speed, false);

		//Test if getBaseSpeed returns previous setted speed
		System.out.println("Speed: " + speed + ", bullet speed: " + bullet.getSpeed());
		assertEquals("The speed of the bullet did not equal what was excpected", bullet.getSpeed(), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBaseSpeed() throws Exception {
		//Setting the speed to a negative value. Exception expected
		bullet.setSpeed(-1, false);
	}

	@Test
	public void testMove() throws Exception {
		//This is the root of 2
		bullet.setSpeed((int) Math.sqrt(2), false);
		bullet.move(new Vector2(2, 2), false);
		assertEquals(bullet.getSpeed(), (int)Math.sqrt(2));

		// TODO Fix this error
//		assertEquals("In case you get this error the move method did not return the correct value", bullet.location, endLocation);
	}

    @Test
    public void testSetDamage(){
        bullet.setDamage(100,false);

        assertEquals("Returned damage does not equal the expected damage", 100, bullet.getDamage());
    }

    @Test
    public void testSetDamage2(){
        try{
            bullet.setDamage(-1,false);
            fail();
        }
        catch(IllegalArgumentException e){

        }
    }
}