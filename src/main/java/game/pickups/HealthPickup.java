package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * Created by Teun on 19-10-2015.
 */
public class HealthPickup extends Pickup {

	private static double HEALTH_MULTIPLIER = 10;
	private static int DEFAULT_EFFECTTIME = 10;

	public HealthPickup(Game game, Vector2 location, int effectTime, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, effectTime, 1, 1, HEALTH_MULTIPLIER, 1, spriteWidth, spriteHeight);
	}

	public HealthPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, DEFAULT_EFFECTTIME, 1, 1, HEALTH_MULTIPLIER, 1, spriteWidth, spriteHeight);
	}

}
