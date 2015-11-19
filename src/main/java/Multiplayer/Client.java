package Multiplayer;


import callofcactus.IGame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

/**
 * Created by woute on 9-11-2015.
 */
public class Client {

	//socket region
//	Socket socket;
//	SocketHints socketHints;
//
//	public Client() {
//
//		socketHints = new SocketHints();
//		// Socket will time our in 4 seconds
//		socketHints.connectTimeout = 4000;
//		socketHints.keepAlive = true;
//
//		//create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
//		Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 9021, socketHints);
//
//	}

//	public static void main(String args[]) {
//		Client c = new Client();
//		for (int i = 0; i < 10; i++) {
//			c.sendMessage("kkk");
//		}
//	}
//
//	public void sendMessage(String text) {
//
//		String textToSend = new String();
//		if (!socket.isConnected()) {
//			socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 9021, socketHints);
//		}
//		textToSend = text + "\n";
//
//		SocketHints socketHints = new SocketHints();
//
//		try {
//			// write our entered message to the stream
//			//socket.getOutputStream().write(textToSend.getBytes());
//
//			PrintStream ps = new PrintStream(socket.getOutputStream());
//			ps.println(textToSend);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}





	// Set binding name for effectenbeurs
	private static final String bindingName = "game";
	private Timer pollingTimer;
	// References to registry and effectenbeurs
	private Registry registry = null;
	private IGame game = null;

//	public void startClient(String ipAddress, int portNumber) {
public IGame startClient() {

		//TODO get server IP
		String ipAddress = "127.0.0.1";
	int portNumber = 8008; // LOL!

		// Print IP address and port number for registry
		System.out.println("Client: IP Address: " + ipAddress);
		System.out.println("Client: Port number " + portNumber);

		// Locate registry at IP address and port number
		try {
			registry = LocateRegistry.getRegistry(ipAddress, portNumber);
		} catch (RemoteException ex) {
			System.out.println("Client: Cannot locate registry");
			System.out.println("Client: RemoteException: " + ex.getMessage());
			registry = null;
		}

		// Print result locating registry
		if (registry != null) {
			System.out.println("Client: Registry located");
		} else {
			System.out.println("Client: Cannot locate registry");
			System.out.println("Client: Registry is null pointer");
		}

	// Bind game using registry
		if (registry != null) {
//			try {
//
//
//				//this motherfucker spoils it for all the rest
////				game = (IGame) registry.lookup(bindingName);
//
//
////			} catch (RemoteException ex) {
////				System.out.println("Client: Cannot bind Game");
////				System.out.println("Client: RemoteException: " + ex.getMessage());
////			} catch (NotBoundException ex) {
////				System.out.println("Client: Cannot bind Game");
////				System.out.println("Client: NotBoundException: " + ex.getMessage());
////			}
		}

	return (IGame) game;
	}
}
