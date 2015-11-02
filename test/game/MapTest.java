package game;

import junit.framework.TestCase;
import org.junit.Before;
import testClasses.GameMockup;

import static org.junit.Assert.*;

/**
 * Created by guushamm on 2-11-15.
 */
public class MapTest  extends TestCase{

	private Map map;
	@Before
	public void testMap() throws Exception {
		Game game = new Game();

		map = new Map(game,1920,1080);
	}
}