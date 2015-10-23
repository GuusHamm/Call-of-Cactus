package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;

public class HumanCharacter extends Player {

	private int score;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param location      : Coordinates of the entity
	 * @param name			: The name that will be displayed in game
	 * @param role			: The role that the player will play as, different roles have different stats
	 */
	public HumanCharacter(Game game, Vector2 location, String name, Role role,Texture spriteTexture, int spriteWidth,int spriteHeight)
	{
		super(game, location, name, role, spriteTexture, spriteWidth,spriteHeight);
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
		this.score+=score;
	}

	@Override
	public int takeDamage(int damageDone) {

		health -= damageDone;
        System.out.println("health :"+health);
        if (health <= 0)
		{
			health = 300;

		}
		return health;
	}
}