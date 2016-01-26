package callofcactus.entities;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Nino Vrijman
 */
public class DestructibleWall extends NotMovingEntity {

    private int cellX;
    private int cellY;

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    /**
     * Makes a new instance of the class NotMovingEntity
     *
     * @param game          : The callofcactus of which the entity belongs to
     * @param location      : Coordinates of the entity
     * @param solid         : True when solid (Not able to move through), false when not solid (Able to move through)
     * @param health        : Damage that this object can take before being destroyed, null if the object is indestructible
     * @param canTakeDamage : True if it`s able to destroy this object, false if that is not the case
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     * @param spriteHeight  The height of characters sprite
     * @param fromServer
     * @param cellX         : The x index of the cell in the tiled map
     * @param cellY         : The y index of the cell in the tiled map
     */
    public DestructibleWall(IGame game, Vector2 location, boolean solid, int health, boolean canTakeDamage, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer, int cellX, int cellY) {
        super(game, location, solid, health, canTakeDamage, spriteTexture, spriteWidth, spriteHeight, fromServer);
        this.cellX = cellX;
        this.cellY = cellY;
        this.fromServer = fromServer;
    }
}
