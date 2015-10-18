package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import javafx.geometry.Point2D;

public abstract class Entity {

    protected int ID;
    public static int nxtID=0;
	protected Game game;
	protected Vector2 location;
	protected Texture spriteTexture;

	/**s
	 * Makes a new instance of the class Entity and add it to the game
	 * @param game : The game of which the entity belongs to
	 * @param location : Coordinates of the entity
	 */
    public Entity(Game game, Vector2 location, Texture spriteTexture) {
        this.game = game;
        this.location = location;

        this.ID = Entity.nxtID;
        Entity.nxtID+=1;

		this.spriteTexture = spriteTexture;

        game.addEntityToGame(this);
    }

	public Game getGame()
	{
		return game;
	}

	public Vector2 getLocation() {
		return this.location;
	}

	/**
	 * Function that will kill this entity.
	 * This can for example can be used to remove enemies when killed.
	 * @return True when the object is successfully removed, false when it failed
	 */
	public boolean destroy() {
		try {
            //removes it from the list which should be painted.
            //java garbagecollection will take care of it.
            game.removeEntityFromGame(this);
            return true;

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
	}

	/**
	 * Draws the entity on the right location
	 */
	public void paint() {
		// TODO - implement SpriteClass.paint
		throw new UnsupportedOperationException();
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                kochManager.drawEdges();
//            }
//        });

	}
}