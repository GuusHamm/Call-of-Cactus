package callofcactus;

import callofcactus.menu.GameScreen;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jim on 17-10-2015.
 */
public class GameScreenTest extends TestCase {

	@Override
	@Before
	public void setUp() throws Exception {
		// TODO Cant be tested, because libGDX
		GameInitializer gi = new GameInitializer();
		GameScreen gs = new GameScreen(gi);

	}

	@Test
	public void testDrawHud() throws Exception {
		//Todo Implent Test

	}

	@Test
	public void testDrawPlayer() throws Exception {
		//Todo Implement Test
	}
}

