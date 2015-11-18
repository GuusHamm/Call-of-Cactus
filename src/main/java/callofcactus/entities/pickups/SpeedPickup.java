package callofcactus.entities.pickups;

import callofcactus.Game;
import com.badlogic.gdx.graphics.Texture;
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
	public SpeedPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	/**
	 * @return The speed you gained
	 */
	public double getSpeedBoost() {
		return speedBoost;
	}
}
