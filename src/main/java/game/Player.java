package game;

import game.role.Role;

public abstract class Player extends MovingEntity {

	private int health;
	private int damage;
	private int speed;
	private int fireRate;
	private String name;
	private double direction;
	private int currentHealth;
    private Role role;

	/**
	 * @param game     : The game of which the entity belongs to
     * @param spawnLocation
     * @param name
     * @param role
	 */
	public Player(Game game, javafx.geometry.Point2D spawnLocation,String name, Role role) {
		// TODO - implement Player.Player
		super(game, spawnLocation);

        health = (int)Math.round(health * role.getHealthMultiplier());
        damage = (int)Math.round(damage * role.getDamageMultiplier());
        speed = (int)Math.round(speed * role.getSpeedMultiplier());
        fireRate = (int)Math.round(fireRate * role.getFireRateMultiplier());

	}

	public int getHealth() {
		return this.health;
	}

	/**
	 *
	 * @param damageDone
     * @return returns the current health of the player
	 */
	public int takeDamage(int damageDone) {
		// TODO - implement Player.takeDamage

        health -= damageDone;

        if (health <= 0)
        {
            super.destroy();
        }
	}

	public void fireBullet() {
		// TODO - implement Player.fireBullet
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param newRole
	 */
	public void changeRole(Role newRole) {
		// TODO - implement Player.changeRole
		throw new UnsupportedOperationException();
	}

	public double getDirection() {
		return this.direction;
	}

}