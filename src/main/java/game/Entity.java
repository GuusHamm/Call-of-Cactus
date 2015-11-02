package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    public static int nxtID=0;
    protected int ID;
	protected Game game;

    protected Vector2 location;
	protected Texture spriteTexture;
	protected int spriteWidth;
	protected int spriteHeight;
    protected int health=20;
    protected int damage=0;
    protected Vector2 lastLocation;


    /**s
     * Makes a new instance of the class Entity and add it to the game
     * @param game 		: The game of which the entity belongs to
     * @param location 	: Coordinates of the entity
     * @param spriteHeight The height of characters sprite
     * @param spriteTexture Texture to use for this AI
     * @param spriteWidth The width of characters sprite
     */
    protected Entity(Game game, Vector2 location, Texture spriteTexture, int spriteWidth, int spriteHeight) {

        this.game = game;
        this.location = location;
        this.lastLocation= location;

        this.ID = Entity.nxtID;
        Entity.nxtID+=1;

        this.spriteTexture = spriteTexture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        game.addEntityToGame(this);

        if(this instanceof Bullet)          { health=20;}
        if(this instanceof Player)          { health=20;}
        if(this instanceof NotMovingEntity) { health=20;}

    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getLastLocation() {
        return lastLocation;

    }

    public void setLastLocation(Vector2 lastLocation) {
        this.lastLocation = lastLocation;
    }


    public Rectangle getHitBox()
	{
        return new Rectangle(location.x-(spriteWidth/2),location.y-(spriteHeight/2),spriteWidth,spriteHeight);

    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

	public Game getGame()
	{
		return game;
	}

	public Vector2 getLocation() {
		return this.location;
	}

    public void setLocation(Vector2 location) {
        this.location = location;
    }

	public Texture getSpriteTexture() {
		return spriteTexture;
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

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}

    public int takeDamage(int damageDone) {
        health -= damageDone;

        if (health <= 0)
        {
            destroy();
        }
        return health;
    }
}