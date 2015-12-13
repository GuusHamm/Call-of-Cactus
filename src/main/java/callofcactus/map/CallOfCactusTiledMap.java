package callofcactus.map;

import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;


/**
 * @author Teun
 */
public class CallOfCactusTiledMap extends CallOfCactusMap {

    private MapFiles.MAPS map;
    private TiledMap tiledMap;

    /**
     * Creates a tilemap object for the game
     *
     * @param game IGame to apply map to
     * @param map  Map to load from resources folder, you can load maps
     */
    public CallOfCactusTiledMap(IGame game, MapFiles.MAPS map) {
        super(game);
        this.map = map;
    }

    @Override
    public void init() {
        tiledMap = new TmxMapLoader(new InternalFileHandleResolver()).load(MapFiles.getFileName(map));

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        float adjustTileWidth = (float) screenWidth / mapPixelWidth;
        float adjustTileHeight = (float) screenHeight / mapPixelHeight;
        Vector2 adjustedTileSize = new Vector2(adjustTileWidth, adjustTileHeight);

        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        Texture wallTexture = new GameTexture().getTexture(GameTexture.texturesEnum.wallTexture);

        int tiledMapWidth = tiledMapTileLayer.getWidth();
        int tiledMapHeight = tiledMapTileLayer.getHeight();
        for (int i = 0; i < tiledMapWidth; i++) {
            for (int a = 0; a < tiledMapHeight; a++) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(i, a);
                if (cell != null) {
                    new NotMovingEntity(getGame(), new Vector2(i * adjustedTileSize.x, a * adjustedTileSize.y), true, 0, false, GameTexture.texturesEnum.wallTexture, (int) adjustedTileSize.x, (int) adjustedTileSize.y,false);
                }
            }
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
