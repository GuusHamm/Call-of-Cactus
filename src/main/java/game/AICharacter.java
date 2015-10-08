package game;

import java.awt.*;

public class AICharacter extends Player {

	private boolean followPlayer = true;

	/**
	 * Makes a new instance of the class AICharacter
	 */
	public AICharacter() {
		// TODO - implement AICharacter.AICharacter

		throw new UnsupportedOperationException();
	}

	public boolean isFollowPlayer() {
		return this.followPlayer;
	}

	/**
	 * Toggels if this AICharacter must follow the player
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