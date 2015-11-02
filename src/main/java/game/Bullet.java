package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.io.PropertyReader;
import org.json.JSONObject;

import java.util.Random;

public class Bullet extends MovingEntity {

    private int damage=100;
    private Player shooter;
    private double angle;


    public Bullet(Game game, Vector2 location, Player shooter,Texture texture, double angle, int spriteWidth, int spriteHeight) {
        // TODO - set the velocity
        super(game, location, texture, spriteWidth, spriteHeight);

        this.setSpeed(10);
        this.shooter = shooter;
        this.setSpeed((int) Math.round(baseSpeed * shooter.getRole().getSpeedMultiplier()));

        JSONObject jsonObject = game.getJSON();

        int speed = 10;

        try {
            speed = (int)jsonObject.get(PropertyReader.BULLET_SPEED);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        this.shooter = shooter;
        this.setSpeed((int) Math.round(speed * shooter.getRole().getSpeedMultiplier()));
        this.angle = angle;

        Sound gunfire = getRandomGunSound();
        gunfire.play(.3F);
    }

    /**
     * @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
     */
    public int getVelocity() {
        return (int) Math.round(this.baseSpeed * shooter.getRole().getSpeedMultiplier());
    }
	 /**
      *  @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
	 */
	public int getSpeed() {
		return (int) Math.round(super.getSpeed() * shooter.getRole().getSpeedMultiplier());
	}

    public int getDamage()    {
        return damage;
    }

    public void setDamage(int damage)    {
        if (damage < 0) {
            throw new IllegalArgumentException();
        }
        this.damage = damage;
    }

    public double getAngle() {
        return angle;
    }

    /**
     * @return the player that fired the bullet
     */
    public Player getShooter() {
        return this.shooter;
    }
    public void move() {
        location = getGame().calculateNewPosition(this.location, getVelocity(), (360-angle)%360);
    }

    @Override
    public int takeDamage(int damageDone) {
        this.destroy();
        return damageDone ;
    }

    /**
     *
     * @return 1 out of 2 gunfire sounds
     */
    private Sound getRandomGunSound(){
        // TODO Unit Test
        Sound sound = null;
        int random = new Random().nextInt(2) + 1;
        switch(random){
            case 1:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun1.mp3"));
                break;
            case 2:
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                break;
        }
        return sound;
    }
}