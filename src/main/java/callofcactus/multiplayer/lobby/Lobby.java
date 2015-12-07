package callofcactus.multiplayer.lobby;

import callofcactus.account.Account;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teun on 7-12-2015.
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    public static final int PORT = 8010;
    public static final String LOBBY_KEY = "LOBBYKEY";

    private String name;
    private Account host;
    private List<Account> players;

    public Lobby(Account host) throws RemoteException {
        this.name = host.getUsername() + "'s lobby";
        this.host = host;
        players = new ArrayList<>();
    }

    @Override
    public Account getHost() {
        return host;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Account> getPlayers() {
        return players;
    }

    @Override
    public boolean join(Account player) {
        return players.add(player);
    }

    @Override
    public boolean leave(Account player) {
        return players.remove(player);
    }
}
