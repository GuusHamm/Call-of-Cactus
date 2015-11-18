package callofcactus.entities;

import callofcactus.IGame;
import callofcactus.io.PropertyReader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.json.JSONObject;

import java.util.Random;

public class Bullet extends MovingEntity {

	private int damage = 10;
	private Player shooter;
	private Random r;

	public Bullet(IGame game, Vector2 location, Player shooter, double DamageMultiplier, Texture texture, double angle, int spriteWidth, int spriteHeight) {
		// TODO - set the velocity
		super(game, location, texture, spriteWidth, spriteHeight);

		this.shooter = shooter;
		this.setSpeed((int) Math.round(baseSpeed * shooter.getRole().getSpeedMultiplier()));
		this.setDamage((int) Math.round(damage * DamageMultiplier));

		this.setSpeed(10);
		JSONObject jsonObject = game.getJSON();
		this.setSpeed((int) jsonObject.get(PropertyReader.BULLET_SPEED));

		this.shooter = shooter;
		this.setSpeed((int) Math.round(speed * shooter.getRole().getSpeedMultiplier()));
		this.angle = angle;

		if (!game.getGodMode() && !game.getMuted()) {
			game.playRandomBulletSound();
		}
		r = new Random();
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
		angle += (r.nextDouble() - 0.5);
		location = getGame().calculateNewPosition(this.location, getSpeed(), (360 - angle) % 360);
	}

	@Override
	public int takeDamage(int damageDone) {
		this.destroy();
		return damageDone;
	}

}