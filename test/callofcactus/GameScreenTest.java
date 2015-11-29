package callofcactus;

import callofcactus.menu.GameScreen;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Teun
 */
public class GameScreenTest extends BaseTest {

	@Override
	@Before
	public void setUp() throws Exception {
		// TODO Cant be tested, because libGDX
		GameInitializer gi = new GameInitializer();
		gi.getBatch();
	}
}

