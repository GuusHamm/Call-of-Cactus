package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Boss;
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

    @Before
    public void setUp() throws Exception {
		this.game = new GameMockup();

		Vector2 location = new Vector2(1, 1);
		String name = "testplayer";
		Role role = new Soldier();
		Texture playerTexture = null;

		humanCharacter = new HumanCharacter(game, location, name,role,playerTexture);
	}
    @Test
    public void testGetLocation() throws Exception {
        //Todo Implent Test

    }
    @Test
    public void testDestroy() throws Exception {
		assertTrue(game.getMovingEntities().contains(humanCharacter));

		assertTrue(humanCharacter.destroy());

		assertFalse(game.getMovingEntities().contains(humanCharacter));



    }
    @Test
    public void testPaint() throws Exception {
        //Todo Implent Test

    }
}