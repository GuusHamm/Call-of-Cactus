package multiplayer;


import callofcactus.IGame;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

/**
 * Created by woute on 9-11-2015.
 */
public class ClientRMI implements Remote{


	// Set binding name for effectenbeurs
	private static final String bindingName = "game";
	private Timer pollingTimer;
	// References to registry and effectenbeurs
	private Registry registry = null;
	private IGame game = null;

//	public void startClient(String ipAddress, int portNumber) {
public IGame startClient() throws RemoteException{

		//TODO get server IP
	String ipAddress = "127.0.0.1";
	int portNumber = 8008; // LOL!

    //UnicastRemoteObject.exportObject(this,1100);
//    try {
//        game =(IGame) LocateRegistry.getRegistry("127.0.0.1",8008).lookup(bindingName);
//
//
//
//    } catch (NotBoundException e) {
//        e.printStackTrace();
//    }
//
//		// Print IP address and port number for registry
		System.out.println("ClientRMI: IP Address: " + ipAddress);
		System.out.println("ClientRMI: Port number " + portNumber);

		// Locate registry at IP address and port number
		try {
			registry = LocateRegistry.getRegistry(ipAddress, portNumber);
		} catch (RemoteException ex) {
			System.out.println("ClientRMI: Cannot locate registry");
			System.out.println("ClientRMI: RemoteException: " + ex.getMessage());
			registry = null;
		}

		// Print result locating registry
		if (registry != null) {
			System.out.println("ClientRMI: Registry located");
		} else {
			System.out.println("ClientRMI: Cannot locate registry");
			System.out.println("ClientRMI: Registry is null pointer");
		}

	// Bind game using registry
		if (registry != null) {
			try {


				//this motherfucker spoils it for all the rest
				game = (IGame) registry.lookup(bindingName);


			} catch (RemoteException ex) {
				System.out.println("ClientRMI: Cannot bind Game");
				System.out.println("ClientRMI: RemoteException: " + ex.getMessage());
			} catch (NotBoundException ex) {
				System.out.println("ClientRMI: Cannot bind Game");
				System.out.println("ClientRMI: NotBoundException: " + ex.getMessage());
			}
		}
    if(game!=null){
        System.out.println("thefuck");
    }

	return game;
	}
}
