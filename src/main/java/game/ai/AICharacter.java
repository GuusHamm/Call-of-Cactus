package game.ai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.Game;
import game.HumanCharacter;
import game.Player;
import game.role.Boss;
import game.role.Role;

public class AICharacter extends Player {
	private HumanCharacter playerToFollow;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param spawnLocation : The location where the AI will start from
	 * @param name          : The name of the AI, here we can make a difference between a normal one and a boss
	 * @param role          : the role of the ai
	 * @param player        : the player the ai will follow
	 * @param spriteHeight  The height of characters sprite
	 * @param spriteTexture game.Texture to use for this AI
	 * @param spriteWidth   The width of characters sprite
	 */
	public AICharacter(Game game, Vector2 spawnLocation, String name, Role role, HumanCharacter player, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, spawnLocation, name, role, spriteTexture, spriteWidth, spriteHeight);
		this.playerToFollow = player;
	}


	public int takeDamage(int damageDone, HumanCharacter player) {
		super.takeDamage(damageDone);
		if (super.health <= 0) {
			if (role instanceof Boss) {
				player.addScore(5);
			} else {
				player.addScore(1);
			}
		}

		return super.health;
	}

	public HumanCharacter getPlayerToFollow() {
		return playerToFollow;
	}
}