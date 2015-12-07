package callofcactus.entities;

import callofcactus.GameTexture;
import callofcactus.IGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.io.Serializable;

public abstract class Entity implements Serializable{

	public static int nxtID = 0;

	protected int ID;
	protected transient IGame game;
	protected transient Vector2 location;
	protected transient Texture spriteTexture;
	protected GameTexture.texturesEnum textureType;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int health = 20;
	protected int damage = 10;
	protected transient Vector2 lastLocation;


	/**
	 * Makes a new instance of the class Entity and add it to the callofcactus
	 *
	 * @param game          : The callofcactus of which the entity belongs to
	 * @param location      : Coordinates of the entity
	 * @param spriteHeight  The height of characters sprite
	 * @param spriteTexture callofcactus.Texture to use for this AI
	 * @param spriteWidth   The width of characters sprite
	 */
	protected Entity(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight) {

		this.game = game;
		this.location = location;
		this.lastLocation = location;

		this.ID = Entity.nxtID;
		Entity.nxtID += 1;

		this.textureType = spriteTexture;
		this.spriteTexture = game.getTextures().getTexture(spriteTexture);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;

		game.addEntityToGame(this);

		spriteTexture.toString();

	}

    protected Entity(){

    }

	public static int getNxtID() {
		return nxtID;
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

	public Rectangle getHitBox() {
		return new Rectangle(location.x - (spriteWidth / 2), location.y - (spriteHeight / 2), spriteWidth, spriteHeight);

	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public IGame getGame() {
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
	 *
	 * @return True when the object is successfully removed, false when it failed
	 */
	public boolean destroy() {
		try {
			//removes it from the list which should be painted.
			//java garbagecollection will take care of it.
			game.removeEntityFromGame(this);
			Runtime.getRuntime().gc();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int takeDamage(int damageDone) {
		health -= damageDone;

		if (health <= 0) {
			destroy();

		}
		return health;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	protected void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeFloat(location.x);
		stream.writeFloat(location.y);

		stream.writeChars(textureType.toString());

		stream.writeFloat(lastLocation.x);
		stream.writeFloat(lastLocation.y);
	}

	protected void readObject(java.io.ObjectInputStream stream) throws IOException {
		try {
			stream.defaultReadObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		location = new Vector2(stream.readFloat(), stream.readFloat());
		spriteTexture = game.getTextures().getTexture(GameTexture.texturesEnum.valueOf(stream.readLine()));
		lastLocation = new Vector2(stream.readFloat(), stream.readFloat());

	}
}