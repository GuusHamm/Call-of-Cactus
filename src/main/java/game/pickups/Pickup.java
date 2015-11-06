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
	 * @param spriteHeight     The height of characters sprite
	 * @param spriteTexture    Texture to use for this AI
	 * @param spriteWidth      The width of characters sprite
	 */
	protected Pickup(Game game,
					 Vector2 location,
					 Texture spriteTexture,
					 int spriteWidth,
					 int spriteHeight) {

		super(game, location, spriteTexture, spriteWidth, spriteHeight);
		this.effectTime = 20;
	}

	public int getEffectTime() {
		return this.effectTime;
	}

	public int getHealth() {
		return this.health;
	}

	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
	}

	@Override
	public int takeDamage(int damageDone){
		return health;
	}

}