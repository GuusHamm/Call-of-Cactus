package Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class Server {

	public Server() {

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
		Server s = new Server();
	}
}
