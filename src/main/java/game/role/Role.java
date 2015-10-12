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

	public double getFireRateMultiplier()
	{
		return fireRateMultiplier;
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

	/**
	 *
	 * @param value The new value for the healthMultiplier, can't be 0 or less
	 */
	public void setHealthMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		healthMultiplier = value;
	}

	/**
	 *
	 * @param value The new value for the damageMultiplier, can't be 0 or less
	 */
	public void setDamageMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		damageMultiplier = value;
	}

	/**
	 *
	 * @param value The new value for the speedMultiplier, can't be 0 or less
	 */
	public void setSpeedMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		speedMultiplier = value;
	}

	/**
	 *
	 * @param value The new value for the fireRateMultiplier, can't be 0 or less
	 */
	public void setFireRateMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		fireRateMultiplier = value;
	}


}