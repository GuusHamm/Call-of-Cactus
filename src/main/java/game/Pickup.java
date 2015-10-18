package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import javafx.geometry.Point2D;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int health;
	private double speedMultiplier;
	private double healthMultiplier;
	private double damageMultiplier;

	/**
	 * Makes a new instance of the class Pickup
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 * @param effectTime : Time this pickup will exists
	 * @param health : Damage that this object can take before being destroyed
	 * @param speedMultiplier
	 * @param healthMultiplier
	 */
	public Pickup(Game game, Vector2 location,int effectTime, int health, double speedMultiplier, double healthMultiplier,Texture spriteTexture) {
		// TODO - implement Pickup.Pickup
		super(game, location,spriteTexture);
		throw new UnsupportedOperationException();
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