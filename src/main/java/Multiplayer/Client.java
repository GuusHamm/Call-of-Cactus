package Multiplayer;

import com.badlogic.gdx.Game;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.IOException;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
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





	private Timer pollingTimer;

	// Set binding name for effectenbeurs
	private static final String bindingName = "game";

	// References to registry and effectenbeurs
	private Registry registry = null;
	private Game game = null;

//	public void startClient(String ipAddress, int portNumber) {
	public void startClient() {

		//TODO get server IP
		String ipAddress = "127.0.0.1";
		int portNumber = 8008;

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

		// Bind student administration using registry
		if (registry != null) {
			try {
				game = (Game) registry.lookup(bindingName);
			} catch (RemoteException ex) {
				System.out.println("Client: Cannot bind Game");
				System.out.println("Client: RemoteException: " + ex.getMessage());
				game = null;
			} catch (NotBoundException ex) {
				System.out.println("Client: Cannot bind Game");
				System.out.println("Client: NotBoundException: " + ex.getMessage());
				game = null;
			}
		}

		// Print result binding student administration
		if (game != null) {
			System.out.println("Client: Game bound");
		} else {
			System.out.println("Client: Game is null pointer");
		}


	}

    /*
    *  We are using port 8008 for all network communication within our application
    */

    Socket socket ;
    SocketHints socketHints;

    public Client() {

        socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 4000;
        socketHints.keepAlive=true;

        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 9021
//        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 9021, socketHints);

    }
    public void  sendMessage(String text){

        // When the button is clicked, get the message text or create a default string value
        String textToSend = new String();
            textToSend = text + ("\n"); // Brute for a newline so readline gets a line

        SocketHints socketHints = new SocketHints();
        // Socket will time our in 4 seconds
        socketHints.connectTimeout = 1000;
        //create the socket and connect to the server entered in the text box ( x.x.x.x format ) on port 8008
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "127.0.0.1", 8008, socketHints);
        try {
            // write our entered message to the stream
            socket.getOutputStream().write(textToSend.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void test()
    {
        System.out.println("wow much network");

        for(int i =0; i<10;i++)
        {
            sendMessage("kkkk");
            System.out.println("kkkk sent");
        }
    }
}
