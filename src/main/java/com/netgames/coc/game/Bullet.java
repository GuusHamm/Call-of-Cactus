package com.netgames.coc.game;

import javafx.geometry.Point2D;

public class Bullet extends MovingEntity {

	private int velocity;
	private int baseDamage;
	private Player shooter;

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

	/**
	 * create a new instance of bullet
	 * @param shooter : The player who shot the bullet
	 * @param spawnLocation : The place where the bullet will be initialized
	 */
	public Bullet(Player shooter, Point2D spawnLocation) {
	// todo - register bullet to container
        // TODO - set the velocity
	}

}