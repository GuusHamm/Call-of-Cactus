package game;

import com.badlogic.gdx.Gdx;
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

		if (!game.getGodmode())
		{
			gunSounds = new Sound[] {
					Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3")),
					Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3"))
			};

			Sound gunfire = getRandomGunSound();
			gunfire.play(.3F);
		}

	}

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

	public void move() {
		location = getGame().calculateNewPosition(this.location, getSpeed(), (360 - angle) % 360);
	}

	@Override
	public int takeDamage(int damageDone) {
		this.destroy();
		return damageDone;
	}

	/**
	 * @return 1 out of 2 gunfire sounds
	 */
	private Sound getRandomGunSound() {
		return (Sound) Utils.getRandomObjectFromArray(gunSounds);
	}
}