package game.role;

public class Boss extends Role {

	/**
	 * Makes a new instance of the class Boss
	 */
	public Boss() {
		// TODO - implement Boos.boss
        this.setHealthMultiplier(5);
        this.setDamageMultiplier(2);
        this.setFireRateMultiplier(2);
        this.setSpeedMultiplier(0.5);

	}

        @Override
        public String toString(){
                return "Boss";
        }

	// TODO - implements unique elements for this role

}