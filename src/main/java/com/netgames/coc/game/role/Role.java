package com.netgames.coc.game.role;

public abstract class Role {

	// Multiplies the base health of a player with the multiplier
	private double healthMultiplier;
    // Multiplies the base health of a player with the multiplier
    private double damageMultiplier;
    // Multiplies the base health of a player with the multiplier
    private double speedMultipier;
    // Multiplies the base health of a player with the multiplier
    private double fireRateMultiplier;

	/**
	 * @return the healthmultiplier
	 */
	public double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	/**
	 * @return the damagemultiplier
	 */
	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	/**
	 * @return the speedmulitplier
	 */
	public double getSpeedMultipier() {
		return this.speedMultipier;
	}

}