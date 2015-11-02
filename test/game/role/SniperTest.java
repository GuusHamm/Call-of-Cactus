package game.role;

import junit.framework.TestCase;

/**
 * @author Teun
 */
public class SniperTest extends TestCase {

	private Sniper sniper;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		sniper = new Sniper();
	}

	public void testGetName() throws Exception {
		assertEquals("Sniper", sniper.getName());
	}
}