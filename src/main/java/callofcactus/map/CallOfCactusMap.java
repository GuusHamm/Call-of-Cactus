package callofcactus.map;

import callofcactus.IGame;

public abstract class CallOfCactusMap {

    private IGame game;

    public CallOfCactusMap(IGame game) {
        this.game = game;
    }

    public abstract void init();

    protected IGame getGame() {
        return game;
    }
}
