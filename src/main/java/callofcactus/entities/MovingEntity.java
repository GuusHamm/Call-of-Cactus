package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.multiplayer.Command;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;


public abstract class MovingEntity extends Entity implements Serializable {
    protected double baseSpeed = 2;
    protected int damage = 1;
    protected int speed = 2;
    protected double angle;

    /**
     * Makes a new instance of the class MovingEntity
     *
     * @param game          : The callofcactus of which the entity belongs to
     * @param location      : Coordinates of the entity
     * @param spriteHeight  The height of characters sprite
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     */
    protected MovingEntity(IGame game, Vector2 location, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer) {
        super(game, location, spriteTexture, spriteWidth, spriteHeight, fromServer);
    }

    protected MovingEntity() {

    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed, boolean shouldSend) {
        if(this.speed!= speed && shouldSend){
            sendChangeCommand(this,"speed",speed + "", Command.objectEnum.MovingEntity);
        }
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(int angle, boolean shouldSend) {
        if (shouldSend&& !(this instanceof Bullet)){
            if(this.angle != angle ){
                sendChangeCommand(this,"angle",angle + "", Command.objectEnum.MovingEntity);
            }
        }
        this.angle = angle;

    }

    public void setDamage(int damage,boolean shouldSend) {
        if (damage < 0) {
            throw new IllegalArgumentException();
        }
        this.damage = damage;

        if (shouldSend){
            sendChangeCommand(this,"damage",damage + "", Command.objectEnum.MovingEntity);
        }
    }

    /**
     * Moves the entity towards a specific point
     *
     * @param Point : Coordinates of where the object will move to
     */
    public void move(Vector2 Point, boolean shouldSend) {

        Vector2 calculateNewPosition = null;
        if (game != null)
        {
            calculateNewPosition = getGame().calculateNewPosition(this.location, Point, speed);
        }
        else {
            calculateNewPosition = Administration.getInstance().calculateNewPosition(this.location, Point, speed);
        }

        if (calculateNewPosition.x < 0) calculateNewPosition.x = 0;
        if (calculateNewPosition.y < 0) calculateNewPosition.y = 0;
        lastLocation = new Vector2(location.x, location.y);
        location = calculateNewPosition;
        if(shouldSend) {
            sendChangeCommand(this, "location", location.x + ";" + location.y, Command.objectEnum.MovingEntity);
        }
    }
}