package game.role;

import junit.framework.TestCase;

/**
 * @author Teun
 */
public class BossTest extends TestCase {

    private Boss testBoss, testBoss2;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        testBoss = new Boss();
        testBoss2 = new Boss();
    }

    public void testGetName() throws Exception {
        assertEquals("Boss", testBoss.getName());
        assertEquals("Boss", testBoss2.getName());
    }
}