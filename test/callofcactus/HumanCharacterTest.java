package callofcactus;

import callofcactus.entities.HumanCharacter;
import callofcactus.map.MapFiles;
import callofcactus.role.Role;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xubuntu on 12-10-15.
 */
public class HumanCharacterTest extends BaseTest {
	private HumanCharacter humanCharacter;


	@Before
	public void setUp() throws Exception {
		IGame game = new SinglePlayerGame(MapFiles.MAPS.COMPLICATEDMAP);

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();

		humanCharacter = new HumanCharacter(game, location, name, role, GameTexture.texturesEnum.damagePickupTexture, 64, 64, false);


	}

	@Test
	public void testGetScore() throws Exception {
		assertEquals(0, humanCharacter.getScore());

	}

	@Test
	public void testTakeDamage() throws Exception {
		int startHealth = humanCharacter.getHealth();

		humanCharacter.takeDamage(startHealth - 1);

		assertEquals(startHealth - (startHealth - 1), humanCharacter.getHealth());
		humanCharacter.takeDamage(startHealth - 1);
	}

	@Test
	public void testMove() throws Exception {
		Vector2 location = new Vector2(1, 1);

		humanCharacter.move(location);

		assertEquals(location, humanCharacter.getLocation());

		location = new Vector2(-1, 1);
		Vector2 properLocation = new Vector2(0, 1);

		humanCharacter.move(location);

		assertEquals(properLocation, humanCharacter.getLocation());

		location = new Vector2(1, -1);
		properLocation = new Vector2(1, 0);

		humanCharacter.move(location);

		assertEquals(properLocation, humanCharacter.getLocation());

		location = new Vector2(1, -1);
		properLocation = new Vector2(1, 0);

		humanCharacter.move(location);

		assertEquals(properLocation, humanCharacter.getLocation());


		location = new Vector2(801, 1);
		properLocation = new Vector2(800, 1);

		humanCharacter.setLocation(properLocation,false);

		humanCharacter.move(location);

		assertEquals(properLocation, humanCharacter.getLocation());

		location = new Vector2(1, 481);
		properLocation = new Vector2(1, 480);

		humanCharacter.setLocation(properLocation,false);

		humanCharacter.move(location);

		assertEquals(properLocation, humanCharacter.getLocation());


	}


}