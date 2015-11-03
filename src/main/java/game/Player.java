package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.io.PropertyReader;
import game.role.Role;
import org.json.JSONObject;

public abstract class Player extends MovingEntity {

	protected int health;
	//protected int damage;
	protected int fireRate;
	protected String name;
	protected int direction;

	protected Role role;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param spawnLocation : The location where the player will start
	 * @param name          : The name that will be displayed
	 * @param role          : The role that the player will play
	 * @param spriteHeight  The height of characters sprite
	 * @param spriteTexture Texture to use for this AI
	 * @param spriteWidth   The width of characters sprite
	 */
	protected Player(Game game, Vector2 spawnLocation, String name, Role role, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		// TODO - implement Player.Player
		super(game, spawnLocation, spriteTexture, spriteWidth, spriteHeight);

		JSONObject jsonObject = game.getJSON();

		int baseHealth = 20;
		int baseDamage = 1;
		int baseSpeed = 10;
		int baseFireRate = 5;

		try {
			baseHealth = (int) jsonObject.get(PropertyReader.PLAYER_HEALTH);
			baseDamage = (int) jsonObject.get(PropertyReader.PLAYER_DAMAGE);
			baseSpeed = (int) jsonObject.get(PropertyReader.PLAYER_SPEED);
			baseFireRate = (int) jsonObject.get(PropertyReader.PLAYER_FIRERATE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.health = (int) Math.round(baseHealth * role.getHealthMultiplier());
		this.damage = (int) Math.round(baseDamage * role.getDamageMultiplier());
		this.setSpeed((int) Math.round(baseSpeed * role.getSpeedMultiplier()));
		this.fireRate = (int) Math.round(baseFireRate * role.getFireRateMultiplier());

		this.role = role;
		this.name = name;
		this.direction = 0;

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

	public Role getRole() {
		return role;
	}

	@Override
	/**

	 *
	 * @param damageDone : The amount of damage that the player will take
	 * @return returns the current health of the player
	 */
	public int takeDamage(int damageDone) {
		// TODO - implement Player.takeDamage

		health -= damageDone;

		if (health <= 0) {
			super.destroy();

		}
		return health;
	}

	public void fireBullet(Texture texture) {
		try{
			if (texture == null) {
				texture = new Texture("spike.png");
			}
			//getGame().addEntityToGame(new Bullet(getGame(),getLocation(),this,this.direction,texture,game.angle(location, game.getMouse()),10,10));
			if (!game.getGodmode()) {
				Bullet b = new Bullet(game, location, this, texture, angle, 15, 15);
			}
			else {
				for (int i = 0; i<360;i+=5) {
					new Bullet(game, location, this, texture, i, 15, 15);
				}
			}
		}catch (Exception e){
			Bullet b = new Bullet(game, location, this, null, angle, 15, 15);

		}

	}

	/**
	 * @param newRole : The role that the player will play as
	 */
	public void changeRole(Role newRole) {
		// TODO - implement Player.changeRole

		//first return everything to it's base value
		this.health = (int) Math.round(this.health / role.getHealthMultiplier());
		this.damage = (int) Math.round(this.damage / role.getDamageMultiplier());
		this.setSpeed((int) Math.round(this.getSpeed() / role.getSpeedMultiplier()));
		this.fireRate = (int) Math.round(this.fireRate / role.getFireRateMultiplier());

		this.role = newRole;

		this.health = (int) Math.round(this.health * role.getHealthMultiplier());
		this.damage = (int) Math.round(this.damage * role.getDamageMultiplier());
		this.setSpeed((int) Math.round(this.getSpeed() * role.getSpeedMultiplier()));
		this.fireRate = (int) Math.round(this.fireRate * role.getFireRateMultiplier());
	}

	public int getDirection() {
		return this.direction;
	}

	public void setDirection(int angle) {
		direction = angle;
	}

	//TODO bug that needs fixing, Entity damage is not the same as in player
	@Override
	public int getDamage()
	{
		return damage;
	}
}