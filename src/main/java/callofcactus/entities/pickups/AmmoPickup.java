package callofcactus.entities.pickups;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Guus on 2015-11-05.
 */
public class AmmoPickup extends Pickup {

    private double ammoBoost = 30;

    /**
     * @param game          : The callofcactus in which the ammo will spawn
     * @param location      : The location where the pickup will spawn
     * @param spriteTexture : The texture of the pickup
     * @param spriteWidth   : The width of the pickup
     * @param spriteHeight  : The height of the pickup
     */
    public AmmoPickup(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer) {
        super(game, location, spriteTexture, spriteWidth, spriteHeight, fromServer);
    }

    /**
     * @return the ammount of ammo the pickup will give you
     */
    public double getAmmoBoost() {
        return ammoBoost;
    }
}

