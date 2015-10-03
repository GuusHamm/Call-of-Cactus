package com.netgames.coc.game;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int health;
	private double speedMultiplier;
	private double healthMulitplier;
	private double damageMultplier;

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

	/**
	 * 
	 * @param effectTime
	 * @param health
	 * @param speedMultiplier0
	 * @param healthMultiplier
	 */
	public Pickup(int effectTime, int health, double speedMultiplier0, double healthMultiplier) {
		// TODO - implement Pickup.Pickup
		throw new UnsupportedOperationException();
	}

}