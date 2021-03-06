package callofcactus.map;

import callofcactus.GameTexture;
import callofcactus.IGame;
import callofcactus.entities.NotMovingEntity;
import com.badlogic.gdx.math.Vector2;

public class DefaultMap extends CallOfCactusMap {

    private static final double width = 800;
    private static final double height = 480;
    private GameTexture.texturesEnum wallTexture;
    private IGame game;

    private double currentWidth;
    private double currentHeight;
    private double widthRatio;
    private double heightRatio;

    public DefaultMap(IGame currentGame, int currentWidth, int currentHeight) {
        super(currentGame);

        wallTexture = GameTexture.texturesEnum.wallTexture;

        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
        widthRatio = (double) currentWidth / width;
        heightRatio = (double) currentHeight / height;
    }

    @Override
    public void init() {
        IGame game = getGame();
        int verticaal = (int) Math.floor(3 * heightRatio) - 1;
        int horizontaal = (int) (2 * widthRatio);

        //  Bottom left wall
        double startYCoordinat3 = 48 * heightRatio;

        for (int i = 1; i <= verticaal; i++) {
            Vector2 location = new Vector2((int) (128 * widthRatio), (int) startYCoordinat3);
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startYCoordinat3 = startYCoordinat3 + (int) (32 * heightRatio);
        }

        double startXCoordinat3 = 160 * widthRatio - 1;

        for (int i = 1; i <= horizontaal; i++) {
            Vector2 location = new Vector2((int) (startXCoordinat3), (int) (48 * heightRatio));
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startXCoordinat3 = startXCoordinat3 + (int) (32 * widthRatio);
        }

        //  Upper left wall
        double startYCoordinat = currentHeight - (48 * heightRatio) * 2;

        for (int i = 1; i <= verticaal; i++) {
            Vector2 location = new Vector2((int) (128 * widthRatio), (int) startYCoordinat);
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startYCoordinat = startYCoordinat - (int) (32 * heightRatio);
        }

        double startXCoordinat = 160 * widthRatio - 1;

        for (int i = 1; i <= horizontaal; i++) {
            Vector2 location = new Vector2((int) startXCoordinat, (int) (currentHeight - (48 * heightRatio) * 2));
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startXCoordinat = startXCoordinat + (int) (32 * widthRatio);
        }

        //  Upper right wall
        double startYCoordinat2 = currentHeight - (48 * heightRatio) * 2;

        for (int i = 1; i <= verticaal; i++) {
            Vector2 location = new Vector2((int) (currentWidth - (128 * widthRatio) - (32 * widthRatio)), (int) (startYCoordinat2));
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startYCoordinat2 = startYCoordinat2 - (int) (32 * heightRatio);
        }

        double startXCoordinat2 = currentWidth - (128 * widthRatio) - (32 * widthRatio) + 1;

        for (int i = 1; i <= horizontaal; i++) {
            Vector2 location = new Vector2((int) startXCoordinat2, (int) (currentHeight - (48 * heightRatio) * 2));
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startXCoordinat2 = startXCoordinat2 - (int) (32 * widthRatio);
        }

        //  Bottom right wall
        double startYCoordinat4 = 48 * heightRatio;

        for (int i = 1; i <= verticaal; i++) {
            Vector2 location = new Vector2(((int) (currentWidth - (128 * widthRatio) - (32 * widthRatio))), (int) startYCoordinat4);
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startYCoordinat4 = startYCoordinat4 + (int) (32 * heightRatio);
        }

        double startXCoordinat4 = currentWidth - (128 * widthRatio) - (32 * widthRatio) + 1;

        for (int i = 1; i <= horizontaal; i++) {
            Vector2 location = new Vector2((int) startXCoordinat4, (int) (48 * heightRatio));
            new NotMovingEntity(game, location, true, 20, false, wallTexture, (int) (32 * widthRatio), (int) (32 * heightRatio), false);
            startXCoordinat4 = startXCoordinat4 - (int) (32 * widthRatio);
        }
    }
}