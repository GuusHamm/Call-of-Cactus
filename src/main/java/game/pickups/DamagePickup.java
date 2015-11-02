package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Teun
 */
public class DamagePickup extends Pickup {

	private static double DAMAGE_MULTIPLIER = 1.5;
	private static double HEALTH_MULTIPLIER = 0.75;
	private static int DEFAULT_TIME = 10;

	public DamagePickup(Game game, Vector2 location, int effectTime, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, effectTime, 1, 1, HEALTH_MULTIPLIER, DAMAGE_MULTIPLIER, spriteWidth, spriteHeight);
	}

	public DamagePickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, spriteTexture, DEFAULT_TIME, 1, 1, HEALTH_MULTIPLIER, DAMAGE_MULTIPLIER, spriteWidth, spriteHeight);
	}

}
