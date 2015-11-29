package callofcactus;

import callofcactus.map.DefaultMap;
import org.junit.Test;

/**
 * @author Teun
 */
public class DefaultMapTest extends BaseTest {

	@Test
	public void testMap() throws Exception {
		IGame game = new SinglePlayerGame();

		DefaultMap defaultMap = new DefaultMap(game, 1920, 1080);
	}
}