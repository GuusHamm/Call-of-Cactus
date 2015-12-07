package callofcactus;

import org.junit.Before;

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

