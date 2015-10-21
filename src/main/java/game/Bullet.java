package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MovingEntity {
    private int velocity;

    private int damage;
    private Player shooter;

	/**
	 * create a new instance of bullet
	 * @param shooter : The player who shot the bullet
	 */
	public Bullet(Game game, Vector2 location,Player shooter) {
        // TODO - set the velocity
		super(game, location,null);

        this.setBaseSpeed(10);


		this.shooter = shooter;
        this.velocity = (int) Math.round(this.getBaseSpeed() * shooter.getRole().getSpeedMultiplier());

	}

    /**
	 * @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
	 */
	public int getVelocity() {
		return (int) Math.round(this.getBaseSpeed() * shooter.getRole().getSpeedMultiplier());
	}

    /**
     * @return the amount of damage the bullet does
     */
    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        if (damage < 0) {
            throw new IllegalArgumentException();
        }
        this.damage = damage;
    }

    /**
     * @return the player that fired the bullet
     */
    public Player getShooter(){
        return this.shooter;
    }

    /**
     * THis will damage whatever it hits
     * @param e who or what it hit
     */
    public void hit(Entity e)
    {
        if(e instanceof HumanCharacter)
        {
            ((HumanCharacter)e).takeDamage(damage);
        }
        else if(e instanceof AICharacter)
        {
            ((AICharacter)e).takeDamage(damage);
        }
        else if(e instanceof NotMovingEntity)
        {
            ((NotMovingEntity)e).takeDamage(damage);
        }
        try {
            if (((HumanCharacter) e).getHealth() <= 0) {
                ((HumanCharacter) this.shooter).addScore(1);
            }
        }catch(ClassCastException exception){exception.printStackTrace();}
    }

}