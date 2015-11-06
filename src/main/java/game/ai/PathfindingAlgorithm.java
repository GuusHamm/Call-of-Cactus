package game.ai;

import com.badlogic.gdx.math.Vector2;
import game.Entity;
import game.Game;

/**
 * Created by Teun on 6-11-2015.
 */
public class PathfindingAlgorithm {

    private Game game;
    private AICharacter ai;
    private Entity target;

    public PathfindingAlgorithm(Game game, AICharacter ai, Entity target) {
        this.game = game;
        this.ai = ai;
        this.target = target;
    }

    public Vector2 getNextLocation() {

        return null;
    }
}
