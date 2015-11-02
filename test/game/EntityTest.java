package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;
import game.role.Soldier;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by Wouter Vanmulken on 8-10-2015.
 */
public class EntityTest extends TestCase {
	private Game game;
	private HumanCharacter humanCharacter;
	private Vector2 location;

	@Before
	public void setUp() throws Exception {
		this.game = new GameMockup();

		this.location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();
		Texture playerTexture = null;

		humanCharacter = new HumanCharacter(game, location, name, role, playerTexture, 64, 64);
	}

	@Test
	public void testGetLocation() throws Exception {
		assertEquals(this.location, humanCharacter.getLocation());

	}

	@Test
	public void testDestroy() throws Exception {
		assertTrue(game.getMovingEntities().contains(humanCharacter));

		assertTrue(humanCharacter.destroy());

		assertFalse(game.getMovingEntities().contains(humanCharacter));

	}

	@Test
	public void testGetDamage() throws Exception {
		assertEquals(10, humanCharacter.getDamage());
	}

	@Test
	public void testGetLastlocation() throws Exception {
		Vector2 lastLocation = humanCharacter.getLocation();

//		humanCharacter.move(new Vector2(lastLocation.x + 1,lastLocation.y+1));

		assertEquals(lastLocation, humanCharacter.getLastLocation());
	}

	@Test
	public void testPaint() throws Exception {
		//Todo Implent Test

	}
}