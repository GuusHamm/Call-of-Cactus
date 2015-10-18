package game.role;

import junit.framework.TestCase;

/**
 * @author Teun
 */
public class BossTest extends TestCase {

    private Boss testBoss, testBoss2;

    public void setUp() throws Exception {
        super.setUp();

        testBoss = new Boss();
        testBoss2 = new Boss();
    }

    public void testToString() throws Exception {
        assertEquals("Boss", testBoss.toString());
        assertEquals("Boss", testBoss2.toString());
    }
}