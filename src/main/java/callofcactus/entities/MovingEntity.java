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

    public void setSpeed(int speed) {
        if(this.speed!= speed){
            sendChangeCommand(this,"speed",speed + "", Command.objectEnum.MovingEntity, fromServer);
        }
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(int angle, boolean fucksGiven) {
        if (fucksGiven && !(this instanceof Bullet)){
            // this.angle == 10 + 2 (12)
            // angle == 5
            if(this.angle != angle ){

                sendChangeCommand(this,"angle",angle + "", Command.objectEnum.MovingEntity, fromServer);
            }
            this.angle = angle;
        }

    }

    public void setDamage(int damage,boolean fucksGiven) {
        if (damage < 0) {
            throw new IllegalArgumentException();
        }
        this.damage = damage;

        if (fucksGiven){
            sendChangeCommand(this,"damage",damage + "", Command.objectEnum.MovingEntity, fromServer);
        }
    }

    /**
     * Moves the entity towards a specific point
     *
     * @param Point : Coordinates of where the object will move to
     */
    public void move(Vector2 Point) {

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

        sendChangeCommand(this,"location",location.x+";"+location.y, Command.objectEnum.MovingEntity, fromServer);
    }

//    public void writeObject(ObjectOutputStream stream) throws IOException {
//        super.writeObject(stream);
//
//    }
//
//    public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        super.readObject(stream);
//    }
}