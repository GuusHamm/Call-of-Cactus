package Multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Wouter Vanmulken on 9-11-2015.
 */
public class ServerSocket {

	public ServerSocket() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				ServerSocketHints serverSocketHint = new ServerSocketHints();

				serverSocketHint.acceptTimeout = 10000;

				// Create the socket server using TCP protocol and listening on 8008
				// Only one app can listen to a port at a time, keep in mind many ports are reserved
				// especially in the lower numbers ( like 21, 80, etc )

				// Create a socket
				com.badlogic.gdx.net.ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 8008, serverSocketHint);
				Socket socket = serverSocket.accept(null);

				// Loop forever
				while (true) {

					// Read data from the socket into a BufferedReader
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					try {

						if(buffer.readLine()!=null)System.out.println(buffer.readLine());
                        else System.out.print("-");

                    } catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); // And, start the thread running
	}

	public static void main(String args[]) {
        ServerSocket s = new ServerSocket();
	}
}
