package com.netgames.coc.game.role;

public abstract class Role {

	private double healthMultiplier;
	private double damageMultiplier;
	private double speedMultipier;
	private double fireRateMultiplier;

	public double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	public double getSpeedMultipier() {
		return this.speedMultipier;
	}

}