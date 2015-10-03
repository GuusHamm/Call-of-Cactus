package com.netgames.coc.game;

import java.awt.*;

public abstract class MovingEntity extends Entity {

	private double baseSpeed;

	public double getBaseSpeed() {
		return this.baseSpeed;
	}

	public void setBaseSpeed(double baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	/**
	 * 
	 * @param timeElapsed
	 */
	public void update(float timeElapsed) {
		// TODO - implement MovingEntity.update
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Point
	 */
	public Point move(int Point) {
		// TODO - implement MovingEntity.move
		throw new UnsupportedOperationException();
	}

	public MovingEntity() {
		// TODO - implement MovingEntity.MovingEntity
		throw new UnsupportedOperationException();
	}

}