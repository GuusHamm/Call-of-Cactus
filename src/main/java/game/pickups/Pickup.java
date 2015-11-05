package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;
import game.MovingEntity;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int health;
	private double speedBoost;
	private double healthBoost;
	private double damageBoost;

	/**
	 * Makes a new instance of the class Pickup
	 *
	 * @param game             : The game of which the entity belongs to
	 * @param location         : Coordinates of the entity
	 * @param effectTime       : Time this pickup will exists
	 * @param health           : Damage that this object can take before being destroyed
	 * @param speedMultiplier  : The number which speed will be multiplied with
	 * @param healthBoost : The number which health will be multiplied with
	 * @param damageMultiplier The number which damage will be multiplied with
	 * @param spriteHeight     The height of characters sprite
	 * @param spriteTexture    Texture to use for this AI
	 * @param spriteWidth      The width of characters sprite
	 */
	protected Pickup(Game game,
					 Vector2 location,
					 Texture spriteTexture,
					 int effectTime,
					 int health,
					 double speedMultiplier,
					 double healthBoost,
					 double damageMultiplier,
					 int spriteWidth,
					 int spriteHeight) {

		super(game, location, spriteTexture, spriteWidth, spriteHeight);
		this.effectTime = effectTime;
		this.health = health;
		this.speedBoost = speedMultiplier;
		this.healthBoost = healthBoost;
		this.damageBoost = damageMultiplier;
	}

	public int getEffectTime() {
		return this.effectTime;
	}

	public int getHealth() {
		return this.health;
	}

	public double getSpeedBoost() {
		return this.speedBoost;
	}

	public double getHealthBoost() {
		return this.healthBoost;
	}

	public double getDamageBoost() {
		return this.damageBoost;
	}

	@Override
	public int takeDamage(int damageDone){
		return health;
	}

}