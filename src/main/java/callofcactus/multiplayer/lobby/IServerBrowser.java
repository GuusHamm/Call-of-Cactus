package callofcactus.multiplayer.lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Teun on 29-12-2015.
 */
public interface IServerBrowser extends Remote {

    ILobby[] getLobbies() throws RemoteException;

    boolean removeLobby(String name) throws RemoteException;

    void addLobby(ILobby lobby) throws RemoteException;

}
