package callofcactus.entities.pickups;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Teun
 */
public class SpeedPickup extends Pickup {

	private double speedBoost = 1.2;

	/**
	 * @param game          : The callofcactus in which the ammo will spawn
	 * @param location      : The location where the pickup will spawn
	 * @param spriteTexture : The texture of the pickup
	 * @param spriteWidth   : The width of the pickup
	 * @param spriteHeight  : The height of the pickup
	 */
	public SpeedPickup(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	/**
	 * @return The speed you gained
	 */
	public double getSpeedBoost() {
		return speedBoost;
	}
}
