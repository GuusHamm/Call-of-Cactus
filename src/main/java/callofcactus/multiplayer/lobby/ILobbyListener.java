package callofcactus.multiplayer.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Teun on 7-12-2015.
 */
public interface ILobbyListener extends Remote {

    void onStart(String hostip) throws RemoteException;

}
