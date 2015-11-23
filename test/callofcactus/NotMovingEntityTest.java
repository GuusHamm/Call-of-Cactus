package callofcactus;

import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * @author Wouter
 */
public class NotMovingEntityTest extends TestCase {

	private NotMovingEntity entity;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		entity = new NotMovingEntity(new GameMockup(), new Vector2(1, 1), false, 1, false, null, 20, 20);
	}

	@Test
	public void testIsSolid() throws Exception {
		assertEquals(false, entity.isSolid());
	}

	@Test
	public void testGetHealth() throws Exception {
		assertEquals(1, entity.getHealth());
	}

	@Test
	public void testGetLocation() throws Exception {
		assertEquals(1.f, entity.getLocation().x);
		assertEquals(1.f, entity.getLocation().y);
	}
//not-implemented yet //TODO implement notmovingtest
//    @Test
//    public void testDestroy() throws Exception {
//        entity.destroy();
//        assertNull(entity);
//    }

}