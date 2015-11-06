package game.ai;

import com.badlogic.gdx.ai.pfa.Connection;

/**
 * Created by Teun on 6-11-2015.
 */
public class AIConnection<N> implements Connection<N> {

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public N getFromNode() {
        return null;
    }

    @Override
    public N getToNode() {
        return null;
    }
}
