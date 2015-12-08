package callofcactus.multiplayer.lobby;

import callofcactus.account.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Teun on 7-12-2015.
 */
public interface ILobby extends Remote {

    String getName() throws RemoteException;

    Account getHost() throws RemoteException;

    List<Account> getPlayers() throws RemoteException;

    boolean join(Account player, ILobbyListener lobbyListener) throws RemoteException;

    boolean leave(Account player, ILobbyListener lobbyListener) throws RemoteException;

    void start() throws RemoteException;

}
