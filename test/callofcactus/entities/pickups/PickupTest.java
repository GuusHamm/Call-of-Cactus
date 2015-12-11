package callofcactus.entities.pickups;

import callofcactus.BaseTest;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.SinglePlayerGame;
import callofcactus.map.MapFiles;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xubuntu on 12-10-15.
 */
public class PickupTest extends BaseTest {

	Pickup p;
	Pickup pickupWithoutTime;
	Pickup speedPickup;
	Pickup speedPickupWithoutTime;
	Pickup healthPickup;
	Pickup healthPickupWithoutTime;
	Vector2 spawnlocation = new Vector2(100, 100);
	IGame game;

	@Before
	public void setUp() throws Exception {
		//Todo Implent Test
		game = new SinglePlayerGame(MapFiles.MAPS.COMPLICATEDMAP);

		//	public DamagePickup(Game callofcactus, Vector2 location, int effectTime, callofcactus.Texture spriteTexture, int spriteWidth, int spriteHeight)
		p = new DamagePickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);
		//	public DamagePickup(Game callofcactus, Vector2 location, callofcactus.Texture spriteTexture, int spriteWidth, int spriteHeight)
		pickupWithoutTime = new DamagePickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);

		speedPickup = new SpeedPickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);
		speedPickupWithoutTime = new SpeedPickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);

		healthPickup = new HealthPickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);
		healthPickupWithoutTime = new HealthPickup(game, spawnlocation, GameTexture.texturesEnum.damagePickupTexture, 10, 10);
	}

	@Test
	public void testGetEffectTime() throws Exception {
		//Todo Implent Test
		// The time was set in the setUp to 5 for p and 10(default) for pickupWithoutTime
		org.junit.Assert.assertEquals("The effectTime was not Expected!", p.getEffectTime(), 5);
		org.junit.Assert.assertEquals("The effectTime was not Expected!", pickupWithoutTime.getEffectTime(), 10);


	}

	@Test
	public void testGetHealth() throws Exception {
		//Todo Implent Test
		//The health of the Pickup is 1
		org.junit.Assert.assertEquals("The health was not Expected!", p.getHealth(), 1);

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