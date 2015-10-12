package game.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.Game;
import game.GameInitializer;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen
{
    private List<Game> games;
	private BitmapFont bitmapFont;
	
	private GameInitializer gameInitializer;
	private SpriteBatch batch;
	/**
	 * Makes a new instance of the class MainMenu
	 */
	public MainMenu(GameInitializer gameInitializer) {
		// TODO - implement MainMenu.MainMenu
		games = new ArrayList<>();

		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.RED);
		bitmapFont.getData().setScale(2, 2);
		
		this.gameInitializer = gameInitializer;
		this.batch = gameInitializer.getBatch();
		Gdx.input.setInputProcessor(inputProcessor);
	}

	private void navigateToNextScreen() {
		// TODO Go to next screen
		System.out.println("Navigated");
		gameInitializer.setScreen(new GameScreen(gameInitializer));
	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float v)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			bitmapFont.draw(batch, "Press any button you gibbon!", 200, 200);
		batch.end();
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

	/**
	 * Returns a list with all the games that are currently active
	 * @return the list of all the current games
	 */
	public List<Game> getAllGames() {
		// TODO - implement MainMenu.getAllLobbies
		throw new UnsupportedOperationException();
	}


	private InputProcessor inputProcessor = new InputProcessor() {
		@Override
		public boolean keyDown(int i)
		{
			navigateToNextScreen();
			return false;
		}

		@Override
		public boolean keyUp(int i)
		{
			return false;
		}

		@Override
		public boolean keyTyped(char c)
		{
			return false;
		}

		@Override
		public boolean touchDown(int i, int i1, int i2, int i3)
		{
			return false;
		}

		@Override
		public boolean touchUp(int i, int i1, int i2, int i3)
		{
			return false;
		}

		@Override
		public boolean touchDragged(int i, int i1, int i2)
		{
			return false;
		}

		@Override
		public boolean mouseMoved(int i, int i1)
		{
			return false;
		}

		@Override
		public boolean scrolled(int i)
		{
			return false;
		}
	};

}