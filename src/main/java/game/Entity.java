package game;

import javafx.geometry.Point2D;

public abstract class Entity {

    private int ID;

	private Game game;

	private Point2D location;
	/**s
	 * Makes a new instance of the class Entity and add it to the game
	 * @param game : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 */
    public Entity(Game game, Point2D location) {
        this.game = game;
        this.location = location;

        game.addEntityToGame(this);
    }

	public Game getGame()
	{
		return game;
	}

	public Point2D getLocation() {
		return this.location;
	}

	/**
	 * Function that will kill this entity.
	 * This can for example can be used to remove enemies when killed.
	 * @return True when the object is successfully removed, false when it failed
	 */
	public boolean destroy() {
		// TODO - implement SpriteClass.kill
		throw new UnsupportedOperationException();
	}

	/**
	 * Draws the entity on the right location
	 */
	public void paint() {
		// TODO - implement SpriteClass.paint
		throw new UnsupportedOperationException();
	}
}