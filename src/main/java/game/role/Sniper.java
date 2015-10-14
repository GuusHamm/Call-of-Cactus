package game.role;

public class Sniper extends Role {

	/**
	 * Makes a new instance of the class Sniper
	 */
	public Sniper() {
		// TODO - implement Sniper.Sniper
		this.setHealthMultiplier(0.65);
		this.setDamageMultiplier(5);
		this.setFireRateMultiplier(0.25);
		this.setSpeedMultiplier(1.2);
	}
	@Override
	public String toString(){
		return "Sniper";
	}

	// TODO - implements unique elements for this role

}