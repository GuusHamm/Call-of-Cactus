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

	/**
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 * @param name
	 * @param role
	 * @param spawnLocation
	 */
	public Player(Game game, javafx.geometry.Point2D location,String name, Role role, Point2D spawnLocation) {
		// TODO - implement Player.Player
		super(game, location);
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