package game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.io.PropertyReader;
import org.json.JSONObject;

import java.util.Random;

public class Bullet extends MovingEntity {

	private int damage = 10;
	private Player shooter;

	private Sound[] gunSounds;
	private Random r;

	/**
	 *
	 * @param game : The game of which the bullet needs to be implemented
	 * @param location : The spawnlocation of the bullet
	 * @param shooter : The player who shot this bullet
	 * @param DamageMultiplier : Multiplier which multiplies the damage of the bullet
	 * @param texture : The sprite
	 * @param angle : The angle in which the bullet will travel
	 * @param spriteWidth : The width of the bullet
	 * @param spriteHeight : The height of the bullet
	 */
	public Bullet(Game game, Vector2 location, Player shooter,double DamageMultiplier, Texture texture, double angle, int spriteWidth, int spriteHeight) {
		// TODO - set the velocity
		super(game, location, texture, spriteWidth, spriteHeight);

		this.setSpeed(10);
		this.shooter = shooter;
		this.setSpeed((int) Math.round(baseSpeed * shooter.getRole().getSpeedMultiplier()));
		this.setDamage((int) Math.round(damage * DamageMultiplier));
		JSONObject jsonObject = game.getJSON();

		speed = (int) jsonObject.get(PropertyReader.BULLET_SPEED);

		this.shooter = shooter;
		this.setSpeed((int) Math.round(speed * shooter.getRole().getSpeedMultiplier()));
		this.angle = angle;

		if (!game.getGodmode() && !game.isMuted())
		{
			game.getGameSounds().playBulletFireSound();
		}
		r = new Random();
	}

	/**
	 *
	 * @param game : The game of which the bullet needs to be implemented
	 * @param location : The spawnlocation of the bullet
	 * @param shooter : The player who shot this bullet
	 * @param texture : The sprite
	 * @param angle : The angle in which the bullet will travel
	 * @param spriteWidth : The width of the bullet
	 * @param spriteHeight : The height of the bullet
	 * @param sounds : The sound the bullet makes when it spawns
	 */
	public Bullet(Game game, Vector2 location, Player shooter, Texture texture, double angle, int spriteWidth, int spriteHeight, Sound[] sounds) {
		// TODO - set the velocity
		super(game, location, texture, spriteWidth, spriteHeight);

		this.setSpeed(10);
		this.shooter = shooter;
		this.setSpeed((int) Math.round(baseSpeed * shooter.getRole().getSpeedMultiplier()));
		this.setDamage((int) Math.round(damage * shooter.getRole().getDamageMultiplier()));
		JSONObject jsonObject = game.getJSON();

		speed = (int) jsonObject.get(PropertyReader.BULLET_SPEED);

		this.shooter = shooter;
		this.setSpeed((int) Math.round(speed * shooter.getRole().getSpeedMultiplier()));
		this.angle = angle;

		this.gunSounds = sounds;
	}

	/**
	 * @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
	 */
	public int getSpeed() {
		return (int) Math.round(super.getSpeed() * shooter.getRole().getSpeedMultiplier());
	}

	/**
	 * @return the player that fired the bullet
	 */
	public Player getShooter() {
		return this.shooter;
	}

	/**
	 * Moves the bullet towards a new location depending on the angle
	 */
	public void move() {
		angle += (r.nextDouble()-0.5);
		location = getGame().calculateNewPosition(this.location, getSpeed(), (360 - angle) % 360);
	}

	@Override
	public int takeDamage(int damageDone) {
		this.destroy();
		return damageDone;
	}



}