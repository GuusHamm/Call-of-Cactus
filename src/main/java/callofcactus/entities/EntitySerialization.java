package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.MultiPlayerGame;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Wouter Vanmulken on 8-12-2015.
 */
public class EntitySerialization {

    private static EntitySerialization instance;
    private MultiPlayerGame game;
    private EntitySerialization() {

    }
    private EntitySerialization(MultiPlayerGame game) {
        this.game = game;
    }

    public static EntitySerialization getInstance() {
        if (instance == null) {
            instance = new EntitySerialization();
        }
        return instance;
    }

    public void writeObjectEntity(ObjectOutputStream stream, Entity entity) throws IOException {
        stream.defaultWriteObject();
        stream.writeBoolean(!entity.getFromServer());
        stream.writeFloat(entity.location.x);
        stream.writeFloat(entity.location.y);


        stream.writeObject(entity.textureType.toString());

        stream.writeFloat(entity.lastLocation.x);
        stream.writeFloat(entity.lastLocation.y);
        if(game ==null){
            entity.setClientS();
        }
        if (entity instanceof MovingEntity) {
            writeObjectMovingEntity(stream, (MovingEntity) entity);
        } else if (entity instanceof NotMovingEntity) {
            writeObjectNotMovingEntity(stream, (NotMovingEntity) entity);
        }
    }

    public void readObjectEntity(ObjectInputStream stream, Entity entity) throws IOException {
        try {
            stream.defaultReadObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        entity.fromServer =stream.readBoolean();
        entity.location = new Vector2(stream.readFloat(), stream.readFloat());
        Administration administration = Administration.getInstance();


        try {
            entity.spriteTexture = administration.getGameTextures().getTexture(GameTexture.texturesEnum.valueOf((String) stream.readObject()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        entity.lastLocation = new Vector2(stream.readFloat(), stream.readFloat());

        if (entity instanceof MovingEntity) {
            readObjectMovingEntity(stream, (MovingEntity) entity);
        } else if (entity instanceof NotMovingEntity) {
            readObjectNotMovingEntity(stream, (NotMovingEntity) entity);
        }
    }

    private void writeObjectNotMovingEntity(ObjectOutputStream stream, NotMovingEntity entity) throws IOException {
        stream.writeFloat(entity.getHitBox().getX());
        stream.writeFloat(entity.getHitBox().getY());
        stream.writeFloat(entity.getHitBox().getWidth());
        stream.writeFloat(entity.getHitBox().getHeight());
    }

    private void readObjectNotMovingEntity(ObjectInputStream stream, NotMovingEntity entity) throws IOException {
        entity.setHitbox(new Rectangle(stream.readFloat(), stream.readFloat(), stream.readFloat(), stream.readFloat()));

    }

    private void writeObjectMovingEntity(ObjectOutputStream stream, MovingEntity entity) throws IOException {
        if (entity instanceof Bullet) {
            writeObjectBullet(stream, (Bullet) entity);
        }
    }

    private void readObjectMovingEntity(ObjectInputStream stream, MovingEntity entity) throws IOException {
        if (entity instanceof Bullet) {
            readObjectBullet(stream, (Bullet) entity);
        }
    }

    private void writeObjectBullet(ObjectOutputStream stream, Bullet entity) throws IOException {
        stream.writeInt(entity.getShooter().getID());
    }

    private void readObjectBullet(ObjectInputStream stream, Bullet entity) {

        int playerId = 0;
        try {
            playerId = stream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(game == null) {
            entity.setShooter(Administration.getInstance().searchPlayer(playerId));

            if (entity.getShooter() == null) {
                System.out.println("Bullet.readObject : No player found for given id.");
            }
        }else if(game !=null){
            entity.setShooter(game.searchPlayer(playerId));

            if (entity.getShooter() == null) {
                System.out.println("Bullet.readObject : No player found for given id.");//////////////////////////////////////////////////////////////////////
            }
        }
        entity.setRandom();

    }
}
