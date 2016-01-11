package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.GameTexture;
import callofcactus.account.Account;
import callofcactus.io.DatabaseManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.utils.random.IntegerDistribution;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nekkyou on 11-1-2016.
 */
public class AchievementScreen implements Screen
{
    private GameInitializer gameInitializer;
    private SpriteBatch batch;
    private Stage stage;

    private DatabaseManager databaseManager;

    //GUI fields
    private Skin skin;
    private Music themeMusic;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    private float screenHeight;
    private float screenWidth;

    private ImageButton.ImageButtonStyle imageButtonStyle;

    // Game list elements
    private Table achievementContainer;

    private Account account;

    private HashMap<String, Integer> achievements;

    public AchievementScreen(GameInitializer initializer) {
        gameInitializer = initializer;
        achievements = new HashMap<>();
        databaseManager = new DatabaseManager();

        this.batch = gameInitializer.getBatch();
        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

        //GUI code
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();

        // Create the Back button
        TextButton newBackButton = new TextButton("Back", skin); // Use the initialized skin
        newBackButton.setPosition(screenWidth / 2 - screenWidth / 8, newBackButton.getHeight() + 1);
        stage.addActor(newBackButton);


        this.account = Administration.getInstance().getLocalAccount();

        createAchievementTable();

        //Button actions
        //Sets all the actions for all buttons
        newBackButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                navigateToMainMenu();
            }
        });

    }

    public AchievementScreen(GameInitializer initializer, Account acc) {
        gameInitializer = initializer;
        achievements = new HashMap<>();
        databaseManager = new DatabaseManager();

        this.batch = gameInitializer.getBatch();
        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

        //GUI code
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();

        // Create the Back button
        TextButton newBackButton = new TextButton("Back", skin); // Use the initialized skin
        newBackButton.setPosition(screenWidth / 2 - screenWidth / 8, newBackButton.getHeight() + 1);
        stage.addActor(newBackButton);


        this.account = acc;

        createAchievementTable();

        //Button actions
        //Sets all the actions for all buttons
        newBackButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                navigateToMainMenu();
            }
        });

    }

    private void navigateToMainMenu()
    {
        this.dispose();
        gameInitializer.setScreen(new MainMenu(gameInitializer, account));
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float v)
    {
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


    private void createBasicSkin() {
        //Create a font unique stuff
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));
        skin.add("listBackground", new Texture(Gdx.files.internal("ScrollPaneBackground.png")));
        skin.add("gameBarBackground", new Texture(Gdx.files.internal("lobbyGameBarBackground.png")));
        skin.add("accountBackground", new Texture(Gdx.files.internal("lobbyAccountDataBackground.png")));
        skin.add("cursor", new Texture(Gdx.files.internal("cursor.png")));
        skin.add("listForeground", new Texture(Gdx.files.internal("ScrollPaneBackgroundOverlay.png")));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        SpriteDrawable icon            = new SpriteDrawable(new Sprite(GameTexture.getInstance().getTexture(GameTexture.texturesEnum.refresh_icon)));
        SpriteDrawable background      = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("MenuButtonBase.png"))));
        SpriteDrawable backgroundHover = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("MenuButtonBaseHover.png"))));
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = background;
        imageButtonStyle.down = background;
        imageButtonStyle.checked = background;
        imageButtonStyle.over = backgroundHover;
        imageButtonStyle.checkedOver = backgroundHover;
        imageButtonStyle.imageUp = icon;
        imageButtonStyle.imageDown = icon;
        imageButtonStyle.imageChecked = icon;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

        // Create Textfield style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = skin.newDrawable("gameBarBackground");
        textFieldStyle.cursor = skin.getDrawable("cursor");
        skin.add("default", textFieldStyle);
    }


    public void createAchievementTable() {
        achievementContainer = new Table();

        int xSize = Gdx.graphics.getWidth() / 2;
        int ySize = (int) (Gdx.graphics.getHeight() / 1.5);
        achievementContainer.setSize(xSize, ySize);
        achievementContainer.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        stage.addActor(achievementContainer);

        Table innerContainer = new Table();

        Table resultTable = new Table(skin);
        resultTable.background(new SpriteDrawable(new Sprite(new Texture("ScrollPaneBackground.png"))));
        resultTable.setColor(Color.WHITE);

        Label achievementNameLabel = new Label("Name", createBasicLabelSkin());
        Label gotAchievement = new Label("Completed achievement", createBasicLabelSkin());

        resultTable.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
        addToTable(resultTable, achievementNameLabel);
        addToTable(resultTable, gotAchievement);
        resultTable.row();


        addToTable(resultTable, new Label("", createBasicLabelSkin()));
        resultTable.row();

        HashMap<Integer, String> allAchievements = databaseManager.getAllAchievements();
        ArrayList<String> completedAchievements = databaseManager.getCompletedAchievements(Administration.getInstance().getLocalAccount().getID());

        for (Map.Entry<Integer, String> entry : allAchievements.entrySet()) {
            Label nameLabel = new Label(entry.getValue(), createBasicLabelSkin());
            Label completedAchievement;
            if (completedAchievements.contains(entry.getValue())) {
                completedAchievement = new Label("X", createBasicLabelSkin());
            }
            else {
                completedAchievement = new Label("", createBasicLabelSkin());
            }

            resultTable.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
            addToTable(resultTable, nameLabel);
            addToTable(resultTable, completedAchievement);
            resultTable.row();
        }
        achievementContainer.add(innerContainer);
        innerContainer.add(resultTable);
    }

    public void addToTable(Table t, Label addition) {
        t.add(addition);
        t.add(new Label("", skin)).width(Gdx.graphics.getWidth() / 20);// a spacer
    }

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
}
