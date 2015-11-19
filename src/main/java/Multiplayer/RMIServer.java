package Multiplayer;

import callofcactus.IGame;
import callofcactus.MultiPlayerGame;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

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
	private IGame game = null;

	// Constructor
	public RMIServer() {

		// Print port number for registry
		System.out.println("Server: Port number " + portNumber);

		// Create Game
		try {
			game = new MultiPlayerGame();

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

		// Bind game using registry
		try {
			registry.rebind(bindingName, (Remote) game);

		} catch (RemoteException ex) {
			System.out.println("Server: Cannot bind Game");
			System.out.println("Server: RemoteException: " + ex.getMessage());
		}
		Timer t = new Timer();

		TimerTask tt1 = new TimerTask() {
			@Override
			public void run() {
				game.compareHit();
			}
		};
		TimerTask tt2 = new TimerTask() {
			@Override
			public void run() {
				game.createPickup();
			}
		};
		t.scheduleAtFixedRate(tt1, 0, 10);
		t.scheduleAtFixedRate(tt2, 0, 5000);

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
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
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
		RMIServer server = new RMIServer();
	}
}
