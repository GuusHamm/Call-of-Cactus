package game;

import callofcactus.Game;
import callofcactus.Map;
import junit.framework.TestCase;
import org.junit.Test;
import testClasses.GameMockup;

/**
 * Created by guushamm on 2-11-15.
 */
public class MapTest extends TestCase {

	private Map map;

	@Test
	public void testMap() throws Exception {
		Game game = new GameMockup();

		map = new Map(game, 1920, 1080);
	}
}