package callofcactus;

import callofcactus.map.DefaultMap;
import junit.framework.TestCase;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * @author Teun
 */
public class DefaultMapTest extends TestCase {

	@Test
	public void testMap() throws Exception {
		Game game = new GameMockup();

		DefaultMap defaultMap = new DefaultMap(game, 1920, 1080);
	}
}