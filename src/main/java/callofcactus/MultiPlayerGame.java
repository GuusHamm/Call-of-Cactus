package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.*;
import callofcactus.entities.ai.AICharacter;
import callofcactus.entities.pickups.*;
import callofcactus.io.DatabaseManager;
import callofcactus.io.PropertyReader;
import callofcactus.map.MapFiles;
import callofcactus.role.Boss;
import callofcactus.role.Sniper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by guushamm on 16-11-15.
 */
public class MultiPlayerGame implements IGame {

    //sets the pixels per steps that are taken with every calculation in calculateNewPosition
    protected int steps = 1;
    protected ArrayList<Account> accountsInGame;
    protected boolean bossModeActive;
    protected int maxScore;
    protected int maxNumberOfPlayers;
    protected ArrayList<NotMovingEntity> notMovingEntities;
    protected ArrayList<MovingEntity> movingEntities;
    protected ArrayList<HumanCharacter> players;
    protected Vector2 mousePositions = new Vector2(0, 0);
    protected PropertyReader propertyReader;
    protected Intersector intersector;
    protected int waveNumber = 0;
    protected GameTexture textures;
    protected Random random;
    protected boolean godMode = false;
    protected boolean muted = true;
    protected DatabaseManager databaseManager;
    protected HashMap<InetAddress, Account> administraties = new HashMap<>();

    //  Tiled Map
    private TiledMap tiledMap;
    private MapLayer collisionLayer;
    private ArrayList<MapObject> collisionObjects;
    private int mapWidth;
    private int mapHeight;

    public MultiPlayerGame() {

        // TODO make this stuff dynamic via the db
        this.maxNumberOfPlayers = 1;
        this.bossModeActive = false;
        this.maxScore = 100;

        this.players = new ArrayList<>();


        this.notMovingEntities = new ArrayList<>();
        this.movingEntities = new ArrayList<>();

        this.textures = new GameTexture();
        this.databaseManager = new DatabaseManager();

        try {
            this.propertyReader = new PropertyReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.intersector = new Intersector();
        this.random = new Random();

//        ServerS ss = new ServerS(this);

        //       ClientS s = new ClientS();
        //s.sendMessage("playrandombulletsound");

        //addSinglePlayerHumanCharacter();

        //  Tiled Map implementation
        this.tiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load(MapFiles.getFileName(MapFiles.MAPS.COMPLICATEDMAP));
        //  Set the layer you want entities to collide with
        this.collisionLayer = tiledMap.getLayers().get("CollisionLayer");
        //  Get all the objects (walls) from the designated collision layer and add them to the arraylist
        MapObjects          mapObjects = collisionLayer.getObjects();
        Iterator<MapObject> iterator   = mapObjects.iterator();

        this.collisionObjects = new ArrayList<>();

        while (iterator.hasNext()) {
            this.collisionObjects.add(iterator.next());
        }

        MapProperties prop = tiledMap.getProperties();
        mapWidth = prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class);
        mapHeight = prop.get("height", Integer.class) * prop.get("tileheight", Integer.class);
    }

//    public void addSinglePlayerHumanCharacter() {
//        Player p = new HumanCharacter(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), "CaptainCactus", new Sniper(), GameTexture.texturesEnum.playerTexture, 64, 26);
//        this.players.add((HumanCharacter) p);
//    }


    public boolean getMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public GameTexture getTextures() {
        return textures;
    }

    public void setMousePositions(int x, int y) {
        this.mousePositions = new Vector2(x, y);
    }

    public JSONObject getJSON() {
        return propertyReader.getJsonObject();
    }

    public ArrayList<NotMovingEntity> getNotMovingEntities() {
        return notMovingEntities;
    }

    public ArrayList<HumanCharacter> getPlayers() {
        return players;
    }

    public HumanCharacter getPlayer() {
        return players.get(0);
    }

    public synchronized ArrayList<MovingEntity> getMovingEntities() {
        return movingEntities;
    }

    public Vector2 getMouse() {
        float x = this.mousePositions.x;//Gdx.input.getX();
        float y = this.mousePositions.y;// Gdx.input.getY();

        return new Vector2(x, y);
    }

    public List<Entity> getAllEntities() {
        List<Entity> result = new ArrayList<>();
        result.addAll(notMovingEntities);
        result.addAll(movingEntities);
        return Collections.unmodifiableList(result);
    }

    @Override
    public void setAllEntities(List<Entity> entities) {

        notMovingEntities.clear();
        movingEntities.clear();
        players.clear();

        for (Entity e : entities) {
            if (e instanceof NotMovingEntity) {
                notMovingEntities.add((NotMovingEntity) e);
            } else if (e instanceof HumanCharacter) {
                players.add((HumanCharacter) e);
            }
            if (e instanceof MovingEntity) {
                movingEntities.add((MovingEntity) e);
            }

        }
    }

    public boolean getGodMode() {
        return this.godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public int getWaveNumber() {
        return this.waveNumber;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    /**
     * Generates spawnvectors for every entity in the callofcactus that needs to be spawned.
     * This includes players (both human and AI), bullets, pickups and all not-moving entities.
     *
     * @return the spawnvector for the selected entity
     * @throws NoValidSpawnException Thrown when no valid spawn position has been found
     */
    public Vector2 generateSpawn() throws NoValidSpawnException {

        SpawnAlgorithm spawnAlgorithm = new SpawnAlgorithm(this);
        return spawnAlgorithm.findSpawnPosition();
    }

    /**
     * Calculates the angle between two vectors
     *
     * @param beginVector : The vector that will be used as center
     * @param endVector   : Where the object has to point to
     * @return Returns the angle, this will be between 0 and 360 degrees
     */
    public int angle(Vector2 beginVector, Vector2 endVector) {
        return (360 - (int) Math.toDegrees(Math.atan2(endVector.y - beginVector.y, endVector.x - beginVector.x))) % 360;
    }

    /**
     * Calculates the new position between the currentPosition to the Endposition.
     *
     * @param currentPosition : The current position of the object
     * @param EndPosition     : The position of the end point
     * @param speed           : The speed that the object can move with
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, Vector2 EndPosition, double speed) {

        float x = currentPosition.x;
        float y = currentPosition.y;

        //gets the difference of the two x coordinates
        double differenceX = EndPosition.x - x;
        //gets the difference of the two y coordinates
        double differenceY = EndPosition.y - y;

        //pythagoras formula
        double c = Math.sqrt(Math.pow(Math.abs(differenceX), 2) + Math.pow(Math.abs(differenceY), 2));

        if (c <= (steps * speed)) {
            return EndPosition;
        }

        double ratio = c / (steps * speed);

        x += (differenceX / ratio);
        y += (differenceY / ratio);

        return new Vector2(x, y);
    }

    /**
     * Calculates the new position from a beginposition and a angle..
     *
     * @param currentPosition : The current position of the object
     * @param speed           : The speed that the object can move with
     * @param angle           : The angle of where the object should be heading
     * @return the new position that has been calculated
     */
    public Vector2 calculateNewPosition(Vector2 currentPosition, double speed, double angle) {

        double newAngle = angle + 90f;

        double x = currentPosition.x;
        double y = currentPosition.y;

        //uses sin and cos to calculate the EndPosition
        x = x + (Math.sin(Math.toRadians(newAngle)) * (steps * speed));
        y = y + (Math.cos(Math.toRadians(newAngle)) * (steps * speed));

        float xF = Float.parseFloat(Double.toString(x));
        float yF = Float.parseFloat(Double.toString(y));
        return new Vector2(xF, yF);
    }

    /**
     * Called when an entity needs to be added to the callofcactus (Only in the memory, but it is not actually drawn)
     *
     * @param entity : Entity that should be added to the callofcactus
     */
    public void addEntityToGame(Entity entity) {

        if (entity.getID() == -1) {
            entity.setID(Entity.getNxtID());
        }
        if (entity instanceof MovingEntity) {
            movingEntities.add((MovingEntity) entity);
            if (entity instanceof HumanCharacter) System.out.println("add human");
        } else {
            notMovingEntities.add((NotMovingEntity) entity);
        }
    }
    /**
     * Called when an entity needs to be added to the callofcactus (Only in the memory, but it is not actually drawn)
     *
     * @param entity : Entity that should be added to the callofcactus
     */
    public int addEntityToGameWithIDReturn(Entity entity) {
        if (entity.getID() == 0 || entity.getID() == -1) {
            entity.setID(Entity.getNxtID());
        }
        if (entity instanceof MovingEntity) {
            movingEntities.add((MovingEntity) entity);
        } else {
            notMovingEntities.add((NotMovingEntity) entity);
        }
        System.out.println("My ideeee is : "+entity.getID());
        return entity.getID();
    }

    public void removeEntityFromGame(Entity entity) {

        if (entity instanceof MovingEntity) {
            movingEntities.remove(entity);
            if (entity instanceof HumanCharacter)
                System.out.println("remove human");
            //  TODO change end callofcactus condition for iteration 2 of the callofcactus

        } else if (entity instanceof NotMovingEntity) {
            notMovingEntities.remove(entity);
        }
    }

    public void createPickup() {
        int i = (int) (Math.random() * 5);

        Pickup pickup = null;
        if (i == 0) {
            pickup = new DamagePickup(this, new Vector2(1, 1), GameTexture.texturesEnum.damagePickupTexture, 50, 40, true);
        } else if (i == 1) {
            pickup = new HealthPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.healthPickupTexture, 35, 17, true);
        } else if (i == 2) {
            pickup = new SpeedPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.speedPickupTexture, 40, 40, true);
        } else if (i == 3) {
            pickup = new AmmoPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.bulletTexture, 30, 30, true);
        } else if (i == 4) {
            pickup = new FireRatePickup(this, new Vector2(1, 1), GameTexture.texturesEnum.fireRatePickupTexture, 30, 40, true);
        }
        try {
            pickup.setLocation(generateSpawn(),true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long secondsToMillis(int seconds) {
        return seconds * 1000;
    }

    /**
     * This method checks every entity in callofcactus if two hitboxes overlap, if they do the appropriate action will be taken.
     * This method has reached far beyond what should be asked of a single method but it works.
     * Follow the comments on its threaturous path and you will succes in finding what you seek.
     * This should also be ported to callofcactus in the next itteration.
     */
    public void compareHit() {

        //Gets all the entities to check
        List<Entity> entities = this.getAllEntities();
        //A list to put the to remove entities in so they won't be deleted mid-loop.
        List<Entity> toRemoveEntities = new ArrayList<>();

        //A if to make sure the player is correctly checked in the list of entities

        for (HumanCharacter h : players) {
            if (!entities.contains(h)) {
                addEntityToGame(h);
            }
        }

        //starts a loop of entities that than creates a loop to compare the entity[i] to entity[n]
        //n = i+1 to prevent double checking of entities.
        //Example:
        // entity[1] == entity[2] will be checked
        // entity[2] == entity[1] will not be checked
        //this could be shorter by checking both
        //instead of the ifs but this will be re-evaluated once past the first iteration
        for (int i = 0; i < entities.size(); i++) {
            //gets the first entity to compare to
            Entity a = entities.get(i);

            checkTiledMapCollision(a, toRemoveEntities);

            for (int n = i + 1; n < entities.size(); n++) {
                //gets the second entity to compare to
                Entity b = entities.get(n);

                //Checks if the hitbox of entity a overlaps with the hitbox of entity b, for the hitboxes we chose to use rectangles
                if (a.getHitBox().overlaps(b.getHitBox())) {

                    //Every check needs to be checked twice for a and b and the other way around to
                    // make sure everything gets checked.
                    if (!checkBullet(a, b)) continue;
                    if (!checkBullet(b, a)) continue;

                    checkHumanCharacterAndAI(a, b, toRemoveEntities);
                    checkHumanCharacterAndAI(b, a, toRemoveEntities);

                    checkPickupAndHumanCharacter(a, b, toRemoveEntities);
                    checkPickupAndHumanCharacter(b, a, toRemoveEntities);

                    checkNotMovingEntity(a, b, toRemoveEntities);
                    checkNotMovingEntity(b, a, toRemoveEntities);

                }
            }
        }
        //This will destroy all the entities that will need to be destroyed for the previous checks.
        //this needs to be outside of the loop because you can't delete objects in a list while you're
        //working with the list
        toRemoveEntities.forEach(Entity::destroy);

    }

    private void checkTiledMapCollision(Entity a, List<Entity> toRemoveEntities)
    {
        for (MapObject collisionObject : collisionObjects) {
            //  Check if object is an actual tile that should be collided with
            if (collisionObject instanceof TextureMapObject) {
                continue;
            }

            Rectangle wallHitbox;
            if (collisionObject instanceof RectangleMapObject) {
                Rectangle entityHitbox = a.getHitBox();

                wallHitbox = ((RectangleMapObject) collisionObject).getRectangle();

                if (entityHitbox.overlaps(wallHitbox)) {
                    if (a instanceof MovingEntity) {
                        if (a instanceof Bullet) {
                            toRemoveEntities.add(a);
                            return;
                        }
                        a.setLocation(a.getLastLocation(),true);
                        return;
                    }
                    else {
                        toRemoveEntities.add(a);
                        return;
                    }
                }
            }
            else {
                System.out.println("Map object instance isn't a rectangle map object");
            }
        }
    }

    /**
     * Executes actions that need to be executed if a bullet collides with something
     *
     * @param a
     * @param b
     * @return
     */
    private boolean checkBullet(Entity a, Entity b) {

        if (a instanceof Bullet) {
            if (b instanceof Pickup && !((Pickup) b).isSolid())
                return false;


            //makes it so your own bullets wont destroy eachother
            if (b instanceof Bullet && ((Bullet) a).getShooter().equals(((Bullet) b).getShooter()))
                return false;

            //if b is the shooter of bullet a then continue to the next check.
            //because friendly fire is off standard
            if (b instanceof HumanCharacter && ((Bullet) a).getShooter() == b)
                return false;


            //if the bullet hit something the bullet will disapear by taking damage (this is standard behaviour for bullet.takedamage())
            // and the other entity will take the damage of the bullet.
            a.takeDamage(1);
            if (b instanceof AICharacter) {
                ((AICharacter) b).takeDamage(b.getDamage(), (HumanCharacter) ((Bullet) a).getShooter());
            } else {
                b.takeDamage(a.getDamage());
                //Check if a Bullet hits an enemy
                if (b instanceof HumanCharacter) {
                    //Check if the health is less or equal to zero.
                    if (((HumanCharacter) b).getHealth() <= 0) {
                        ((HumanCharacter) b).addDeath();

                        checkBossMode((HumanCharacter) b);

                        //Add a kill to the person who shot the Bullet
                        if (((Bullet) a).getShooter() instanceof HumanCharacter) {
                            HumanCharacter h = (HumanCharacter) ((Bullet) a).getShooter();
                            h.addKill();

                            if (h.getKillCount() >= h.getKillToBecomeBoss() && h.getCanBecomeBoss()) {
                                h.becomeBoss();
                                for (HumanCharacter hm : players) {
                                    hm.setCanBecomeBoss(false);
                                }
                            }
                        }



                    }
                }
            }

            playRandomHitSound();

        }
        return true;
    }


    private void checkHumanCharacterAndAI(Entity a, Entity b, List<Entity> toRemoveEntities) {

        //Check collision between AI and player
        if (a instanceof HumanCharacter && b instanceof AICharacter) {
            if (!this.getGodMode()) {
                System.out.println("B: " + b.getDamage() + ";  " + b.toString());
                a.takeDamage(b.getDamage());
            }
            toRemoveEntities.add(b);


            if (!getMuted()) {
                playRandomHitSound();
            }
        }
    }

    public void checkPickupAndHumanCharacter(Entity a, Entity b, List<Entity> toRemoveEntities) {

        if (a instanceof HumanCharacter && b instanceof Pickup) {
            ((HumanCharacter) a).setCurrentPickup((Pickup) b);
            toRemoveEntities.add(b);
            if (!getMuted()) {
                playRandomHitSound();
            }
        }
    }

    public void checkNotMovingEntity(Entity a, Entity b, List<Entity> toRemoveEntities) {

        if (a instanceof Pickup && !((Pickup) a).isSolid()) {
            return;
        }
        //checks if a MovingEntity has collided with a NotMovingEntity
        //if so, the current location will be set to the previous location
        if (a instanceof NotMovingEntity && ((NotMovingEntity) a).isSolid() && b instanceof MovingEntity) {
            b.setLocation(b.getLastLocation(),true);
        }
    }

    public synchronized void playRandomHitSound() {
        return;
    }

    public synchronized void playRandomBulletSound() {
        return;
    }

    @Override
    public ArrayList<MapObject> getCollisionObjects()
    {
        return this.collisionObjects;
    }


    public HumanCharacter searchPlayer(int id) {

        for (HumanCharacter p : players) {
            if (p.getID() == id) {
                HumanCharacter player = p;
                return player;
            }
        }

        return null;
    }
    public void respawnAllPlayers() {
        for (HumanCharacter h : players) {
            h.respawn();
            h.setCanBecomeBoss(true);
        }
    }

    public void checkBossMode(HumanCharacter h) {
        //Check if BossMode active is.
        if (!bossModeActive) {
            (h).respawn();
        }

        //Check if the person who died is the Boss
        if (h.getRole() instanceof Boss) {
            //TODO grab the original Role that the player was
            h.changeRole(new Sniper());
            h.setKillToBecomeBoss();
            respawnAllPlayers();
        }
    }
}
