package game;

import game.role.Role;
import javafx.geometry.Point2D;

public class AICharacter extends Player {

	private boolean followPlayer=false;
    private HumanCharacter playerToFollow;
    
	/**
	 * @param game          : The game of which the entity belongs to
	 * @param spawnLocation
	 * @param name
	 * @param role : the role of the ai
     * @param player : the player the ai will follow
	 */
	public AICharacter(Game game, Point2D spawnLocation, String name, Role role, HumanCharacter player)
	{
		super(game, spawnLocation, name, role);
        this.playerToFollow=player;
        
        if(playerToFollow!=null)
        {
            followPlayer=true;
        }		
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
        super.move(getGame().calculateNewPosition(
                this.getLocation(),
                  playerToFollow.getLocation(),1)
        );

    }



}