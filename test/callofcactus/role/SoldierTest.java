package callofcactus.role;

import callofcactus.BaseTest;

/**
 * @author Teun
 */
public class SoldierTest extends BaseTest {

	private Soldier soldier;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		soldier = new Soldier();
	}

	public void testConstructor() throws Exception {
		assertEquals(1., soldier.getHealthMultiplier());
		assertEquals(1., soldier.getDamageMultiplier());
		assertEquals(1., soldier.getSpeedMultiplier());
		assertEquals(1., soldier.getFireRateMultiplier());
	}

	public void testGetName() throws Exception {
		assertEquals("Soldier", soldier.getName());
	}
}