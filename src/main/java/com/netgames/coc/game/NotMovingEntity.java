package com.netgames.coc.game;

public class NotMovingEntity extends Entity {

	private boolean solid;
	private int health;
	private boolean canTakeDamage;

	public boolean isSolid() {
		return this.solid;
	}

	public int getHealth() {
		return this.health;
	}

	/**
	 * 
	 * @param solid
	 * @param health
	 * @param canTakeDamage
	 */
	public NotMovingEntity(boolean solid, int health, boolean canTakeDamage) {
		// TODO - implement NotMovingEntity.NotMovingEntity
		throw new UnsupportedOperationException();
	}

}