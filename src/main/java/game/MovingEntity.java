package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public abstract class MovingEntity extends Entity
{
	protected double baseSpeed = 2;
    protected int damage=1;
	protected int speed = 2;
	/**
	 * Makes a new instance of the class MovingEntity
	 *
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 */
	protected MovingEntity(Game game, Vector2 location, Texture spriteTexture, int spriteWidth,int spriteHeight)	{
		super(game, location, spriteTexture, spriteWidth,spriteHeight);
	}

    public int getDamage() {
        return damage;
    }

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public Game getGame(){
		return super.getGame();
	}

    @Override
	public Vector2 getLocation(){
        return super.getLocation();
    }


	/**
	 * Moves the entity towards a specific point
	 * @param Point : Coordinates of where the object will move to
	 */
	public void move(Vector2 Point) {


		 Vector2 calculateNewPosition= getGame().calculateNewPosition(this.location, Point,speed);

            if(calculateNewPosition.x<0)calculateNewPosition.x=0;
            if(calculateNewPosition.y<0)calculateNewPosition.y=0;
            if(calculateNewPosition.x> Gdx.graphics.getWidth())calculateNewPosition.x=Gdx.graphics.getWidth();
            if(calculateNewPosition.y> Gdx.graphics.getHeight())calculateNewPosition.y=Gdx.graphics.getHeight();
			lastLocation = location;
			location=calculateNewPosition;
	}

}