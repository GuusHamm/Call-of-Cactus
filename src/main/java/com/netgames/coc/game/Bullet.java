package com.netgames.coc.game;

import javafx.geometry.Point2D;

public class Bullet extends MovingEntity {

	private Point2D velocity;
	private int baseDamage;
	Player shooter;

	public Point2D getVelocity() {
		return this.velocity;
	}

	/**
	 * 
	 * @param shooter
	 * @param spawnLocation
	 */
	public Bullet(Player shooter, Point2D spawnLocation) {

	}

}