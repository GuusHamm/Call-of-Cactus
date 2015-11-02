package game.role;

import junit.framework.TestCase;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by guushamm on 2-11-15.
 */
public class AITest extends TestCase{

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