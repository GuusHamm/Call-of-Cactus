package game;

import game.role.Role;
import javafx.geometry.Point2D;

import java.awt.*;

public abstract class Player extends MovingEntity {

	private int health;
	private int damage;
	private int speed;
	private int fireRate;
	private String name;
	private int direction;

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

        int baseHealth = 20;
        int baseDamage = 1;
        int baseSpeed = 2;
        int baseFireRate = 5;


        this.health = (int)Math.round(baseHealth * role.getHealthMultiplier());
        this.damage = (int)Math.round(baseDamage * role.getDamageMultiplier());
        this.speed = (int)Math.round(baseSpeed * role.getSpeedMultiplier());
        this.fireRate = (int)Math.round(baseFireRate * role.getFireRateMultiplier());

        this.role = role;
        this.name = name;
        this.direction = 0;

	}

	public int getDamage() {
		return damage;
	}

	public int getSpeed() {
		return speed;
	}

	public int getFireRate() {
		return fireRate;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return this.health;
	}

	public Role getRole()
	{
		return role;
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
        return health;
	}

	public void fireBullet() {
		// TODO - implement Player.fireBullet

        Bullet bullet = new Bullet(super.getGame(),super.getLocation(),this);
	}

	/**
	 *
	 * @param newRole
	 */
	public void changeRole(Role newRole) {
		// TODO - implement Player.changeRole

		//first return everything to it's base value
		this.health = (int)Math.round(this.health / role.getHealthMultiplier());
		this.damage = (int)Math.round(this.damage / role.getHealthMultiplier());
		this.speed = (int)Math.round(this.speed / role.getHealthMultiplier());
		this.fireRate = (int)Math.round(this.fireRate / role.getHealthMultiplier());

		this.role = newRole;

		this.health = (int)Math.round(this.health * role.getHealthMultiplier());
		this.damage = (int)Math.round(this.damage * role.getHealthMultiplier());
		this.speed = (int)Math.round(this.speed * role.getHealthMultiplier());
		this.fireRate = (int)Math.round(this.fireRate * role.getHealthMultiplier());



	}

	public int getDirection() {
        direction = getGame().angle(getLocation(), new Point2D(MouseInfo.getPointerInfo().getLocation().getX(),MouseInfo.getPointerInfo().getLocation().getY()));
		return this.direction;
	}

}