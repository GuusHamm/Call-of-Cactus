package callofcactus.entities;

import callofcactus.Administration;
import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.io.PropertyReader;
import callofcactus.multiplayer.Command;
import com.badlogic.gdx.math.Vector2;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class Bullet extends MovingEntity implements Serializable {

    private int damage = 10;
    private Player shooter;
    private transient Random r;

    public Bullet(IGame game, Vector2 location, Player shooter, double damageMultiplier, double speedMultiplier, GameTexture.texturesEnum texture, double angle, int spriteWidth, int spriteHeight) {
        // TODO - set the velocity
        super(game, location, texture, spriteWidth, spriteHeight);

        this.shooter = shooter;
        this.setDamage((int) Math.round(damage * damageMultiplier));

        this.setSpeed(10);
        if (game != null) {
            JSONObject jsonObject = game.getJSON();
            int speed = (int) jsonObject.get(PropertyReader.BULLET_SPEED);

            if (!game.getGodMode() && !game.getMuted()) {
                game.playRandomBulletSound();
            }
        }

        this.setSpeed((int) (speed * speedMultiplier));

        this.shooter = shooter;
        this.angle = angle;


        r = new Random();

        // Post this entity to ClientS. ClientS will handle the transfer to the server.
        client = Administration.getInstance().getClient();
        sendPostMessage();
    }

    /**
     * @return the speed of the bullet, this can be different than baseSpeed if you get a speed bonus.
     */
    public int getSpeed() {
        return (int) Math.round(super.getSpeed() * shooter.getRole().getSpeedMultiplier());
    }

    /**
     * @return the player that fired the bullet
     */
    public Player getShooter() {
        return this.shooter;
    }

    public void setShooter(Player shooter) {
        this.shooter = shooter;

    }

    public void move() {
        angle += (r.nextDouble() - 0.5);
        location = Administration.getInstance().calculateNewPosition(this.location, getSpeed(), (360 - angle) % 360);
        sendChangeCommand(this,"location",location + "", Command.objectEnum.Bullet);
    }

    public void setRandom() {
        this.r = new Random();
    }

    @Override
    public int takeDamage(int damageDone) {
        this.destroy();
        return damageDone;

    }

//    public void writeObject(java.io.ObjectOutputStream stream) {
//        try {
//            stream.defaultWriteObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            super.writeObject(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            stream.writeInt(shooter.getID());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void readObject(java.io.ObjectInputStream stream)  throws IOException, ClassNotFoundException {
//        try {
//            stream.defaultReadObject();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            super.readObject(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int playerId = 0;
//        try {
//            playerId = stream.readInt();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.administration = Administration.getInstance();
//        this.shooter = administration.searchPlayer(playerId);
//
//        if (shooter == null) {
//            System.out.println("Bullet.readObject : No player found for given id.");
//        }
//        r = new Random();
//
//    }

    /**
     * Post this instance to ClientS.
     */
    private void sendPostMessage(){
        if(client != null){
            Entity[] entity = new Entity[1];
            entity[0] = this;
            client.sendMessageAndReturn(new Command(Command.methods.POST, entity, Command.objectEnum.Entity));
        }
    }
}