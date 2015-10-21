package game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;
import game.MovingEntity;

public abstract class Pickup extends MovingEntity
{

	private int effectTime;
	private int health;
	private double speedMultiplier;
	private double healthMultiplier;
	private double damageMultiplier;

	/**
	 * Makes a new instance of the class Pickup
	 * @param game     				: The game of which the entity belongs to
	 * @param location 				: Coordinates of the entity
	 * @param effectTime 			: Time this pickup will exists
	 * @param health 				: Damage that this object can take before being destroyed
	 * @param speedMultiplier		: The number which speed will be multiplied with
	 * @param healthMultiplier		: The number which health will be multiplied with
	 */
	protected Pickup(Game game,
				  Vector2 location,
				  Texture spriteTexture,
				  int effectTime,
				  int health,
				  double speedMultiplier,
				  double healthMultiplier,
				  double damageMultiplier,
					 int spriteWidth,
					 int spriteHeight)
	{
		super(game, location, spriteTexture, spriteWidth,spriteHeight);
		this.effectTime = effectTime;
		this.health = health;
		this.speedMultiplier = speedMultiplier;
		this.healthMultiplier = healthMultiplier;
		this.damageMultiplier = damageMultiplier;
	}

	public int getEffectTime() {
		return this.effectTime;
	}

	public int getHealth() {
		return this.health;
	}

	public double getSpeedMultiplier() {
		return this.speedMultiplier;
	}

	public double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}

}