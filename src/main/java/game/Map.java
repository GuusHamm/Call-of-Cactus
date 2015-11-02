package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nino Vrijman on 31-10-2015.
 */
public class Map
{
    private static final int width = 2016;
    private static final int height = 1728;
    private static final Texture wallTexture = new Texture(Gdx.files.internal("wall.png"));
    private Game game;

    private int widthRatio;
    private int heightRatio;

    public Map(Game currentGame, int currentWidth, int currentHeight)
    {
        game = currentGame;

        widthRatio = width / currentWidth;
        heightRatio = height / currentHeight;


        initAllWalls();
//        initMidLeftWall();
//        initTopMidWall();
//        initMiddleRightWall();
//        initBotLeftWall();
//        initBotRightWall();
    }

    private void initAllWalls()
    {
        //  Upper left wall
        int startYCoordinat = 312;

        for (int i = 1; i <= 3; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(128, startYCoordinat), true, 20, false, wallTexture, 32, 32);
            startYCoordinat = startYCoordinat + 32;
        }


        int startXCoordinat = 160;

        for (int i = 1; i <= 2; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(startXCoordinat, 376), true, 20, false, wallTexture, 32, 32);
            startXCoordinat = startXCoordinat + 32;
        }

        //  Upper right wall
        int startYCoordinat2 = 312;

        for (int i = 1; i <= 3; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(608, startYCoordinat2), true, 20, false, wallTexture, 32, 32);
            startYCoordinat2 = startYCoordinat2 + 32;
        }


        int startXCoordinat2 = 544;

        for (int i = 1; i <= 2; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(startXCoordinat2, 376), true, 20, false, wallTexture, 32, 32);
            startXCoordinat2 = startXCoordinat2 + 32;
        }

        //  Players spawn in the same location as this the bottom left wall so this code is commented for now.
        //  Bottom left wall
//        int startYCoordinat3 = 32;
//
//        for (int i = 1; i <= 3; i++) {
//            NotMovingEntity e = new NotMovingEntity(game, new Vector2(128, startYCoordinat3), true, 20, false, wallTexture, 32, 32);
//            startYCoordinat3 = startYCoordinat3 + 32;
//        }
//
//
//        int startXCoordinat3 = 160;
//
//        for (int i = 1; i <= 2; i++) {
//            NotMovingEntity e = new NotMovingEntity(game, new Vector2(startXCoordinat3, 32), true, 20, false, wallTexture, 32, 32);
//            startXCoordinat3 = startXCoordinat3 + 32;
//        }

        //  Bottom right wall
        int startYCoordinat4 = 64;

        for (int i = 1; i <= 3; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(608, startYCoordinat4), true, 20, false, wallTexture, 32, 32);
            startYCoordinat4 = startYCoordinat4 + 32;
        }


        int startXCoordinat4 = 544;

        for (int i = 1; i <= 2; i++) {
            NotMovingEntity e = new NotMovingEntity(game, new Vector2(startXCoordinat4, 64), true, 20, false, wallTexture, 32, 32);
            startXCoordinat4 = startXCoordinat4 + 32;
        }
    }
}
