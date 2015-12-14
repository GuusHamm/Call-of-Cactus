package game.menu;

import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.menu.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Jim on 16-11-2015.
 */
public class LobbyScreen implements Screen {
    private GameInitializer gameInitializer;
    private SpriteBatch batch;
    private Stage stage;
    //GUI fields
    private Skin skin;
    private Music themeMusic;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;

    private float screenHeight;
    private float screenWidth;

    // Game list elements
    private Table container;

    public LobbyScreen(GameInitializer gameInitializer){
        this.gameInitializer = gameInitializer;
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

//        //Create the list of available games
//        List gamesList = new List(skin,"default");
//        gamesList.setPosition(screenWidth / 2 + screenWidth / 8, screenHeight / 2);
//        String[] testStrings = new String[3];
//        testStrings[0] = "Jimbolul's Deathmatch";
//        testStrings[1] = "GuusHamm's Killzone";
//        testStrings[2] = "kikingkikker's Frog Annihilation";
//        gamesList.setItems(testStrings);
//        stage.addActor(gamesList);
//

        // Create a table that will contain the list of 'Join game' bars
        container = new Table();
        container.setSize(screenWidth / 2, screenHeight / 2);
        container.setPosition(screenWidth / 2, screenHeight /2);
        container.background(skin.getDrawable("listBackground"));
        stage.addActor(container);

        // Create a test 'Join game' bar
        Table testGameBar = new Table();
        testGameBar.add(new Image(new Texture(Gdx.files.internal("player.png"))));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar.add(new Label("Jimbolul's Deathmatch Of Destructinationness", skin));
        testGameBar.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar.add(createJoinGameButton()).size(screenWidth / 12, screenHeight / 20);

        Table testGameBar2 = new Table();
        testGameBar2.add(new Image(new Texture(Gdx.files.internal("player.png"))));
        testGameBar2.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar2.add(new Label("GuusHamm is een neger.nl", skin));
        testGameBar2.add(new Label("", skin)).width(screenWidth / 20);// a spacer
        testGameBar2.add(createJoinGameButton()).size(screenWidth / 12, screenHeight / 20);

        // Create the inner table that will serve as the container for all "join game' bars
        Table innerContainer = new Table();
        innerContainer.add(testGameBar);
        innerContainer.row();
        innerContainer.add(testGameBar2);

        // Create the scrollpane for selecting a game
        ScrollPane gamesPane = new ScrollPane(innerContainer);
        stage.addActor(gamesPane);

        // Add the scrollpane to the container
        container.add(gamesPane).fill().expand();

        testGameBar.addListener(new FocusListener(){
            @Override
            public boolean handle(Event event){

                if (event.toString().equals("mouseMoved")){
                    //testGameBar.background(new TextureRegionDrawable(new TextureRegion(new Texture(skin.get("MenuButtonBaseHover")))));

                    return false;
                }
                else if(event.toString().equals("exit")){
                    //table1.setBackground(null);
                    //table1.background("");
                    //testGameBar.setBackground(null, false);

                    return false;
                }
                return true;
            }

        });

        //Sets all the actions for the Back Button
        newBackButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                navigateToMainMenu();
            }
        });
    }

    private void navigateToMainMenu(){
        this.dispose();
        gameInitializer.setScreen(new MainMenu(gameInitializer,null));
    }

    private TextButton createJoinGameButton(){
        TextButton joinGameButton = new TextButton("Join Game",skin);
        return joinGameButton;
    }
    @Override
    public void show() {
        stage.act();
        stage.draw();
    }

    @Override
    public void render(float v) {
        //GUI code
        backgroundRenderer.render(backgroundBatch);
        stage.act();
        stage.draw();

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

    private void createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        skin.add("hoverImage", new Texture(Gdx.files.internal("MenuButtonBaseHover.png")));
        skin.add("image", new Texture(Gdx.files.internal("MenuButtonBase.png")));
        skin.add("listBackground", new Texture(Gdx.files.internal("ScrollPaneBackground.png")));


        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("image");
        textButtonStyle.down = skin.newDrawable("hoverImage");
        textButtonStyle.checked = skin.newDrawable("hoverImage");
        textButtonStyle.over = skin.newDrawable("hoverImage");
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //Create a list style
        List.ListStyle listStyle = new List.ListStyle();
        listStyle.background = skin.newDrawable("listBackground");
        listStyle.font = skin.getFont("default");
        listStyle.fontColorSelected = Color.BLACK;
        listStyle.fontColorUnselected = Color.BLACK;
        listStyle.selection = skin.newDrawable("hoverImage");
        skin.add("default",listStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

    }
}
