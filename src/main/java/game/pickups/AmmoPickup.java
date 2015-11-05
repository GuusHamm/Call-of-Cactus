package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * Created by Guus on 2015-11-05.
 */
    public class AmmoPickup extends Pickup {

        private double ammoBoost = 30;

        public AmmoPickup(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {
            super(game, location, spriteTexture, spriteWidth, spriteHeight);
        }

        public double getAmmoBoost() {
            return ammoBoost;
        }
    }

