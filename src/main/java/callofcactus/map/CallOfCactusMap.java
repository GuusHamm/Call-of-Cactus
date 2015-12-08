package callofcactus.map;

import callofcactus.Administration;
import callofcactus.IGame;

public abstract class CallOfCactusMap {

    private IGame game;
    private Administration administration;

    public CallOfCactusMap(IGame game) {
        this.game = game;
    }

    public abstract void init();

    protected IGame getGame() {
        return game;
    }
}
