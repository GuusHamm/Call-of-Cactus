package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.Command;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Entity implements Serializable {

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
    protected transient ClientS client;
    protected transient Object[] entity;


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

        client = Administration.getInstance().getClient();

    }

    protected Entity() {

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
        sendChangeCommand(this,"lastLocation",lastLocation.toString(), Command.objectEnum.Entity);
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
        sendChangeCommand(this,"location",location.toString(), Command.objectEnum.Entity);
    }

    public void setGame(IGame game) {
        this.game = game;
    }
    public void setHealth(int health) {
        this.health = health;
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
        sendChangeCommand(this,"health", health + "", Command.objectEnum.Entity);
        return health;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
        sendChangeCommand(this,"ID", ID + "", Command.objectEnum.Entity);
    }


    private void writeObject(ObjectOutputStream stream) throws IOException {
        EntitySerialization.getInstance().writeObjectEntity(stream,this);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
       EntitySerialization.getInstance().readObjectEntity(stream,this);
    }

    /**
     * Send a change in this instance to ClientS.
     */
    protected void sendChangeCommand(Object o, String fieldToChange, String newValue, Command.objectEnum objectToChange){
        if(client != null){
            entity = new Object[1];
            entity[0] = o;
            client.sendMessageAndReturn(new Command(Command.methods.CHANGE, entity, fieldToChange, newValue, objectToChange));
        }
    }

}