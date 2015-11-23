package callofcactus;

import callofcactus.entities.Bullet;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.MovingEntity;
import callofcactus.role.Role;
import callofcactus.role.Sniper;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

import static org.junit.Assert.assertNotEquals;

/**
 * @author Guus
 */
public class PlayerTest extends BaseTest {
	private HumanCharacter humanCharacter;
	private Role role;
	private Game game;

	@Before
	public void setUp() throws Exception {


		this.game = new GameMockup();

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		role = new Soldier();

		humanCharacter = new HumanCharacter(game, location, name, role, null, 64, 64);
	}

	@Test
	public void testGetRole() throws Exception {
		assertEquals(role, humanCharacter.getRole());
	}

	@Test
	public void testTakeDamage() throws Exception {
		//Get the starting health
		int startHealth = humanCharacter.getHealth();

		//Make the player take damage
		humanCharacter.takeDamage(5);

		assertEquals("The player is not taking damage properly", startHealth - 5, humanCharacter.getHealth());

	}

	@Test
	public void testFireBullet() throws Exception {
		//Todo Implent Test

		humanCharacter.fireBullet(null);

		boolean bulletInGame = false;

		for (MovingEntity movingEntity : this.game.getMovingEntities()) {
			if (movingEntity.getClass() == Bullet.class) {
				Bullet bullet = (Bullet) movingEntity;
				if (bullet.getShooter() == humanCharacter) {
					bulletInGame = true;
				}
			}
		}

		assertTrue(bulletInGame);

	}

	@Test
	public void testChangeRole() throws Exception {
		Role startingRole = humanCharacter.getRole();

		int startingHealth = humanCharacter.getHealth();
		int startingDamage = humanCharacter.getDamage();
		int startingSpeed = humanCharacter.getSpeed();
		int startingFireRate = humanCharacter.getFireRate();


		Role newRole = new Sniper();

		humanCharacter.changeRole(newRole);

		assertNotEquals("the role has not changed", startingRole, humanCharacter.getRole());
		assertEquals("the new role is not the one that was set", newRole, humanCharacter.getRole());

		assertNotEquals("the health has not changed after switching role", startingHealth, humanCharacter.getHealth());
		assertNotEquals("the damage has not changed after switching role", startingDamage, humanCharacter.getDamage());
		assertNotEquals("the speed has not changed after switching role", startingSpeed, humanCharacter.getSpeed());
		assertNotEquals("the fire-rate has not changed after switching role", startingFireRate, humanCharacter.getFireRate());
	}

	@Test
	public void testGetDirection() throws Exception {
		// The default direction when an object is initialized will be 0
		int direction = (int) humanCharacter.getAngle();
		assertEquals("The initial direction was not set properly", 0, direction);


	}
}