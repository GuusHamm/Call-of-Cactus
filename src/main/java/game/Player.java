package game;

import game.role.Role;

import java.awt.geom.Point2D;

public abstract class Player extends MovingEntity {

	private int baseHealth;
	private int baseDamge;
	private int baseSpeed;
	private int baseFireRate;
	private String name;
	private double direction;

	public Player()
	{

	}

	/**
	 *
	 * @param name
	 * @param role
	 * @param spawnLocation
	 */
	public Player(String name, Role role, Point2D spawnLocation) {
		// TODO - implement Player.Player
		throw new UnsupportedOperationException();
	}

	public int getBaseHealth() {
		return this.baseHealth;
	}

	/**
	 *
	 * @param damageDone
	 */
	public int takeDamage(int damageDone) {
		// TODO - implement Player.takeDamage
		throw new UnsupportedOperationException();
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