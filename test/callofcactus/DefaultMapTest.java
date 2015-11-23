package callofcactus;

import callofcactus.map.DefaultMap;
import junit.framework.TestCase;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by guushamm on 2-11-15.
 */
public class DefaultMapTest extends TestCase {

	private DefaultMap defaultMap;

	@Test
	public void testMap() throws Exception {
		Game game = new GameMockup();

		defaultMap = new DefaultMap(game, 1920, 1080);
	}
}