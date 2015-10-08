package game;

import javafx.geometry.Point2D;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int health;
	private double speedMultiplier;
	private double healthMulitplier;
	private double damageMultplier;

	/**
	 * Makes a new instance of the class Pickup
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 * @param effectTime : Time this pickup will excists
	 * @param health : Damage that this object can take before beeing destroyed
	 * @param speedMultiplier
	 * @param healthMultiplier
	 */
	public Pickup(Game game, Point2D location,int effectTime, int health, double speedMultiplier, double healthMultiplier) {
		// TODO - implement Pickup.Pickup
		super(game, location);
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

	public double getHealthMulitplier() {
		return this.healthMulitplier;
	}

	public double getDamageMultplier() {
		return this.damageMultplier;
	}

}