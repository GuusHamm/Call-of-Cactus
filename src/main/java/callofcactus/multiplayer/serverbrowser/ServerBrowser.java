package callofcactus.multiplayer.serverbrowser;

import callofcactus.io.DatabaseManager;
import com.badlogic.gdx.Gdx;

/**
 * Created by Teun on 4-1-2016.
 */
public class ServerBrowser {

    private DatabaseManager databaseManager;

    public void retrieveRooms(OnRoomsRetrievedListener listener) {
        Gdx.app.postRunnable(() -> {
            if (databaseManager == null)
                databaseManager = new DatabaseManager();

            if (!databaseManager.isConnectionOpen())
                databaseManager.openConnection();

            listener.onRoomsRetrieved(databaseManager.getRooms());
            databaseManager.closeConnection();
        });
    }

}
