package callofcactus.multiplayer.lobby;

import callofcactus.GameInitializer;
import callofcactus.MultiPlayerGame;
import callofcactus.account.Account;
import callofcactus.io.IPReader;
import callofcactus.menu.WaitingRoom;
import callofcactus.multiplayer.ServerS;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public void start(GameInitializer initializer) {
        MultiPlayerGame game = initializer.createNewMultiplayerGame();
        ServerS servers = new ServerS(game, ipaddresses);

        String ip = "";

        if (WaitingRoom.useLocalNetwork) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }else{
            ip = new IPReader().readIP().getIp();
        }
        final String lIp = ip;
        listeners.stream().forEach((lobbyListener) -> {
            try {
                lobbyListener.onStart(lIp);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

}
