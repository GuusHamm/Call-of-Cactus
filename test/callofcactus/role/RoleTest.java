package callofcactus.role;

import callofcactus.BaseTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xubuntu on 19-10-15.
 */
public class RoleTest extends BaseTest {

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

		try{

			sniper.setHealthMultiplier(multiplier);
			fail();
		}
		catch(IllegalArgumentException e){

		}
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

		try{

			sniper.setDamageMultiplier(multiplier);
			fail();
		}
		catch(IllegalArgumentException e){

		}
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

		try{

			sniper.setSpeedMultiplier(multiplier);
			fail();
		}
		catch(IllegalArgumentException e){

		}
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

		try{

			sniper.setFireRateMultiplier(multiplier);
			fail();
		}
		catch(IllegalArgumentException e){

		}
	}
}