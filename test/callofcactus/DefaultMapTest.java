package callofcactus;

import callofcactus.map.DefaultMap;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by guushamm on 2-11-15.
 */
public class DefaultMapTest extends BaseTest {

	private DefaultMap defaultMap;

	@Test
	public void testMap() throws Exception {
		Game game = new GameMockup();

		defaultMap = new DefaultMap(game, 1920, 1080);
	}
}