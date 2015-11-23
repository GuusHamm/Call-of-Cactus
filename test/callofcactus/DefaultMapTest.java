package callofcactus;

import callofcactus.map.DefaultMap;
import org.junit.Test;

/**
 * Created by guushamm on 2-11-15.
 */
public class DefaultMapTest extends BaseTest {

	private DefaultMap defaultMap;

	@Test
	public void testMap() throws Exception {
		IGame game = new SinglePlayerGame();

		defaultMap = new DefaultMap(game, 1920, 1080);
	}
}