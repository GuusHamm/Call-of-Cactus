package callofcactus.entities.pickups;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Teun
 */
public class DamagePickup extends Pickup {

    private double damageBoost = 1.2;

    /**
     * @param game          : The callofcactus in which the ammo will spawn
     * @param location      : The location where the pickup will spawn
     * @param spriteTexture : The texture of the pickup
     * @param spriteWidth   : The width of the pickup
     * @param spriteHeight  : The height of the pickup
     */
    public DamagePickup(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer) {
        super(game, location, spriteTexture, spriteWidth, spriteHeight, fromServer);
    }

    /**
     * @return the ammount of damage you will gain
     */
    public double getDamageBoost() {
        return damageBoost;
    }
}
