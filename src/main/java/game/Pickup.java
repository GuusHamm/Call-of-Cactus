package game;

public abstract class Pickup extends MovingEntity {

	private int effectTime;
	private int health;
	private double speedMultiplier;
	private double healthMulitplier;
	private double damageMultplier;

	/**
	 * Makes a new instance of the class Pickup
	 * @param effectTime : Time this pickup will excists
	 * @param health : Damage that this object can take before beeing destroyed
	 * @param speedMultiplier
	 * @param healthMultiplier
	 */
	public Pickup(int effectTime, int health, double speedMultiplier, double healthMultiplier) {
		// TODO - implement Pickup.Pickup
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