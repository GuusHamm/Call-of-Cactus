package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.IGame;
import callofcactus.account.Account;
import callofcactus.entities.HumanCharacter;
import callofcactus.entities.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nekkyou on 23-11-2015.
 */
public class MultiPlayerEndScreen implements Screen
{
    private Stage stage;
    private GameInitializer gameInitializer;
    private Administration administration;
    private BitmapFont bitmapFont;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;
    private Skin skin;

    private Table container;
    //Buttons and Labels
    private TextButton mainMenuButton;
    private TextButton exitButton;

    private int labelHeight = 450;
    private int matchID;

    public MultiPlayerEndScreen(GameInitializer gameInitializer, Administration administration) {
        this.gameInitializer = gameInitializer;
        this.administration = administration;

        createBasicSkin();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("EndScreenBackground.jpg");

        writeToDatabase();

        createButtons();
        createLabels();
    }


    private void createButtons() {
        mainMenuButton = new TextButton("Go to main menu", createBasicButtonSkin());
        exitButton = new TextButton("Exit", createBasicButtonSkin());
        setButtonPosition();

        stage.addActor(mainMenuButton);
        stage.addActor(exitButton);

        addButtonListeners();
    }

    private void setButtonPosition() {
        mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() - 10);
    }

    private void addButtonListeners() {
        //Main button listeners
        mainMenuButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(.3F);
                navigateToMainMenu();
            }
        });

        mainMenuButton.addListener(new InputListener() {
            boolean playing = false;

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gui/coc_buttonHover.mp3"));
                    sound.play(.2F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        });

        //Exit button listeners
        exitButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

        exitButton.addListener(new InputListener()
        {
            boolean playing = false;

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
            {
                super.enter(event, x, y, pointer, fromActor);
                if (!playing)
                {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gui/coc_buttonHover.mp3"));
                    sound.play(.2F);
                    playing = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
            {
                super.exit(event, x, y, pointer, toActor);
                playing = false;
            }
        });

    }

    private void createLabels() {
        //Create a Table first
        container = new Table();
        container.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        container.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        container.background(skin.getDrawable("listBackground"));
        stage.addActor(container);

        Table innerContainer = new Table();

        ResultSet resultSet = administration.getDatabaseManager().getSortedScores(matchID);
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                String score = resultSet.getString("SCORE");
                String kills = resultSet.getString("KILLS");
                String deaths = resultSet.getString("DEATHS");

                Table row = new Table();
                row.add(new Label(username, skin));
                row.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
                row.add(new Label(kills, skin));
                row.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
                row.add(new Label(deaths, skin));
                row.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
                row.add(new Label(score, skin));
                innerContainer.add(row);
                innerContainer.row();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        container.add(innerContainer);

    }

    private void writeToDatabase(){
        //TODO get matchID
        this.matchID = 0;

        List<HumanCharacter> allPlayers = administration.getPlayers();
        for(HumanCharacter player : allPlayers){
            administration.getDatabaseManager().addMultiplayerResult(player.getID(),matchID,player.getScore(),player.getKillCount(),player.getDeathCount());
        }
    }

    private void createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));
        skin.add("listBackground", new Texture(Gdx.files.internal("EndScreenBackground.jpg")));


        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

    }

    /**
     * Go to main menu
     */
    private void navigateToMainMenu() {
        this.dispose();
        gameInitializer.setScreen(new MainMenu(gameInitializer));
    }



    /**
     * In this method the skin for the buttons is created
     *
     * @return Returns the skin for the buttons
     */
    private Skin createBasicButtonSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);
        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

    /**
     * In this method the skin for the Labels is created
     *
     * @return Returns the skin for the Labels
     */
    private Skin createBasicLabelSkin() {
        //  Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //  Create a label style

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

        return skin;
    }

    @Override
    public void show()
    {
        stage.act();
        stage.draw();
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  GUI code
        backgroundRenderer.render(backgroundBatch);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}