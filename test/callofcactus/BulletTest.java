package callofcactus;

import callofcactus.entities.Bullet;
import callofcactus.entities.HumanCharacter;
import callofcactus.map.MapFiles;
import callofcactus.role.Boss;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by xubuntu on 12-10-15.
 */
public class BulletTest extends BaseTest {
	private Bullet bullet;
	private HumanCharacter human;
	private IGame game;

	@BeforeClass
	public void setUp() throws Exception {

		game = new SinglePlayerGame();
		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Boss rol = new Boss();
		Texture bulletTexture = null;
		Texture playerTexture = null;

		human = new HumanCharacter(game, location, name, rol, GameTexture.texturesEnum.playerTexture, 64, 64, false);

		bullet = new Bullet(human.getGame(), new Vector2(1, 1), human, 100, 1, GameTexture.texturesEnum.bulletTexture, 0, 10, 10, false);

		bullet.setSpeed(1, false);
	}

	@Test
	public void testGetVelocity() throws Exception {
		//The standard speed of a bullet is 20, the speedMultiplier of boss is 0.5, so 20 * 0.5 = 10
		bullet.setSpeed(20, false);
        //bullet.getspeed applies a role-based-multiplier to the speed property.

		assertEquals("This error indicates that the expected Velocity doesn't match the actual one", 15, bullet.getSpeed());
		assertEquals("This error will show when the damage you expected was different than the actual value", bullet.getDamage(), 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDamageBadValue() throws IllegalArgumentException {
		try{

			bullet.setDamage(-5,false);
			fail();
		}
		catch(IllegalArgumentException e){

		}
	}

	@Test
	public void testGetShooter() throws Exception {
		assertEquals("The shooters aren't the same", bullet.getShooter(), human);

	}
}