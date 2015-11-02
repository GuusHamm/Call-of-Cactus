package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Teun
 */
public class SpeedPickup extends Pickup {

	private static double SPEED_MULTIPLIER = 1.5;
	private static int DEFAULT_EFFECTTIME = 10;

	public SpeedPickup(Game game, Vector2 location, int effectTime, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, effectTime, 1, SPEED_MULTIPLIER, 1, 1, spriteWidth, spriteHeight);
	}

	public SpeedPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, DEFAULT_EFFECTTIME, 1, SPEED_MULTIPLIER, 1, 1, spriteWidth, spriteHeight);
	}
}
