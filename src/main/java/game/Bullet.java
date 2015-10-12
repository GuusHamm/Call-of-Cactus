package game;

import javafx.geometry.Point2D;

public class Bullet extends MovingEntity {

	private int velocity;

    private int damage;
    private Player shooter;

	/**
	 * create a new instance of bullet
	 * @param shooter : The player who shot the bullet
	 */
	public Bullet(Game game, Point2D location,Player shooter) {
        // TODO - set the velocity
		super(game, location);

        this.setBaseSpeed(10);


		this.shooter = shooter;
        this.velocity = (int) Math.round(this.getBaseSpeed() * shooter.getRole().getSpeedMultiplier());

	}

    /**
	 * @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
	 */
	public int getVelocity() {
		return this.velocity;
	}

    /**
     * @return the amount of damage the bullet does
     */
    public int getDamage()
    {
        return damage;
    }

    /**
     * @return the player that fired the bullet
     */
    public Player getShooter(){
        return this.shooter;
    }

}