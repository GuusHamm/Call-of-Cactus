package callofcactus.role;

import callofcactus.BaseTest;

/**
 * @author Teun
 */
public class BossTest extends BaseTest {

	private Boss testBoss;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		testBoss = new Boss();
	}

	public void testGetName() throws Exception {
		assertEquals("Boss", testBoss.getName());
	}
}