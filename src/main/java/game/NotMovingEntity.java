package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class NotMovingEntity extends Entity {

	private boolean solid;
	private int health;
	private boolean canTakeDamage;

	/**
	 * Makes a new instance of the class NotMovingEntity
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 * @param solid : True when solid (Not able to move through), false when not solid (Able to move through)
	 * @param health : Damage that this object can take before being destroyed, null if the object is indestructible
	 * @param canTakeDamage : True if it`s able to destroy this object, false if that is not the case
	 */
	public NotMovingEntity(Game game, Vector2 location,boolean solid, int health, boolean canTakeDamage,Texture spriteTexture, int spriteWidth,int spriteHeight) {
		// TODO - implement NotMovingEntity.NotMovingEntity
		super(game, location,spriteTexture, spriteWidth,spriteHeight);
		this.solid = solid;
		this.health = health;
		this.canTakeDamage = canTakeDamage;

	}

	public boolean isSolid() {
		return this.solid;
	}

	public int getHealth() {
		return this.health;
	}

	public int takeDamage(int damage){

        if	(canTakeDamage && solid)        {
            health -= damage;
        }

        if	(health<=0)        {
            this.destroy();
        }
		return  damage;
    }

}