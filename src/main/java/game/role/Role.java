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
	private int ammo;


	/**
	 * Default role constructor, all values are 1.0 by default
	 */
	protected Role() {
		this.healthMultiplier = 1.0;
		this.damageMultiplier = 1.0;
		this.speedMultiplier = 1.0;
		this.fireRateMultiplier = 1.0;
		this.ammo = 60;
	}

	protected Role(double healthMultiplier, double damageMultiplier, double speedMultiplier, double fireRateMultiplier,int ammo) {
		this.healthMultiplier = healthMultiplier;
		this.damageMultiplier = damageMultiplier;
		this.speedMultiplier = speedMultiplier;
		this.fireRateMultiplier = fireRateMultiplier;
		this.ammo = ammo;
	}

	/**
	 * @return the healthMultiplier
	 */
	public double getHealthMultiplier() {
		return this.healthMultiplier;
	}

	/**
	 * @param value The new value for the healthMultiplier, can't be 0 or less
	 */
	public void setHealthMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		healthMultiplier = value;
	}

	public double getFireRateMultiplier() {
		return fireRateMultiplier;
	}

	/**
	 * @param value The new value for the fireRateMultiplier, can't be 0 or less
	 */
	public void setFireRateMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		fireRateMultiplier = value;
	}

	/**
	 * @return the damageMultiplier
	 */
	public double getDamageMultiplier() {
		return this.damageMultiplier;
	}

	/**
	 * @param value The new value for the damageMultiplier, can't be 0 or less
	 */
	public void setDamageMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		damageMultiplier = value;
	}

	/**
	 * @return the speedMultiplier
	 */
	public double getSpeedMultiplier() {
		return this.speedMultiplier;
	}

	/**
	 * @param value The new value for the speedMultiplier, can't be 0 or less
	 */
	public void setSpeedMultiplier(double value) {
		if (value <= 0) {
			throw new IllegalArgumentException();
		}
		speedMultiplier = value;
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int modifier) {
		this.ammo += modifier;
	}
}