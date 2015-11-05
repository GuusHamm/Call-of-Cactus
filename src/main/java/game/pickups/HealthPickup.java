package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * Created by Teun on 19-10-2015.
 */
public class HealthPickup extends Pickup {

	private double healthBoost = 10;

	public HealthPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, spriteWidth, spriteHeight);
	}

	public double getHealthBoost() {
		return healthBoost;
	}

}
