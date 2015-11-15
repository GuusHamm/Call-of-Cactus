package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;
import game.MovingEntity;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int initialValue;

	/**
	 * Makes a new instance of the class Pickup
	 *
	 * @param game             : The game of which the entity belongs to
	 * @param location         : Coordinates of the entity
	 * @param spriteHeight     : The height of characters sprite
	 * @param spriteTexture    : game.Texture to use for this AI
	 * @param spriteWidth      : The width of characters sprite
	 */
	protected Pickup(Game game,
					 Vector2 location,
					 Texture spriteTexture,
					 int spriteWidth,
					 int spriteHeight) {

		super(game, location, spriteTexture, spriteWidth, spriteHeight);
		this.effectTime = 20;
	}

	/**
	 *
	 * @return the duration of this effect
	 */
	public int getEffectTime() {
		return this.effectTime;
	}

	/**
	 *
	 * @return the health of this object
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 *
	 * @return the value before the pickup
	 */
	public int getInitialValue() {
		return initialValue;
	}

	/**
	 *
	 * @param initialValue : What the initalvalue is before the change
	 */
	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 *
	 * @param damageDone : The ammount of damage done
	 * @return the health of the pickup ?????
	 */
	@Override
	public int takeDamage(int damageDone){
		return health;
	}

}