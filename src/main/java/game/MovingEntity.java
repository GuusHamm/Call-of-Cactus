package game;

import javafx.geometry.Point2D;

import java.awt.*;

public abstract class MovingEntity extends Entity
{

	private double baseSpeed;

	/**
	 * Makes a new instance of the class MovingEntity
	 *
	 * @param game     : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 */
	public MovingEntity(Game game, Point2D location)
	{
		super(game, location);
	}

	public double getBaseSpeed() {
		return this.baseSpeed;
	}

	public void setBaseSpeed(double baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public Game getGame(){
		return super.getGame();
	}

    public Point2D getLocation(){
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
	 * @return the new coordinate of the entity after being moved
	 */
	public Point move(int Point) {
		// TODO - implement MovingEntity.move
		throw new UnsupportedOperationException();
	}

}