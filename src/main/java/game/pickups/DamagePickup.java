package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Teun
 */
public class DamagePickup extends Pickup {

	private double damageBoost = 1.2;

	/**
	 * @param game          : The game in which the ammo will spawn
	 * @param location      : The location where the pickup will spawn
	 * @param spriteTexture : The texture of the pickup
	 * @param spriteWidth   : The width of the pickup
	 * @param spriteHeight  : The height of the pickup
	 */
	public DamagePickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	/**
	 * @return the ammount of damage you will gain
	 */
	public double getDamageBoost() {
		return damageBoost;
	}
}
