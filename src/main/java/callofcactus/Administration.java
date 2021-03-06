package callofcactus;

import callofcactus.account.Account;
import callofcactus.entities.*;
import callofcactus.io.DatabaseManager;
import callofcactus.map.MapFiles;
import callofcactus.menu.GameScreen;
import callofcactus.multiplayer.ClientS;
import callofcactus.multiplayer.ClientSideServer;
import callofcactus.multiplayer.Command;
import callofcactus.multiplayer.Rank;
import callofcactus.role.Soldier;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Wouter Vanmulken on 23-11-2015.
 */
public class Administration {
    private static Administration instance = null;
    private GameTexture gameTextures;
    private GameSounds gameSounds;
    private GameScreen gameScreen;
    private Account localAccount;
    private HumanCharacter localPlayer;
    private DatabaseManager databaseManager = new DatabaseManager();
    private boolean muted = false;
    private boolean godmode = false;
    private CopyOnWriteArrayList<NotMovingEntity> notMovingEntities;
    private CopyOnWriteArrayList<MovingEntity> movingEntities;
    private CopyOnWriteArrayList<HumanCharacter> players;
    private Vector2 mousePosition;
    private int steps = 1;
    private int matchID = 0;
    private boolean bossModeActive = false;

    private ClientS client = ClientS.getInstance();

    // Account info
    private String totalKills;
    private String totalDeaths;
    private String totalScore;
    private String totalGamesPlayed;

    //  MP_Score
    private HashMap<String, Integer> scoreBoard;
    private boolean connectionLost = false;

    // Rank
    private Rank rank;

    private ClientSideServer clientSideServer ;

    //  Tiled Map initialization with destructible objects
    private TiledMap tiledMap;
    private TiledMapTileLayer destrWallLayer;
    private MapObjects mapObjects;
    private MapLayer collisionLayer;
    private ArrayList<MapObject> collisionObjects;


    private boolean mapChanged;

    private Administration()
    {


        this.notMovingEntities = new CopyOnWriteArrayList<>();
        this.movingEntities = new CopyOnWriteArrayList<>();
        this.players = new CopyOnWriteArrayList<>();
        //this.localAccount = localAccount;
        this.gameTextures = new GameTexture();
        this.gameSounds = new GameSounds(this);

        this.scoreBoard = new HashMap<>();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName(), h.getKillCount());
        }

//        try {
            this.tiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load(MapFiles.getFileName(MapFiles.MAPS.COMPLICATEDMAP));
            destrWallLayer = (TiledMapTileLayer) tiledMap.getLayers().get("DestructibleLayer");
//        } catch (NullPointerException e) {
//            this.tiledMap = null;
//            destrWallLayer = null;
//        }
        //  Tiled Map initialization

        //  Set the layer you want entities to collide with
        this.collisionLayer = tiledMap.getLayers().get("CollisionLayer");
        //  Get all the objects (walls) from the designated collision layer and add them to the arraylist
        MapObjects mapObjects = collisionLayer.getObjects();
        Iterator<MapObject> iterator = mapObjects.iterator();
        this.collisionObjects = new ArrayList<>();
        while (iterator.hasNext()) {
            this.collisionObjects.add(iterator.next());
        }


        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                updateEntities();
            }
        }, 10);
    }

    public static Administration getInstance()
    {
        if (instance == null) {
            instance = new Administration();
            instance.setClientS();
        }
        return instance;
    }

    public boolean isMapChanged() {
        boolean currentState = mapChanged;
        mapChanged = false;
        return currentState;
    }

    public MapObjects getMapObjects()
    {
        return mapObjects;
    }

    public ArrayList<MapObject> getCollisionObjects()
    {
        return collisionObjects;
    }

    public boolean isConnectionLost() {
        return connectionLost;
    }

    public void setConnectionLost(boolean connectionLost)
    {
        this.connectionLost = connectionLost;
    }

    public Rank getRank(){return rank;}

    public void setRank(Rank rank){this.rank = rank;}

    public String getTotalKills() {
        return totalKills;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getTotalScore() {
        if (totalScore == null)
            return "0";

        return totalScore;
    }

    public boolean getBossModeActive()
    {
        return bossModeActive;
    }

    public void setBossModeActive(boolean value) {
        bossModeActive = value;
    }

    public String getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setClientS(){
        if(clientSideServer == null) {
            clientSideServer = new ClientSideServer();
        }
        if(instance == null){
            getInstance();
        }
        if(client==null) {
            client = ClientS.getInstance();
        }
    }
    public long secondsToMillis(int seconds) {
        return seconds * 1000;
    }

    public void removeEntity(Entity entity) {

        if (entity instanceof MovingEntity) {

            if (entity instanceof HumanCharacter){
                // Start respawn cycle
                if (entity.getID() == localPlayer.getID()){
                    localAccount = ((HumanCharacter) entity).getAccount();
                    System.out.println("Respawn in 3 seconds.");
                    // Create a new local player after 3 seconds
                    if (!getBossModeActive()) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                addSinglePlayerHumanCharacter();
                            }
                        }, 3000);
                    }

                }

            }
            movingEntities.remove(entity);

//            if (entity instanceof HumanCharacter)
//                System.out.println("remove human");
//                players.remove(entity);
            //  TODO change end callofcactus condition for iteration 2 of the callofcactus

        } else if (entity instanceof DestructibleWall) {
            destrWallLayer.getCell(((DestructibleWall)entity).getCellX(), ((DestructibleWall)entity).getCellY()).setTile(null);
            System.out.println("Cell set to null");
            this.mapChanged = true;
        } else if (entity instanceof NotMovingEntity) {
            notMovingEntities.remove(entity);
        }
    }

    public int angle(Vector2 beginVector, Vector2 endVector) {
        return (360 - (int) Math.toDegrees(Math.atan2(endVector.y - beginVector.y, endVector.x - beginVector.x))) % 360;
    }

    public Vector2 getMouse() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();

        return new Vector2(x, y);
    }
    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(int x, int y) {
        this.mousePosition = new Vector2(x,y);
    }

    public GameTexture getGameTextures() {
        return gameTextures;
    }

    public ClientS getClient() {
        return client;
    }

    public GameSounds getGameSounds() {
        return gameSounds;
    }

    public boolean getMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean getGodmode() {
        return godmode;
    }

    public void setGodmode(boolean godmode) {
        this.godmode = godmode;
    }

    public HumanCharacter getLocalPlayer() {
        for (MovingEntity movingEntity : movingEntities){
            if (movingEntity instanceof HumanCharacter){
                if ( movingEntity.getID() == localPlayer.getID()){
                    return (HumanCharacter)movingEntity;
                }
            }
        }

        return localPlayer;
    }

    public void setLocalPlayer(HumanCharacter localPlayer) {
        this.localPlayer = localPlayer;
    }

    public Account getLocalAccount() {
        return localAccount;
    }

    public void setLocalAccount(Account localAccount) {
        this.localAccount = localAccount;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public List<NotMovingEntity> getNotMovingEntities() {
        return notMovingEntities;
    }

    public CopyOnWriteArrayList<MovingEntity> getMovingEntities() {
        return movingEntities;
    }

    public List<HumanCharacter> getPlayers() {

        List<MovingEntity> searchables = movingEntities;
        List<HumanCharacter> returnValues = searchables.stream().filter(e -> e instanceof HumanCharacter).map(e -> (HumanCharacter) e).collect(Collectors.toList());
        return returnValues;
    }

    public List<Entity> getAllEntities() {
        List<Entity> entities = new ArrayList<Entity>();
        entities.addAll(notMovingEntities);
        entities.addAll(movingEntities);
        entities.addAll(players);
        return entities;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public void updateScoreBoard()
    {
        Collections.sort(this.players);

        this.scoreBoard.clear();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName()+"Kills", h.getScore());
            scoreBoard.put(h.getName()+"Deaths", h.getScore());
        }
    }

    public HashMap<String, Integer> getScoreBoard() {
        Collections.sort(this.players);

        this.scoreBoard.clear();

        for (HumanCharacter h : this.players) {
            scoreBoard.put(h.getName()+"Kills", h.getAccount().getKillCount());
            scoreBoard.put(h.getName()+"Deaths", h.getAccount().getDeathCount());
        }

        return this.scoreBoard;
    }

    public void updateEntities() {
//        players           = client.getLatestUpdatesPlayers(players);
//        movingEntities    = client.getLatestUpdatesMovingEntities(movingEntities);
//        notMovingEntities = client.getLatestUpdatesNotMovingEntities(notMovingEntities);

        updateScoreBoard();
    }

    public void setEntitiesClientS(Entity[] originalEntities, Entity[] entities) {

        for (int i = 0; i < originalEntities.length; i++) {

            int index = notMovingEntities.indexOf(originalEntities[i]);
            if(index!=-1){
                notMovingEntities.set(index, (NotMovingEntity) entities[i]);
            }

            index = movingEntities.indexOf(originalEntities[i]);
            if(index!=-1){
                movingEntities.set(index, (MovingEntity) entities[i]);
            }

            index = players.indexOf(originalEntities[i]);
            if(index!=-1){
                players.set(index, (HumanCharacter) entities[i]);

            }

            if(originalEntities[i] == localPlayer ){
                localPlayer = (HumanCharacter) entities[i];

            }

            if (entities[i] instanceof DestructibleWall) {
                DestructibleWall wall = (DestructibleWall)entities[i];
                if (wall.getHealth() <= 0) {
                    removeEntity(wall);
                }
            }
        }
//        for(Entity e : getAllEntities()){
//            System.out.println("fuck guus" + e.getID());
//        }
    }
    public void addEntity(Entity e){

        if(e instanceof HumanCharacter){
            players.add((HumanCharacter) e);
        }
        if(e instanceof NotMovingEntity){
            notMovingEntities.add((NotMovingEntity) e);
        }
        if(e instanceof MovingEntity){
            movingEntities.add((MovingEntity) e);
        }
    }
    public void replaceEntity(Entity e){

        int index = notMovingEntities.indexOf(e);
        if(index!=-1){
            notMovingEntities.set(index, (NotMovingEntity) e);
        }
        if(index==-1 && e instanceof NotMovingEntity){
            notMovingEntities.add((NotMovingEntity) e);
        }

        index = movingEntities.indexOf(e);
        if(index!=-1){
            movingEntities.set(index, (MovingEntity) e);
        }
        if(index==-1 && e instanceof MovingEntity){
            movingEntities.add((MovingEntity) e);
        }

        index = players.indexOf(e);
        if(index!=-1){
            players.set(index, (HumanCharacter) e);
        }
        if(index==-1 && e instanceof HumanCharacter){
            players.add((HumanCharacter) e);
        }
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

    public HumanCharacter searchPlayer(int id) {

        for (HumanCharacter p : players) {
            if (p.getID() == id) {
                HumanCharacter player = p;
                return player;
            }
        }

        return null;
    }
    public Entity searchEntity(int id) {

        for (MovingEntity p : movingEntities) {
            if (p.getID() == id) {
                MovingEntity entity = p;
                return entity;
            }
        }
        for (HumanCharacter p : players) {
            if (p.getID() == id) {
                HumanCharacter entity = p;
                return entity;
            }
        }
        for (NotMovingEntity p : notMovingEntities) {
            if (p.getID() == id) {
                NotMovingEntity entity = p;
                return entity;
            }
        }

        return null;
    }

    public void addSinglePlayerHumanCharacter() {
        HumanCharacter p;
        if (getLocalAccount() != null) {
            p = new HumanCharacter(null, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), localAccount.getUsername(), new Soldier(), GameTexture.texturesEnum.playerTexture, 64, 26, false, getLocalAccount());
            //((HumanCharacter) p).setKillCount();
        }
        else {
            p = new HumanCharacter(null, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), localAccount.getUsername(), new Soldier(), GameTexture.texturesEnum.playerTexture, 64, 26, false);

        }
        players.add(p);
        localPlayer = p;
        client.sendMessageAndReturn(new Command(Command.methods.POST, new Entity[]{p}, Command.objectEnum.HumanCharacter));
    }

    public boolean logIn(String username, String password){
        System.out.println("calling login function");
        if(databaseManager.verifyAccount(username,password)){
            this.setLocalAccount(new Account(username));
            try{
                int id = databaseManager.getAccountID(username);
                HashMap<String,String> playerData = databaseManager.getResultsOfPlayer(id);
                totalScore = playerData.get("TotalScore");
                totalDeaths = playerData.get("TotalDeaths");
                totalKills = playerData.get("TotalKills");
                totalGamesPlayed = playerData.get("TotalGamesPlayed");

                System.out.println(totalScore + " - " + totalKills + " - " + totalDeaths + " - " + totalGamesPlayed);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return true;
        }
        //TODO display false login message
        return false;
    }
    public void setMovingEntity(int index, MovingEntity e){
        movingEntities.set(index, e);
    }

    public void dump() {
        instance = new Administration();
        System.out.println("dumped :allEntitiesTotal:" + instance.getAllEntities().size());
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
