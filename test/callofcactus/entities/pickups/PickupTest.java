package callofcactus.entities.pickups;

import callofcactus.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import testClasses.GameMockup;

import static org.junit.Assert.assertEquals;


/**
 * @author Guus
 */
public class PickupTest {

	private Pickup p;
	private Pickup pickupWithoutTime;
	private Pickup speedPickup;
	private Pickup speedPickupWithoutTime;
	private Pickup healthPickup;
	private Pickup healthPickupWithoutTime;
	private Vector2 spawnlocation = new Vector2(100, 100);
	private Game game;

	@Before
	public void setUp() throws Exception {
		//Todo Implent Test
		game = new GameMockup();

		//	public DamagePickup(Game callofcactus, Vector2 location, int effectTime, callofcactus.Texture spriteTexture, int spriteWidth, int spriteHeight)
		p = new DamagePickup(game, spawnlocation, null, 10, 10);
		//	public DamagePickup(Game callofcactus, Vector2 location, callofcactus.Texture spriteTexture, int spriteWidth, int spriteHeight)
		pickupWithoutTime = new DamagePickup(game, spawnlocation, null, 10, 10);

		speedPickup = new SpeedPickup(game, spawnlocation, null, 10, 10);
		speedPickupWithoutTime = new SpeedPickup(game, spawnlocation, null, 10, 10);

		healthPickup = new HealthPickup(game, spawnlocation, null, 10, 10);
		healthPickupWithoutTime = new HealthPickup(game, spawnlocation, null, 10, 10);
	}

	@Test
	public void testGetEffectTime() throws Exception {
		//Todo Implent Test
		// The time was set in the setUp to 5 for p and 10(default) for pickupWithoutTime
		assertEquals("The effectTime was not Expected!", p.getEffectTime(), 5);
		assertEquals("The effectTime was not Expected!", pickupWithoutTime.getEffectTime(), 10);


	}

	@Test
	public void testGetHealth() throws Exception {
		//Todo Implent Test
		//The health of the Pickup is 1
		assertEquals("The health was not Expected!", p.getHealth(), 1);

	}

	//TODO- Fix this
//	@Test
//	public void testGetSpeedMultiplier() throws Exception {
//		//Todo Implent Test
//        org.junit.Assert.assertEquals(p.getSpeedBoost(), 1, 1e-6);
//
//    }
//
//	@Test
//	public void testGetHealthMultiplier() throws Exception {
//		//Todo Implent Test
//        org.junit.Assert.assertEquals(p.getHealthBoost(), 0.75, 1e-6);
//	}
//
//	@Test
//	public void testGetDamageMultiplier() throws Exception {
//		//Todo Implent Test
//        org.junit.Assert.assertEquals(p.getDamageBoost(), 1.5, 1e-6);
//	}
}