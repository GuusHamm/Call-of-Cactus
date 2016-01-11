package callofcactus.menu;

import callofcactus.Administration;
import callofcactus.BackgroundRenderer;
import callofcactus.GameInitializer;
import callofcactus.IGame;
import callofcactus.account.Account;
import callofcactus.io.DatabaseManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

    private Stage stage;
    private List<IGame> games;
    private GameInitializer gameInitializer;
    private SpriteBatch batch;
    //GUI fields
    private Skin skin;
    private Music themeMusic;
    private SpriteBatch backgroundBatch;
    private BackgroundRenderer backgroundRenderer;
    private DatabaseManager databaseManager;
    private Administration admin = Administration.getInstance();
    private Account account;

    public static class LoginDialog extends Dialog {

        public LoginDialog(String title, Skin skin) {
            super(title, skin);
        }

        public LoginDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public LoginDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }

        {
            text("Please log in.");
            button("Login");
            button("Cancel");
            System.out.println("Dialog text and buttons set");
        }

        @Override
        protected void result(Object object) {
            System.out.println("dialog result given");
            super.result(object);
        }


    }

    /**
     * Makes a new instance of the class MainMenu
     *
     * @param gameInitializer Initializer used in-callofcactus
     */
    public MainMenu(GameInitializer gameInitializer, Account account) {
        games = new ArrayList<>();

        this.gameInitializer = gameInitializer;
        this.batch = gameInitializer.getBatch();

        this.databaseManager = new DatabaseManager();

        this.backgroundBatch = new SpriteBatch();
        this.backgroundRenderer = new BackgroundRenderer("CartoonDesert.jpg");

        this.admin.setLocalAccount(account);

        if(admin.getLocalAccount() != null){
            System.out.println("Account entering main menu: " + admin.getLocalAccount().getUsername());
        }

        //GUI code
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        createBasicSkin();

        TextButton newSinglePlayerButton = new TextButton("Singleplayer", skin); // Use the initialized skin
        newSinglePlayerButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, (Gdx.graphics.getHeight() / 2) + newSinglePlayerButton.getHeight() + 1);
        stage.addActor(newSinglePlayerButton);

        if(admin.getLocalAccount() != null){
            // Create the multiplayer button
            TextButton newMultiPlayerButton = new TextButton("Multiplayer", skin); // Use the initialized skin
            TextButton achievementButton = new TextButton("Achievements", skin);
            newMultiPlayerButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / (2));
            achievementButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2 - (newMultiPlayerButton.getHeight() + 1));
            stage.addActor(newMultiPlayerButton);
            stage.addActor(achievementButton);

            //Sets all the actions for the multiplayer Button
            newMultiPlayerButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                    sound.play(0.3f);

                    try{
                        if(admin.getLocalAccount() != null){
                            navigateToMultiPlayerLobby();
                        }
                        else{
                            System.out.println(admin.getLocalAccount().getUsername() + "doesn't exist?");
                        }
                    }
                    catch(NullPointerException e){
                        System.out.println("Please login first.");
                    }
                }
            });

            achievementButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                    sound.play(0.3f);

                    try{
                        if(admin.getLocalAccount() != null){
                            navigateToAchievementScreen();
                        }
                        else{
                            System.out.println(admin.getLocalAccount().getUsername() + "doesn't exist?");
                        }
                    }
                    catch(NullPointerException e){
                        System.out.println("Please login first.");
                    }
                }
            });

        }else{
            // Create the login button
            TextButton newLoginButton = new TextButton("Log in", skin); // Use the initialized skin
            newLoginButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / (2));
            stage.addActor(newLoginButton);

            //Sets all the actions for the login Button
            newLoginButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                    sound.play(0.3f);
                    //showLoginDialog();

                    //LoginDialog dialog = new LoginDialog("Login menu",skin);
                    //stage.addActor(dialog);
                    navigateToLoginScreen();

                    System.out.println("Showing dialog");

                }
            });
        }




        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 6);
        stage.addActor(exitButton);

        //Sets all the actions for the Singleplayer Button
        newSinglePlayerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gunfire/coc_gun2.mp3"));
                sound.play(0.3f);
                navigateToSinglePlayerGame();

            }
        });

        //Sets all the actions for the Exit Button
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //this will make sure sounds will play on hover
        InputListener il = new InputListener() {
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
        };

        newSinglePlayerButton.addListener(il);
        //newLoginButton.addListener(il);
        exitButton.addListener(il);


        // Playing audio
        themeMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/theme.mp3"));
        themeMusic.setVolume(0.35f);
        themeMusic.setLooping(true);
        themeMusic.play();




    }

    /**
     * Goes to the singleplayer screen.
     */
    private void navigateToSinglePlayerGame() {
        // TODO Go to next screen

        this.dispose();
        gameInitializer.createSinglePlayerGame();

        gameInitializer.setScreen(new GameScreen(gameInitializer));
    }

    /**
     * Goes to the multiplayer lobby.
     */
    private void navigateToMultiPlayerLobby() {
        // TODO Go to next screen

        this.dispose();
        gameInitializer.setScreen(new ServerBrowserScreen(gameInitializer));
    }

    private void navigateToLoginScreen(){
        this.dispose();
        gameInitializer.setScreen(new LoginScreen(gameInitializer));
    }

    private void navigateToAchievementScreen() {
        this.dispose();
        gameInitializer.setScreen(new AchievementScreen(gameInitializer));
    }

    public Boolean createAccount(String username, String password) {

        if (password.matches("\\b[a-z]+") || password.matches("\\b[A-Z]+")) {
            System.out.println("Passwords cant be all one case");
            return false;
        }
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long");
            return false;
        }
        if (!password.matches("[^A-z|\\s]+")) {
            System.out.println("Password must have at least one special character");
            return false;
        }

        if (databaseManager.usernameExists(username)) {
            System.out.println("Username is taken");
        }


        return false;
    }

    /**
     * Shows the screen.
     */
    @Override
    public void show() {
        stage.act();
        stage.draw();
    }

    /**
     * Renders the callofcactus
     *
     * @param v : The last time this method was called.
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (stage.keyDown(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

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
        themeMusic.stop();
        themeMusic.dispose();
    }

    /**
     * Returns a list with all the games that are currently active
     *
     * @return the list of all the current games
     */
    public List<IGame> getAllGames() {
        // TODO - implement MainMenu.getAllLobbies
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a skin for the GUI to use
     */
    private void createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
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

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        //labelStyle.background = skin.newDrawable("background", Color.LIGHT_GRAY);
        skin.add("default", labelStyle);

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = skin.getFont("default");
        windowStyle.titleFontColor = Color.BLACK;
        //windowStyle.background = skin.getDrawable("image");
        //windowStyle.stageBackground = skin.getDrawable("image");
        skin.add("default", windowStyle);

    }

    /**
     * Displays the login dialog.
     * The dialog contains 2 textfields, 1 for a username and another for a password.
     * The dialog also features 2 buttons, 1 for login and another for cancelling the login and closing the dialog.
     */

    private void showLoginDialog(){

        final Dialog dialog = new Dialog("", skin) {
            @Override
            public float getPrefWidth() {
                // force dialog width
                return Gdx.graphics.getWidth() / 2;

            }

            @Override
            public float getPrefHeight() {
                // force dialog height
                return Gdx.graphics.getWidth() / 2;

            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

//        TextureRegion myTex = new TextureRegion(_dialogBackgroundTextureRegion);
//        myTex.flip(false, true);
//        myTex.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        Drawable drawable = new TextureRegionDrawable(myTex);
        //dialog.setBackground(skin.newDrawable("image"));

        TextButton btnLogin = new TextButton("Login", skin);
        TextButton btnCancel = new TextButton("Cancel", skin);

        btnLogin.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                // Do whatever here for exit button

                Administration admin = Administration.getInstance();
                //TODO get username and password from textfields
                admin.logIn("To", "Do");
                dialog.hide();
                dialog.cancel();
                dialog.remove();

                return true;
            }

        });

        btnCancel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {

                //Do whatever here for cancel

                dialog.cancel();
                dialog.hide();

                return true;
            }

        });

        float btnSize = 80f;
        Table t = new Table();
        // t.debug();

        dialog.getContentTable().add(new Label("Login", skin)).padTop(40f);
        t.add(btnLogin).width(btnSize).height(btnSize);
        t.add(btnCancel).width(btnSize).height(btnSize);

        dialog.getButtonTable().add(t).center().padBottom(80f);
        dialog.show(stage).setPosition(
                (Gdx.graphics.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2));

        dialog.setName("quitDialog");
        stage.addActor(dialog);


    }

    /**
     * Create a fake account for testing purposes
     *
     */
    private void createFooAccount(){
        admin.setLocalAccount(new Account("Test1234"));
    }
}