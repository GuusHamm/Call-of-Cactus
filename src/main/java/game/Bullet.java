package game;

import javafx.geometry.Point2D;

public class Bullet extends MovingEntity {

	private int velocity;
	private int baseDamage;
	private Player shooter;

	/**
	 * create a new instance of bullet
	 * @param shooter : The player who shot the bullet
	 */
	public Bullet(Game game, Point2D location,Player shooter) {
		// todo - register bullet to container
        // TODO - set the velocity
		super(game, location);
	}
	/**
	 * @return the speed of the bullet
	 */
	public int getVelocity() {
		return this.velocity;
	}

    /**
     * @return the player that fired the bullet
     */
    public Player getShooter(){
        return this.shooter;
    }

}