package game.role;

public abstract class Role {

	// Multiplies the base health of a player with the multiplier
	private double healthMultiplier;
    // Multiplies the base health of a player with the multiplier
    private double damageMultiplier;
    // Multiplies the base health of a player with the multiplier
    private double speedMultiplier;
    // Multiplies the base health of a player with the multiplier
    private double fireRateMultiplier;

	/**
	 * @return the healthMultiplier
	 */
	public double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	/**
	 * @return the damageMultiplier
	 */
	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	/**
	 * @return the speedMultiplier
	 */
	public double getSpeedMultiplier() {
		return this.speedMultiplier;
	}

}