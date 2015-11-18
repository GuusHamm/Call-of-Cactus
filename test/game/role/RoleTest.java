package game.role;

import callofcactus.role.Sniper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by xubuntu on 19-10-15.
 */
public class RoleTest {

	private Sniper sniper;

	@Before
	public void setUp() throws Exception {
		sniper = new Sniper();

	}

	@Test
	public void testSetHealthMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setHealthMultiplier(multiplier);
		assertEquals(multiplier, sniper.getHealthMultiplier(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetHealthMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setHealthMultiplier(multiplier);
	}

	@Test
	public void testSetDamageMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setDamageMultiplier(multiplier);
		assertEquals(multiplier, sniper.getDamageMultiplier(), 0.0);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetDamageMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setDamageMultiplier(multiplier);
	}

	@Test
	public void testSetSpeedMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setSpeedMultiplier(multiplier);
		assertEquals(multiplier, sniper.getSpeedMultiplier(), 0.);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetSpeedMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setSpeedMultiplier(multiplier);
	}

	@Test
	public void testSetFireRateMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setFireRateMultiplier(multiplier);
		assertEquals(multiplier, sniper.getFireRateMultiplier(), 0.0);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetFireRateMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setFireRateMultiplier(multiplier);
	}
}