package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.Game;
import game.GameInitializer;

/**
 * @author Teun
 */
public class EndScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private GameInitializer gameInitializer;
    private Game game;
    private BitmapFont bitmapFont;

    public EndScreen(GameInitializer gameInitializer, Game game) {
        this.gameInitializer = gameInitializer;
        this.game = game;
        //GUI code
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();
        TextButton newGameButton = new TextButton("Go to main menu", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(newGameButton);

        newGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                navigateToMainMenu();
            }
        });

        bitmapFont = new BitmapFont();

    }

    private void navigateToMainMenu() {
        gameInitializer.setScreen(new MainMenu(gameInitializer));
    }

    private String getScoreText() {
        return "Score: " + game.getPlayer().getScore();
    }

    @Override
    public void show() {
        SpriteBatch spriteBatch = gameInitializer.getBatch();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();

        bitmapFont.draw(spriteBatch, getScoreText(), Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/4);
        stage.act();
        stage.draw();

        spriteBatch.end();
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

    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() /4, Gdx.graphics.getHeight() /10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }
}
