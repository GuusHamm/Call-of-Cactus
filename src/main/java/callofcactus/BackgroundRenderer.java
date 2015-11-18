package callofcactus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Teun
 */
public class BackgroundRenderer {

	private Texture texture;

	/**
	 * makes a new instance of the class BackgroundRenderer with a default background
	 */
	public BackgroundRenderer() {
		texture = new Texture("background.png");
	}

	/**
	 * makes a new instance of the class BackgroundRenderer
	 *
	 * @param textureName : The texture of the background
	 */
	public BackgroundRenderer(String textureName) {
		texture = new Texture(textureName);
	}

	/**
	 * @param spriteBatch : The sprites to render
	 */
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		spriteBatch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
	}

}
