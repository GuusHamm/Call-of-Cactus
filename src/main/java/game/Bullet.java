package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MovingEntity {
    private int velocity;

    private int damage;
    private Player shooter;
    private double angle;

	/**
	 * create a new instance of bullet
	 * @param shooter : The player who shot the bullet
	 */

	public Bullet(Game game, Vector2 location,Player shooter,Texture spriteTexture, int spriteWidth,int spriteHeight) {
        // TODO - set the velocity
        super(game, location, spriteTexture, spriteWidth, spriteHeight);
        this.shooter = shooter;
    }

	public Bullet(Game game, Vector2 location,Player shooter, int spriteWidth,int spriteHeight) {
        // TODO - set the velocity
		super(game, location,null, spriteWidth,spriteHeight);

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

    public double getAngle() {
        return angle;
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


    public void move() {
        super.move(getGame().calculateNewPosition(this.location,getVelocity(),angle));
    }


}