package callofcactus.role;

import callofcactus.BaseTest;

/**
 * @author Teun
 */
public class SniperTest extends BaseTest {

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