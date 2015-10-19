package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * Created by Teun on 19-10-2015.
 */
public class HealthPickup extends Pickup
{

    public HealthPickup(Game game, Vector2 location, int effectTime, Texture spriteTexture)
    {
        super(game, location, spriteTexture, effectTime, 1, 1, 1.5, 1);
    }

    public HealthPickup(Game game, Vector2 location, Texture spriteTexture)
    {
        this(game, location, 10, spriteTexture);
    }

}
