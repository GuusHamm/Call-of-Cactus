package callofcactus.entities.pickups;

import callofcactus.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Guus
 */
public class FireRatePickup extends Pickup {

	private double fireRateBoost = 1.2;

	/**
	 * @param game          : The callofcactus in which the ammo will spawn
	 * @param location      : The location where the pickup will spawn
	 * @param spriteTexture : The texture of the pickup
	 * @param spriteWidth   : The width of the pickup
	 * @param spriteHeight  : The height of the pickup
	 */
	public FireRatePickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	/**
	 * @return The ammount of which your fire rate will rise
	 */
	public double getFireRateBoost() {
		return fireRateBoost;
	}
}
