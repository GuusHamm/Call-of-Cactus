package callofcactus.entities.pickups;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Teun on 19-10-2015.
 */
public class HealthPickup extends Pickup {

    private double healthBoost = 10;

    /**
     * @param game          : The callofcactus in which the ammo will spawn
     * @param location      : The location where the pickup will spawn
     * @param spriteTexture : The texture of the pickup
     * @param spriteWidth   : The width of the pickup
     * @param spriteHeight  : The height of the pickup
     */
    public HealthPickup(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight) {
        super(game, location, spriteTexture, spriteWidth, spriteHeight);
    }

    /**
     * @return the ammount of Health you gained
     */
    public double getHealthBoost() {
        return healthBoost;
    }

}
