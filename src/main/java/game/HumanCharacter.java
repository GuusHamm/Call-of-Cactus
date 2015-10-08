package game;

import game.role.Role;
import javafx.geometry.Point2D;

public class HumanCharacter extends Player {

	private int score;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param location      : Coordinates of the entity
	 * @param name
	 * @param role
	 * @param spawnLocation
	 */
	public HumanCharacter(Game game, Point2D location, String name, Role role, java.awt.geom.Point2D spawnLocation)
	{
		super(game, location, name, role, spawnLocation);
	}

	public int getScore() {
		return this.score;
	}

	/**
	 * Called when a player earns points.
	 * The given value will be added to the total score of the player.
	 * @param score : Value that will be added to the total score of this player
	 */
	public void addScore(int score) {
		// TODO - implement HumanCharacter.addScore
		throw new UnsupportedOperationException();
	}

}