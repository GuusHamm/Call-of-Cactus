package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Guus
 */
public class FireRatePickup extends Pickup {

	private double fireRateBoost = 1.2;

	public FireRatePickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	public double getFireRateBoost() {
		return fireRateBoost;
	}
}
