package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;

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
	protected Player(Game game, Vector2 spawnLocation,String name, Role role,Texture spriteTexture, int spriteWidth,int spriteHeight) {
		// TODO - implement Player.Player
		super(game, spawnLocation,spriteTexture, spriteWidth,spriteHeight);

        int baseHealth = 20;
        int baseDamage = 1;
        int baseSpeed = 10;
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

        Bullet bullet = new Bullet(super.getGame(),super.getLocation(),this,new Texture("spike.png"),10,10);
	}

	/**
	 *
	 * @param newRole
	 */
	public void changeRole(Role newRole) {
		// TODO - implement Player.changeRole

		//first return everything to it's base value
		this.health = (int)Math.round(this.health / role.getHealthMultiplier());
		this.damage = (int)Math.round(this.damage / role.getDamageMultiplier());
		this.speed = (int)Math.round(this.speed / role.getSpeedMultiplier());
		this.fireRate = (int)Math.round(this.fireRate / role.getFireRateMultiplier());

		this.role = newRole;

		this.health = (int)Math.round(this.health * role.getHealthMultiplier());
		this.damage = (int)Math.round(this.damage * role.getDamageMultiplier());
		this.speed = (int)Math.round(this.speed * role.getSpeedMultiplier());
		this.fireRate = (int)Math.round(this.fireRate * role.getFireRateMultiplier());
	}

	public int getDirection() {
		return this.direction;
	}

	public void setDirection(){
//		Vector2 directionVector = new Vector2(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y);
		direction = getGame().angle(getLocation(),getGame().getMouse());
	}

}