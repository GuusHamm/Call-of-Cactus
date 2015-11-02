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
 * Created by xubuntu on 12-10-15.
 */
public class HumanCharacterTest extends TestCase {
	HumanCharacter humanCharacter;


	@Before
	public void setUp() throws Exception {
		Game game = new GameMockup();

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();
		Texture playerTexture = null;

		humanCharacter = new HumanCharacter(game, location, name, role, playerTexture, 64, 64);


	}

	@Test
	public void testGetScore() throws Exception {
		//Todo Implent Test

	}

	@Test
	public void testTakeDamage() throws Exception {
		int startHealth = humanCharacter.getHealth();

		humanCharacter.takeDamage(startHealth - 1);

		assertEquals(startHealth - (startHealth - 1), humanCharacter.getHealth());
		humanCharacter.takeDamage(startHealth - 1);
	}


}