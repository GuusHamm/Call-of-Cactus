package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public abstract class MovingEntity extends Entity
{

	private double baseSpeed = 2;

	/**
	 * Makes a new instance of the class MovingEntity
	 *
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 */
	protected MovingEntity(Game game, Vector2 location, Texture spriteTexture, int spriteWidth,int spriteHeight)
	{
		super(game, location, spriteTexture, spriteWidth,spriteHeight);
	}

	public double getBaseSpeed() {
		return this.baseSpeed;
	}

	public void setBaseSpeed(double baseSpeed) {
		this.baseSpeed = baseSpeed;
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
	 * ...
	 * @param timeElapsed : ...
	 */
	public void update(float timeElapsed) {
		// TODO - implement MovingEntity.update
		throw new UnsupportedOperationException();
	}

	/**
	 * Moves the entity towards a specific point
	 * @param Point : Coordinates of where the object will move to
	 */
	public void move(Vector2 Point) {
		location = getGame().calculateNewPosition(this.location, Point,baseSpeed);
	}

}