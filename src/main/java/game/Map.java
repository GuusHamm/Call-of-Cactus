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

        initMidLeftWall();
        initTopMidWall();
//        initMiddleRightWall();
//        initBotLeftWall();
//        initBotRightWall();
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static Texture getWallTexture()
    {
        return wallTexture;
    }

    public int getWidthRatio()
    {
        return widthRatio;
    }

    public int getHeightRatio()
    {
        return heightRatio;
    }

    public void initMidLeftWall()
    {
        int startYCoordinat = 768;

        for (int i = 1; i <= 6; i++) {
            new NotMovingEntity(game, new Vector2(192 / widthRatio, startYCoordinat / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startYCoordinat = startYCoordinat + 96;
        }
    }

    public void initTopMidWall()
    {
        new NotMovingEntity(game, new Vector2(480 / widthRatio, 1440 / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);

        int startXCoordinat = 480;

        for (int i = 1; i <= 4; i++) {
            new NotMovingEntity(game, new Vector2(startXCoordinat / widthRatio, 1344 / heightRatio), true, 20, false, wallTexture,96 / widthRatio, 96 / heightRatio);
            startXCoordinat = startXCoordinat + 96;
        }
    }

    public void initMiddleRightWall()
    {
        int startXCoordinat = 864;

        for (int i = 1; i <= 5; i++) {
            new NotMovingEntity(game, new Vector2(startXCoordinat / widthRatio, 768 / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startXCoordinat = startXCoordinat + 96;
        }

        int startYCoordinat = 960;

        for (int i = 1; i <= 4; i++) {
            new NotMovingEntity(game, new Vector2(1152 / widthRatio, startYCoordinat / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startYCoordinat = startYCoordinat + 96;
        }
        startYCoordinat = 960;

        for (int i = 1; i <= 4; i++) {
            new NotMovingEntity(game, new Vector2(1248 / widthRatio, startYCoordinat / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startYCoordinat = startYCoordinat + 96;
        }
    }

    public void initBotLeftWall()
    {
        int startXCoordinat = 192;

        for (int i = 1; i <= 6; i++) {
            new NotMovingEntity(game, new Vector2(startXCoordinat / widthRatio, 1248 / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startXCoordinat = startXCoordinat + 96;
        }

        int startYCoordinat = 192;

        for (int i = 1; i <= 3; i++) {
            new NotMovingEntity(game, new Vector2(768 / widthRatio, startYCoordinat / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startYCoordinat = startYCoordinat + 96;
        }
    }

    public void initBotRightWall()
    {
        int startXCoordinat = 1152;

        for (int i = 1; i <= 3; i++) {
            new NotMovingEntity(game, new Vector2(startXCoordinat / widthRatio, 1344 / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startXCoordinat = startXCoordinat + 96;
        }

        int startYCoordinat = 288;

        for (int i = 1; i <= 4; i++) {
            new NotMovingEntity(game, new Vector2(1440 / widthRatio, startYCoordinat / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startYCoordinat = startYCoordinat + 96;
        }

        int startXCoordinat2 = 1536;

        for (int i = 1; i <= 3; i++) {
            new NotMovingEntity(game, new Vector2(startXCoordinat2 / widthRatio, 1056 / heightRatio), true, 20, false, wallTexture, 96 / widthRatio, 96 / heightRatio);
            startXCoordinat2 = startXCoordinat2 + 96;
        }
    }
}
