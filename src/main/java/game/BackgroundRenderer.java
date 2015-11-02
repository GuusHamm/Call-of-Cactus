package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Teun
 */
public class BackgroundRenderer {

	private Texture texture;

	public BackgroundRenderer() {
		texture = new Texture("background.png");
	}

	public BackgroundRenderer(String textureName) {
		texture = new Texture(textureName);
	}

	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		spriteBatch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
	}

}
