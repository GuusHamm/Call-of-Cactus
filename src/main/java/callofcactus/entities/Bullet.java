package callofcactus.entities;

import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.io.PropertyReader;
import com.badlogic.gdx.math.Vector2;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

public class Bullet extends MovingEntity implements Serializable{

	private int damage = 10;
	private Player shooter;
	private transient Random r;

	public Bullet(IGame game, Vector2 location, Player shooter, double damageMultiplier, double speedMultiplier, GameTexture.texturesEnum texture, double angle, int spriteWidth, int spriteHeight) {
		// TODO - set the velocity
		super(game, location, texture, spriteWidth, spriteHeight);

		this.shooter = shooter;
		this.setDamage((int) Math.round(damage * damageMultiplier));

		this.setSpeed(10);
		JSONObject jsonObject = game.getJSON();
		int speed = (int) jsonObject.get(PropertyReader.BULLET_SPEED);
		this.setSpeed((int) (speed * speedMultiplier));

		this.shooter = shooter;
		this.angle = angle;

		if (!game.getGodMode() && !game.getMuted()) {
			game.playRandomBulletSound();
		}
		r = new Random();
	}

    public Bullet(){

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

	protected void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        super.writeObject(stream);

	}

	protected void readObject(java.io.ObjectInputStream stream) throws IOException {
        super.readObject(stream);
        r = new Random();

	}

}