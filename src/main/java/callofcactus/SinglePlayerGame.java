package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.*;
import callofcactus.entities.ai.AICharacter;
import callofcactus.entities.pickups.*;
import callofcactus.io.DatabaseManager;
import callofcactus.io.PropertyReader;
import callofcactus.map.MapFiles;
import callofcactus.role.AI;
import callofcactus.role.Boss;
import callofcactus.role.Sniper;
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
import com.badlogic.gdx.utils.TimeUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by guushamm on 16-11-15.
 */
public class SinglePlayerGame implements IGame {

    //sets the pixels per steps that are taken with every calculation in calculateNewPosition
    protected int steps = 1;
    protected ArrayList<Account> accountsInGame;
    protected boolean bossModeActive;
    protected int maxScore;
    protected int maxNumberOfPlayers;
    protected CopyOnWriteArrayList<NotMovingEntity> notMovingEntities;
    protected CopyOnWriteArrayList<MovingEntity> movingEntities;
    protected CopyOnWriteArrayList<HumanCharacter> players;
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
    private Administration administration = Administration.getInstance();
    private long lastSpawnTime;
    private int AInumber;
    private int AIAmount;
    private int maxAI;
    private int nextBossAI;

    //List for toremoveEntities
    private ArrayList<Entity> toRemoveEntities;

    //  Tiled Map
    private TiledMap tiledMap;
    private MapLayer collisionLayer;
    private ArrayList<MapObject> collisionObjects;
    private int mapWidth;
    private int mapHeight;

    private SpawnAlgorithm spawnAlgorithm;

    public SinglePlayerGame() {
        // TODO make this stuff dynamic via the db
        this.maxNumberOfPlayers = 1;
        this.bossModeActive = false;
        this.maxScore = 100;

        this.players = new CopyOnWriteArrayList<>();


        this.notMovingEntities = new CopyOnWriteArrayList<>();
        this.movingEntities = new CopyOnWriteArrayList<>();

        this.textures = new GameTexture();
        this.databaseManager = new DatabaseManager();

        try {
            this.propertyReader = new PropertyReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.intersector = new Intersector();
        this.random = new Random();


        this.lastSpawnTime = 0;
        this.AInumber = 0;
        this.AIAmount = 3;
        this.maxAI = 20;
        this.nextBossAI = 10;

        toRemoveEntities = new ArrayList<>();


        try {
            File f = new File("checkpoint.dat");
            if (f.isFile()) {
                readFromCheckpoint();
            }
        }
        catch (Exception e) {
            System.out.println("Errored at reading the file");
            e.printStackTrace();
        }

        this.spawnAlgorithm = new SpawnAlgorithm(this);

        try {
            this.addSinglePlayerHumanCharacter();
        } catch (NoValidSpawnException e) {
            e.printStackTrace();
        }
    }

    public SinglePlayerGame(MapFiles.MAPS map) {

        // TODO make this stuff dynamic via the db
        this.maxNumberOfPlayers = 1;
        this.bossModeActive = false;
        this.maxScore = 100;

        this.players = new CopyOnWriteArrayList<>();


        this.notMovingEntities = new CopyOnWriteArrayList<>();
        this.movingEntities = new CopyOnWriteArrayList<>();

        this.textures = new GameTexture();
        this.databaseManager = new DatabaseManager();

        try {
            this.propertyReader = new PropertyReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.intersector = new Intersector();
        this.random = new Random();


        this.lastSpawnTime = 0;
        this.AInumber = 0;
        this.AIAmount = 3;
        this.maxAI = 20;
        this.nextBossAI = 10;

        toRemoveEntities = new ArrayList<>();


        try {
            File f = new File("checkpoint.dat");
            if (f.isFile()) {
                readFromCheckpoint();
            }
        }
        catch (Exception e) {
            System.out.println("Errored at reading the file");
            e.printStackTrace();
        }

        //  Tiled Map implementation
        this.tiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load(MapFiles.getFileName(map));
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

        this.spawnAlgorithm = new SpawnAlgorithm(this);

        try {
            this.addSinglePlayerHumanCharacter();
        } catch (NoValidSpawnException e) {
            e.printStackTrace();
        }
    }


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

    public CopyOnWriteArrayList<NotMovingEntity> getNotMovingEntities() {
        return notMovingEntities;
    }

    public CopyOnWriteArrayList<HumanCharacter> getPlayers() {
        return players;
    }

    public CopyOnWriteArrayList<MovingEntity> getMovingEntities() {
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

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    public TiledMap getTiledMap()
    {
        return tiledMap;
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

        if (entity instanceof MovingEntity) {
            movingEntities.add((MovingEntity) entity);
            if (entity instanceof HumanCharacter) System.out.println("add human");
        } else {
            notMovingEntities.add((NotMovingEntity) entity);
        }
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
            pickup = new DamagePickup(this, new Vector2(1, 1), GameTexture.texturesEnum.damagePickupTexture, 50, 40, false);
        } else if (i == 1) {
            pickup = new HealthPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.healthPickupTexture, 35, 17, false);
        } else if (i == 2) {
            pickup = new SpeedPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.speedPickupTexture, 40, 40, false);
        } else if (i == 3) {
            pickup = new AmmoPickup(this, new Vector2(1, 1), GameTexture.texturesEnum.bulletTexture, 30, 30, false);
        } else if (i == 4) {
            pickup = new FireRatePickup(this, new Vector2(1, 1), GameTexture.texturesEnum.fireRatePickupTexture, 30, 40, false);
        }
        try {
            pickup.setLocation(generateSpawn(), true);
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
        //List<Entity> toRemoveEntities = new ArrayList<>();

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

            checkTiledMapCollision(a);

            for (int n = i + 1; n < entities.size(); n++) {
                //gets the second entity to compare to
                Entity b = entities.get(n);

                //Checks if the hitbox of entity a overlaps with the hitbox of entity b, for the hitboxes we chose to use rectangles
                if (a.getHitBox().overlaps(b.getHitBox())) {

                    //Every check needs to be checked twice for a and b and the other way around to
                    // make sure everything gets checked.
                    if (!checkBullet(a, b)) continue;
                    if (!checkBullet(b, a)) continue;

                    checkHumanCharacterAndAI(a, b);
                    checkHumanCharacterAndAI(b, a);

                    checkPickupAndHumanCharacter(a, b);
                    checkPickupAndHumanCharacter(b, a);

//                    checkNotMovingEntity(a, b);
//                    checkNotMovingEntity(b, a);

                }
            }
        }
        //This will destroy all the entities that will need to be destroyed for the previous checks.
        //this needs to be outside of the loop because you can't delete objects in a list while you're
        //working with the list
        toRemoveEntities.forEach(Entity::destroy);
        toRemoveEntities.clear();

    }

    private void checkTiledMapCollision(Entity a)
    {
        for (MapObject collisionObject : collisionObjects) {
            //  Check if object is an actual tile that should be collided with
            if (collisionObject instanceof TextureMapObject) {
                continue;
            }

            Rectangle wallHitbox;
            if (collisionObject instanceof RectangleMapObject) {
                Rectangle entityHitbox = a.getHitBox();

//                if (a instanceof MovingEntity) {
//                    entityHitbox.x += a.getSpriteWidth() / 2;
//                    entityHitbox.y += a.getSpriteHeight() / 2;
//                }

                wallHitbox = ((RectangleMapObject) collisionObject).getRectangle();

                if (entityHitbox.overlaps(wallHitbox)) {
                    if (a instanceof MovingEntity) {
                        if (a instanceof Bullet) {
                            toRemoveEntities.add(a);
                            return;
                        }
                        a.setLocation(a.getLastLocation(),false);
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

    @Override
    public ArrayList<MapObject> getCollisionObjects()
    {
        return collisionObjects;
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
            a.takeDamage(1, false);
            if (b instanceof AICharacter) {
                ((AICharacter) b).takeDamage(b.getDamage(), (HumanCharacter) ((Bullet) a).getShooter());
                //Add a kill if the AI is dead
                if (((AICharacter) b).getHealth() <= 0) {
                    getPlayer().addKill(false);
                    if (((AICharacter) b).getRole() instanceof Boss) {
                        getPlayer().addScore(5, false);
                    }
                    else {
                        getPlayer().addScore(1, false);
                    }

                }
            } else {
                b.takeDamage(a.getDamage(), false);
            }

            playRandomHitSound();

        }
        return true;
    }


    private void checkHumanCharacterAndAI(Entity a, Entity b) {

        //Check collision between AI and player
        if (a instanceof HumanCharacter && b instanceof AICharacter) {
            if (!this.getGodMode()) {
                System.out.println("B: " + b.getDamage() + ";  " + b.toString());
                a.takeDamage(b.getDamage(), false);
                if (((HumanCharacter) a).getHealth() <= 0) {
                    getPlayer().addDeath(false);
                }
            }
            toRemoveEntities.add(b);


            if (!getMuted()) {
                playRandomHitSound();
            }
        }
    }

    public void checkPickupAndHumanCharacter(Entity a, Entity b) {

        if (a instanceof HumanCharacter && b instanceof Pickup) {
            ((HumanCharacter) a).setCurrentPickup((Pickup) b);
            toRemoveEntities.add(b);
            if (!getMuted()) {
                playRandomHitSound();
            }
        }
    }

    public void checkNotMovingEntity(Entity a, Entity b) {

        if (a instanceof Pickup && !((Pickup) a).isSolid()) {
            return;
        }
        //checks if a MovingEntity has collided with a NotMovingEntity
        //if so, the current location will be set to the previous location
        if (a instanceof NotMovingEntity && ((NotMovingEntity) a).isSolid() && b instanceof MovingEntity) {
            b.setLocation(b.getLastLocation(),false);
        }
    }

    public GameSounds getGameSounds() {
        return administration.getGameSounds();
    }

    public HumanCharacter getPlayer() {
        return this.players.get(0);
    }

    public void spawnAI() {
        //Check if the last time you called this method was long enough to call it again.
        //You can change the rate at which the waves spawn by altering the parameter in secondsToMillis

        if (TimeUtils.millis() - lastSpawnTime < secondsToMillis(5)) {
            return;
        }
        waveNumber++;

        if (waveNumber % 10 == 0) {
            createCheckpoint();
            System.out.println("Checkpoint reached");
        }

        for (int i = 0; i < AIAmount; i++) {
            nextBossAI--;
            if (nextBossAI == 0) {
                nextBossAI = 10;
                createBossAI();
            } else {
                createMinionAI();
            }
        }
        if ((waveNumber % (int) getJSON().get(PropertyReader.PICKUP_PER_WAVE)) == 0) {
            for (int i = 0; i < 100; i++){
                this.createPickup();
            }

        }

        //The amount of AI's that will spawn next round will increase with 1 if it's not max already
        if (AIAmount < maxAI) {
            AIAmount++;
        }

        //Set the time to lastSpawnTime so you know when you should spawn next time
        lastSpawnTime = TimeUtils.millis();
    }

    private void createMinionAI() {
        //If it's not a boss

        AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + this.AInumber++), new AI(), getPlayer(), GameTexture.texturesEnum.aiTexture, 30, 30, false);

        try {
            a.setLocation(generateSpawn(),false);
        } catch (NoValidSpawnException nvs) {
            a.destroy();
        }
        //Set the speed for the AI's
        a.setSpeed(2, false);
    }

    private void createBossAI() {

        AICharacter a = new AICharacter(this, new Vector2(1, 1), ("AI" + AInumber++), new Boss(), getPlayer(), GameTexture.texturesEnum.bossTexture, 35, 70, false);
        try {
            a.setLocation(generateSpawn(),false);
        } catch (NoValidSpawnException nvs) {
            a.destroy();
        }
        //Set the speed for the AI's
        a.setSpeed(4, false);
    }

    public void addSinglePlayerHumanCharacter() throws NoValidSpawnException {
        Player p;
        if (administration.getLocalAccount() != null) {
            p = new HumanCharacter(this, generateSpawn(), "CaptainCactus", new Sniper(), GameTexture.texturesEnum.playerTexture, 64, 26, false, administration.getLocalAccount());
        }
        else {
            p = new HumanCharacter(this, generateSpawn(), "CaptainCactus", new Sniper(), GameTexture.texturesEnum.playerTexture, 64, 26, false);
        }
        this.players.add((HumanCharacter) p);

    }

    @Override
    public void playRandomHitSound() {
        administration.getGameSounds().playRandomHitSound();
    }

    @Override
    public void playRandomBulletSound() {
        administration.getGameSounds().playBulletFireSound();
    }

    public void createCheckpoint() {
        ArrayList<Object> objectsToSend = new ArrayList<>();
        objectsToSend.add(getPlayer());
        objectsToSend.add(getMovingEntities());
        objectsToSend.add(waveNumber);
        objectsToSend.add(AIAmount);
        objectsToSend.add(nextBossAI);
        System.out.println("Size of objecten: " + objectsToSend.size());


        FileOutputStream     fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream   oos = null;

        try {
            fos = new FileOutputStream("checkpoint.dat");
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Something went wrong here: FileNotFoundException");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong here : IOException");
            e.printStackTrace();
        }

        try {
            if (oos != null)
            {
                oos.writeObject(objectsToSend);
                oos.close();
            }

        }
        catch (IOException e)
        {
            System.out.println("Failed to write to file or close connection");
            e.printStackTrace();
        }
    }

    public void readFromCheckpoint()
    {
        FileInputStream   fis = null;
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;

        try
        {
            fis = new FileInputStream("checkpoint.dat");
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Exception!!");
            e.printStackTrace();
        }


        //Actual reading:
        ArrayList<Object> objectenInFile = null;
        try
        {
            objectenInFile = (ArrayList<Object>) ois.readObject();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (objectenInFile == null)
        {
            return;
        }

        //Create the player
        players.clear();
        HumanCharacter h = (HumanCharacter) objectenInFile.get(0);
        h.setGame(this);
        players.add(h);


        CopyOnWriteArrayList<MovingEntity> toSetTheGame = (CopyOnWriteArrayList<MovingEntity>) objectenInFile.get(1);
        for (Entity e : toSetTheGame) {
            e.setGame(this);
        }
        //Moving entities
        movingEntities = toSetTheGame;
        waveNumber = (int) objectenInFile.get(2);
        AIAmount = (int) objectenInFile.get(3);
        nextBossAI = (int) objectenInFile.get(4);
    }

    public void deleteCheckpoint() {
        try
        {
            Files.delete(Paths.get("checkpoint.dat"));
        }
        catch (IOException e)
        {
            System.out.println("Failed to delete the checkpoint");
            e.printStackTrace();
        }
    }
}
