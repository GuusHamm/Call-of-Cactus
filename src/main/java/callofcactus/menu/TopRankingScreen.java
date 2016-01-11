package callofcactus.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.multiplayer.serverbrowser.ServerBrowser;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Timer;

/**
 * Created by Jim on 11-1-2016.
 */
public class TopRankingScreen implements Screen {
    private GameInitializer gameInitializer;

    private SpriteBatch batch;
    private Stage stage;
    //GUI fields
    private Skin skin;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    private float screenHeight;
    private float screenWidth;

    // Game list elements
    private Table gameContainer;

    public TopRankingScreen(){

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
