package callofcactus.role;

import callofcactus.BaseTest;

/**
 * Created by guushamm on 2-11-15.
 */
public class AITest extends BaseTest {

	private AI testAI;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		testAI = new AI();
	}

	public void testGetName() throws Exception {
		assertEquals("AI", testAI.getName());
	}
}