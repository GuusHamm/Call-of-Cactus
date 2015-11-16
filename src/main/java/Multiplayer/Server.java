package Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class SocketServer {

	public SocketServer() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				//        ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 means no timeout.  Probably not the greatest idea in production!
				//          serverSocketHint.acceptTimeout = 100;

				// Create the socket server using TCP protocol and listening on 9021
				// Only one app can listen to a port at a time, keep in mind many ports are reserved
				// especially in the lower numbers ( like 21, 80, etc )
//            ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);

				// Loop forever
//            while (true) {
//                // Create a socket
//                Socket socket = serverSocket.accept();
//
//                // Read data from the socket into a BufferedReader
//                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                try {
//                    // Read to the next newline (\n) and display that text on labelMessage
//                    System.out.println(buffer.readLine());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
				//Socket sock = serverSocket.accept(new SocketHints());

//            while(true)
//            {
//                //Reading the message from the client
//                InputStream is = sock.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String number = "aaaa";
//                try {
//                    System.out.println("f");
//                    System.out.println(br.readLine());
//                    System.out.println("k");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Message received from client is "+number);
//
//            }

				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 means no timeout.  Probably not the greatest idea in production!
				serverSocketHint.acceptTimeout = 10000;

				// Create the socket server using TCP protocol and listening on 9021
				// Only one app can listen to a port at a time, keep in mind many ports are reserved
				// especially in the lower numbers ( like 21, 80, etc )
				ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);
				Socket socket = serverSocket.accept(null);

				// Loop forever
				while (true) {
					// Create a socket

					// Read data from the socket into a BufferedReader
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					try {
						// Read to the next newline (\n) and display that text on labelMessage
						System.out.println(buffer.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); // And, start the thread running
	}

	public static void main(String args[]) {
        SocketServer s = new SocketServer();
	}
}

/**
 * Created by Wouter on 16-11-2015.
 */
public class RMIServer {

	// Set port number
	private static final int portNumber = 8008;

	// Set binding name for effectenbeurs
	private static final String bindingName = "game";

	// References to registry and effectenbeurs
	private Registry registry = null;
	private Game game = null;

	// Constructor
	public RMIServer() {

		// Print port number for registry
		System.out.println("Server: Port number " + portNumber);

		// Create effectenbeurs
		try {
			game = new Game();

			System.out.println("Server: Game created");

		} catch (RemoteException ex) {
			System.out.println("Server: Cannot create Game");
			System.out.println("Server: RemoteException: " + ex.getMessage());
			game = null;
		}

		// Create registry at port number
		try {
			registry = LocateRegistry.createRegistry(portNumber);
			System.out.println("Server: Registry created on port number " + portNumber);
		} catch (RemoteException ex) {
			System.out.println("Server: Cannot create registry");
			System.out.println("Server: RemoteException: " + ex.getMessage());
			registry = null;
		}

		// Bind effectenbeurs using registry
		try {
			registry.rebind(bindingName, game);
		} catch (RemoteException ex) {
			System.out.println("Server: Cannot bind student administration");
			System.out.println("Server: RemoteException: " + ex.getMessage());
		}
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
	//			effectenbeurs.fluctueer();
			}
		};
		t.scheduleAtFixedRate(tt,0,2000);


	}

	// Print IP addresses and network interfaces
	private static void printIPAddresses() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println("Server: IP Address: " + localhost.getHostAddress());
			// Just in case this host has multiple IP addresses....
			InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
			if (allMyIps != null && allMyIps.length > 1) {
				System.out.println("Server: Full list of IP addresses:");
				for (InetAddress allMyIp : allMyIps) {
					System.out.println("    " + allMyIp);
				}
			}
		} catch (UnknownHostException ex) {
			System.out.println("Server: Cannot get IP address of local host");
			System.out.println("Server: UnknownHostException: " + ex.getMessage());
		}

		try {
			System.out.println("Server: Full list of network interfaces:");
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					System.out.println("        " + enumIpAddr.nextElement().toString());
				}
			}
		} catch (SocketException ex) {
			System.out.println("Server: Cannot retrieve network interface list");
			System.out.println("Server: UnknownHostException: " + ex.getMessage());
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		// Welcome message
		System.out.println("SERVER USING REGISTRY");

		// Print IP addresses and network interfaces
		printIPAddresses();

		// Create server
		EffectenBeursServer server = new EffectenBeursServer();
	}
}
