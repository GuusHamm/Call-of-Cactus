package callofcactus.multiplayer.serverbrowser;

import callofcactus.multiplayer.lobby.Lobby;

import java.util.List;

/**
 * Created by Teun on 4-1-2016.
 */
public interface OnRoomsRetrievedListener {

    void onRoomsRetrieved(List<BrowserRoom> browserRooms);

}
