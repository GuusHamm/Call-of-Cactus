package callofcactus.entities;

import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.account.Account;
import callofcactus.entities.pickups.*;
import callofcactus.io.PropertyReader;
import callofcactus.role.Role;
import callofcactus.role.Sniper;
import callofcactus.role.Soldier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class Player extends MovingEntity implements Serializable {

    protected int health;
    protected int fireRate;
    protected String name;
    protected int direction;

    protected Role role;

    protected Pickup currentPickup;

    protected Account account;

    protected int baseDamage;
    protected int baseSpeed;
    protected int baseFireRate;

    /**
     * @param game          : The callofcactus of which the entity belongs to
     * @param spawnLocation : The location where the player will start
     * @param name          : The name that will be displayed
     * @param role          : The role that the player will play
     * @param spriteHeight  The height of characters sprite
     * @param spriteTexture callofcactus.Texture to use for this AI
     * @param spriteWidth   The width of characters sprite
     */
    protected Player(IGame game, Vector2 spawnLocation, String name, Role role, GameTexture.texturesEnum spriteTexture, int spriteWidth, int spriteHeight, boolean fromServer, Account account) {
        // TODO - implement Player.Player
        super(game, spawnLocation, spriteTexture, spriteWidth, spriteHeight, fromServer);

        int baseHealth = 20;
        baseDamage = 1;
        baseSpeed = 10;
        baseFireRate = 20;

        if (game != null) {
            JSONObject jsonObject = game.getJSON();

            try {
                baseHealth = (int) jsonObject.get(PropertyReader.PLAYER_HEALTH);
                baseDamage = (int) jsonObject.get(PropertyReader.PLAYER_DAMAGE);
                baseSpeed = (int) jsonObject.get(PropertyReader.PLAYER_SPEED);
                baseFireRate = (int) jsonObject.get(PropertyReader.PLAYER_FIRERATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.health = (int) Math.round(baseHealth * role.getHealthMultiplier());
        this.damage = (int) Math.round(baseDamage * role.getDamageMultiplier());
        this.speed = (int) Math.round(baseSpeed * role.getSpeedMultiplier());
        this.fireRate = (int) Math.round(baseFireRate * role.getFireRateMultiplier());

        this.role = role;
        this.name = name;
        this.direction = 0;
        this.currentPickup = null;
        this.account = account;

        super.setStartHealth();
    }

    protected Player() {

    }

    public int getFireRate() {
        return fireRate;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return super.health;
    }

    public Role getRole() {
        return role;
    }

    public Account getAccount() {return account;}

    public int getBaseDamage() {return (int) (baseDamage * role.getDamageMultiplier());}

    public int getBaseSpeed() {return (int) (baseSpeed * role.getSpeedMultiplier());}

    public int getBaseFireRate() { return (int) (baseFireRate * role.getFireRateMultiplier());}
    /**
     * @param damageDone : The amount of damage that the player will take
     * @return returns the current health of the player
     */
    public int takeDamage(int damageDone) {
        // TODO - implement Player.takeDamage

        super.health -= damageDone;
        System.out.println("Entity " + this.getID() +"has taken damage. New health: " + health);
        if (super.health <= 0) {
            super.destroy();

        }
        return super.health;
    }

    public void setCurrentPickup(Pickup newPickup) {
        this.currentPickup = newPickup;

        Timer timer = new Timer();

        if (newPickup != null) {
            //in case of a damage pickup
            if (newPickup instanceof DamagePickup) {
                DamagePickup pickup = (DamagePickup) newPickup;
                pickup.setInitialValue(damage);
                damage = (int) (damage * pickup.getDamageBoost());
                if (this instanceof HumanCharacter) {
                    HumanCharacter h = (HumanCharacter) this;
                    h.setDamage(h.getDamage() + (int)pickup.getDamageBoost(), false);
                }
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        setPlayerBaseDamage();
                        damage = getBaseDamage();
                    }
                }, pickup.getEffectTime());
                timer.start();
            }
            // in case of a health pickup
            if (newPickup instanceof HealthPickup) {
                HealthPickup pickup = (HealthPickup) newPickup;
                health = (int) (health + pickup.getHealthBoost());
                if (this instanceof HumanCharacter) {
                    HumanCharacter h = (HumanCharacter) this;
                    h.setHealth((h.getHealth() + (int)pickup.getHealthBoost()), false);
                }
            }
            //In case of a speed pickup
            if (newPickup instanceof SpeedPickup) {
                SpeedPickup pickup = (SpeedPickup) newPickup;
                pickup.setInitialValue(speed);
                speed = (int) (speed * pickup.getSpeedBoost());
                if (this instanceof HumanCharacter) {
                    HumanCharacter h = (HumanCharacter) this;
                    h.setSpeed(h.getSpeed() + (int)pickup.getSpeedBoost(), false);
                }
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        setPlayerBaseSpeed();
                        speed = getBaseSpeed();
                    }
                }, pickup.getEffectTime());
                timer.start();

            }
            //in case of an ammo pickup
            if (newPickup instanceof AmmoPickup) {
                AmmoPickup pickup = (AmmoPickup) newPickup;
                role.setAmmo((int) pickup.getAmmoBoost());

            }
            //in case of a firerate pickup
            if (newPickup instanceof FireRatePickup) {
                FireRatePickup pickup = (FireRatePickup) newPickup;
                pickup.setInitialValue(fireRate);
                fireRate = (int) (fireRate / pickup.getFireRateBoost());
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        fireRate = getBaseFireRate();
                    }
                }, pickup.getEffectTime());
                timer.start();
            }
        }

    }

    public void setPlayerBaseSpeed() {
        HumanCharacter h = (HumanCharacter) this;
        h.setSpeed(getBaseSpeed(), false);
    }

    public void setPlayerBaseDamage() {
        HumanCharacter h = (HumanCharacter) this;
        h.setDamage(getBaseDamage(), false);
    }

    public void fireBullet(GameTexture.texturesEnum texture) {
        if (game != null) {
            if (game.getGodMode()) {
                for (int i = 0; i < 72; i++) {
                    new Bullet(game, location, this, role.getDamageMultiplier(), 2, texture, (i * 5), 15, 15, false);
                }
            }
        }
        //Fire a normal bullet
        System.out.printf("angle: "+ angle);
        new Bullet(game, location, this, role.getDamageMultiplier(), 1, texture, angle, 15, 15, false);
    }

    public void fireBulletShotgun(GameTexture.texturesEnum texture) {
        if (role instanceof Soldier) {
            if (role.getAmmo() >= 1) {

                new Bullet(game, location, this, (role.getDamageMultiplier() / 2), 1, texture, angle, 15, 15, false);
                new Bullet(game, location, this, (role.getDamageMultiplier() / 2), 1, texture, angle + 5, 15, 15, false);
                new Bullet(game, location, this, (role.getDamageMultiplier() / 2), 1, texture, angle - 5, 15, 15, false);

                if(game != null) {
                    if (!game.getGodMode()) {
                        role.setAmmo(-1);
                    }
                }
            }
        }
        if (role instanceof Sniper) {
            if (role.getAmmo() >= 1) {

                new Bullet(game, location, this, (role.getDamageMultiplier() * 2), 2, texture, angle, 25, 25, false);

                if(game != null) {
                    if (!game.getGodMode()) {
                        role.setAmmo(-1);
                    }
                }
            }
        }


    }

    /**
     * @param newRole : The role that the player will play as
     */
    public void changeRole(Role newRole) {
        // TODO - implement Player.changeRole

        //first return everything to it's base value
        this.health = (int) Math.round(this.health / role.getHealthMultiplier());
        this.damage = (int) Math.round(this.damage / role.getDamageMultiplier());
        this.speed = (int) Math.round(this.getSpeed() / role.getSpeedMultiplier());
        this.fireRate = (int) Math.round(this.fireRate / role.getFireRateMultiplier());

        this.role = newRole;

        this.health = (int) Math.round(this.health * role.getHealthMultiplier());
        this.damage = (int) Math.round(this.damage * role.getDamageMultiplier());
        this.speed = (int) Math.round(this.getSpeed() * role.getSpeedMultiplier());
        this.fireRate = (int) Math.round(this.fireRate * role.getFireRateMultiplier());
    }


    //TODO bug that needs fixing, Entity damage is not the same as in player
    @Override
    public int getDamage() {
        return damage;
    }
}