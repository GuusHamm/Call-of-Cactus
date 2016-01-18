package callofcactus;

import callofcactus.entities.HumanCharacter;
import callofcactus.role.Role;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class EntityTest extends BaseTest {
	private IGame game;
	private HumanCharacter humanCharacter;
	private Vector2 location;

	@BeforeClass
	public void setUp() throws Exception {
		this.game = new SinglePlayerGame();

		this.location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();

		humanCharacter = new HumanCharacter(game, location, name, role, GameTexture.texturesEnum.playerTexture, 64, 64, true);
	}

	@Test
	public void testGetLocation() throws Exception {
		assertEquals(this.location, humanCharacter.getLocation());

	}

	@Test
	public void testGetSpriteWidth() throws Exception {
		assertEquals(64, humanCharacter.getSpriteWidth());

	}

	@Test
	public void testGetSpriteHeight() throws Exception {
		assertEquals(64, humanCharacter.getSpriteHeight());

	}

	@Test
	public void testDestroy() throws Exception {
		assertTrue(game.getMovingEntities().contains(humanCharacter));

		assertTrue(humanCharacter.destroy());

		assertFalse(game.getMovingEntities().contains(humanCharacter));

	}

	@Test
	public void testSetHealth()
	{
		humanCharacter.setHealth(10, false);
		assertEquals(humanCharacter.getHealth(), 10);
	}

	@Test
	public void testSetLastLocation()
	{
		Vector2 vector2 = new Vector2(1, 1);
		humanCharacter.setLastLocation(vector2);
		humanCharacter.setToLastLocation(vector2, false);
		assertEquals(humanCharacter.getLastLocation(), vector2);
	}

	@Test
	public void testSetID()
	{
		int id = 13;
		humanCharacter.setID(id, false);

		assertEquals(humanCharacter.getID(), id);
	}

	@Test
	public void testChangeCommand()
	{
		humanCharacter.setID(13, true);
	}

	@Test
	public void testGetDamage() throws Exception {
		assertEquals(10, humanCharacter.getDamage());
	}

	@Test
	public void testTameDamage() throws Exception
	{
		int startHealth = humanCharacter.getHealth();
		humanCharacter.takeDamage(1, false);
		assertEquals(startHealth - 1, humanCharacter.getHealth());
	}
	@Test
	public void testsetlocation() throws Exception {
		Vector2 lastLocation = humanCharacter.getLocation();
		Vector2 newLocation = new Vector2(lastLocation.x + 1, lastLocation.y + 1);

		humanCharacter.setLocation(newLocation,false);

		assertEquals(newLocation, humanCharacter.getLocation());
	}


	@Test
	public void testLastlocation() throws Exception {
		Vector2 lastLocation = humanCharacter.getLocation();
		Vector2 newLocation = new Vector2(lastLocation.x + 1, lastLocation.y + 1);

		humanCharacter.setLastLocation(newLocation);

		assertEquals(newLocation, humanCharacter.getLastLocation());
	}

}