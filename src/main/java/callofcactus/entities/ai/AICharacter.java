package callofcactus.entities.ai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import callofcactus.Game;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.Player;
import callofcactus.role.Boss;
import callofcactus.role.Role;

public class AICharacter extends Player {
	private HumanCharacter playerToFollow;

	/**
	 * @param game          : The callofcactus of which the entity belongs to
	 * @param spawnLocation : The location where the AI will start from
	 * @param name          : The name of the AI
	 * @param role          : the role of the ai, here we can make a difference between a normal one and a boss
	 * @param player        : the player the ai will follow
	 * @param spriteTexture : callofcactus.Texture to use for this AI
	 * @param spriteWidth   : The width of characters sprite
	 * @param spriteHeight  : The height of characters sprite
	 */
	public AICharacter(Game game, Vector2 spawnLocation, String name, Role role, HumanCharacter player, Texture spriteTexture, int spriteWidth, int spriteHeight) {
		super(game, spawnLocation, name, role, spriteTexture, spriteWidth, spriteHeight);
		this.playerToFollow = player;
	}

	/**
	 * @param damageDone : The amount of damage that will be done
	 * @param player     : Which player gains the score.
	 * @return the Health of the AI
	 */
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

	/**
	 * @return returns the player that the AI is following
	 */
	public HumanCharacter getPlayerToFollow() {
		return playerToFollow;
	}


}