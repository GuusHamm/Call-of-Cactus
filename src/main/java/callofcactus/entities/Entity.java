package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.SinglePlayerGame;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.Command;
import callofcactus.multiplayer.ServerS;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.spi.activation.Server;

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
    protected transient boolean fromServer;
    protected transient ClientS client;
    protected transient Entity[] entity;

    /**
     * Makes a new instance of the class Entity and add it to the callofcactus
     *
     * @param game          : The callofcactus of which the entity belongs to
     * @param location      : Coordinates of the entity
     * @param spriteHeight  The height of characters sprite
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     */
    protected Entity(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer)
    {

        this.game = game;
        this.location = location;
        this.lastLocation = location;
        if (fromServer) {
            this.ID = getNxtID();
        }
        else {
            this.ID = -1;
        }

        this.textureType = spriteTexture;
        this.spriteTexture = GameTexture.getInstance().getTexture(spriteTexture);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        if (game != null) {
            game.addEntityToGame(this);
        }
        else {
//            Administration.getInstance().replaceEntity(this);
        }
        client = Administration.getInstance().getClient();
    }

    protected Entity()
    {

    }

    public static int getNxtID()
    {
        nxtID++;
        return nxtID;
    }

    public boolean getFromServer()
    {
        return fromServer;
    }

    public void setFromServer(boolean fromServer)
    {
        this.fromServer = fromServer;
    }

    public void setClientS()
    {
        this.client = ClientS.getInstance();
    }

    public void setStartHealth() {
        health = (int) (100 * ((Player) this).getRole().getHealthMultiplier());
    }

    public int getHealth() {
        return health;
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

    public void setSpriteWidth(int width) {
        spriteWidth = width;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int height) {
        spriteHeight = height;
    }

    public IGame getGame() {
        return game;
    }

    public void setGame(IGame game)
    {
        this.game = game;
    }

    public Vector2 getLocation() {
        return this.location;
    }

    public void setLocation(Vector2 location, boolean shouldSend) {
        this.location = location;
        if(fromServer && this instanceof HumanCharacter){
            System.out.println("So this is happening");
        }
        if (shouldSend || (fromServer && this instanceof HumanCharacter)){// && !(this instanceof HumanCharacter)) {
            System.out.println("fucking hell, should this be called right now ?");
            sendChangeCommand(this, "location", location.x + ";" + location.y, Command.objectEnum.Entity);
        }

    }

    /**
     * ONLY CALL THIS FROM SERVER!!!!!!!!!!!!!
     * @param location
     * @param shouldSend
     */
    public void setToLastLocation(Vector2 location, boolean shouldSend) {
        this.location = location;

        if (shouldSend) {
            sendChangeCommand(this, "location", location.x + ";" + location.y, Command.objectEnum.SetLastLocation);
        }


    }

    public void setHealth(int health, boolean shouldSend ) {
        this.health = health;
        if(shouldSend) {
            sendChangeCommand(this, "health", String.valueOf(health), Command.objectEnum.Entity);
        }
    }

    public Texture getSpriteTexture() {
        return spriteTexture;
    }

    //The code below should work but it just makes it hang so for now i'll leave it be //////////////////////////////////////////
    public boolean destroy() {
//        try {
            //removes it from the list which should be painted.
            //java garbagecollection will take care of it.
            if( game instanceof SinglePlayerGame) {
                game.removeEntityFromGame(this);
                return true;
            }
            if(fromServer){
                if (this instanceof DestructibleWall) {
                    ServerS.getInstance().sendMessagePush(new Command(Command.methods.DESTROY, new Entity[]{this}, Command.objectEnum.DestructibleWall));
                } else {
                    ServerS.getInstance().sendMessagePush(new Command(Command.methods.DESTROY, this.getID(), "destroy", "", Command.objectEnum.valueOf(this.getClass().getSimpleName())));
                }
                //Todo might need check if the deserialization doesn't properly set the game
                ServerS.getInstance().getGame().removeEntityFromGame(this);
            }
            else if (!fromServer) {
                Administration.getInstance().removeEntity(this);
            }
            Runtime.getRuntime().gc();
            return true;

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    public int takeDamage(int damageDone, boolean shouldSend) {
        if(fromServer) {
            health -= damageDone;
            if (health <= 0) {
                if (this instanceof HumanCharacter) {
                    HumanCharacter h = (HumanCharacter) this;
                    h.addDeath(true);
                }
                destroy();
            }
            if (shouldSend) {
                sendChangeCommand(this, "health", health + "", Command.objectEnum.Entity);////////////////////////////////////////////////////////////////////////////
            }
            System.out.println("___Entity.takeDamage " + this.getID() + "has taken damage. New health: " + health);


            // Play a hit sound when a HumanCharacter takes damage
            if (this instanceof HumanCharacter) {
                Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3"));
            }
        } else if(game!=null && game instanceof SinglePlayerGame){
            health -= damageDone;
        }
        // Play a hit sound when a HumanCharacter takes damage
//        if(this instanceof HumanCharacter){
//            Gdx.audio.newSound(Gdx.files.internal("sounds/hitting/coc_stab1.mp3")).play(.4f);
//        }

        return health;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID, boolean shouldSend) {
        this.ID = ID;
        if(shouldSend) {
            sendChangeCommand(this, "ID", ID + "", Command.objectEnum.Entity);
        }
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
    protected void sendChangeCommand(Entity o, String fieldToChange, String newValue, Command.objectEnum objectToChange){
        if(game instanceof SinglePlayerGame) return;
        if(fromServer){
            ServerS.getInstance().sendMessagePush(new Command(o.getID(), fieldToChange, newValue, objectToChange));
        }
        else if(!fromServer){
            ClientS.getInstance().sendMessageAndReturn(new Command(o.getID(), fieldToChange, newValue, objectToChange));
        }
    }
    /**
     * Send a change in this instance to ClientS.
     */
    protected void sendPostCommand(){
        if(game instanceof SinglePlayerGame) return;
        if(fromServer){
            ServerS.getInstance().sendMessagePush(new Command(Command.methods.POST,new Entity[]{this}, Command.objectEnum.valueOf(this.getClass().getSimpleName())));
        }
        else if(!fromServer){
            ClientS.getInstance().sendMessageAndReturn(new Command(Command.methods.POST,new Entity[]{this}, Command.objectEnum.valueOf(this.getClass().getSimpleName())));
        }
    }

}