package game.role;

import org.junit.Assert;
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
		assertEquals("The health was not correctly multiplied", multiplier, sniper.getHealthMultiplier());
	}

	@Test
	public void testSetHealthMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setHealthMultiplier(multiplier);
	}

	@Test
	public void testSetDamageMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setDamageMultiplier(multiplier);
		assertEquals("The damage was not correctly set", multiplier, sniper.getDamageMultiplier());

	}
	@Test
	public void testSetDamageMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setDamageMultiplier(multiplier);
	}
	@Test
	public void testSetSpeedMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setSpeedMultiplier(multiplier);
		assertEquals("The speed was not correctly set", multiplier, sniper.getSpeedMultiplier());

	}

	@Test
	public void testSetSpeedMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setSpeedMultiplier(multiplier);
	}

	@Test
	public void testSetFireRateMultiplier() throws Exception {
		double multiplier = 1.25;

		sniper.setFireRateMultiplier(multiplier);
		assertEquals("The firerate was not correctly multiplied", multiplier, sniper.getFireRateMultiplier());

	}
	@Test
	public void testSetFireRateMultiplierBadValue() throws IllegalArgumentException {
		double multiplier = -1;

		sniper.setFireRateMultiplier(multiplier);
	}
}