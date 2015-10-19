package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;

/**
 * @author Teun
 */
public class DamagePickup extends Pickup
{
    public DamagePickup(Game game, Vector2 location, int effectTime, Texture spriteTexture)
    {
        super(game, location, spriteTexture, effectTime, 1, 1, 0.75, 1.5);
    }

    public DamagePickup(Game game, Vector2 location, Texture spriteTexture)
    {
        this(game, location, 10, spriteTexture);
    }

}
