package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;

public class HumanCharacter extends Player {

	private int score;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param location      : Coordinates of the entity
	 * @param name          : The name that will be displayed in game
	 * @param role          : The role that the player will play as, different roles have different stats
	 * @param spriteHeight  : The height of characters sprite
	 * @param spriteTexture : game.Texture to use for this AI
	 * @param spriteWidth   : The width of characters sprite
	 */
	public HumanCharacter(Game game, Vector2 location, String name, Role role, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, name, role, spriteTexture, spriteWidth, spriteHeight);
	}

	/**
	 * @return Current Score of this HumanCharacter
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Called when a player earns points.
	 * The given value will be added to the total score of the player.
	 *
	 * @param score : Value that will be added to the total score of this player
	 */
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	/**
	 * Moves the entity towards a specific point
	 * @param Point : Coordinates of where the object will move to
	 */
	public void move(Vector2 Point) {

		Vector2 calculateNewPosition = getGame().calculateNewPosition(this.location, Point, speed);

		int width;
		try {
			width = Gdx.graphics.getWidth();
		}catch (Exception e){
			width = 800;
		}

		int height;
		try {
			height = Gdx.graphics.getHeight();
		}catch (Exception e){
			height = 480;
		}

		if (calculateNewPosition.x < 0) {
			calculateNewPosition.x = 0;
		}
		if (calculateNewPosition.y < 0) {
			calculateNewPosition.y = 0;
		}
		if (calculateNewPosition.x > width) {
			calculateNewPosition.x = width;
		}
		if (calculateNewPosition.y > height){
			calculateNewPosition.y = height;
		}

		location = calculateNewPosition;
	}
}