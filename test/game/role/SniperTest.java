package game.role;

import junit.framework.TestCase;

/**
 * @author Teun
 */
public class SniperTest extends TestCase {

    private Sniper sniper;

    public void setUp() throws Exception {
        super.setUp();

        sniper = new Sniper();
    }

    public void testToString() throws Exception {
        assertEquals("Sniper", sniper.toString());
    }
}