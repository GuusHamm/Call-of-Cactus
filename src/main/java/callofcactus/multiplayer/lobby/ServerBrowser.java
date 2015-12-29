package callofcactus.multiplayer.lobby;

import callofcactus.Utils;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teun on 29-12-2015.
 */
public class ServerBrowser extends UnicastRemoteObject implements IServerBrowser {

    public static final int SERVERBROWSER_PORT = 8011;
    public static final String SERVERBROWSER_KEY = "SERVERBROWSER_CALLOFCACTUS";

    private List<ILobby> lobbies;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        ServerBrowser serverBrowser = new ServerBrowser();

        Registry registry = LocateRegistry.createRegistry(SERVERBROWSER_PORT);
        registry.bind(SERVERBROWSER_KEY, serverBrowser);
    }

    public ServerBrowser() throws RemoteException {
        lobbies = new ArrayList<>();
    }

    @Override
    public ILobby[] getLobbies() {
        return Utils.convertList(lobbies);
    }

    @Override
    public void removeLobby(String name) {
        ILobby lobby = lobbies.stream().filter(o -> {
            try {
                return o.getName().equals(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }).findFirst().get();
        lobbies.remove(lobby);
    }

    @Override
    public void addLobby(ILobby lobby) {
        lobbies.add(lobby);
    }
}
