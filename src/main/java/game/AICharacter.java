package game;

import game.role.Role;
import javafx.geometry.Point2D;

public class AICharacter extends Player {

	private boolean followPlayer = true;

	/**
	 * @param game          : The game of which the entity belongs to
	 * @param spawnLocation
	 * @param name
	 * @param role
	 */
	public AICharacter(Game game, Point2D spawnLocation, String name, Role role)
	{
		super(game, spawnLocation, name, role);
	}

	public boolean isFollowPlayer() {
		return this.followPlayer;
	}

	/**
	 * Toggles if this AICharacter must follow the player
	 * @param followPlayer : True if this AI must follow the player, false if not so
	 */
	public void setFollowPlayer(boolean followPlayer) {
		this.followPlayer = followPlayer;
	}

	/**
	 * Move directly towards the player
	 * If blocked by a wall, it will not find an other route
	 */
    public void move(){
		//TODO - follow the player

    }
    

}