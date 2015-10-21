package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import game.role.Role;

public class AICharacter extends Player {

	private boolean followPlayer=false;
    private HumanCharacter playerToFollow;
    
	/**
	 * @param game          : The game of which the entity belongs to
	 * @param spawnLocation : The lccation where the AI will start from
	 * @param name          : The name of the AI, here we can make a difference between a normal one and a boss
	 * @param role : the role of the ai
     * @param player : the player the ai will follow
	 */
	public AICharacter(Game game, Vector2 spawnLocation, String name, Role role, HumanCharacter player,Texture spriteTexture, int spriteWidth,int spriteHeight)
	{
		super(game, spawnLocation, name, role,spriteTexture, spriteWidth, spriteHeight);
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
        super.move(playerToFollow.getLocation());
    }



}