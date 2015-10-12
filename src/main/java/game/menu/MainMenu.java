package game.menu;


import com.badlogic.gdx.Screen;
import game.Game;
import game.GameInitializer;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen
{
    private List<Game> games;
	/**
	 * Makes a new instance of the class MainMenu
	 */
	public MainMenu(GameInitializer gameInitializer) {
		// TODO - implement MainMenu.MainMenu
		games = new ArrayList<>();

	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float v)
	{

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

}