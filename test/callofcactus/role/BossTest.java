package callofcactus.role;

import junit.framework.TestCase;

/**
 * @author Teun
 */
public class BossTest extends TestCase {

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