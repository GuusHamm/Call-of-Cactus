package callofcactus.multiplayer.lobby;

import callofcactus.MultiPlayerGame;
import callofcactus.account.Account;
import callofcactus.multiplayer.ServerS;

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
    private List<String> ipaddresses;
    private List<ILobbyListener> listeners;

    public Lobby(Account host) throws RemoteException {
        this.name = host.getUsername() + "'s lobby";
        this.host = host;
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.ipaddresses = new ArrayList<>();
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
    public boolean join(Account player, ILobbyListener lobbyListener, String ip) throws RemoteException {
        listeners.add(lobbyListener);
        ipaddresses.add(ip);
        return players.add(player);
    }

    @Override
    public boolean leave(Account player, ILobbyListener lobbyListener) {
        listeners.remove(lobbyListener);
        ipaddresses.remove(player);
        return players.remove(player);
    }

    @Override
    public void start() {
        ServerS servers = new ServerS(new MultiPlayerGame(), ipaddresses);

        listeners.stream().forEach((lobbyListener) -> {
            try {
                lobbyListener.onStart();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }
}
