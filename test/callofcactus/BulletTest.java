package callofcactus;

import callofcactus.entities.Bullet;
import callofcactus.entities.HumanCharacter;
import callofcactus.role.Boss;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by xubuntu on 12-10-15.
 */
public class BulletTest extends BaseTest {
	private Bullet bullet;
	private HumanCharacter human;
	private IGame game;

	@Before
	public void setUp() throws Exception {

		game = new SinglePlayerGame();
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();
		Texture bulletTexture = null;
		Texture playerTexture = null;

		human = new HumanCharacter(game, location, name, rol, playerTexture, 64, 64);

		bullet = new Bullet(human.getGame(), new Vector2(1, 1), human, 100, 1, bulletTexture, 0, 10, 10);

		bullet.setSpeed(1);
	}

	@Test
	public void testGetVelocity() throws Exception {
		//The standard speed of a bullet is 20, the speedMultiplier of boss is 0.5, so 20 * 0.5 = 5
		bullet.setSpeed(20);
		assertEquals("This error indicates that the expected Velocity doesn't match the actual one", 40, bullet.getSpeed());
		assertEquals("This error will show when the damage you expected was different than the actual value", bullet.getDamage(), 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDamageBadValue() throws IllegalArgumentException {
		bullet.setDamage(-5);
	}

	@Test
	public void testGetShooter() throws Exception {
		assertEquals("The shooters aren't the same", bullet.getShooter(), human);

	}
}