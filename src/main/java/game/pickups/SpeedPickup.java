package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Teun
 */
public class SpeedPickup extends Pickup {

	private double speedBoost = 1.2;

	public SpeedPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	public double getSpeedBoost() {
		return speedBoost;
	}
}
