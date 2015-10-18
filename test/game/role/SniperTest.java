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

    public void testConstructor() throws Exception {
        assertEquals(0.65, sniper.getHealthMultiplier());
        assertEquals(5.0, sniper.getDamageMultiplier());
        assertEquals(0.25, sniper.getSpeedMultiplier());
        assertEquals(1.2, sniper.getFireRateMultiplier());
    }

    public void testToString() throws Exception {
        assertEquals("Sniper", sniper.toString());
    }
}