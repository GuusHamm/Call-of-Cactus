package callofcactus.entities;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class NotMovingEntity extends Entity implements Serializable{

    private boolean solid;
    private int health;
    private boolean canTakeDamage;
    private transient Rectangle hitbox;

    /**
     * Makes a new instance of the class NotMovingEntity
     *
     * @param game          : The callofcactus of which the entity belongs to
     * @param location      : Coordinates of the entity
     * @param solid         : True when solid (Not able to move through), false when not solid (Able to move through)
     * @param health        : Damage that this object can take before being destroyed, null if the object is indestructible
     * @param canTakeDamage : True if it`s able to destroy this object, false if that is not the case
     * @param spriteHeight  The height of characters sprite
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     */
    public NotMovingEntity(IGame game, Vector2 location, boolean solid, int health, boolean canTakeDamage, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer) {
        // TODO - implement NotMovingEntity.NotMovingEntity
        super(game, location, spriteTexture, spriteWidth, spriteHeight, fromServer);
        this.solid = solid;
        this.health = health;
        this.canTakeDamage = canTakeDamage;
        this.hitbox = new Rectangle(location.x, location.y, spriteWidth, spriteHeight);
    }

    public boolean isSolid() {
        return this.solid;
    }

    public int getHealth() {
        return this.health;
    }

    public int takeDamage(int damage) {

        if (canTakeDamage && solid) {
            health -= damage;
        }

        if (health <= 0) {
            this.destroy();
        }
        return damage;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

//    @Override
//    public void writeObject(ObjectOutputStream stream) throws IOException {
//        super.writeObject(stream);
//        stream.writeFloat(hitbox.getX());
//        stream.writeFloat(hitbox.getY());
//        stream.writeFloat(hitbox.getWidth());
//        stream.writeFloat(hitbox.getHeight());
//    }
//
//    @Override
//    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        super.readObject(stream);
//        hitbox = new Rectangle(stream.readFloat(), stream.readFloat(), stream.readFloat(), stream.readFloat());
//    }

}