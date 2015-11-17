package game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by guushamm on 16-11-15.
 */
public class MultiPlayerGame extends Game  {

    public MultiPlayerGame() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 8008);
    }
}
