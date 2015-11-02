package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nino Vrijman on 31-10-2015.
 */
public class Map
{
    private static final double width = 800;
    private static final double height = 480;
    private static final Texture wallTexture = new Texture(Gdx.files.internal("wall.png"));
    private Game game;

    private double currentWidth;
    private double currentHeight;
    private double widthRatio;
    private double heightRatio;

    public Map(Game currentGame, int currentWidth, int currentHeight)
    {
        game = currentGame;

        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
        widthRatio = (double)currentWidth / width;
        heightRatio = (double)currentHeight / height;

        initAllWalls();
    }

    private void initAllWalls()
    {
        //  Upper left wall
        double startYCoordinat = currentHeight - (480 - 312 * heightRatio);

        for (int i = 1; i <= 3 * heightRatio; i++) {
            Vector2 location = new Vector2((int)(128 * widthRatio), (int)startYCoordinat);
            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
            startYCoordinat = startYCoordinat + (int)(32 * heightRatio);
        }

        double startXCoordinat = 160 * widthRatio;

        for (int i = 1; i <= 2 * widthRatio; i++) {
            Vector2 location = new Vector2((int)startXCoordinat, (int)(startYCoordinat - (32 * heightRatio)));
            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
            startXCoordinat = startXCoordinat + (int)(32 * widthRatio);
        }
//
//        //  Upper right wall
//        double startYCoordinat2 = currentHeight - (480 - 312 * heightRatio);
//
//        for (int i = 1; i <= 3 * heightRatio; i++) {
//            Vector2 location = new Vector2((int)(608 * widthRatio), (int)(startYCoordinat2));
//            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
//            startYCoordinat2 = startYCoordinat2 + (int)(32 * heightRatio);
//        }
//
//        double startXCoordinat2 = 576 * widthRatio;
//
//        for (int i = 1; i <= 2 * widthRatio; i++) {
//            Vector2 location = new Vector2((int)startXCoordinat2, (int)(startYCoordinat2 - (32 * heightRatio)));
//            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
//            startXCoordinat2 = startXCoordinat2 - (int)(32 * widthRatio);
//        }

        //  Players spawn in the same location as this the bottom left wall so this code is commented for now.
        //  Bottom left wall
        double startYCoordinat3 = 32 * heightRatio;

        for (int i = 1; i <= 3 * heightRatio; i++) {
            Vector2 location = new Vector2((int)(128 * widthRatio), (int)startYCoordinat3);
            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
            startYCoordinat3 = startYCoordinat3 + (int)(32 * heightRatio);
        }


        double startXCoordinat3 = 160 * widthRatio;

        for (int i = 1; i <= 2 * widthRatio; i++) {
            Vector2 location = new Vector2((int)(startXCoordinat3), (int)(32 * heightRatio));
            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
            startXCoordinat3 = startXCoordinat3 + (int)(32 * widthRatio);
        }

//        //  Bottom right wall
//        double startYCoordinat4 = 64 * heightRatio;
//
//        for (int i = 1; i <= 3; i++) {
//            Vector2 location = new Vector2((int)(currentWidth - (800 - 608 * widthRatio)), (int)startYCoordinat4);
//            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
//            startYCoordinat4 = startYCoordinat4 + (int)(32 * heightRatio);
//        }
//
//        double startXCoordinat4 = currentWidth - (800 - 576);
//
//        for (int i = 1; i <= 2; i++) {
//            Vector2 location = new Vector2((int)startXCoordinat4, (int)(64 * heightRatio));
//            NotMovingEntity e = new NotMovingEntity(game, location, true, 20, false, wallTexture, (int)(32 * widthRatio), (int)(32 * heightRatio));
//            startXCoordinat4 = startXCoordinat4 - (int)(32 * widthRatio);
//        }
    }
}
