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

    public Bullet(IGame game, Vector2 location, Player shooter, double damageMultiplier, double speedMultiplier, GameTexture.texturesEnum texture, double angle, int spriteWidth, int spriteHeight, boolean fromServer) {
        // TODO - set the velocity
        super(game, location, texture, spriteWidth, spriteHeight, fromServer);

        this.shooter = shooter;
        this.setDamage((int) Math.round(damage * damageMultiplier),false);

        int speed;
        try{
            JSONObject jsonObject = game.getJSON();
            speed = (int) jsonObject.get(PropertyReader.BULLET_SPEED);

        }
        catch(Exception e){
            speed = 50;
        }

        if (game != null) {

            if (!game.getGodMode() && !game.getMuted()) {
                game.playRandomBulletSound();
            }
        }

        this.setSpeed((int) (speed * speedMultiplier), false);

        this.shooter = shooter;
        this.angle = angle;
        this.r = new Random();

        // Post this entity to ClientS. ClientS will handle the transfer to the server.
        client = Administration.getInstance().getClient();
        if(!fromServer) {
            client.sendMessageAndReturn(new Command(Command.methods.POST,new Entity[]{this}, Command.objectEnum.Bullet));
        }
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
///////////////////////////////////////////////////////////
        //should this be sent to the server, this should honestly never happen
///////////////////////////////////////////////////////////
    }

    public void move() {
        angle += (r.nextDouble() - 0.5);
        location = Administration.getInstance().calculateNewPosition(this.location, getSpeed(), (360 - angle) % 360);
//        sendChangeCommand(this,"location",location.x + ";" + location.y, Command.objectEnum.Bullet, fromServer);
    }

    public void setRandom() {
        this.r = new Random();
    }

    @Override
    public int takeDamage(int damageDone, boolean shouldSend) {
        this.destroy();
        return damageDone;

    }

//        /**
//         * Post this instance to ClientS.
//         */
//        private void sendPostMessage(){
//            if(game instanceof SinglePlayerGame) return;
//            if(!fromServer ){
//                Entity[] entity = new Entity[1];
//                entity[0] = this;
//                client.sendMessageAndReturn(new Command(Command.methods.POST, entity, Command.objectEnum.Entity));
//            }
//        }

}