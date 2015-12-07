package callofcactus.entities;

import callofcactus.IGame;
import callofcactus.role.Role;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class HumanCharacter extends Player {

	private int score;
	private int killCount;
	private int deathCount;

	/**
	 * @param game          : The callofcactus of which the entity belongs to
	 * @param location      : Coordinates of the entity
	 * @param name          : The name that will be displayed in callofcactus
	 * @param role          : The role that the player will play as, different roles have different stats
	 * @param spriteHeight  The height of characters sprite
	 * @param spriteTexture callofcactus.Texture to use for this AI
	 * @param spriteWidth   The width of characters sprite
	 */
	public HumanCharacter(IGame game, Vector2 location, String name, Role role, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, location, name, role, spriteTexture, spriteWidth, spriteHeight);
		score = 0;
		killCount = 0;
		deathCount = 0;
	}

	/**
	 * @return Current Score of this HumanCharacter
	 */
	public int getScore() {
		return score;
	}

	/**
	 *
	 * @return the amount of players you killed
	 */
	public int getKillCount() {
		return killCount;
	}

	/**
	 *
	 * @return the amount of times you died
	 */
	public int getDeathCount() {
		return deathCount;
	}

	/**
	 * When you killed an enemy, raise the killCount variable
	 */
	public void addKill() {
		killCount++;
	}

	/**
	 * When you die, raise the deathCount variable
	 */
	public void addDeath() {
		deathCount++;
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
		} catch (Exception e) {
			width = 800;
		}

		int height;
		try {
			height = Gdx.graphics.getHeight();
		} catch (Exception e) {
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
		if (calculateNewPosition.y > height) {
			calculateNewPosition.y = height;
		}

		location = calculateNewPosition;
	}
}