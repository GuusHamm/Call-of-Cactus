package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.GameTexture;
import callofcactus.account.Account;
import callofcactus.io.DatabaseManager;
import callofcactus.role.Role;
import callofcactus.role.Sniper;
import callofcactus.role.Soldier;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kees on 18/01/2016.
 */
public class ChooseRoleScreen implements Screen {

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
    private Table roleContainer;
    private Table roleInnerContainer;
    private Table roleContainerOverlay;

    private Account account;
    private ArrayList<Role> roles;

    private HashMap<String, Integer> achievements;

    public ChooseRoleScreen(GameInitializer initializer) {
        gameInitializer = initializer;
        achievements = new HashMap<>();
        databaseManager = new DatabaseManager();
        roles = new ArrayList<Role>();

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

        //RoleBackground
        createRoleBackground();
        //role overlay
        createRoleOverlay();

        try {
            Role sniper = new Sniper();
            Role soldier = new Soldier();
            //Role tank = new Tank();
            roles.add(sniper);
            roles.add(soldier);
            //roles.add(tank);
            createRoleTable(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public ChooseRoleScreen(GameInitializer initializer, Account acc) {
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

        //RoleBackground
        createRoleBackground();
        //role overlay
        createRoleOverlay();

        try {
            Role sniper = new Sniper();
            Role soldier = new Soldier();
            //Role tank = new Tank();
            roles.add(sniper);
            roles.add(soldier);
            //roles.add(tank);
            createRoleTable(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void createRoleBackground() {
        roleContainer = new Table();
        roleContainer.setSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        roleContainer.setPosition(screenWidth / 2 - screenWidth / 8 + 10, Gdx.graphics.getHeight() / 2 - roleContainer.getHeight() / 2);
        roleContainer.background(skin.getDrawable("listBackground"));
        stage.addActor(roleContainer);
    }

    private void createRoleOverlay(){
        roleInnerContainer = new Table();
        ScrollPane gamesPane = new ScrollPane(roleInnerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        roleContainer.add(gamesPane).fill().expand();

        // Add a border overlay to the server selection table
        roleContainerOverlay = new Table();
        roleContainerOverlay.setSize(screenWidth / 8, screenHeight / 2);
        roleContainerOverlay.setPosition(screenWidth / 2 - screenWidth / 8 + 10, Gdx.graphics.getHeight() / 2 - roleContainer.getHeight() / 2);
        roleContainerOverlay.background(skin.getDrawable("listForeground"));
        stage.addActor(roleContainerOverlay);
    }

    public void createRoleTable(ArrayList<Role> allRoles) {
        roleContainer.clear();
        for (Role r : allRoles) {
            Table testRoleBar = new Table();
            TextButton roleButton = new TextButton(r.getName(), skin);
            roleButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (r instanceof Sniper) {
                        Administration.getInstance().getLocalAccount().setRole(new Sniper());
                    } else if (r instanceof Soldier) {
                        Administration.getInstance().getLocalAccount().setRole(new Soldier());
                    }
                }
            });

            testRoleBar.background(skin.getDrawable("gameBarBackground"));
            testRoleBar.add(roleButton).size(screenWidth / 12, screenHeight / 20);

            testRoleBar.addListener(new FocusListener() {
                @Override
                public boolean handle(Event event) {
                    if (event.toString().equals("mouseMoved") || event.toString().equals("exit")) {
                        return false;
                    }
                    return true;
                }
            });

            roleContainer.row();
            roleContainer.add(testRoleBar).size(roleContainer.getWidth() - roleContainer.getWidth() / 20, roleContainer.getHeight() / 5);

        }
    }
}
